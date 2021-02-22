package com.ztesoft.net.mall.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.jdbc.core.RowMapper;

import params.ZteBusiRequest;
import params.ZteClsRequest;
import params.ZteRequest;
import params.ZteResponse;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.common.annontion.context.request.RequestFieldDefinition;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.ZteBusiWraperRequest;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.ILogsServices;
import zte.params.order.req.AttrInstValueEditReq;
import zte.params.order.resp.AttrInstValueEditResp;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.cache.CacheList;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupportRop;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.StoreErrLog;
import com.ztesoft.net.mall.core.service.IStoreErrLogManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicSQLInfoCacheProxy;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrTable;
import com.ztesoft.net.model.AttrTableCache;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.util.CacheUtils;

import commons.CommonTools;
import consts.ConstsCore;

public class BusiManager extends BaseSupportRop  implements IBusiManager{
	
	@Resource
	private ILogsServices logsServices;
	private IDaoSupport baseDaoSupport;
 	private ICacheUtil cacheUtil;
	private void init(){
		logsServices = SpringContextHolder.getBean("logsServices");
	}
	/**
	 * 实体对象异步查询处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({"rawtypes" })
	public  ZteBusiRequest asyDbQuery(ZteBusiRequest request){
		//异步插入数据库操作
//		AsynServiceConfig confgiInfo=new AsynServiceConfig(new BusinessHandler(){
//			public void execute(ZteBusiRequest zteRequest) {
				 //业务处理逻辑
				dbQuery((ZteInstQueryRequest)request);
//			}
//		});
//		DisruptorInstFactory.getInst().submit(confgiInfo, disruptor);
//		
		return request;
	}
	
	
	
	/**
	 * 异步归档历史数据,单线程异步执行
	 * @param insertSql
	 */
	public void asyInsertHis(String insertSql){
		
		this.baseDaoSupport.execute(insertSql,null);
		
//		AsynServiceConfig confgiInfo=new AsynServiceConfig(new BusinessHandler(){
//			public void execute(ZteBusiRequest request) {
//				ZteCrudBusiRequest req = (ZteCrudBusiRequest)request;
//				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
//				support.execute(req.getInsertSql(), null);
//			}
//		});
//		ZteCrudBusiRequest crudBusi = new ZteCrudBusiRequest();
//		crudBusi.setInsertSql(insertSql );
//		confgiInfo.setZteRequest(crudBusi);
//		DisruptorInstFactory.getInst().submit(confgiInfo, disruptor);
	}
	
	/**
	 * 异步插入数据库,单线程异步执行
	 * @param insertSql
	 */
	public void asyInsert(String table_name,Map insertMap){
		this.init();
		//历史表更新
		String order_id =(String) insertMap.get("order_id");
		if(!StringUtil.isEmpty(order_id)){
			OrderTreeBusiRequest orderTreeBusiRequest = logsServices.getOrderTreeFromCache(order_id);
			if(orderTreeBusiRequest !=null && ConstsCore.CONSTS_YES.equals(orderTreeBusiRequest.getCol10()))
				table_name =table_name+"_his";
		}
		this.baseDaoSupport.insert(table_name,insertMap);
	}
	public void asyDelete(String table_name,String order_id,ZteBusiRequest busiRequest){
		this.init();
		String sql = "delete from "+table_name+" where order_id = '"+order_id+"' ";
		String busiPrimSql = BusiUtils.getPrimperySql(busiRequest);
		sql = sql + busiPrimSql;
		this.baseDaoSupport.execute(sql);
	}
	
	
	/**
	 * 异步更新数据库,单线程异步执行
	 * @param insertSql
	 */
	public void asyUpdate(String table_name,Map insertMap,Map keyMap,ZteBusiRequest request) throws Exception{
		//历史表更新
		this.init();
		String order_id = (String) MethodUtils.invokeMethod(request, "getOrder_id", null);
		if(!StringUtil.isEmpty(order_id)){
			OrderTreeBusiRequest orderTreeBusiRequest = logsServices.getOrderTreeFromCache(order_id);
			if(orderTreeBusiRequest !=null && ConstsCore.CONSTS_YES.equals(orderTreeBusiRequest.getCol10()))
				table_name =table_name+"_his";
		}
		if(!"es_order_trees".equals(table_name)){
			insertMap.remove("order_id");
			this.baseDaoSupport.update(table_name,insertMap,keyMap);
		}
	}
	
	/**
	 * 实体对象异步入库处理,对象不返回
	 * @param request
	 * @return
	 * @throws Exception
	 */

	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public  ZteBusiRequest asyDbStore(ZteBusiRequest request){
		ZteClsRequest clsReq = new ZteClsRequest();
		clsReq.setZteBusiRequest(request);
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(clsReq) {
			@Override
			public ZteResponse execute(ZteRequest zteRequest) {
				//全量查询订单树'
				ZteClsRequest clsReq =(ZteClsRequest)zteRequest;
					 //业务处理逻辑
				dbStore(clsReq.getZteBusiRequest());
				return new ZteResponse();
			}
		});
		ThreadPoolFactory.getDefaultThreadPoolExector().execute(taskThreadPool);
		return null;
	}
	
	
	/**
	 * 对象存储数据库，同步将结果render到对象节点上处理
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteBusiRequest dbStore(ZteBusiRequest request){
		try{
			if(request instanceof AttrInstBusiRequest){
				long begin = System.currentTimeMillis();
				AttrInstBusiRequest ar = (AttrInstBusiRequest) request;
				if(StringUtil.isEmpty(ar.getAttr_spec_id()))
				{
					 AttrDef attrDef = CacheUtils.getCacheAttrDef(ar.getField_name()+EcsOrderConsts.ATTR_SPEC_ID_999);
					 ar.setAttr_spec_id(attrDef.getAttr_spec_id());
					 ar.setField_attr_id(attrDef.getField_attr_id());
					 ar.setFiled_desc(attrDef.getField_desc());
					 ar.setAttr_inst_id(attrDef.getField_attr_id());
				}
				request = updateAttrInst((AttrInstBusiRequest)request,ConstsCore.CONSTS_NO);
				if(request !=null && !StringUtil.isEmpty(((AttrInstBusiRequest) request).getField_value_desc()))
					ar.setField_value_desc(((AttrInstBusiRequest) request).getField_value_desc());
				AttrInstRedisTools.setAttrInstBusiRequestsToRedis(ar);
				
				long end = System.currentTimeMillis();
				logger.info(((AttrInstBusiRequest) request).getOrder_id()+"属性"+ar.getField_name()+"处理器执行操作时间=========>"+(end-begin));
				return request;
			}
			
			List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition(request);
			String attr_inst_from_redis ="yes";//cacheUtil.getConfigInfo(EcsOrderConsts.ATTR_INST_FROM_REDIS);
			if(serviceDefinition == null || fieldDefinitions ==null || fieldDefinitions.size() ==0)
				return request;
			//非脏数据，不处理
			if(!ConstsCore.CONSTS_YES.equals(request.getIs_dirty()))
				return request;
			
			//非数据库存储,直接退出
			if(!(ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type()) || ConstsCore.STORE_TYPE_JAVA.equals(serviceDefinition.getStore_type())))
				return request;
			
			String table_name = serviceDefinition.getTable_name();
			
			String db_action = BusiUtils.getDbOper(request,null); //获取操作
			List<String> changeFields = new ArrayList<String>();
			List<String> vChangeFields  = new ArrayList<String>();
			if(ConstsCore.DB_ACTION_INSERT.equals(db_action)||ConstsCore.DB_ACTION_DELETE.equals(db_action)){ //新增操作
				vChangeFields.add(EcsOrderConsts.ALL_FIELDS);
				request.setChangeFiels(vChangeFields);
			}else{
				ZteBusiRequest odlBusiRequest = BusiUtils.getOldZteBusiRequestFromOrderTree(request); //修改变动字段
				request.getChangeFiels().addAll(BusiUtils.getChageFields(odlBusiRequest, request));//获取变动字段
				vChangeFields = BusiUtils.getChangeValueFields(request.getChangeFiels(), fieldDefinitions,request); //获取代变更的值
			}
			if(!ListUtil.isEmpty(vChangeFields)&& vChangeFields.get(0).equals(EcsOrderConsts.ALL_FIELDS)){ //变更字段为空，则判断是否为全部变更
				changeFields = BusiUtils.getAllChageFields(null, request, fieldDefinitions);
				vChangeFields = changeFields;
				request.setChangeFiels(changeFields);
			}
			//数据入库处理
			Map insertsMaps = new HashMap();
			for(String field_name:vChangeFields){ //add by wui优化，修改值只修改单个实体对象，不考虑联集变更情况
				//add by wui更新orderTree，临时解决方案
				if(field_name.equals("es_order_items_prod") || field_name.equals("es_order_items_ext") || field_name.equals("es_order_items_apt_prints")
						 || field_name.equals("es_order_items_prod_ext"))
					field_name ="es_order_items";
				if(field_name.equals("es_attr_gift_param"))
					field_name ="es_attr_gift_info";
				
				RequestFieldDefinition  fieldDefinition=BusiUtils.getRequestFieldDefinitionByName(field_name, fieldDefinitions);
				if(fieldDefinition == null){
					continue;
				}
				if(!ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert())) //验证字段是否需要插入
					continue;
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String dname = fieldDefinition.getDname();
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					ZteBusiRequest subZteRequest = (ZteBusiRequest) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					subZteRequest = dbStore(subZteRequest);
					dbstoreValueWrapper(request,dname,fname,subZteRequest,insertsMaps);
				}else if("java.util.List".equals(type))
				{
					List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					List<ZteBusiRequest> psubZteRequests = new ArrayList<ZteBusiRequest>();
					for (ZteBusiRequest zteRequest:subZteRequests) {
						psubZteRequests.add(dbStore(zteRequest)); //数据存储
					}
					dbstoreValueWrapper(request,dname,fname,psubZteRequests,insertsMaps);
				}else{
					Object value =  MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					if(null !=value && value instanceof String)
						if(ConstsCore.NULL_VAL.equals(value)){
							value ="";
							MethodUtils.invokeMethod(request, "set"+fname.substring(0,1).toUpperCase()+fname.substring(1),value);
						}
					insertsMaps.put(fieldDefinition.getDname(),value); //获取字段值,优化后处理
				}
			}
//			}
			//非数据库操作，则不处理
			if(!ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type()))
				return request;
			//数据库操作处理
			if(!ListUtil.isEmpty(vChangeFields)){
				if(ConstsCore.DB_ACTION_INSERT.equals(db_action)){
					if("es_order_trees".equals(table_name)) //性能优化处理add by wui首次不写日志
						this.baseDaoSupport.execute("insert into es_order_trees (ORDER_ID, CREATE_TIME, UPDATE_TIME, SOURCE_FROM) values(?,sysdate,sysdate,'" + ManagerUtils.getSourceFrom() + "')", insertsMaps.get("order_id"));
//					else if("es_attr_inst".equals(table_name)){ //add by wui，属性实例插入，获取规格数据
//						AttrInstBusiRequest ar = (AttrInstBusiRequest) request;
//						if(StringUtil.isEmpty(ar.getAttr_spec_id()))
//						{
//							 AttrDef attrDef = CacheUtils.getCacheAttrDef(ar.getField_name()+EcsOrderConsts.ATTR_SPEC_ID_999);
//							 ar.setAttr_spec_id(attrDef.getAttr_spec_id());
//							 ar.setField_attr_id(attrDef.getField_attr_id());
//							 ar.setFiled_desc(attrDef.getField_desc());
//							 insertsMaps.put("attr_spec_id", attrDef.getAttr_spec_id());
//							 insertsMaps.put("field_attr_id", attrDef.getField_attr_id());
//							 insertsMaps.put("filed_desc",attrDef.getField_desc());
//						}
//						//插入完成二次做更新操作
//						
//						//属性数据redis改造，add by wui
//						if(ConstsCore.CONSTS_YES.equals(attr_inst_from_redis)){
//							AttrInstRedisTools.setAttrInstBusiRequestsToRedis(ar);
//						}else{
//							asyInsert(table_name,insertsMaps);
//						}
//						//设置缓存，方便更新获取到数据
////						OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(ar.getOrder_id());
////						orderTreeBusiRequest.getAttrInstBusiRequests().add(ar);
////						logsServices.cacheOrderTree(orderTreeBusiRequest);
//						request = updateAttrInst(request,ConstsCore.CONSTS_NO);
//					}
					else{
						asyInsert(table_name,insertsMaps);
					}
				}else if(ConstsCore.DB_ACTION_UPDATE.equals(db_action)){
					Map fieldMap = new HashMap(),whereMap = new HashMap();
					if(ConstsCore.CONSTS_YES.equals(serviceDefinition.getTable_archive())){
						if(request instanceof OrderTreeBusiRequest){
							//add by wui暂时关闭日志写入
//							asyInsertHis("insert into "+table_name+"_his (order_id,es_order,es_order_ext,es_order_items,es_attr_inst,es_delivery,es_payment_logs,es_order_items_ext,create_time,update_time,source_from) select a.order_id,a.es_order,a.es_order_ext,a.es_order_items,a.es_attr_inst,a.es_delivery,a.es_payment_logs,a.es_order_items_ext,a.create_time,sysdate,a.source_from from "+table_name+" a where 1=1 "+BusiUtils.getPrimperySql(request));
						}
					}
					//更新处理
//					if("es_attr_inst".equals(table_name))
//					{
//						String update_attr = ConstsCore.CONSTS_YES;
//						if(ConstsCore.CONSTS_YES.equals(attr_inst_from_redis)){ //add  by wui
//							AttrInstRedisTools.setAttrInstBusiRequestsToRedis((AttrInstBusiRequest) request);
//							update_attr = ConstsCore.CONSTS_NO;
//						}
//						request = updateAttrInst(request,update_attr);
//					}else{
						Map updateMap =insertsMaps;//此字段已是变动过后的属性字段,不需要在递归循环
						Map keyMap =  BusiUtils.getWhereFields(serviceDefinition, request, whereMap);
						updateMap.putAll(keyMap);
						//异步更新操作
						asyUpdate(table_name, updateMap, keyMap,request);
//					}
				}else if(ConstsCore.DB_ACTION_DELETE.equals(db_action)){
					//es_order_trees和es_attr_inst不做删除操作
					if(!"es_order_trees".equals(table_name)&&!"es_attr_inst".equals(table_name)){
						//历史数据不能删除
						if(!table_name.endsWith("his")){
							asyDelete(table_name,insertsMaps.get("order_id").toString(),request);
							OrderTreeBusiRequest orderTreeBusiRequest = BusiUtils.removeCacheOrderTree(request);
							logsServices.cacheOrderTree(orderTreeBusiRequest);
							return null;
						}
					}
				}
			}
			
			request.setIs_dirty(ConstsCore.IS_DIRTY_NO);
			request.setDb_action("");
			
		}catch(Exception e){
			e.printStackTrace();
			CommonTools.addFailError("RequestStoreUtils.dbstore出错"+e.getLocalizedMessage());
		}
		return request;
	}
	
	private ZteBusiRequest updateAttrInst(AttrInstBusiRequest request,String need_update_attr) throws Exception{
		AttrInstBusiRequest ar = request;
		long begin = System.currentTimeMillis();
		AttrInstValueEditReq req = new AttrInstValueEditReq();
		String order_id =ar.getOrder_id();
		req.setAttr_inst_id(ar.getAttr_inst_id());
		req.setValue(ar.getField_value());
		req.setNeed_update_attr("NO");
		req.setField_name(ar.getField_name());
		req.setOrder_id(order_id);
		AttrInstValueEditResp resp = this.editAttrInstValue(req);
		long end = System.currentTimeMillis();
		logger.info(ar.getOrder_id()+"属性写入数据库时间：============================》"+(end-begin));
		List<String> changeFiels = new ArrayList<String>();
		changeFiels.add("field_value");
		changeFiels.add("field_value_desc");
		ar.setField_value(resp.getField_value());
		ar.setField_value_desc(resp.getField_value_desc());
		ar.setChangeFiels(changeFiels);
		//订单属性实例更新扩展表
		long begin1 = System.currentTimeMillis();
		attrInstValueSyOrdeTreeExtInst(ar);
		long end1 = System.currentTimeMillis();
		logger.info(ar.getOrder_id()+"属性更新扩展表时间：============================》"+(end1-begin1));
		request = ar;
		return request;
	}
	
	
	public AttrInstValueEditResp editAttrInstValue(AttrInstValueEditReq req) {
		AttrInstValueEditResp resp = new AttrInstValueEditResp();
		try {
			resp = this.updateAttrInst(req.getOrder_id(),req.getNeed_update_attr(), req.getField_name(),req.getAttr_inst_id(), req.getValue());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("修改失败");
		}
		return resp;
	}

	
	public AttrInstValueEditResp updateAttrInst(String order_id,String need_update_attr,String field_name,String attr_inst_id, String value) throws Exception{
		AttrInstValueEditResp resp = new AttrInstValueEditResp();
		AttrDef attrDef = CacheUtils.getCacheAttrDef(field_name+EcsOrderConsts.ATTR_SPEC_ID_999);
		AttrDef def = attrDef;
		String attr_code = def.getAttr_code();
		String value_desc = null;
		if(!StringUtil.isEmpty(value) && !StringUtil.isEmpty(attr_code)){
			List<Map> list = getConsts(attr_code);
			if(list!=null && list.size()>0){
				for(Map am:list){
					String a_value = (String) am.get("value");
					if(value.equals(a_value)){
						value_desc = (String) am.get("value_desc");
						break ;
					}
				}
			}
		}
		this.updateAttrInstExtValue(order_id,attr_inst_id, value,def);
		resp.setField_value(value);
		resp.setField_value_desc(value_desc);
		return resp;
	}
	public List<Map> getConsts(String key){
		DcPublicSQLInfoCacheProxy dcPublicSqlCache = new DcPublicSQLInfoCacheProxy();
        List<Map> list = dcPublicSqlCache.getDropdownData(key);
        return list;
	}
	/**
	 * 修改扩展表属字段
	 * @作者 MoChunrun
	 * @创建日期 2014-9-23 
	 * @param attr_inst_id
	 * @param value
	 * @throws Exception
	 */
	
	private void updateAttrInstExtValue(String order_id,String attr_inst_id,String value,AttrDef def)throws Exception{
		if(def!=null){
			//修改属性ext表
			List<AttrTable> tbs =this.queryAttrInstTables(def.getField_attr_id());
			if(ListUtil.isEmpty(tbs))
			{
				String sql = SF.orderSql("QUERY_ATTR_INST_TABLES");
				tbs = this.baseDaoSupport.queryForList(sql, AttrTable.class,def.getField_attr_id());
			}
			if(tbs!=null && tbs.size()>0){
				for(AttrTable at:tbs){
					String tableName = at.getTable_name();
					String fieldName = at.getField_name();
					if(!StringUtil.isEmpty(tableName) && !StringUtil.isEmpty(fieldName)){
						String pk ="order_id";
						String usql ="";
						if(!StringUtil.isEmpty(value) && ManagerUtils.timeMatch(value)){
							value = " to_date('"+value+"','"+ManagerUtils.getSqlFormatStr(value)+"')";
							usql = "update "+tableName+" set "+fieldName+"="+value+" where " + pk + "=?";
							this.baseDaoSupport.execute(usql,order_id);
						}else{
							usql = "update "+tableName+" set "+fieldName+"=? where " + pk + "=?";
							if(Const.SYSDATE.equals(value)){
								usql = "update "+tableName+" set "+fieldName+"="+value+" where " + pk + "=?";								
								this.baseDaoSupport.execute(usql, order_id);
							}else{
								this.baseDaoSupport.execute(usql, value,order_id);
							}
						}
						
					}
				}
			}
		}
	}
	
	public static final ThreadLocal<Boolean> CACHE_LOCAL = new ThreadLocal<Boolean>();
	public static boolean _CACHE_FLAG = true;
	private INetCache cache = CacheFactory.getCacheByType("");
	public static int space = Const.CACHE_SPACE;
	/**
	 * 按属性实例数据查询对应的扩展表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-28 
	 * @param attr_inst_id
	 * @return
	 */
	private List<AttrTable> queryAttrInstTables(String attr_field_id){
		String sql = SF.orderSql("QUERY_ATTR_INST_TABLES");
		List<AttrTable> list = null;
		String CACHE_KEY = CacheUtils.ATTR_DEF_TABLE_KEY+attr_field_id;
		Boolean tFlag = CACHE_LOCAL.get();
		if(tFlag==null)tFlag = false;
		if(_CACHE_FLAG && !tFlag){
			list =(List<AttrTable>)cache.get(space, CACHE_KEY);
			if(list!=null)return list;
		}
		list = this.baseDaoSupport.queryForList(sql, AttrTable.class,attr_field_id);
		if(list!=null && _CACHE_FLAG){
			CacheList<AttrTable> clist = new CacheList<AttrTable>();
			clist.addAll(list);
			cache.set(space, CACHE_KEY,clist);
		}
		return list;
	}
	
	/**
	 * 属性更新订单树
	 * @param request
	 */
	public void attrInstValueSyOrdeTreeExtInst(AttrInstBusiRequest request){
		try{
			this.init();
			if(StringUtil.isEmpty(request.getField_name()))
				return;
			AttrDef attrDef = new AttrDef();
			attrDef = CacheUtils.getCacheAttrDef(request.getField_name()+EcsOrderConsts.ATTR_SPEC_ID_999);
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(request.getOrder_id());
			List<AttrTable> tbs = CacheUtils.getCacheAttrTable(attrDef.getField_attr_id());
			if(ListUtil.isEmpty(tbs))
				return;
			for (AttrTable tb:tbs) {
				String ext_table_name = tb.getTable_name();
				RequestBeanDefinition tableBeanDefinition = BusiUtils.getRequestServiceDefinitionByTableName(ext_table_name);
				if(tableBeanDefinition !=null)
				{
					StringBuffer primpekSql =new StringBuffer();
					String inst_id =request.getInst_id();
					if(StringUtil.isEmpty(inst_id))
						inst_id = request.getOrder_id();
					primpekSql.append(" and ").append(tableBeanDefinition.getPrimary_keys()).append(" = '").append(inst_id+"'");
					ZteBusiRequest busiRequest = BusiUtils.getZteBusiRequestByServiceBean(orderTree, tableBeanDefinition,primpekSql.toString());
					if(busiRequest !=null)
					{
						String value =request.getField_value();
						if(StringUtil.isEmpty(value))
							value ="";
						Object field_value = null;
						Class clazz = getFieldType(busiRequest, tb.getField_name());
						if(null != clazz && Long.class.equals(clazz)){
							field_value = Long.valueOf(value);
						}else if(null != clazz && Integer.class.equals(clazz)){
							field_value = Integer.valueOf(value);
						}else {
							field_value = value;
						}
						MethodUtils.invokeMethod(busiRequest, "set"+tb.getField_name().substring(0,1).toUpperCase()+tb.getField_name().substring(1), field_value);
						//设置变动字段
						List<String> changeFiels = new ArrayList<String>();
						changeFiels.add(tb.getField_name());
						busiRequest.setChangeFiels(changeFiels);
						//更新对象
						OrderTreeBusiRequest orderTreeBusiRequest= BusiUtils.updateCacheOrderTree(busiRequest);
						logsServices.cacheOrderTree(orderTreeBusiRequest);
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

		
	
	//封装值处理
	@SuppressWarnings({ "rawtypes","unchecked"})
	public void dbstoreValueWrapper(ZteBusiRequest zteRequest,String dname,String name,Object psubZteRequests,Map insertsMaps) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		//设置数据库字段值
		ZteBusiWraperRequest zteBusiListRequest = new ZteBusiWraperRequest();
		zteBusiListRequest.setField_name(dname);
		zteBusiListRequest.setField_value(psubZteRequests);
//		if(zteRequest instanceof OrderTreeBusiRequest){
//			String value = CommonTools.beanToJson(zteBusiListRequest); //对象转换,空字段不处理 
	//		logger.info(dname+"对象序列化总耗时："+(end-start));
//			insertsMaps.put(dname, value);
//			value ="";
//		}
		//add by wui属性修改不需要更新到订单Tree数据库，全量从数据库读取
		MethodUtils.invokeMethod(zteRequest, "set"+name.substring(0,1).toUpperCase()+name.substring(1), psubZteRequests);
	}
	
	/**
	 * 实体对象数据查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  <T> T dbQuery(ZteInstQueryRequest<? extends ZteBusiRequest> instQuery){
		
		T queryRequestEntry = (T) instQuery.getQueryObject();
		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition((ZteBusiRequest)queryRequestEntry);
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition((ZteBusiRequest)queryRequestEntry);
		if(serviceDefinition ==null || fieldDefinitions ==null || fieldDefinitions.size() ==0)
			return queryRequestEntry;
		if(!ConstsCore.CONSTS_YES.equals(serviceDefinition.getQuery_store()))//非查询，则不处理
			return queryRequestEntry;
		try{
			//orderTree特殊处理装载
			if(queryRequestEntry instanceof OrderTreeBusiRequest){
				String old_need_query  = ((OrderTreeBusiRequest) queryRequestEntry).getNeed_query();
				//.............
				instQuery.getQueryParmas().put(ConstsCore.QUERY_SQL_CONSTS, BusiUtils.getQuerySqlsByFieldDefines(instQuery,serviceDefinition.getService_name()));
				queryRequestEntry  = (T) MethodUtils.invokeMethod(queryRequestEntry, "load", instQuery);
				((OrderTreeBusiRequest) queryRequestEntry).setNeed_query(old_need_query);
				instQuery.getQueryParmas().put(ConstsCore.DYPARAM_PREFIX+serviceDefinition.getService_name(),queryRequestEntry);
			}
//			if(queryRequestEntry instanceof OrderItemBusiRequest)
//				logger.info("111111111");
			//load下级信息
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				//下级节点是否需要查询,或者强制查询
				String fname = fieldDefinition.getField().getName();
				String service_name = fieldDefinition.getService_name();
//				if(fname.equals("orderItemsAptPrintsRequests"))
//					logger.info("111111111");
				if(ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_query()) || ConstsCore.CONSTS_YES.equals(((ZteBusiRequest)queryRequestEntry).getNeed_query())){ //子节点需要存储则处理
					RequestBeanDefinition<ZteBusiRequest> subBeanDefinition = BusiUtils.getContext().getActionRequestServiceMap(service_name); //获取field归属服务
					if(subBeanDefinition ==null)
						continue;
					Object serviceBean =org.springframework.beans.BeanUtils.instantiateClass(subBeanDefinition.getBeanClass());
					if(serviceBean instanceof ZteBusiRequest){
						
						//获取当前实例值
						MethodUtils.invokeMethod(instQuery, "setQueryObject", serviceBean);
						instQuery.getQueryParmas().putAll(setQueryParamsByZteBusiRequest((ZteBusiRequest)queryRequestEntry));
						instQuery.getQueryParmas().put("queryRequestEntry", queryRequestEntry);
//						instQuery.getQueryParmas().put("query_his_table", value)
						instQuery.getQueryParmas().put(ConstsCore.QUERY_SQL_CONSTS, BusiUtils.getQuerySqlsByFieldDefines(instQuery,subBeanDefinition.getService_name()));
						T subexecResult  = (T) MethodUtils.invokeMethod(serviceBean, "load", instQuery);
						String setMethodName ="set"+fname.substring(0,1).toUpperCase()+fname.substring(1);
						if(subexecResult ==null)
							 continue;
						MethodUtils.invokeMethod(queryRequestEntry, setMethodName, subexecResult);
						if(subexecResult instanceof List)
						{
							List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>)subexecResult; 
							for (ZteBusiRequest subZteRequest:subZteRequests) {
								ZteInstQueryRequest subInstQuery =new ZteInstQueryRequest<ZteBusiRequest>();
								subInstQuery.setQueryObject(subZteRequest);
								subInstQuery.getQueryParmas().putAll(setQueryParamsByZteBusiRequest((ZteBusiRequest)queryRequestEntry));
								subInstQuery.getQueryParmas().put("query_his_table", instQuery.getQueryParmas().get("query_his_table"));
								dbQuery(subInstQuery);
							}
						}else if(subexecResult instanceof ZteBusiRequest){
							ZteInstQueryRequest subInstQuery =new ZteInstQueryRequest<ZteBusiRequest>();
							subInstQuery.setQueryObject((ZteBusiRequest)subexecResult);
							subInstQuery.getQueryParmas().putAll(setQueryParamsByZteBusiRequest((ZteBusiRequest)queryRequestEntry));
							subInstQuery.getQueryParmas().put("query_his_table", instQuery.getQueryParmas().get("query_his_table"));
							dbQuery(subInstQuery);
						}
					}
				}
			}
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		return queryRequestEntry;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Map setQueryParamsByZteBusiRequest(ZteBusiRequest request)
	{
		Map param = new HashMap();
		param.putAll(BusiUtils.getPrimperyMapValues(request));
		param.putAll(BusiUtils.getDefencyMapValues(request));
		return param;
	}
	
	private Class getFieldType(ZteBusiRequest busiRequest, String field_name){
		try{
			Field field = busiRequest.getClass().getDeclaredField(field_name);
			return field.getType();
		}catch(Exception e){
//			e.printStackTrace();
		}
		return null;
	}
	
	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}
	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
	/**
	 * 实体对象异步入库处理,对象不返回
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@Override
	@SuppressWarnings({ "rawtypes" })
	public ZteBusiRequest pofAsyDbStore(ZteBusiRequest request) {
		ZteClsRequest clsReq = new ZteClsRequest();
		clsReq.setZteBusiRequest(request);
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(clsReq) {
			@Override
			public ZteResponse execute(ZteRequest zteRequest) {
				// 全量查询订单树'
				ZteClsRequest clsReq = (ZteClsRequest) zteRequest;
				// 业务处理逻辑
				pofDbStore(clsReq.getZteBusiRequest());
				return new ZteResponse();
			}
		});
		ThreadPoolFactory.getDefaultThreadPoolExector().execute(taskThreadPool);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ZteBusiRequest pofDbStore(ZteBusiRequest request) {

		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(request);
		if (serviceDefinition == null || fieldDefinitions == null || fieldDefinitions.size() == 0)
			return request;
		// 非脏数据，不处理
		if (!ConstsCore.CONSTS_YES.equals(request.getIs_dirty()))
			return request;

		// 非数据库存储,直接退出
		if (!(ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type()) || ConstsCore.STORE_TYPE_JAVA.equals(serviceDefinition.getStore_type())))
			return request;

		String table_name = serviceDefinition.getTable_name();
		//基本字段
		List<String> baseAttrList = new ArrayList<String>();
		//纵表字段
		List<String> dyAttrList = new ArrayList<String>();
		try {
			List<String> changeFields = new ArrayList<String>();

			String db_action = BusiUtils.getDbOper(request, null); // 获取操作
			List<String> vChangeFields = new ArrayList<String>();
			if (ConstsCore.DB_ACTION_INSERT.equals(db_action)) { // 新增操作
				vChangeFields.add(EcsOrderConsts.ALL_FIELDS);
				request.setChangeFiels(vChangeFields);
			} else {
				//cache内获取旧对象..
				ZteBusiRequest odlBusiRequest = BusiUtils.getPofOldZteBusiRequestFromCache(request); 
				//获取变动字段
				request.getChangeFiels().addAll(BusiUtils.getPofChageFields(odlBusiRequest, request));
				//排除非数据库操作字段.并对NULL_VAL进行处理
				vChangeFields = BusiUtils.getPofChangeValueFields(request.getChangeFiels(), fieldDefinitions, request); 
				//判断变动字段属于基本表或者动纵表
				if(!vChangeFields.isEmpty()){
					for(int i = 0; i<vChangeFields.size(); i++){
						//不包含#号则非dy_field内变动字段
						String changeField = vChangeFields.get(i);
						if(!changeField.contains("#")) continue;
						AttrTableCache attrTableCache = AttrUtils.getInstance().getAttrTableByAttrTableFieldName(changeField,"");
//						if(null != attrTableCache && serviceDefinition.getTable_name().equals(attrTableCache.getRel_table_name())){
//							baseAttrList.add(changeField);
//						}else{
//							//纵表属性,删除 vChangeFields
//							vChangeFields.remove(i);
//							dyAttrList.add(changeField);
//							
//						}
					}
				}
			}
			if (!ListUtil.isEmpty(vChangeFields) && vChangeFields.get(0).equals(EcsOrderConsts.ALL_FIELDS)) { // 变更字段为空，则判断是否为全部变更
				changeFields = BusiUtils.getAllChageFields(null, request, fieldDefinitions);
				vChangeFields = changeFields;
				//判断dyn_field内字段 是 基本表或者纵表
				for (Map.Entry<String, Object> entry : ((Map<String, Object>)request.getDyn_field()).entrySet()) {
					AttrTableCache attrTableCache = AttrUtils.getInstance().getAttrTableByAttrTableFieldName(entry.getKey(),"");
//					if(serviceDefinition.getTable_name().equals(attrTableCache.getRel_table_name())){
//						vChangeFields.add(entry.getKey());
//						baseAttrList.add(entry.getKey());
//					}else{
//						dyAttrList.add(entry.getKey()+"#insert");
//					}
				}
				
				request.setChangeFiels(changeFields);
			}
			// 数据入库处理
			Map insertsMaps = new HashMap();
			for (String field_name : vChangeFields) { 
				RequestFieldDefinition fieldDefinition = BusiUtils.getRequestFieldDefinitionByName(field_name, fieldDefinitions);
				if (null == fieldDefinition  && !request.getDyn_field().containsKey(field_name) ) {
					continue;
				}
				if(null != fieldDefinition){
					if (!ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert())) // 验证字段是否需要插入
						continue;
					String type = fieldDefinition.getField().getType().getName(); //字段类型
					String fname = fieldDefinition.getField().getName();
					String dname = fieldDefinition.getDname();
				
					if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1 || type.indexOf("service.") > -1) {
						ZteBusiRequest subZteRequest = (ZteBusiRequest) MethodUtils.invokeMethod(request, "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1), null);
						subZteRequest = pofDbStore(subZteRequest);
						dbstoreValueWrapper(request, dname, fname, subZteRequest, insertsMaps);
					} else if ("java.util.List".equals(type)) {
						List<ZteBusiRequest> subZteRequests = (List<ZteBusiRequest>) MethodUtils.invokeMethod(request,
								"get" + fname.substring(0, 1).toUpperCase() + fname.substring(1), null);
						List<ZteBusiRequest> psubZteRequests = new ArrayList<ZteBusiRequest>();
						for (ZteBusiRequest zteRequest : subZteRequests) {
							psubZteRequests.add(pofDbStore(zteRequest)); // 数据存储
						}
						dbstoreValueWrapper(request, dname, fname, psubZteRequests, insertsMaps);
					}else {
						Object value = MethodUtils.invokeMethod(request, "get" + fname.substring(0, 1).toUpperCase() + fname.substring(1), null);
	
						if (null != value && value instanceof String)
							if (ConstsCore.NULL_VAL.equals(value)) {
								value = "";
								MethodUtils.invokeMethod(request, "set" + fname.substring(0, 1).toUpperCase() + fname.substring(1), value);
							}
						insertsMaps.put(fieldDefinition.getDname(), value); // 获取字段值,优化后处理
					}
				}else{
					for (Map.Entry<String, Object> entry : ((Map<String, Object>)request.getDyn_field()).entrySet()) {
						Object value = entry.getValue();
						if (null != value && value instanceof String && ConstsCore.NULL_VAL.equals(value)) entry.setValue("");
					}
					//基本属性添加
					for(String baseAttr :baseAttrList){
						insertsMaps.put(baseAttr, request.getDyn_field().get(baseAttr));
					}
				}
			}
			// }
			//处理动态字段新增or插入.. 固定了纵表有问题.
			AttrInstBusiRequest attrInstBusiRequest = null;
			List<AttrInstBusiRequest> insertAttrInstList = new ArrayList<AttrInstBusiRequest>();
			List<AttrInstBusiRequest> updateAttrInstList = new ArrayList<AttrInstBusiRequest>();
			for(String dyfield :dyAttrList){
				String [] dyfieldAndOperate  = dyfield.split("#");
				final String operate = dyfieldAndOperate[1];
				attrInstBusiRequest = new AttrInstBusiRequest();
				if("insert".equals(operate)){
					
					insertAttrInstList.add(attrInstBusiRequest);
				}else if("update".equals(operate)){
					updateAttrInstList.add(attrInstBusiRequest);
				}
				
  			}
			
			// 非数据库操作，则不处理
			if (!ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type()))
				return request;
			// 数据库操作处理
			if (!ListUtil.isEmpty(vChangeFields)) {
				if (ConstsCore.DB_ACTION_INSERT.equals(db_action)) {
					
					asyPofInsert(table_name, insertsMaps);
				} else if (ConstsCore.DB_ACTION_UPDATE.equals(db_action)) {
					Map fieldMap = new HashMap(), whereMap = new HashMap();
					
						Map updateMap = insertsMaps;// 此字段已是变动过后的属性字段,不需要在递归循环
						Map keyMap = BusiUtils.getWhereFields(serviceDefinition, request, whereMap);
						updateMap.putAll(keyMap);
						// 异步更新操作
						asyPofUpdate(table_name, updateMap, keyMap, request);
				}
			}
			request.setIs_dirty(ConstsCore.IS_DIRTY_NO);
			request.setDb_action("");

		} catch (Exception e) {
			//记录异常日志
			StoreErrLog log = new StoreErrLog();
			log.setService_name(request.getClass().getName());
			String order_id = "";
			try {
				order_id = (String) MethodUtils.invokeMethod(request, "getOrder_id", null);
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			log.setStore_key(order_id);
			log.setErr_msg(e.getMessage());
			log.setStore_obj(JSONObject.fromObject(request).toString());
			log.setCreate_time("sysdate");
			IStoreErrLogManager storeErrLogManager = SpringContextHolder.getBean("storeErrLogManager");
			storeErrLogManager.log(log);
			e.printStackTrace();
			CommonTools.addFailError("RequestStoreUtils.dbstore出错" + e.getLocalizedMessage());
		}
		return request;

	}
	
	/**
	 * 根据表名称,字段名称获取动态字段保存信息
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	private String getDynfieldDbInfo(String tableName,String fieldName){
		
		return null;
	}
	
	/**
	 * 异步插入数据库,单线程异步执行
	 * @param insertSql
	 */
	public void asyPofInsert(String table_name,Map insertMap){
		this.init();
		//历史表更新
		String order_id =(String) insertMap.get("order_id");
//		if(!StringUtil.isEmpty(order_id)){
//			OrderTreeBusiRequest orderTreeBusiRequest = logsServices.getOrderTreeFromCache(order_id);
//			if(orderTreeBusiRequest !=null && ConstsCore.CONSTS_YES.equals(orderTreeBusiRequest.getCol10()))
//				table_name =table_name+"_his";
//		}
		this.baseDaoSupport.insert(table_name,insertMap);
	}
	
	/**
	 * 异步更新数据库,单线程异步执行
	 * @param insertSql
	 */
	public void asyPofUpdate(String table_name,Map insertMap,Map keyMap,ZteBusiRequest request) throws Exception{
		//历史表更新
		this.init();
//		String order_id = (String) MethodUtils.invokeMethod(request, "getOrder_id", null);
//		if(!StringUtil.isEmpty(order_id)){
//			OrderTreeBusiRequest orderTreeBusiRequest = logsServices.getOrderTreeFromCache(order_id);
//			if(orderTreeBusiRequest !=null && ConstsCore.CONSTS_YES.equals(orderTreeBusiRequest.getCol10()))
//				table_name =table_name+"_his";
//		}
		this.baseDaoSupport.update(table_name,insertMap,keyMap);
	}
	
	

	/**
	 * 实体对象数据查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  <T> T pofDbQuery(ZteInstQueryRequest<? extends ZteBusiRequest> instQuery){
		
		T queryRequestEntry = (T) instQuery.getQueryObject();  
		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition((ZteBusiRequest)queryRequestEntry);
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition((ZteBusiRequest)queryRequestEntry);
		if(serviceDefinition ==null || fieldDefinitions ==null || fieldDefinitions.size() ==0)
			return queryRequestEntry;
		if(!ConstsCore.CONSTS_YES.equals(serviceDefinition.getQuery_store()))//非查询，则不处理
			return queryRequestEntry;
		try{
			//orderTree特殊处理装载
			if(queryRequestEntry instanceof OrderTreeBusiRequest){
				String old_need_query  = ((OrderTreeBusiRequest) queryRequestEntry).getNeed_query();
				//获取基本属性
				instQuery.getQueryParmas().put(ConstsCore.QUERY_SQL_CONSTS, BusiUtils.getQuerySqlsByFieldDefines(instQuery,serviceDefinition.getService_name()));
				queryRequestEntry  = (T) MethodUtils.invokeMethod(queryRequestEntry, "load", instQuery);
				((OrderTreeBusiRequest) queryRequestEntry).setNeed_query(old_need_query);
				instQuery.getQueryParmas().put(ConstsCore.DYPARAM_PREFIX+serviceDefinition.getService_name(),queryRequestEntry);
				//获取dyMap属性
				
				
				//getAttrTableByAttrTableFieldName
				
			}
			
		}catch(Exception e){
//			e.printStackTrace();
		}
		return queryRequestEntry;
	}
	/**
	 * 插入对象
	 * @param table_name
	 * @param insertMap
	 */
	@Override
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest req){
		ZteBusiRequest request = (ZteBusiRequest)req.getZteBusiRequest();
		return insertZteRequestInst(request);
	}
	
	/**
     * 插入对象
     * @param table_name
     * @param insertMap
     */
    @Override
	public ZteBusiResponse insertZteRequestInst(ZteBusiRequest request){
        ZteBusiResponse resp = new ZteBusiResponse();
        //获取订单字段及字段数据
        try {
            List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
            RequestBeanDefinition<ZteBusiRequest>  requestBean=BusiUtils.getRequestServiceDefinition(request);
            //判断逐渐是否有值，无值时不插入
            String key=requestBean.getPrimary_keys();
            Object obj =MethodUtils.invokeMethod(request, "get"+key.substring(0,1).toUpperCase()+key.substring(1), null);
            if(obj==null||"".equals(obj)){
            	return null;
            }
            if(null!=fieldDefinitions&&fieldDefinitions.size()>0){
                //字段封装
                HashMap<String, Object> insertsMaps = new HashMap<String, Object>();
                
                for (int i = 0; i < fieldDefinitions.size(); i++) {
                    RequestFieldDefinition  fieldDefine = fieldDefinitions.get(i);
                    String needInsert = fieldDefine.getNeed_insert();
                    
                    if(!"no".equalsIgnoreCase(needInsert)){
                    	
                    	String type = fieldDefine.getField().getType().getName(); //字段类型
                        String fname = fieldDefine.getDname();
                    	if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1 || type.indexOf("service.") > -1) {
                    		String file_name =fieldDefine.getField().getName();
                    		ZteBusiRequest zteBusiRequest=(ZteBusiRequest)MethodUtils.invokeMethod(request, "get"+file_name.substring(0,1).toUpperCase()+file_name.substring(1), null);
                    		insertZteRequestInst(zteBusiRequest);
                    		//continue;
                        } else if ("java.util.List".equals(type)) {
                        	String file_name =fieldDefine.getField().getName();
                        	List<ZteBusiRequest> list =  (List<ZteBusiRequest>)MethodUtils.invokeMethod(request, "get"+file_name.substring(0,1).toUpperCase()+file_name.substring(1), null);
                        	if(list!=null&&list.size()>0){
                        		for (ZteBusiRequest zteBusiRequest : list) {
                        			insertZteRequestInst(zteBusiRequest);
								}
                        	}
//                            continue;
                        }else{
                        	Object value =  MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
                            if(null !=value && ConstsCore.NULL_VAL.equals(value)){
                                  value ="";
                            }                       
                            insertsMaps.put(fname,value); //获取字段值,优化后处理
                        }
                    	
                        
                    }
                }
                RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition(request);
                this.baseDaoSupport.insertByMap(serviceDefinition.getTable_name(),insertsMaps);
                resp.setError_code(ConstsCore.ERROR_SUCC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonTools.addFailError("RequestStoreUtils.dbstore出错"+e.getLocalizedMessage());
            resp.setError_code(ConstsCore.ERROR_FAIL);
            resp.setError_msg("RequestStoreUtils.dbstore出错");
        }
        return resp;
    }
	
	public void insert(String table_name,Map insertMap){
		this.init();
		this.baseDaoSupport.insert(table_name,insertMap);
	}
	
 	@Override
	public IDaoSupport getBaseDaoSupport() {
		return baseDaoSupport;
	}
 	
	@Override
	public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
		this.baseDaoSupport = baseDaoSupport;
	}
	@Override
	public QueryResponse loadZteBusiRequestByDb( final ZteBusiRequest request,Boolean query_his_table,String queryParam){
		QueryResponse response = new QueryResponse();
		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition(request);
		if(serviceDefinition ==null || fieldDefinitions ==null || fieldDefinitions.size() ==0){
			return response;
		}
		StringBuffer sqls = new StringBuffer("select ");
		for (int i = 0; i < fieldDefinitions.size(); i++) {
			RequestFieldDefinition field= fieldDefinitions.get(i);
			if(!"no".equalsIgnoreCase(field.getNeed_query())){//封装查询参数
				sqls.append(field.getQname()).append(",");
			}
		}
		sqls.append(" sysdate");
		if(ConstsCore.CONSTS_YES.equals(query_his_table))
			sqls.append(" from ").append(serviceDefinition.getTable_name()+"_his").append(" where 1=1 ");
		else
			sqls.append(" from ").append(serviceDefinition.getTable_name()).append(" where 1=1 ");
		sqls.append(" and ").append(queryParam);
		List<ZteBusiRequest> queryRets = new ArrayList<ZteBusiRequest>();
		queryRets = this.baseDaoSupport.queryForList(sqls.toString(), new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				try{
					BusiUtils.dbToBusiEntryMapper(rs,request);
				}catch(Exception e){
					e.printStackTrace();
				}
				return request;
			}
		},null);
		if(ListUtil.isEmpty(queryRets))
			return response;
		response.setQueryResults(queryRets);
		return response;
	}
//	public void updateZteRequestInst(String table,HashMap updateParam ,HashMap whereParam){
//		this.baseDaoSupport.update(table, updateParam, whereParam);
//	}
}
