/**
 * 
 */
package com.ztesoft.net.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;

import java.util.HashMap;

import org.springframework.jdbc.core.RowMapper;

















import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.OrderWarning;
import com.ztesoft.net.model.inf.InfCompareResultVO;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdWarningManager;
import com.ztesoft.net.sqls.SF;

import consts.ConstsCore;


/**
 * 订单预警配置
 * @author jun
 *
 */
public class OrdWarningManagerImpl extends BaseSupport implements IOrdWarningManager {

	/**
	 * 列表查询
	 */
	@Override
	public Page queryList(OrderWarning orderWarning, int pageNo, int pageSize) {
		String sql = SF.ecsordSql("SELECT_ES_ORDER_WARNING");
		if(orderWarning!=null){
			if(!StringUtil.isEmpty(orderWarning.getGroup_name())){
				   sql+=" and a.group_name like '%"+orderWarning.getGroup_name()+"%'";
			}
			
			if(!StringUtil.isEmpty(orderWarning.getOrder_origin())){
				   sql+=" and a.order_origin = '"+orderWarning.getOrder_origin()+"'";
			}
			if(!StringUtil.isEmpty(orderWarning.getOrder_from())){
				   sql+=" and a.order_from = '"+orderWarning.getOrder_from()+"'";
			}
			
		}
		Page pageInformation=this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return pageInformation;
	}

	/**
	 * 根据ID查询详情
	 * @param invoiceModeVO
	 */
	@Override
	public OrderWarning queryOrderWarnById(String warningId) {
		OrderWarning orderWarning=new OrderWarning();
		String sql  = SF.ecsordSql("SELECT_ES_ORDER_WARNING");
		sql=sql+" and warning_id=?  ";
		orderWarning=(OrderWarning) this.daoSupport.queryForObject(sql, OrderWarning.class, warningId);
			
		return orderWarning;
	}

	/**
	 * 更新
	 */
	@Override
	public void update(OrderWarning orderWarning) {
		this.daoSupport.update( "ES_ORDER_WARNING", orderWarning, " WARNING_ID='"+orderWarning.getWarning_id()+"'");
	}

	/**
	 *新增
	 */
	@Override
	public void save(OrderWarning orderWarning) {
		String warningId=this.daoSupport.getSequences("SEQ_ES_ORDER_WARNING");
		orderWarning.setWarning_id(warningId);
		this.daoSupport.insert("ES_ORDER_WARNING",orderWarning);
		
	}
	

	/**
	 * 
	 * 从老系统的库搬预警规则数据到现系统的库里
	 */
	@Override
	public void inserDataFromOldSys() {
		try {
		String querySql="select "
				+" a.group_id,a.group_name,a.order_status,a.belong_city,a.order_origin,product_type,a.memo "
				+",(select distinct b1.min_value from ES_ORDER_TIME_WARNING_RULE b1 where b1.group_id=a.group_id and b1.warning_color='01') yel_val "
				+",(select b1.min_value from ES_ORDER_TIME_WARNING_RULE b1 where b1.group_id=a.group_id and b1.warning_color='02') org_val "
				+",(select b1.min_value from ES_ORDER_TIME_WARNING_RULE b1 where b1.group_id=a.group_id and b1.warning_color='03') red_val " +
				",(select b1.max_value from ES_ORDER_TIME_WARNING_RULE b1 where b1.group_id=a.group_id and b1.warning_color='03') max_val"
				+"  from ES_ORDER_WARNING_RULE a";
		List<Map> list=this.baseDaoSupport.queryForList(querySql);
        logger.info("获取记录数"+list.size());
		
		String batchSql="insert into es_order_warning ( warning_id, group_name, flow_id, order_origin, order_from, product_type, memo, "
				+" warning_time_1, warning_time_2, warning_time_3, warning_time_4, create_user,  source_from )"
				+" values (seq_es_order_warning.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		List<String[]> sqlParam = new ArrayList<String[]>();
		if(list !=null  && !list.isEmpty()){
			for (Map m : list) {
				
				String flow_id=changNewflowTraceName(Const.getStrValue(m, "order_status"));//环节编码
				String order_from=changOrderFrom(Const.getStrValue(m, "order_origin"));//订单来源
				List<String > typeList=changProductType(Const.getStrValue(m, "product_type"));//商品类型
				String belong_city= changOrderOrigin(Const.getStrValue(m, "belong_city"));//归属地
				String time = DateUtil.getTime2();
			
				if(!StringUtil.isEmpty(flow_id)){//现系统没有的环节，不再插入
					for (String productType : typeList) {
						String[] infResult = new String[]
								{Const.getStrValue(m, "group_name"),flow_id
								  ,belong_city,order_from	,productType
								  ,Const.getStrValue(m, "memo"),Const.getStrValue(m, "yel_val"),Const.getStrValue(m, "org_val")	
								  ,Const.getStrValue(m, "red_val"),Const.getStrValue(m, "max_val"),"admin"
								  ,ManagerUtils.getSourceFrom()
								};
						sqlParam.add(infResult);
					}
				}
			}
			this.baseDaoSupport.batchExecute(batchSql, sqlParam);
		}
		logger.info("插入完毕");
		} catch (FrameException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * 旧系统环节ID转换成本系统的值  对应到6个环节的数据
	 * @param oldVal
	 * @return
	 */
	private String changNewflowTraceName(String oldVal){
		String reVal="";
		if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_A)){//订单预处理
			reVal="B";
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_B)){//客户回访
			
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_C)){//预拣货
			reVal=EcsOrderConsts.DIC_ORDER_NODE_C;
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_D)){//开户
			reVal=EcsOrderConsts.DIC_ORDER_NODE_D;
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_E)){//拣货出库
			
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_F)){//质检稽核
			reVal=EcsOrderConsts.DIC_ORDER_NODE_F;
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_H)){//发货
			reVal=EcsOrderConsts.DIC_ORDER_NODE_H;
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_J)){//回单
			reVal=EcsOrderConsts.DIC_ORDER_NODE_J;
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_K)){//售后回访
			//本系统无该环节
		}else if(oldVal.equals(EcsOrderConsts.DIC_ORDER_NODE_M)){//退款确认
			//本系统无该环节
		}
		return reVal;
	}
	/**
	 * 城市转换  --旧数据只有四种WO、ZB、PP、TB
	 * ZB特殊
	 * @param oldVal
	 * @return
	 */
	private String changOrderFrom(String oldVal){
		String reVal="";
		if(oldVal.equals("ZB")){//总部商城
			reVal="10003";
		}else if(oldVal.equals("PP")){//拍拍
			reVal="10005";
		}else if(oldVal.equals("WO")){//沃商城
			reVal="10036";
		}else if(oldVal.equals("TB")){//淘宝网厅(淘宝商城)
			reVal="10001";
		}
		return reVal;
	}
	/**
	 * 商品类型转换
	 * 
	 *、、
	 *
	 *
	 * @param oldVal
	 * @return
	 */
	private List<String> changProductType(String oldVal){
		List<String> list=new ArrayList<String>();
		if(oldVal.equals("03")){//号码产品-03
			list.add(EcsOrderConsts.GOODS_TYPE_NUM_CARD);//号卡
			list.add(EcsOrderConsts.GOODS_TYPE_WIFI_CARD);//上网卡
		}else if(oldVal.equals("01")){//实物-01
			list.add(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE);//合约机
			list.add(EcsOrderConsts.GOODS_TYPE_PHONE);//裸机
			list.add(EcsOrderConsts.GOODS_TYPE_ADJUNCT);//配件
		}else if(oldVal.equals("02")){//业务办理-02
			list.add(EcsOrderConsts.GOODS_TYPE_ZENGZHI);//增值业务
		}
		return list;
	}
	/**
	 * 归属地市映射
	 * @param oldVal
	 * @return
	 */
	private String changOrderOrigin(String oldVal){
		String reVal="";
		reVal=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.BSS_AREA_CODE,oldVal);
		return reVal;
	}

	@Override
	public Page getDevelopmentList(String developmentCode,String developmentName,int pageIndex,int pageSize){
		String sql = "select a.field_value developmentCode,a.field_desc developmentName from es_mall_config a where a.field_content_desc = '发展人名称' ";
		if(!StringUtils.isEmpty(developmentCode)){
			sql = sql + " and field_value = '"+developmentCode+"'";
		}
		if(!StringUtils.isEmpty(developmentName)){
			sql = sql + " and field_desc like '%"+developmentName+"%'";
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageIndex, pageSize);
		return page;
	}
	
	@Override
	public Page getXcountyList(String countyName,String regionId,int pageIndex,int pageSize) {
		String sql = "select a.areaid,a.areadef,a.countyid,a.countyname from es_busicty a where 1=1 ";
		if(!StringUtils.isEmpty(countyName)) {
			sql = sql + " and a.countyname like '%"+countyName+"%'";
		}
		if(!StringUtils.isEmpty(regionId)) {
			sql = sql + " and a.area_com = '"+regionId+"'";
		}
		sql = sql + " and a.source_from ='"+ManagerUtils.getSourceFrom()+"'";
		
		return this.baseDaoSupport.queryForPage(sql, pageIndex, pageSize);
	}
	
	@Override
	public String qryAreaIdByOrderId(String order_id) {
		String sql = "select ec.areaid" +
					 " from es_order_ext eoe" +
					 " left join es_regions er" +
					 " on eoe.order_city_code = er.region_id"+
					 " left join es_county ec"+
					 " on substr(er.local_name, 0, 2) = ec.areadef" +
					 " where eoe.order_id='"+order_id+"'"+
					 " and ec.region_type = 'city'" +
					 " and ec.source_from ='"+ManagerUtils.getSourceFrom()+"'";
		
		return this.baseDaoSupport.queryForString(sql);
	}
	
	public List<Map> getDcSqlByDcName(String dcName) {
		List<Map> list = new ArrayList<Map>();
		StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='"+dcName+"'");
		List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
		if (dc_sql_list != null && dc_sql_list.size() > 0) {
			String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
			list = baseDaoSupport.queryForList(dc_sql);
		}
		return list;
	}
	
	public Page queryGoodsList(String goodsName,String order_id,String line_type,String service_type,int pageNo,int pageSize){
		String where_sql = "";
		//String service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);//业务类型
		//String line_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "line_type");
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.TYPE_ID);
		String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String change_goods_where = cacheUtil.getConfigInfo("CHANGE_GOODS_WHERE");
		List list = getDcSqlByDcName("DC_BSS_CITY");
		for (int i = 0; i < list.size(); i++) {
			String value = Const.getStrValue((Map) list.get(i), "value");
			String value_desc = Const.getStrValue((Map) list.get(i), "value_desc");
			if (StringUtil.equals(value, order_city_code)) {
				order_city_code = value_desc;
				break;
			}
		}
		where_sql += " and c.countyid= '"+order_city_code+"' ";
		String[] line_types = new String[]{};
		if(!StringUtils.isEmpty(line_type)){
			line_types = line_type.split(",");
		}
		/*if(line_types.length>0){
			where_sql += " and ( ";
			for (int i = 0; i < line_types.length; i++) {
				where_sql += " t.params like '%"+change_goods_where+line_types[i]+"%' ";
				if(i+1!=line_types.length){
					where_sql += " or ";
				}
			}
			where_sql += " ) ";
		}else{
			where_sql += "and t.params like '%"+change_goods_where+service_type+"%' ";
		}*/
		if(!StringUtil.isEmpty(service_type)){
			where_sql += "and t.params like '%"+change_goods_where+service_type+"%' ";
		}else{
			if(line_types.length>0){
				where_sql += " and ( ";
				for (int i = 0; i < line_types.length; i++) {
					where_sql += " t.params like '%"+change_goods_where+line_types[i]+"%' ";
					if(i+1!=line_types.length){
						where_sql += " or ";
					}
				}
				where_sql += " ) ";
			}else{
				where_sql += "and t.params like '%"+change_goods_where+CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE)+"%' ";
			}
			
		}
		if(!StringUtils.isEmpty(goodsName)){
			where_sql += " and t.name like '%"+goodsName+"%' ";
		}
		String sql = " select t.goods_id,t.name as goodsname,gt.name as goodstype,t.price,c.countyname from es_goods t "
				   + " left join es_goods_rel_county rc on t.goods_id=rc.goods_id "
				   + " left join es_county c on c.countyid=rc.countyid "
				   + " left join es_goods_type gt on gt.type_id=t.type_id  "
				   + " where t.type='goods' and t.goods_id=rc.goods_id and c.countyid=rc.countyid and  t.source_from = '"+ManagerUtils.getSourceFrom()+"' "
				   + " and t.goods_id in (select goods_id from es_goods_change where source_from ='"+ManagerUtils.getSourceFrom()+"') "
				   + " and gt.type_id='"+type_id+"' ";
		Page page = this.baseDaoSupport.queryForPage(sql+where_sql, pageNo, pageSize);
		List<Map> result = page.getResult();
		//List<Map> new_result = result;
		for (int i = 0; i < result.size(); i++) {
			Map map = result.get(i);
			//Map new_map = map;
			String goods_id = Const.getStrValue(map, "goods_id");
			String new_service_type = CommonDataFactory.getInstance().getGoodSpec(null, goods_id, AttrConsts.ORDER_SERVICE_TYPE);//业务类型
			List service_typeList =  getDcSqlByDcName("DIC_SERVICE_TYPE");
			for (int j = 0; j < service_typeList.size(); j++) {
				String value = Const.getStrValue((Map) service_typeList.get(j), "value");
				String value_desc = Const.getStrValue((Map) service_typeList.get(j), "value_desc");
				if (StringUtil.equals(value, new_service_type)) {
					new_service_type = value_desc;
					break;
				}
			}
			map.put("service_type", new_service_type);
		}
		return page;
	}
	
	public String changeNewGoods(String new_goods_id, String stdOrderId){
		String return_json = "{result:1,msg:'操作失败!'}";
		IGoodsService goodServices = SpringContextHolder.getBean("goodServices");
		Map<String, String> param = new HashMap<String, String>();
		GoodsGetReq req = new GoodsGetReq();
		req.setGoods_id(new_goods_id);//商品ID
		GoodsGetResp resp = null;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(stdOrderId);
		String old_goods_name = orderTree.getOrderItemBusiRequests().get(0).getName();
		try {
			resp = goodServices.getGoods(req);
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
				Map<String,String> old_map = new HashMap();
				Map<String,String> new_map = new HashMap();
				old_map.put("goods_id", orderItemBusiRequests.get(0).getGoods_id());
				old_map.put("goods_name", orderItemBusiRequests.get(0).getName());
				old_map.put("type_id", orderItemBusiRequests.get(0).getOrderItemExtBusiRequest().getGoods_type());
				old_map.put("order_amount", orderTree.getOrderBusiRequest().getOrder_amount()+"");
				old_map.put("paymoney", orderTree.getOrderBusiRequest().getPaymoney()+"");
				new_map.put("goods_id", param.get("goods_id"));
				new_map.put("goods_name", param.get("goods_name"));
				new_map.put("type_id", param.get("type_id"));
				new_map.put("order_amount", param.get("goods_price")==null?"0":param.get("goods_price"));
				new_map.put("paymoney", param.get("goods_price")==null?"0":param.get("goods_price"));
				IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
				ecsOrdManager.saveChange(old_map,new_map,stdOrderId);
				for (OrderItemBusiRequest orderItemBusiRequest : orderItemBusiRequests) {
					orderItemBusiRequest.setGoods_id(param.get("goods_id"));
					orderItemBusiRequest.setProduct_id(param.get("product_id"));
					orderItemBusiRequest.setName(param.get("goods_name"));
					orderItemBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderItemBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderItemBusiRequest.store();
				}
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setBrand_number(param.get("brand_name"));
				orderItemExtBusiRequest.setGoods_type(param.get("type_id"));
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.store();
				OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
				orderBusiRequest.setOrder_amount(Double.parseDouble(param.get("goods_price")==null?"0":param.get("goods_price")));
				orderBusiRequest.setPaymoney(Double.parseDouble(param.get("goods_price")==null?"0":param.get("goods_price")));
				orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderBusiRequest.store();
				CommonDataFactory.getInstance().updateAttrFieldValue(stdOrderId, 
						new String[]{"GoodsID","GoodsName","brand_name","brand_number","goods_type","out_plan_id_bss","out_plan_id_ess","sell_price","pro_realfee","order_amount","order_realfee","oldGoodsName"}, 
						new String[]{param.get("goods_id"),param.get("goods_name"),param.get("brand_name"),param.get("brand_number"),param.get("type_id"),param.get("out_plan_id_bss"),param.get("out_plan_id_ess"),param.get("goods_price"),param.get("goods_price"),Double.parseDouble(param.get("goods_price")==null?"0":param.get("goods_price"))+"",Double.parseDouble(param.get("goods_price")==null?"0":param.get("goods_price"))+"",old_goods_name});
				return_json = "{result:0,msg:'操作成功!'}";
			}else {
				return_json = "{result:1,msg:'找不到商品详情，操作失败!'}";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return return_json;
	}
}
