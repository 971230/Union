package com.ztesoft.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderBatchImport;
import com.ztesoft.net.model.OrderBatchLogiImport;
import com.ztesoft.net.service.IOrderBatchImportManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.util.CacheUtils;

import consts.ConstsCore;
import util.MoneyAuditExcelUtil;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.params.orderctn.resp.OrderCtnResp;

public class OrderBatchImportManager extends BaseSupport implements IOrderBatchImportManager {
	@Resource
	private IOrderExtManager orderExtManager;
	
	@Resource
	private IWorkCustomEngine workCustomEngine;
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	
	private static Logger logger = Logger.getLogger(OrderBatchImportManager.class);
	
	@Override
	public Map<String, String> saveBatchImportOrder(File file, String fileName) {
		//返回数据 默认处理成功
		Map<String, String> result = new HashMap<String, String>();
		result.put("result_code", "1");
		result.put("result_message", "导入成功！");
		//获取excel表中的数据
		List<Map<String, String>> orderDatas = this.getImportExcelOrderData(file, fileName);
		//excel表中的数据入库
		try {
			this.saveBatchImportOrder(orderDatas);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("excel表数据批量入库失败==》"+orderDatas);
			result.put("result_code", "-1");
			result.put("result_message", "数据库异常，excel表数据入库失败！");
			return result;
		}
		//对excel表中的数据进行标准化
		if(null != orderDatas && orderDatas.size() > 0){
			for(int i=0; i<orderDatas.size(); i++){
				Map<String, String> orderData = orderDatas.get(i);
				//需要修改的字段及条件
				Map<String, String> fields = new HashMap<String, String>();
				Map<String, String> where = new HashMap<String, String>();
				where.put("import_id", orderData.get("import_id"));
				//对导入数据进行翻译
				String resultMsg = this.translateOrderData(orderData);
				//数据正确则对订单进行归集
				if(StringUtil.isEmpty(resultMsg)){
					orderData.put("source_from", orderData.get("order_from"));
					orderData.put("source_system", "10068");//批量导入
					OrderCtnResp resp = orderExtManager.saveManualOrder(orderData,"D");
					fields.put("out_tid", resp.getBase_order_id());
					if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){//标准化失败
						//返回结果
						result.put("result_code", "0");
						result.put("result_message", "存在未导入成功的订单，请返回列表查看原因！");
					}else{//标准化成功
						fields.put("import_status", "1");
					}
					fields.put("fail_reason", resp.getError_msg());//原因
				}else{
					fields.put("fail_reason", resultMsg);
					//返回结果
					result.put("result_code", "0");
					result.put("result_message", "存在未导入成功的订单，请返回列表查看原因！");
				}				
				this.baseDaoSupport.update("ES_ORDER_BATCH_IMPORT", fields, where);
			}
		}
		return result;
	}
	/**
	 * 对excel表中的数据入库处理
	 * @param orderDatas
	 */
	public void saveBatchImportOrder(List<Map<String, String>> orderDatas){
		String sql = "INSERT INTO es_order_batch_import(create_time,acc_nbr, cust_name,special_busi_type,"
				+ "is_realname,order_city_code,certi_type,certi_num,source_from,is_old,offer_eff_type,pay_method,"
				+ "prod_offer_type,prod_offer_code,prod_offer_name,pay_money,contract_month,ship_name,ship_addr,"
				+ "ship_phone,ess_money,logi_no,invoice_no,terminal_num,buyer_message,service_remarks,import_status,"
				+ "import_id,order_from) VALUES (sysdate,:acc_nbr, :cust_name,:special_busi_type,"
				+ ":is_realname,:order_city_code,:certi_type,:certi_num,:source_from,:is_old,:offer_eff_type,:pay_method,"
				+ ":prod_offer_type,:prod_offer_code,:prod_offer_name,:pay_money,:contract_month,:ship_name,:ship_addr,"
				+ ":ship_phone,:ess_money,:logi_no,:invoice_no,:terminal_num,:buyer_message,:service_remarks,:import_status,"
				+ ":import_id,:order_from)";
		for(Map<String, String> orderData : orderDatas){
			orderData.put("import_id", SequenceTools.getShort18PrimaryKey());
			orderData.put("import_status", "0");
			orderData.put("source_from", ManagerUtils.getSourceFrom());
		}
		this.baseDaoSupport.batchExecuteByListMap(sql, orderDatas);
	}
	/**
	 * 对导入的数据进行翻译
	 * @param orderData
	 * @return
	 */
	private String translateOrderData(Map<String, String> orderData){
		//是否实名
		String is_realname = orderData.get("is_realname");
		if("是".equals(is_realname)){
			orderData.put("is_realname", "1");
		}else if("否".equals(is_realname)){
			orderData.put("is_realname", "0");
		}else{
			return "是否实名-->未按协议取值";
		}
		//地市
		String order_city_code = orderData.get("order_city_code");
		order_city_code = this.getFieldValue("order_city_code", order_city_code);
		if(StringUtil.isEmpty(order_city_code)){
			return "地市-->未按协议取值";
		}else{
			orderData.put("order_city_code", order_city_code);
		}
		//证件类型
		String certi_type = orderData.get("certi_type");
		certi_type = this.getFieldValue("certi_type", certi_type);
		if(StringUtil.isEmpty(certi_type)){
			return "证件类型-->未按协议取值";
		}else{
			orderData.put("certi_type", certi_type);
		}
		//订单来源
		String order_from = orderData.get("order_from");
		order_from = this.getFieldValue("source_from", order_from);
		if(StringUtil.isEmpty(order_from)){
			return "订单来源-->未按协议取值";
		}else{
			orderData.put("order_from", order_from);
		}
		//新老用户
		String is_old = orderData.get("is_old");
		if("新用户".equals(is_old)){
			orderData.put("is_old", "1");
		}else if("老用户".equals(is_old)){
			orderData.put("is_old", "0");
		}else{
			return "新老用户-->未按协议取值";
		}
		//首月
		String offer_eff_type = orderData.get("offer_eff_type");
		offer_eff_type = this.getFieldValue("offer_eff_type", offer_eff_type);
		if(StringUtil.isEmpty(offer_eff_type)){
			return "订单来源-->未按协议取值";
		}else{
			orderData.put("offer_eff_type", offer_eff_type);
		}
		//支付方式
		String payment_type = orderData.get("pay_method");
		payment_type = this.getFieldValue("payment_type", payment_type);
		if(StringUtil.isEmpty(payment_type)){
			return "支付方式-->未按协议取值";
		}else{
			orderData.put("pay_method", payment_type);
		}
		
		return "";
	}
	
	/**
	 * 获取excel表中的数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	private List<Map<String, String>> getImportExcelOrderData(File file, String fileName){
		//excel 表中的字段名称对应接口的字段名称  key 为接口字段名 value 为excel表对应的字段名
		Map<String,String> fieldMap=new HashMap<String, String>();
 		fieldMap.put("acc_nbr","用户号码");
 		fieldMap.put("cust_name","用户姓名");
 		fieldMap.put("special_busi_type","业务类型" );
 		fieldMap.put("is_realname","是否实名" );
 		fieldMap.put("order_city_code","地市" );
 		fieldMap.put("certi_type","证件类型" );
 		fieldMap.put("certi_num","证件号码" );
 		fieldMap.put("order_from","订单来源" );
 		fieldMap.put("is_old","新老用户" );
 		fieldMap.put("offer_eff_type","首月");
 		fieldMap.put("pay_method","支付方式");
 		fieldMap.put("prod_offer_type","商品类型");
 		fieldMap.put("prod_offer_code","商品编码" );
 		fieldMap.put("prod_offer_name","商品名称" );
 		fieldMap.put( "pay_money","商城实收");
 		fieldMap.put("contract_month","合约期");
 		fieldMap.put("ship_name","收货人姓名" );
 		fieldMap.put("ship_addr","收货人地址" );
 		fieldMap.put("ship_phone","收货人电话" );
 		fieldMap.put("ess_money","营业款(不能填无)" );
 		fieldMap.put("logi_no","物流单号" );
 		fieldMap.put("invoice_no","发票号码(不能填无)" );
 		fieldMap.put("terminal_num","手机串号" );
 		fieldMap.put("buyer_message","买家留言");
 		fieldMap.put("service_remarks","订单备注");
 		//从excel表中获取数据并转换为需要入库的对象
 		List<Map<String,String>> list= MoneyAuditExcelUtil.getBatchMap(file, fieldMap, null,fileName,1);
		return list;
	}
	
	@Override
	public String getFieldValue(String fieldName, String fieldDesc) {
		String cacheed = CacheUtils.getCache(CacheUtils.MALL_DATA_CONFIG+"MALLDATA");
		if(!"yes".equals(cacheed)){
			refreshPkConfig();
		}
		String value = CacheUtils.getCache(CacheUtils.MALL_DATA_CONFIG+fieldName+":"+fieldDesc);
		if(!StringUtil.isEmpty(value))
			return value;
		else{//缓存失效时使用直接查库表的方式
			try {
				String sql = "select * from es_mall_config where field_name = ? and field_desc = ? and source_from is not null";
				List<Map<String, String>> l = baseDaoSupport.queryForList(sql, fieldName,fieldDesc);
				if (null != l && l.size() > 0) {
					value = l.get(0).get("field_value");
				}
			} catch (Exception e) {
				value = "";
				logger.info("check " + fieldName + " error");
				logger.info(e.getMessage(), e);
			}
			return value;
		}
	}
	
	//刷新es_mall_config表数据到缓存，key(field_name:field_desc) value(field_value)
	private void refreshPkConfig(){
		String sql = "select * from es_mall_config where 1=1 ";
		List<Map> keyList = baseDaoSupport.queryForList(sql);
		if(keyList!=null){
			for(Map m:keyList){
				String fieldDecs = (String) m.get("field_desc");
				String fieldValue = (String) m.get("field_value");
				String fieldName = (String) m.get("field_name");
				CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+fieldName+":"+fieldDecs,fieldValue);
			}
			CacheUtils.addCache(CacheUtils.MALL_DATA_CONFIG+"MALLDATA","yes");
		}
	}
	@Override
	public Page queryBatchImportList(OrderBatchImport params, int pageNo, int pageSize) {
		StringBuilder sql = new StringBuilder("SELECT a.* FROM es_order_batch_import a WHERE 1=1");
		List<String> sqlParams = new ArrayList<String>();
		if(!StringUtil.isEmpty(params.getStart_time())){//导入时间
			sql.append(" AND a.create_time>="+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(params.getStart_time());
		}
		if(!StringUtil.isEmpty(params.getEnd_time())){
			sql.append(" AND a.create_time<="+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(params.getEnd_time());
		}
		if(!StringUtil.isEmpty(params.getAcc_nbr())){//用户号码
			sql.append(" AND a.acc_nbr=?");
			sqlParams.add(params.getAcc_nbr());
		}
		if(!StringUtil.isEmpty(params.getCust_name())){//用户姓名
			sql.append(" AND a.cust_name=?");
			sqlParams.add(params.getCust_name());
		}
		if(!StringUtil.isEmpty(params.getCerti_num())){//证件号码
			sql.append(" AND a.certi_num=?");
			sqlParams.add(params.getCerti_num());
		}
		if(!StringUtil.isEmpty(params.getOrder_city_code())){//地市
			sql.append(" AND a.order_city_code=?");
			sqlParams.add(params.getOrder_city_code());
		}
		if(!StringUtil.isEmpty(params.getProd_offer_name())){//商品名称
			sql.append(" AND a.prod_offer_name like ?");
			sqlParams.add("%"+params.getProd_offer_name()+"%");
		}
		sql.append(" ORDER BY a.import_status");
		Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, sqlParams.toArray());
		return page;
	}
	@Override
	public OrderBatchImport getOrderBatchImport(String importId) {
		String sql = "SELECT * FROM es_order_batch_import WHERE import_id=?";
		OrderBatchImport orderBatchImport = (OrderBatchImport) this.baseDaoSupport.queryForObject(sql, OrderBatchImport.class, importId);
		return orderBatchImport;
	}
	@Override
	public OrderCtnResp importOrder(OrderBatchImport orderBatchImport) {
		Map<String, String> orderData = new HashMap<String, String>();
		OrderCtnResp orderCtnResp = new OrderCtnResp();
		try {
			BeanUtils.bean2Map(orderData, orderBatchImport);
			//去除数据库不存在的字段
			orderData.remove("start_time");
			orderData.remove("end_time");
			//除去不需要修改的字段
			orderData.remove("create_time");
		} catch (Exception e) {
			e.printStackTrace();
			orderCtnResp.setError_code(ConstsCore.ERROR_FAIL);
			orderCtnResp.setError_msg("对象转换失败！");
			return orderCtnResp;
		}
		//对订单数据进行备份方便入库管理
		Map<String, String> fields = new HashMap<String, String>();
		fields.putAll(orderData);
		Map<String, String> where = new HashMap<String, String>();
		where.put("import_id", orderData.get("import_id"));
		//对数据进行翻译和校验
		String resultMessage = this.translateOrderData(orderData);
		if(!StringUtil.isEmpty(resultMessage)){//预校验失败
			//更新数据状态
			fields.put("import_status", "0");
			fields.put("fail_reason", resultMessage);
			orderCtnResp.setError_code(ConstsCore.ERROR_FAIL);
			orderCtnResp.setError_msg(resultMessage);
		}else{//预校验成功
			orderData.put("source_from", orderData.get("order_from"));//订单来源
			orderData.put("source_system", "10068");//批量导入
			orderCtnResp = orderExtManager.saveManualOrder(orderData,"D");		
			fields.put("out_tid", orderCtnResp.getBase_order_id());
			if(ConstsCore.ERROR_FAIL.equals(orderCtnResp.getError_code())){//标准化失败
				fields.put("import_status", "0");//标准化状态
			}else{//标准化成功
				fields.put("import_status", "1");
			}
			fields.put("fail_reason", orderCtnResp.getError_msg());		
		}
		try {
			this.updateImportOrder(fields, where);
		} catch (Exception e) {
			orderCtnResp.setError_msg("订单导入成功，修改数据失败！");
		}
		return orderCtnResp;
	}
	
	public void updateImportOrder(Map<String, String> fields, Map<String, String> where){
		this.baseDaoSupport.update("ES_ORDER_BATCH_IMPORT", fields, where);
	}
	@Override
	public Page queryBatchLogiList(OrderBatchLogiImport orderBatchLogiImport, int pageNo, int pageSize) {
		StringBuilder sql = new StringBuilder(" select lo.batch_id, lo.order_id, lo.op_id, lo.org_id, lo.logi_no, lo.create_time, lo.cause_failure, lo.remark, co.name as logi_company, lo.state from es_delivery_import_log lo left join es_logi_company co on co.id = lo.logi_company and co.is_start = '1' where lo.source_from = 'ECS' ");
		List<String> sqlParams = new ArrayList<String>();
		
		// 订单号 
		if(!StringUtil.isEmpty(orderBatchLogiImport.getOrder_id())) {
			sql.append(" and lo.order_id=?");
			sqlParams.add(orderBatchLogiImport.getOrder_id());
		}
		
		// 开始时间 
		if(!StringUtil.isEmpty(orderBatchLogiImport.getStart_time())){
			sql.append(" and lo.create_time >= "+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(orderBatchLogiImport.getStart_time());
		}
		// 结束时间 
		if(!StringUtil.isEmpty(orderBatchLogiImport.getEnd_time())){
			sql.append(" and lo.create_time <= "+DBTUtil.to_sql_date("?", 2));
			sqlParams.add(orderBatchLogiImport.getEnd_time());
		} 
		// 订单号
		if(!StringUtil.isEmpty(orderBatchLogiImport.getOrder_id())) {
			sql.append(" and lo.order_id=?");
			sqlParams.add(orderBatchLogiImport.getOrder_id());
		}
		// 批次号
		if(!StringUtil.isEmpty(orderBatchLogiImport.getBatch_id())) {
			sql.append(" and lo.batch_id=?");
			sqlParams.add(orderBatchLogiImport.getBatch_id());
		}
		// 成功或者是失败 
		if(!StringUtil.isEmpty(orderBatchLogiImport.getState())) {
			sql.append(" and lo.state=?");
			sqlParams.add(orderBatchLogiImport.getState());
		}
		sql.append(" order by lo.create_time desc");
		
		logger.info("文件批量导入信息查询SQL："+sql.toString());
		Page page = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, sqlParams.toArray());
		return page;
	}
	@Override
	public String saveBatchImportOrderLogi(File file, String fileName) throws Exception {
		String resultJson = "";

		// 转换数据
		List<Map<String, String>> listMap = this.getImportExcelOrderLogiData(file, fileName);
		try {
			// 插入书库库
			this.saveBatchImportOrderLogi(listMap);
		} catch (Exception e) {
			resultJson = "{result:1,message:'插入数据库失败！'}";
			e.printStackTrace();
		}
		
		if(!StringUtil.isEmpty(resultJson)) {
			return resultJson;
		}
		// 流转环节
		resultJson = countinueWorkFlow(listMap); 

		return resultJson;
	}
	

	/**
	 * Excel 表批量插入数据库
	 * @author GL
	 * @param listMap
	 */
	@SuppressWarnings("unchecked")
	private void saveBatchImportOrderLogi(List<Map<String, String>> listMap) {
		
		String sql =" insert into es_delivery_import_log "
				+ "(create_time,batch_id,order_id,op_id,org_id,logi_company, logi_no,remark,source_from,state)" + 
				"values "
				+ "(sysdate,:batch_id,:order_id,:op_id,:org_id,:logi_company,:logi_no,:remark,:source_from,:state)";

		try {
			// 生成批次号
			AdminUser user =  ManagerUtils.getAdminUser();
			StringBuffer batch_id = new StringBuffer("B");
			batch_id.append(DateUtil.getTime8());
			batch_id.append(user.getUserid());
			
			for (Map<String, String> map : listMap) {
				map.put("batch_id",batch_id.toString());
				map.put("op_id", user.getUserid());
				map.put("org_id", user.getOrg_id());
				map.put("create_time", DateUtil.getTime2());
				map.put("source_from", ManagerUtils.getSourceFrom());
				map.put("state", "0");
			}
			
			this.baseDaoSupport.batchExecuteByListMap(sql, listMap);
			
		} catch (FrameException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Excel 表数据转换Map
	 * @author GL
	 * @param file
	 * @param fileName
	 * @return
	 */
	private List<Map<String, String>> getImportExcelOrderLogiData(File file, String fileName) {
		Map<String,String> fieldMap = new HashMap<String, String>();
		fieldMap.put("order_id", "订单编号");
		fieldMap.put("logi_company", "物流公司编码");
		fieldMap.put("logi_no", "物流单号");
		fieldMap.put("remark", "备注");
		//从excel表中获取数据并转换为需要入库的对象
 		List<Map<String,String>> list= MoneyAuditExcelUtil.getBatchMap(file, fieldMap, null,fileName,1);
		return list;
	}
	
	public String countinueWorkFlow(List<Map<String, String>> listMap) throws Exception {

		String resultJson = null;
		// 一共多少条
		int count; 
		// 环节流转正确条数
		int correctCount=0; 
		// 环节流转错误条数
		int errorCount =0;
		// 订单编号
		String order_id;
		// 批次号 
		String batch_id;
		
		count= listMap.size();
		
			for (Map<String, String> map : listMap) {
				
				order_id = map.get("order_id");
				batch_id = map.get("batch_id");
				
				// 更新物流表的物流公司编码
				String [] str = new String[2];
			    str[0] = map.get("logi_company");
			    str[1] = map.get("logi_no");
				
			 // 修改字段
				Map<String, String> fields = new HashMap<String, String>();
				// 修改条件
				Map<String, String> where = new HashMap<String, String>();

				where.put("order_id", order_id);
				where.put("batch_id", batch_id);

				OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
				if (tree == null) {
					errorCount++;
					resultJson = "{result:1,message:'订单不存在'}";
					fields.put("cause_failure", "订单不存在");
					fields.put("state", "1");
				} else {
					// 根据订单编号查询自定义流程当前环节
					ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
					pojo.setOrder_id(order_id);
					pojo.setIs_curr_step("1");

					List<ES_WORK_CUSTOM_NODE_INS> inses = this.workCustomCfgManager.qryInsList(pojo, null);

					String curr_old_node_code = "";

					if (inses != null && inses.size() > 0
							&& org.apache.commons.lang.StringUtils.isNotBlank(inses.get(0).getOld_node_code())) {
						// 获取当前环节的旧环节编码
						curr_old_node_code = inses.get(0).getOld_node_code();
					}

					if (!"H".equals(curr_old_node_code)) {
						// 环节校验失败  请确认是否重复执行
						fields.put("cause_failure", "正在处理页面为" + curr_old_node_code + "，实际处理环节为H与实际不一致");
						fields.put("state", "1");
						errorCount++;
					}
					
					if(!"1".equals(fields.get("state"))) {
						// 继续执行流程
						WORK_CUSTOM_FLOW_DATA flowData = this.workCustomEngine.continueWorkFlow(order_id);
						// 执行失败的抛出异常
						if (ConstsCore.ERROR_FAIL.equals(flowData.getRun_result())) {
							errorCount++;
							fields.put("cause_failure", flowData.getRun_msg());
							fields.put("state", "1");
						}
					}
					if(!"1".equals(fields.get("state"))) {
						// 沉淀物流公司编码 物流单号
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
								new String[] { "shipping_company", "logi_no" }, str);
						fields.put("state", "0");
						correctCount++;
					}
				}
				this.baseDaoSupport.update("es_delivery_import_log", fields, where);
			}
			resultJson = "{result:0,message:'共:"+count+"个订单,失败:"+errorCount+",成功:"+correctCount+"'}";
		return resultJson;
	}
	
}
