package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import params.order.resp.AcceptResp;
import rule.params.accept.req.AcceptRuleReq;
import utils.UUIDUtil;
import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.AttrDefGetReq;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.ITplInstManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.TempInst;
import com.ztesoft.net.model.TplAttr;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.util.CacheUtils;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 模板实例数据处理
 * @author wui
 */
public class TplInstManager extends BaseSupport implements ITplInstManager {
	private static Logger logger = Logger.getLogger(TplInstManager.class);
	IOrderServices orderServices;
	ILogsServices logsServices;
	@Override
	public List<TplAttr> getAttrsByCol(Map inMap) {
		TempInst tempInst = null;
		if(inMap.get("temp_inst_id")!=null)
			tempInst = getTempInstByTempInstId((String)inMap.get("temp_inst_id"));
		List<TplAttr> attrInstAttr = new ArrayList<TplAttr>();
		if(tempInst!=null){
			List<TplAttr> tplAttrs = getTplAttrs(tempInst);
			//获取属性信息
			for (int i = 0; i < tplAttrs.size(); i++) {
				TplAttr tplAttr =tplAttrs.get(i);
				AttrDef attrDef = getAttrDef(tplAttr.getField_attr_id());
				String field_name = attrDef.getField_name();
				String o_field_name = attrDef.getO_field_name();
				if(StringUtils.isEmpty(o_field_name))
					o_field_name =field_name; //外系统字段映射处理
				if(inMap.containsKey(o_field_name)){
					attrInstAttr.add(tplAttr);
				}
			}
		}
		return attrInstAttr;
	}
	
	
	@Override
	public AttrDef getAttrDef(String field_attr_id){
		String sql = "select * from es_attr_def where field_attr_id = '"+field_attr_id+"'";
		return (AttrDef) this.baseDaoSupport.queryForObject(sql, AttrDef.class);
	}
	
	@Override
	public TempInst getTempInstByTempInstId(String temp_inst_id){
		String sql = "select * from es_temp_inst where temp_inst_id = '"+temp_inst_id+"'";
		//
		return (TempInst) this.baseDaoSupport.queryForObject(sql, TempInst.class);
	}
	
	public List<TplAttr> getTplAttrs(TempInst tempInst){
		String temp_cols = tempInst.getTemp_cols();
		if(StringUtils.isEmpty(temp_cols)){
			temp_cols = tempInst.getTemp_cols2();
		}else if(!StringUtils.isEmpty(tempInst.getTemp_cols2())){
			temp_cols += tempInst.getTemp_cols2();
		}
		return CommonTools.jsonToList(temp_cols.trim(), TplAttr.class);
	}

	
	@Override
	public List<AttrInst> getOuterAttrInst(String order_id){
		String sql = "select * from es_outer_attr_inst where order_id = '"+order_id+"'";
		return this.baseDaoSupport.queryForList(sql, AttrInst.class);
	}
	

	/**
	 * 保存属性受理数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void saveTableInst(Order order,OrderItem orderItem,Map<String, List<AttrInst>> tableInstAttrs,Map<String, String> tableInstFkeys) {
		
		Iterator it = tableInstAttrs.keySet().iterator();
		
		if(tableInstAttrs.size()==0){
//	    	logger.info("========================================>tableInstAttrs is null ");
			return;
		}
		//循环表名
		while (it.hasNext()) {
			String table_name = (String) it.next();
			List<AttrInst> tLists  = tableInstAttrs.get(table_name);
			
			StringBuffer insertSQL = new StringBuffer();
			StringBuffer whereSQL = new StringBuffer();
			insertSQL.append("insert into  ").append(table_name).append("(inst_id,order_id,item_id,product_id,state,user_id");
			whereSQL.append("(").append("?,?,?,?,?,?");
			
			List sqlParams =new ArrayList();
			String inst_id =this.baseDaoSupport.getSeqByTableName("ES_ORDER_ACCEPT"); //获取受理序列
			sqlParams.add(inst_id);
			sqlParams.add(orderItem.getOrder_id());
			sqlParams.add(orderItem.getItem_id());
			sqlParams.add(orderItem.getProduct_id());
			sqlParams.add(OrderStatus.ACCEPT_STATUS_0+"");
			String accept_user_id =order.getAccept_user_id();
			if(StringUtils.isEmpty(accept_user_id))
				accept_user_id = "";
			sqlParams.add(accept_user_id);
			
			tableInstFkeys.put(table_name, inst_id);
			String prefix =",";
			
			//获取表名属性数据
			for (int i = 0; i < tLists.size(); i++) {
				AttrInst attrInst = tLists.get(i);
				
				//if(i==tLists.size()-1)
				//	prefix ="";
				insertSQL.append(prefix).append(attrInst.getField_name());
				whereSQL.append(prefix).append("?");
				sqlParams.add(attrInst.getField_value());
			}
			insertSQL.append(",create_time) values ");
			whereSQL.append(","+DBTUtil.current()+")");
			String insertSql = insertSQL.toString()+whereSQL.toString();
			
			//保存数据信息
			this.baseDaoSupport.execute(insertSql, sqlParams.toArray());
			/*String sql = "insert into es_order_items_pay (inst_id, order_id, item_id, product_id, state, "+
				" create_time, app_code, phone_num, opn_code, from_url, opn_session_id, "+
				" dev_staff_id, dev_group_id, from_ord_code, item_type) values "+
				" (201310127643000020, 201310128574003659, 201310127222003485, 201310119014001572, "+
				" 0, sysdate, 'app_code',' phone_num', 'opn_code', 'from_url', 'opn_session_id',' "+
				" dev_staff_id', 'dev_group_id', 'from_ord_code', 'item_type')";
			this.baseDaoSupport.execute(sql);*/
			
		}
	}
	
	/**
	 * 保存属性受理数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void saveEmptyTableInst(Order order,OrderItem orderItem,String table_name,Map<String, String> tableInstFkeys) {
		
			StringBuffer insertSQL = new StringBuffer();
			StringBuffer whereSQL = new StringBuffer();
			insertSQL.append("insert into  ").append(table_name).append("(inst_id,order_id,item_id,product_id,state,user_id");
			whereSQL.append("(").append("?,?,?,?,?,?");
			
			List sqlParams =new ArrayList();
			String inst_id =this.baseDaoSupport.getSeqByTableName("ES_ORDER_ACCEPT"); //获取受理序列
			sqlParams.add(inst_id);
			sqlParams.add(orderItem.getOrder_id());
			sqlParams.add(orderItem.getItem_id());
			sqlParams.add(orderItem.getProduct_id());
			sqlParams.add(OrderStatus.ACCEPT_STATUS_0+"");
			String accept_user_id =order.getAccept_user_id();
			if(StringUtils.isEmpty(accept_user_id))
				accept_user_id = "";
			sqlParams.add(accept_user_id);
			
			tableInstFkeys.put(table_name, inst_id);
			String prefix =",";
			
			insertSQL.append(",create_time) values ");
			whereSQL.append(","+DBTUtil.current()+")");
			String insertSql = insertSQL.toString()+whereSQL.toString();
			
			//保存数据信息
			this.baseDaoSupport.execute(insertSql, sqlParams.toArray());
			
	}
	
	
	
	/**
	 * 保存属性实例数据
	 * @param inMap
	 * @param tableInstAttrs
	 */
	@Override
	public void saveAttrInst(OrderItem orderItem,List<AttrInst> propAttrInsts,Map<String,String> tableInstFkeys) {
		
		//def
		if(propAttrInsts.size()==0)
			return;
		
		for (int i = 0; i < propAttrInsts.size(); i++) {
			AttrInst attrInst = propAttrInsts.get(i);
			//attrInst.getAttr_spec_id()
			attrInst.setInst_id(tableInstFkeys.get(this.getAttrDef(attrInst.getField_attr_id()).getRel_table_name()));
			attrInst.setOrder_item_id(orderItem.getItem_id());
			attrInst.setOrder_id(orderItem.getOrder_id());
			attrInst.setCreate_date(DBTUtil.current());
			this.baseDaoSupport.insert("es_attr_inst", attrInst);
			
		}
	}
	
	
	/**e
	 * 分组处理
	 * @param inMap
	 * @param temp_inst_id
	 * @param tableInstAttrs
	 * @param attrInstAttr
	 */
	@Override
	public void groupAttrInsts(List<AttrInst> oAttrInsts,Map<String ,List<AttrInst>> tableAttrInsts,List<AttrInst> propAttrInsts,String sub_attr_spec_type){
	
		//分组处理
		for (int i = 0; i < oAttrInsts.size(); i++) {
			AttrInst oAttrInst =oAttrInsts.get(i);
			AttrDef attrDef = getAttrDef(oAttrInst.getField_attr_id());
			
			//指定类型的数据
			if(!sub_attr_spec_type.equals(attrDef.getSub_attr_spec_type()))
				continue;
			
			String table_name =attrDef.getRel_table_name();
			if(!StringUtils.isEmpty(table_name) && "yes".equals(attrDef.getOwner_table_fields())){ //属性字段归属受理表的
				if(tableAttrInsts.get(table_name) ==null )
				{
					List<AttrInst> tList = new ArrayList<AttrInst>();
					tableAttrInsts.put(table_name,tList);
				}
				List<AttrInst> tpList = tableAttrInsts.get(table_name);
				tpList.add(oAttrInst);
			}else {
				propAttrInsts.add(oAttrInst);
			}
		}
		
	}


	
	
	@Override
	public TempInst getTempInstByGoodsIdAndSource(String goods_id,
			String source_from) {
		String sql = SF.orderSql("SERVICE_TEMP_INST_SELECT")+" and goods_id=? and source_from=? and temp_type=?";
		List<TempInst> tempsLists = this.baseDaoSupport.queryForList(sql, TempInst.class, goods_id,source_from,"goods");
		TempInst inst = null;
		if(!ListUtil.isEmpty(tempsLists))
			inst = tempsLists.get(0);
		if(inst==null){
//			GoodsBaseQueryReq req = new GoodsBaseQueryReq();
//			req.setGoods_id(goods_id);
//			ZteClient client = ClientFactory.getZteDubboClient(source_from);
//			GoodsBaseQueryResp resp = client.execute(req, GoodsBaseQueryResp.class);
//			
//			IGoodsService goodsService = SpringContextHolder.getBean("goodServices");
//			goodsService.g
			sql ="SELECT a.* FROM es_temp_inst a ,es_goods b WHERE  b.goods_id=? and a.source_from=? and a.goods_id =b.type_id and a.temp_type=?";
//			sql = "SELECT a.* FROM es_temp_inst a ,es_goods b WHERE 1=1 and a.goods_id=? and a.source_from=? and a.goods_id =b.type_id ";//SF.orderSql("SERVICE_TEMP_INST_SELECT")+" and goods_id=? and source_from=? and temp_type=?";
			tempsLists = this.baseDaoSupport.queryForList(sql, TempInst.class, goods_id,source_from,"goodstype");
			 if(!ListUtil.isEmpty(tempsLists))
				inst = tempsLists.get(0);
		}
		return inst;
			
	}
	
	/**
	 * 保存受理数据
	 * @param acceptRuleReq
	 * @return
	 * @throws ApiBusiException
	 */
	@Override
	public AcceptResp saveTplInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException{
		AcceptResp acceptResp = new AcceptResp();
		Map params = acceptRuleReq.getParams();
		String table_name = (String) params.get("table_name");
		if(StringUtil.isEmpty(table_name)){
			acceptResp = saveAccepInst(acceptRuleReq);
		}else{
			acceptResp = saveEmptyAccepInst(acceptRuleReq);
		}
		return acceptResp;
	}
	
	//受理数据保存
//	private static int afSaveAcceptInstPerform_in_saveTplInst_index =0;
	public AcceptResp saveAccepInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException{
		
		
//		afSaveAcceptInstPerform_in_saveTplInst_index++;
//    	logger.info(afSaveAcceptInstPerform_in_saveTplInst_index+"========================================>afSaveAcceptInstPerform_in_saveTplInst_index");

    	
		AcceptResp acceptResp = new AcceptResp();
		Map params = acceptRuleReq.getParams();
    	OrderItem orderItem =  acceptRuleReq.getOrderItem();
    	
    	Order order = acceptRuleReq.getOrder();
		
		List<AttrInst> oattrInsts = acceptRuleReq.getOattrInsts();
			
		Map<String ,List<AttrInst>> acceptTableInstAttrs = new HashMap<String, List<AttrInst>>();
		
		List<AttrInst> propAttrInsts = new ArrayList<AttrInst>();
		
		Map<String, String> tableInstFkeys = new HashMap<String, String>();
		
		
		//属性数据分组
		this.groupAttrInsts(oattrInsts, acceptTableInstAttrs, propAttrInsts,OrderStatus.SUB_ATTR_SPEC_TYPE_ACCEPT);
		
		
		//保存受理属性实例数据到指定的库表
		this.saveTableInst(order,orderItem,acceptTableInstAttrs,tableInstFkeys);
		
		//保存受理属性实例数据到属性实例表
		this.saveAttrInst(orderItem,propAttrInsts,tableInstFkeys);
		
		areturn(acceptResp, tableInstFkeys);
		
		return acceptResp;
	}
	
	//受理数据保存
	public AcceptResp saveEmptyAccepInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException{
		AcceptResp acceptResp = new AcceptResp();
		Map params = acceptRuleReq.getParams();
    	OrderItem orderItem =  acceptRuleReq.getOrderItem();
		String table_name = (String) params.get("table_name");
		
		Order order = acceptRuleReq.getOrder();
		
		Map<String, String> tableInstFkeys = new HashMap<String, String>();
		
		//保存受理属性实例数据到指定的库表
		this.saveEmptyTableInst(order,orderItem,table_name,tableInstFkeys);
		
		areturn(acceptResp, tableInstFkeys);
		
		return acceptResp;
	}
	
	//组装返回参数
	@SuppressWarnings("unchecked")
	private void areturn(AcceptResp acceptResp, Map<String, String> tableInstFkeys) {
		
		Iterator it = tableInstFkeys.keySet().iterator();
		//循环表名
		while (it.hasNext()) {
			String table_name = (String) it.next();
			String inst_id = tableInstFkeys.get(table_name);
			acceptResp.addAcceptVo(table_name, inst_id);
		}
	}
	

	public AttrInstLoadResp invokeAttrInstPageLoad(AttrInstLoadReq req){
		AttrInstLoadResp resp = new AttrInstLoadResp();
		OrderTreeBusiRequest orderTree = req.getOrderTree();
		AttrInstBusiRequest attrInstBusiRequest =null;
//		if(ListUtil.isEmpty(attrInsts))
//			attrInsts = orderTree.getAttrInstBusiRequests();
		AttrInstLoadResp attrInstInvoke = new AttrInstLoadResp();
		try{
			//获取实例数据
//			for (AttrInstBusiRequest attrInst:attrInsts ) {
//				String p_field_name = attrInst.getField_name();
//				if(p_field_name.equals(req.getField_name())){
//					attrInstBusiRequest = attrInst; 
//					break;
//				}
//			} 
			if(logsServices ==null)
				logsServices = SpringContextHolder.getBean("logsServices");
			attrInstBusiRequest = logsServices.getAttrInstBusiRequestByOrderId(req.getOrder_id(), req.getField_name());
			if(attrInstBusiRequest ==null){
				AttrDef attrDef = new AttrDef();
				attrDef = CacheUtils.getCacheAttrDef(req.getField_name()+EcsOrderConsts.ATTR_SPEC_ID_999);
				if(attrDef ==null){
					return resp;
				}
				resp.setIs_null(attrDef.getIs_null());
				resp.setIs_edit(attrDef.getIs_null());
				String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				AttrBusiInstTools.genTraceIsNullByAttrDef(flow_trace_id,resp);
				AttrBusiInstTools.genTraceIsEditByAttrDef(flow_trace_id,resp);
				
				if(ConstsCore.IS_NULL_N.equals(resp.getIs_null()))  {
	            	attrInstBusiRequest = new AttrInstBusiRequest();
	            	attrInstBusiRequest.setAttr_inst_id(UUIDUtil.getUUID());
	        		attrInstBusiRequest.setOrder_id(req.getOrder_id());
	        		attrInstBusiRequest.setField_name(req.getField_name());
	        		attrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	        		attrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
	        		attrInstBusiRequest.setCreate_date(Consts.SYSDATE);
	        		attrInstBusiRequest.store();
	            }else{
	            	
					//resp.setCheck_message(attrDef.getField_desc()+ConstsError.INVALID_ATTR_TIP);
					resp.setAttr_code(attrDef.getAttr_code());
					resp.setField_type(req.getField_type());
					resp.setDisabled(req.getDisabled());
					resp.setField_type(attrDef.getField_type());
					resp.setField_attr_id(attrDef.getField_attr_id());
					resp.setField_name(attrDef.getField_name());
					resp.setField_desc(attrDef.getField_desc());
					resp.setOrder_id(req.getOrder_id());
					resp.setField_attr_format(attrDef.getField_attr_format());
					resp.setCheck_message(attrDef.getCheck_message());
					AttrBusiInstTools.genTraceIsNullByAttrDef(flow_trace_id,resp);
					AttrBusiInstTools.genTraceIsEditByAttrDef(flow_trace_id,resp);
					return resp; //匹配不上，表示非属性实例数据
	            }
			}
			
			//规格数据拷贝,不建议用copy,非常消耗性能
			AttrDefGetReq defreq = new AttrDefGetReq();
			defreq.setField_attr_id(attrInstBusiRequest.getField_attr_id());
			AttrDef def = CacheUtils.getCacheAttrDefByFiledAttrId(attrInstBusiRequest.getField_attr_id());
			if(def ==null)
				return resp;
			if(null!=def.getField_attr_format()&&def.getField_attr_format().length()>1){
        		//logger.info();
        	}
			resp.setAttr_code(def.getAttr_code());
			resp.setDisabled(req.getDisabled());
			resp.setField_type(def.getField_type());
			resp.setField_attr_id(def.getField_attr_id());
			resp.setCheck_message(def.getCheck_message());
			resp.setField_name(def.getField_name());
			resp.setField_desc(def.getField_desc());
			resp.setField_value(attrInstBusiRequest.getField_value());
			resp.setAttr_inst_id(attrInstBusiRequest.getAttr_inst_id());
			resp.setField_value_desc(attrInstBusiRequest.getField_value_desc());
			resp.setOrder_id(attrInstBusiRequest.getOrder_id());
			resp.setDisabled(req.getDisabled());
			resp.setField_attr_format(def.getField_attr_format());
//			
//			attrInstBusiRequest.setIs_null(def.getIs_null());
//			attrInstBusiRequest.setIs_edit(def.getIs_edit());
			
			
			
			//init文本类型
			if(StringUtil.isEmpty(resp.getField_type())){ //数据库未配置，则按规则设置缺省
				if(StringUtil.isEmpty(req.getAttr_code()))
					resp.setField_type(ConstsCore.FIELD_TYPE_INPUT);
				else
					resp.setField_type(ConstsCore.FIELD_TYPE_SELECT);
			}
			
			//界面传入数据拷贝
			if(!StringUtil.isEmpty(req.getAttr_code()))
				resp.setAttr_code(req.getAttr_code());
			if(!StringUtil.isEmpty(req.getNew_value()))
				resp.setField_value(req.getNew_value());
			if(!StringUtil.isEmpty(req.getField_type()))
				resp.setField_type(req.getField_type());
			
			
			//根据规格设置是否为空，是否可编辑
			AttrBusiInstTools.setAttrNullAndEditByAttrDef(orderTree.getOrder_id(), attrInstBusiRequest.getField_attr_id(), resp,orderTree);
			//HtmlOrderAttrDictTaglib 也面进入
			if(EcsOrderConsts.ACTION_TIME_PAGELOAD.equals(req.getAction_time()))
				req.setNew_value(resp.getField_value());
			
			
			//根据规格，调用处理器处理数据
			if(ConstsCore.ATTR_ACTION_lOAD.equals(req.getAction_name()))
				attrInstInvoke = AttrBusiInstTools.invokeAttrInstPageLoad(attrInstBusiRequest.getField_attr_id(), req);
			else if(ConstsCore.ATTR_ACTION_UPDATE.equals(req.getAction_name()))  //更新处理
				attrInstInvoke = AttrBusiInstTools.invokeAttrInstPageUpdate(attrInstBusiRequest.getField_attr_id(), req);
			else if(ConstsCore.ATTR_ACTION_LOAD_AND_UPDATE.equals(req.getAction_name()))
			{	//add by wui
				attrInstInvoke = AttrBusiInstTools.invokeAttrInstPageLoad(attrInstBusiRequest.getField_attr_id(), req);
				if(attrInstInvoke ==null || StringUtil.isEmpty(attrInstInvoke.getCheck_message())) //load不通过，则走validate
					attrInstInvoke = AttrBusiInstTools.invokeAttrInstPageUpdate(attrInstBusiRequest.getField_attr_id(), req);
			}
			if(attrInstInvoke  != null){
				if(!StringUtil.isEmpty(attrInstInvoke.getAttr_code()))
					resp.setAttr_code(attrInstInvoke.getAttr_code());
				if(!StringUtil.isEmpty(attrInstInvoke.getField_value()))
					resp.setField_value(attrInstInvoke.getField_value());
				if(!StringUtil.isEmpty(attrInstInvoke.getCheck_message()))
					resp.setCheck_message(attrInstInvoke.getCheck_message());
				if(!StringUtil.isEmpty(attrInstInvoke.getField_type()))
					resp.setField_type(attrInstInvoke.getField_type());
				if(!StringUtil.isEmpty(attrInstInvoke.getIs_null()))
					resp.setIs_null(attrInstInvoke.getIs_null());
				if(!StringUtil.isEmpty(attrInstInvoke.getIs_edit()))
					resp.setIs_edit(attrInstInvoke.getIs_edit());
				if(!StringUtil.isEmpty(attrInstInvoke.getDisabled())){
					resp.setDisabled(attrInstInvoke.getDisabled());
				}
			}
			/*disabled的时候 如果是select类型 就展示翻译值  */
			if(ConstsCore.FILED_STYLE_DISABLED.equals(resp.getDisabled())){
				if(ConstsCore.FIELD_TYPE_SELECT.equals(resp.getField_type())){
					if(!StringUtil.isEmpty(resp.getField_value_desc())){
						resp.setField_type(ConstsCore.FIELD_TYPE_TEXT);
						resp.setField_value(resp.getField_value_desc());
					}
				}else{
					resp.setField_type(ConstsCore.FIELD_TYPE_TEXT);
				}
			}
			/**
			 * 如果是不能为空的字段 内容为空改为输入框  mochunrun
			 */
			if("N".equals(resp.getIs_null()) && StringUtil.isEmpty(attrInstBusiRequest.getField_value())){
				if(ConstsCore.FIELD_TYPE_TEXT.equals(resp.getField_type())){
					if(!StringUtil.isEmpty(def.getAttr_code())){
						resp.setField_type(ConstsCore.FIELD_TYPE_SELECT);
					}else{
						resp.setField_type(ConstsCore.FIELD_TYPE_INPUT);
					}
				}else{
					def.setIs_edit("Y");
				}
			}
			
		}catch(Exception e){e.printStackTrace();}
		return resp;
	}
	
	
	/**
	 * 页面load装载
	 */
	@Override
	public AttrInstLoadResp pageLoadAttr(AttrInstLoadReq req) {
		AttrInstLoadResp resp = invokeAttrInstPageLoad(req);
		
		String message = resp.getCheck_message();
		if(ConstsCore.IS_NULL_N.equals(resp.getIs_null()) && StringUtil.isEmpty(resp.getCheck_message()) && StringUtil.isEmpty(resp.getField_value())){
			//resp.setCheck_message(resp.getField_desc()+"不能为空。");
			message = resp.getField_desc()+"不能为空。";
		}
		
		if(!StringUtil.isEmpty(resp.getField_value())&&StringUtil.isEmpty(resp.getCheck_message())&&null!=resp.getField_attr_format()&&!"".equals(resp.getField_attr_format())){
			HashMap formatMap = JsonUtil.fromJson(resp.getField_attr_format(),HashMap.class);
			String exg = formatMap.get("format").toString();
			if(!resp.getField_value().matches(exg)){
				message = resp.getField_desc()+formatMap.get("message").toString();
//				logger.info("exg:"+exg+"  value:"+resp.getField_value());
			}
		}
		resp.setCheck_message(message);
		
		//静态数据设置
		if(!StringUtil.isEmpty(resp.getAttr_code())){
			IDictManager dictManager = SpringContextHolder.getBean("dictManager");
			DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
			List<Map<String,String>> staticDatas=dc.loadData(resp.getAttr_code());
			resp.setStaticDatas(staticDatas);
		}
		
		return resp;
	}
    public static void main(String[] args) {
		String exg = "^[1-9]d{0,6}.d{1,2}|0.d{1,2}|[1-9]d{0,6}|0$";
		String str1 = "50";
		String str2 = "50.";
		String str3 = "50.0";
		logger.info(str1.matches(exg));
		logger.info(str2.matches(exg));
		logger.info(str3.matches(exg));
	}
}