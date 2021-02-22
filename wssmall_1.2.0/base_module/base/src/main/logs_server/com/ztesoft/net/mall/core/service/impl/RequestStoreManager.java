
package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

import params.ZteBusiRequest;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.ZteInstQueryResponse;
import zte.net.common.params.resp.ZteInstUpdateResponse;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IRequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicSQLInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.BusiUtils;
import com.ztesoft.net.mall.core.utils.IBusiManager;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;

/**
 * 订单管理
 * @author wui
 */

public class RequestStoreManager extends BaseSupport implements IRequestStoreManager  {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public static final String DC_TREE_CACHE_NAME_KEY = "treeCache";
	
	
	@Resource
	IBusiManager busiManager;
	//100以下是保留，最大值不能超过10000
	public static final int NAMESPACE = EcsOrderConsts.ORDER_TREE_NAMESPACE;
	public static final int time =60*24*60*5;//缺省缓存20天,memcache最大有效期是30天
	public static final int min_time =5*60;
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType(""); //add by wui订单调用单独的订单缓存机器，通过业务分开，和业务静态数据分开
	}
	
	/**
	 * 更新实体数据
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteInstUpdateResponse updateZteRequestInst(ZteInstUpdateRequest req){
		RequestBeanDefinition servicebean = BusiUtils.getRequestServiceDefinition(req.getUpdateRequest());
		ZteBusiRequest updateZteRequest = req.getUpdateRequest();
		String cache_key = BusiUtils.genCacheKey(servicebean.getService_name(),updateZteRequest);
		ZteInstUpdateResponse resp = new ZteInstUpdateResponse();
		resp.setError_code("0");
		
		String is_dirty = updateZteRequest.getIs_dirty();
		
		ZteBusiRequest storeRequest = null;
		if(ConstsCore.IS_ASY_YES.equals(updateZteRequest.getAsy())){
			busiManager.asyDbStore(updateZteRequest); //异步执行操作
		}else{
			try {
				storeRequest = busiManager.dbStore(updateZteRequest); //更新处理
			} catch (Exception e) {
				resp.setError_code("-1");
				resp.setError_msg(e.getMessage());
			}
		}
			
		//反向更新到订单树,异步写入数据库
		if(storeRequest !=null && !(storeRequest instanceof OrderTreeBusiRequest) && ConstsCore.CONSTS_YES.equals(is_dirty)){ //处理的数据为脏数据，才需要处理
			
			if(storeRequest instanceof AttrInstBusiRequest){
//				OrderTreeBusiRequest orderTreeBusiRequest = BusiUtils.updateCacheOrderTree(storeRequest); //同时更新缓存
				//不需要更新,已在busiManager.dbStore内置更新
			}else{
				OrderTreeBusiRequest orderTreeBusiRequest = BusiUtils.updateCacheOrderTree(storeRequest); //同时更新缓存
				if(orderTreeBusiRequest !=null){
					String tree_cache_key = BusiUtils.genCacheKey(BusiUtils.getRequestServiceDefinition(orderTreeBusiRequest).getService_name(),orderTreeBusiRequest);
					cacheRequestInst(tree_cache_key,orderTreeBusiRequest);
				}
			}
			
		}
		//设置返回值,统一在查询处设置缓存
		if(storeRequest !=null && storeRequest instanceof OrderTreeBusiRequest){
			OrderTreeBusiRequest orderTreeBusiRequest = (OrderTreeBusiRequest)storeRequest;
			 List<String> changeFields = orderTreeBusiRequest.getChangeFiels();
			 resp.setQueryObject(storeRequest);
			 if(changeFields.size() ==1){
				 for(String changeField:changeFields)
				 {
					 if("col1,col2,col3,col4,col5,col6,col7,col8,col9,col10".indexOf(changeField)>-1){ //订单树扩展属性特殊处理
						 String value;
						try {
							value = (String)MethodUtils.invokeMethod(storeRequest, "get"+changeField.substring(0,1).toUpperCase()+changeField.substring(1),null);
							OrderTreeBusiRequest oldOrderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderTreeBusiRequest.getOrder_id());
							 if(oldOrderTreeBusiRequest !=null)
								 MethodUtils.invokeMethod(oldOrderTreeBusiRequest, "set"+changeField.substring(0,1).toUpperCase()+changeField.substring(1), value);
							 cacheRequestInst(cache_key,oldOrderTreeBusiRequest);
						} catch (Exception e) {}
					 }
				 }
			 }
		}
		return resp;
	}
	
	/**
	 * 获取实体数据
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteInstQueryResponse getZteRequestInst(ZteInstQueryRequest instQuery){
		ZteInstQueryResponse<ZteBusiRequest> resp= new ZteInstQueryResponse<ZteBusiRequest>();
		Object zteRequest= instQuery.getQueryObject();
		Map instParams = instQuery.getQueryParmas();
		String query_from_cache = (String)instParams.get("query_from_cache");
		RequestBeanDefinition definition = BusiUtils.getRequestServiceDefinition(instQuery.getQueryObject());
		if(definition == null)
			return resp;
		String cache_key = BusiUtils.genCacheKey(definition.getService_name(),instQuery.getQueryObject());
		if(definition == null)
			return resp;
		if(definition !=null && ConstsCore.CONSTS_YES.equals(definition.getCache_store()) && !(ConstsCore.CONSTS_NO.equals(query_from_cache))){
			try{
				zteRequest=getBusiCacheValue(cache_key);
			}catch(Exception e){
				zteRequest =null;
				e.printStackTrace();
			}
			//为空，则先查询,设置缓存
			if(zteRequest ==null){
				if(ConstsCore.CONSTS_YES.equals(definition.getAsy_query())) //异步执行查询
					zteRequest = busiManager.asyDbQuery(instQuery);
				else{
					instQuery.setNeed_query(ConstsCore.CONSTS_YES);
					ZteBusiRequest zteRequestN= instQuery.getQueryObject();
					zteRequestN.setNeed_query(ConstsCore.CONSTS_YES);
					zteRequest = busiManager.dbQuery(instQuery);
				}
				boolean canCache = cancache((ZteBusiRequest)zteRequest);
				//设置对象缓存时间，不缓存对象缺省缓存5分钟
				if(zteRequest !=null){
					setBusiCacheValue(zteRequest, cache_key, canCache);
				}
			}else{
				if(zteRequest instanceof OrderTreeBusiRequest){ //为空，则更新订单树、因为更新缓存后不会再写日志。
					OrderTreeBusiRequest treeBusiRequest = (OrderTreeBusiRequest)zteRequest;
					if(StringUtil.isEmpty(treeBusiRequest.getOrderBusiRequest().getOrder_id())){
						instQuery.setNeed_query(ConstsCore.CONSTS_YES);//首次不写日志，缓存失效后二次读取全量从数据库读取,add by wui
						ZteBusiRequest zteRequestN= instQuery.getQueryObject();
						zteRequestN.setNeed_query(ConstsCore.CONSTS_YES);
						zteRequest = busiManager.dbQuery(instQuery);
						setBusiCacheValue(zteRequest, cache_key, true);
					}
				}
			}
		}else{
			if(ConstsCore.CONSTS_YES.equals(definition.getAsy_query())) //异步执行查询
				zteRequest = busiManager.asyDbQuery(instQuery);
			else{
				zteRequest = busiManager.dbQuery(instQuery);
			}
		}
		resp.setQueryObject((ZteBusiRequest)zteRequest);
		return resp;
	}
	
//	private  static int num =0;
//	public boolean isFromDbRead(){
//		Object order_from_db = cache.get(NAMESPACE, "order_from_db");
//		if(order_from_db !=null && "yes".equals(order_from_db)){
//			num++;
//			if(num<200) //小于200
//				logger.info("====================================>开始数据库查询订单信息");
//			return true;
//		}else{
//			num=0;
//		}
//		return false;
//	}
	
	public String getOrderByCacheKey(String cache_key)
	{
		String order_id  =cache_key.replace("order_id", "");
		return order_id;
	}
	private void setBusiCacheValue(Object zteRequest, String cache_key,boolean canCache) {
		try{
//			if(isFromDbRead()){ //数据库读取数据
//				long begin =System.currentTimeMillis();
//				OrderTreeBusiRequest orderTreeBusiRequest = (OrderTreeBusiRequest)zteRequest;
//				List<AttrInstBusiRequest> attrInstBusiRequests = orderTreeBusiRequest.getAttrInstBusiRequests();
//				orderTreeBusiRequest.setAttrInstBusiRequests(null);
//				String treejson =CommonTools.beanToJsonFilterNull(orderTreeBusiRequest);
//				long end =System.currentTimeMillis();
//				orderTreeBusiRequest.setAttrInstBusiRequests(attrInstBusiRequests);
////				logger.info(treejson.length()+"666");
//				List<String> errors = parseStr(treejson);
//				String ORDER_REQ1="",ORDER_REQ2="",ORDER_REQ3="",ORDER_REQ4="",ORDER_REQ5="",ORDER_REQ6="",ORDER_REQ7="",ORDER_REQ8="",ORDER_REQ9="",ORDER_REQ10="";
//				for(int i = 0; i < errors.size(); i++){
//			    	 if(i == 0)
//			    		 ORDER_REQ1 = errors.get(i);
//			    	 else if(i == 1)
//			    		 ORDER_REQ2 = errors.get(i);
//			    	 else if(i == 2)
//			    		 ORDER_REQ3 = errors.get(i);
//			    	 else if(i == 3)
//			    		 ORDER_REQ4 = errors.get(i);
//			    	 else if(i == 4)
//			    		 ORDER_REQ5 = errors.get(i);
//			    	 else if(i == 5)
//			    		 ORDER_REQ6 = errors.get(i);
//			    	 else if(i == 6)
//			    		 ORDER_REQ7 = errors.get(i);
//			    	 else if(i == 7)
//			    		 ORDER_REQ8 = errors.get(i);
//			    	 else if(i == 8)
//			    		 ORDER_REQ9 = errors.get(i);
//			    	 else if(i == 9)
//			    		 ORDER_REQ10 = errors.get(i);
//			     }
//				try{
//					this.baseDaoSupport.execute("delete from ES_ORDER_CACHE_DATAS where ORDER_ID = ? ", getOrderByCacheKey(cache_key));
//					this.baseDaoSupport.execute("insert into ES_ORDER_CACHE_DATAS (ORDER_ID,ORDER_REQ1,ORDER_REQ2,ORDER_REQ3,ORDER_REQ4,ORDER_REQ5,ORDER_REQ6,ORDER_REQ7,ORDER_REQ8,ORDER_REQ9,ORDER_REQ10,SOURCE_FROM) values(?,?,?,?,?,?,?,?,?,?,?,?)", getOrderByCacheKey(cache_key),ORDER_REQ1,ORDER_REQ2,ORDER_REQ3,ORDER_REQ4,ORDER_REQ5,ORDER_REQ6,ORDER_REQ7,ORDER_REQ8,ORDER_REQ9,ORDER_REQ10,ManagerUtils.getSourceFrom());
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//				}else{//内存读取
				if(canCache){
					cache.set(NAMESPACE, DC_TREE_CACHE_NAME_KEY+cache_key,(ZteBusiRequest)zteRequest, time);
				}else {
					cache.set(NAMESPACE, DC_TREE_CACHE_NAME_KEY+cache_key,(ZteBusiRequest)zteRequest, min_time);
				}
//			}
		}catch(Exception e){
			logger.error("cache set fail,key="+DC_TREE_CACHE_NAME_KEY+cache_key+":"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	//获取缓存数据
	private Object getBusiCacheValue(String cache_key) {
		Object zteRequest=null;
		zteRequest=RequestStoreManager.cache.get(NAMESPACE,DC_TREE_CACHE_NAME_KEY+cache_key);
		return zteRequest;
	}
	
	/**
	 * 属性数据动态读取
	 * @param order_id
	 * @param query_sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
//	private List<AttrInstBusiRequest>  orderExtDataToAttrInst(String order_id,String query_sql){
//		
//		String t_prefix = "";
//		if(!StringUtil.isEmpty(query_sql) && query_sql.indexOf("_his")>-1)
//			t_prefix = "_his";
//		@SuppressWarnings("rawtypes")
//		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
//		String attr_defSql ="select distinct a.is_edit,a.is_null,a.attr_code, a.field_name, a.field_desc, a.field_attr_id,  b.table_name, b.field_name t_field_name from es_attr_def a, es_attr_table b where a.field_attr_id = b.attr_def_id  and b.source_from='"+ManagerUtils.getSourceFrom()+"'";
//		@SuppressWarnings("rawtypes")
//		
//		BusiCompRequest req = (BusiCompRequest) cache.get(NAMESPACE, "attrspec");
//		List<Map> attrSpecList = null;
//		if(req ==null){
//			attrSpecList = baseDaoSupport.queryForList(attr_defSql); 
//			req = new BusiCompRequest();
//			Map attrspec =new HashMap();
//			attrspec.put("attrspec", attrSpecList);
//			req.setQueryParams(attrspec);
//			cache.set(NAMESPACE,"attrspec",req);
//		}else{
//			attrSpecList =(List<Map>) req.getQueryParams().get("attrspec");
//		}
//	
//		long end = System.currentTimeMillis();
////		logger.info((end-start)+"查询属性规格数据时间============>");
//		List<AttrInstBusiRequest> attrInstBusiRequests = new ArrayList<AttrInstBusiRequest>();
//		
//		//订单基本信息属性
//		Map orderTreeMap = AttrInstRedisTools.genAttrInstFromOrderTables(order_id, t_prefix);
////		logger.info((end-start)+"查询属性实力数据获取时间============>");
//		for (Map attrSpec:attrSpecList) {
//			String field_name =(String) attrSpec.get("field_name");
//			String field_desc =(String) attrSpec.get("field_desc");
//			String field_attr_id =(String) attrSpec.get("field_attr_id");
//			String table_name =(String) attrSpec.get("table_name");
//			String t_field_name =(String) attrSpec.get("t_field_name");
//			String attr_code =(String)attrSpec.get("attr_code");
//			Object field_value = AttrInstRedisTools.getFieldValueByTableNameAndFieldName(table_name, order_id,"", t_field_name, orderTreeMap);//orderExt.get(t_field_name);
//			String str_field_value ="";
//			AttrDef ad = new AttrDef();
//			ad.setField_attr_id(field_attr_id);
//			ad.setAttr_spec_id(EcsOrderConsts.ATTR_SPEC_ID_999);
//			ad.setField_attr_id(field_attr_id);
//			ad.setField_desc(field_desc);
//			ad.setField_name(field_name);
//			if( field_value instanceof Timestamp){
//				if(field_value !=null){
//					DateFormat sdf = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
//					str_field_value = sdf.format(field_value);
//				}
//			}else if( field_value instanceof Long || field_value instanceof BigDecimal ||  field_value instanceof Integer)
//				str_field_value = field_value+"";
//			else {
//				str_field_value = (String)field_value;
//			}
//			//add by wui描述信息写入
//			String field_value_desc ="";
//			if(!StringUtil.isEmpty(str_field_value) && !StringUtil.isEmpty(attr_code)){
//				List<Map> list = getConsts(attr_code);
//				if(list!=null && list.size()>0){
//					for(Map am:list){
//						String a_value = (String) am.get("value");
//						if(str_field_value.equals(a_value)){
//							field_value_desc = (String) am.get("value_desc");
//							break ;
//						}
//					}
//				}
//			}
//			AttrInstBusiRequest attrInstBusiRequest = packageAttrInst(ad, str_field_value, str_field_value, order_id,field_value_desc);
//			attrInstBusiRequest.setSpec_is_null((String)attrSpec.get("is_null"));
//			attrInstBusiRequest.setSpec_is_edit((String)attrSpec.get("is_edit"));
//			attrInstBusiRequest.setNeed_query(ConstsCore.CONSTS_YES);
//			attrInstBusiRequest.setInst_id(order_id);
//			attrInstBusiRequest.setT_field_name(t_field_name);
//			attrInstBusiRequest.setT_table_name(table_name);
//			attrInstBusiRequest.setSub_attr_spec_type("");
//			attrInstBusiRequest.setAsy(ConstsCore.CONSTS_NO);
//			attrInstBusiRequests.add(attrInstBusiRequest);
//		}
//		return attrInstBusiRequests;
//	}
	
	public List<Map> getConsts(String key){
		DcPublicSQLInfoCacheProxy dcPublicSqlCache = new DcPublicSQLInfoCacheProxy();
        List<Map> list = dcPublicSqlCache.getDropdownData(key);
        return list;
	}
	
	/**
	 * 插入订单属性实例表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-28 
	 * @param attrDef
	 * @param i_value
	 * @param o_value
	 * @param order_id
	 * @param inst_id
	 */
	public AttrInstBusiRequest packageAttrInst(AttrDef attrDef,String i_value,String o_value,String order_id,String value_desc){
		AttrInstBusiRequest attrInst = new AttrInstBusiRequest();
		attrInst.setAttr_inst_id(attrDef.getField_attr_id()); //add by wui，纵表转横表特殊处理
		attrInst.setOrder_id(order_id);
		attrInst.setInst_id("");
		attrInst.setAttr_spec_id(attrDef.getAttr_spec_id());
		attrInst.setField_attr_id(attrDef.getField_attr_id());
		attrInst.setField_name(attrDef.getField_name());
		attrInst.setFiled_desc(attrDef.getField_desc());
		attrInst.setField_value(i_value);
		attrInst.setSec_field_value(o_value);
		attrInst.setField_value_desc(value_desc);
		attrInst.setCreate_date(DBTUtil.current());
		return attrInst;
	}
	
	@SuppressWarnings("rawtypes")
	private List parseStr(String errorStr) {
		List<String> errorsList = new ArrayList<String>();
		parseCicleStr(errorsList, errorStr);
		return errorsList;
	}

	private void parseCicleStr(List<String> errorsList, String errorStr) {
		int splitPoint = 3500;
		for (int i = 0; i <10; i++) {
			if(StringUtil.isEmpty(errorStr))
				break;
			if(errorStr.length()<=splitPoint){
				errorsList.add(errorStr);
				break;
			}
			String error_info = errorStr.substring(0, splitPoint).toString();
			errorsList.add(error_info);
			errorStr = errorStr.substring(splitPoint);
		}
	}
	
	private boolean cancache(ZteBusiRequest zteRequest) {
		//有效期计算，缺省缓存前后两个月期限的订单
		boolean canCache = false;
		if(zteRequest !=null && zteRequest instanceof OrderTreeBusiRequest)
		{
			OrderTreeBusiRequest orderTree = (OrderTreeBusiRequest)zteRequest;
			String create_time = orderTree.getCreate_time();
			String col10 =orderTree.getCol10();
			if(StringUtil.isEmpty(create_time) || Consts.SYSDATE.equals(create_time))
				canCache = true;
			else{
				long ordCTime = DateFormatUtils.parseStrToDate(create_time, CrmConstants.DATE_TIME_FORMAT).getTime();
				long sysTime = System.currentTimeMillis();
				long hours = (ordCTime-sysTime)/ 1000/3600;
				//前后一个月内的订单提供缓存,超过一个月则不提供缓存
				if(Math.abs(hours)>2160) //720*3 缓存前后三个月的单子
					canCache = false;
				else
					canCache = true;
			}
			if(ConstsCore.CONSTS_YES.equals(col10)) //历史单查询，则只缓存5分钟，不做长时间缓存 add by wui
				canCache =false;
				
		}
		return canCache;
	}
	
	/**
	 * 缓存更新对象
	 * @param cache_key
	 * @param zteRequest
	 */
	@SuppressWarnings({ "rawtypes"})
	public void cacheRequestInst(String cache_key,ZteBusiRequest updateRequest){
		RequestBeanDefinition definition = BusiUtils.getRequestServiceDefinition(updateRequest);
		if(StringUtil.isEmpty(cache_key))
			return;
		if(definition !=null && ConstsCore.CONSTS_YES.equals(definition.getCache_store())){
			//数据保存后，判断缓存中是否存在，不存在则读取数据库获取缓存
//			Object cacheRequest=getBusiCacheValue(cache_key);
//			if(cacheRequest instanceof String) //获取缓存，值为String则转对象
//			{
//				long begin =System.currentTimeMillis();
//				cacheRequest =CommonTools.jsonToBean((String)cacheRequest, cacheRequest.getClass());
//				long end =System.currentTimeMillis();
//			}
//			if(cacheRequest ==null && updateRequest instanceof OrderTreeBusiRequest){
//				OrderTreeBusiRequest tree = (OrderTreeBusiRequest)updateRequest;
//				//OrderActionRuleServ.java 首次更新更新则，直接设置内存处理actionConfirm add by wui
//				if(tree !=null &&  !StringUtil.isEmpty(tree.getOrder_id()) && StringUtil.isEmpty(tree.getOrderBusiRequest().getOrder_id()) )
//					updateRequest = updateRequest;
//				else{
//					updateRequest = updateRequest;//CommonDataFactory.getInstance().getOrderTree(tree.getOrder_id());
//				}
//			}
			boolean canCache = cancache(updateRequest);
			//设置对象缓存时间，不缓存对象缺省缓存5分钟
			if(updateRequest !=null){
				setBusiCacheValue(updateRequest, cache_key, canCache);
			}
		}
	}
	
	/**
	 * 订单缓存
	 */
	@Override
	public void cacheOrderTree(OrderTreeBusiRequest orderTreeBusiRequest) {
		RequestBeanDefinition definition = BusiUtils.getRequestServiceDefinition(orderTreeBusiRequest);
		String cache_key = BusiUtils.genCacheKey(definition.getService_name(),orderTreeBusiRequest);
		cacheRequestInst(cache_key, orderTreeBusiRequest);
	}
	
	/**
	 * 更新实体数据
	 * @param order_id
	 * @return
	 * @throws Exception 
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteInstUpdateResponse updatePofZteRequestInst(ZteInstUpdateRequest req){
		RequestBeanDefinition servicebean = BusiUtils.getRequestServiceDefinition(req.getUpdateRequest());
		ZteBusiRequest updateZteRequest = req.getUpdateRequest();
		String cache_key = BusiUtils.genCacheKey(servicebean.getService_name(),updateZteRequest);
		ZteInstUpdateResponse resp = new ZteInstUpdateResponse();
//		String is_dirty = updateZteRequest.getIs_dirty();
		ZteBusiRequest storeRequest = null;
		//判断是否存入cache 是:写入缓存,异步保存数据库;否:异步写入数据库
		try {
			if(ConstsCore.CONSTS_YES.equals(servicebean.getCache_store())){
				try {
					cacheRequestInst(cache_key,storeRequest);
					//异步写入数据库
					storeRequest = busiManager.pofAsyDbStore(updateZteRequest); 
				} catch (Exception e) {
					//失败同步写入数据库
					storeRequest =  busiManager.pofDbStore(updateZteRequest);
				}
			}else{
				//非cache同步写入数据库
				storeRequest =  busiManager.pofDbStore(updateZteRequest);
			}
			cacheRequestInst(cache_key,storeRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setQueryObject(storeRequest);
		return resp;
	}
	@Override
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request){
		return this.busiManager.insertZteRequestInst(request);
	}
}
