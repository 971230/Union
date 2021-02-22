package com.ztesoft.net.attr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import utils.UUIDUtil;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.ILogsServices;
import zte.params.order.req.OrderAttrHanderReq;
import zte.params.order.resp.OrderAttrHanderResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.cache.CacheList;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.attr.service.IAttrDefNManager;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicSQLInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.AttrTable;
import com.ztesoft.net.model.AttrTableCache;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.util.CacheUtils;

import consts.ConstsCore;

public class AttrDefNManager extends BaseSupport implements IAttrDefNManager{
	
	private JdbcTemplate jdbcTemplate;

	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	private ILogsServices logsServices;
	private INetCache cache = CacheFactory.getCacheByType("");
	public static int space = Const.CACHE_SPACE;
	public static boolean _CACHE_FLAG = true;
	public static final ThreadLocal<Boolean> CACHE_LOCAL = new ThreadLocal<Boolean>();
	public static final ThreadLocal<Integer> INDX_LOCAL = new ThreadLocal<Integer>();
	
	protected IOrderManager orderManager;
	
	private ICacheUtil cacheUtil;
	
	public List<Map> getConsts(String key){
		DcPublicSQLInfoCacheProxy dcPublicSqlCache = new DcPublicSQLInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicSqlCache.getDropdownData(key);
        return list;
	}
	
	@Override
	public List<AttrDef> queryAttrDef(String space_type,String sub_space_type, String attr_space_id) {
		List<AttrDef> list = null;
		String CACHE_KEY = CacheUtils.ATTR_SPACE_TYPE+space_type+"_"+attr_space_id;
		if(!StringUtil.isEmpty(sub_space_type))CACHE_KEY += "_"+sub_space_type;
		Boolean tFlag = CACHE_LOCAL.get();
		if(tFlag==null)tFlag = false;
		if(_CACHE_FLAG && !tFlag){
			list =(List<AttrDef>)cache.get(space, CACHE_KEY);
			if(list!=null)return list;
		}
		String sql = SF.orderSql("QUERY_ATTE_DEF_COM");
		//公共属性与自定议属性
		if(!StringUtil.isEmpty(sub_space_type))sql += " and (t.sub_attr_spec_type='"+sub_space_type+"' or t.sub_attr_spec_type='"+Const.COMM_SUB_SPACE_ID+"')";
		sql += " order by t.sort asc";
		list = this.baseDaoSupport.queryForList(sql, AttrDef.class, space_type,attr_space_id,ManagerUtils.getSourceFrom());
		if(list!=null && _CACHE_FLAG){
			CacheList<AttrDef> clist = new CacheList<AttrDef>();
			clist.addAll(list);
			cache.set(space, CACHE_KEY,clist);
		}
		return list;
	}
	
	@Override
	public List<AttrTable> queryAttrTable(String space_type,String sub_space_type,String attr_space_id){
		List<AttrTable> list = null;
		String CACHE_KEY = CacheUtils.ATTR_TABLE_SPACE_TYPE+space_type+"_"+attr_space_id;
		if(!StringUtil.isEmpty(sub_space_type))CACHE_KEY += "_"+sub_space_type;
		Boolean tFlag = CACHE_LOCAL.get();
		if(tFlag==null)tFlag = false;
		if(_CACHE_FLAG && !tFlag){
			list =(List<AttrTable>)cache.get(space, CACHE_KEY);
			if(list!=null)return list;
		}
		String sql = SF.orderSql("QUERY_ATTR_TABLE");
		//公共属性与自定议属性
		if(!StringUtil.isEmpty(sub_space_type))sql += " and (d.sub_attr_spec_type='"+sub_space_type+"' or d.sub_attr_spec_type='"+Const.COMM_SUB_SPACE_ID+"')";
		list = this.baseDaoSupport.queryForList(sql, AttrTable.class, space_type,attr_space_id,ManagerUtils.getSourceFrom());
		if(list!=null && _CACHE_FLAG){
			CacheList<AttrTable> clist = new CacheList<AttrTable>();
			clist.addAll(list);
			cache.set(space, CACHE_KEY,clist);
		}
		return list;
	}

	@Override
	public List<AttrInstBusiRequest> insertAttrTable(String attr_space_type,String attr_sub_space_type, Map values,Map extMap,String order_id,String inst_id,String goods_id,String pro_goods_id,String goods_pro_id,String product_pro_id,int index)throws Exception{
		INDX_LOCAL.set(index);
		//先插入扩展表
		try{
			if("ECS".equalsIgnoreCase(ManagerUtils.getSourceFrom()) || "SHP".equalsIgnoreCase(ManagerUtils.getSourceFrom())){
				String o_id = (String)extMap.get("order_id");
				if(Const.ATTR_DEF_SPACE_TYPE_ORDER_EXT.equals(attr_space_type)){
					//订单表
					OrderExtBusiRequest req = new OrderExtBusiRequest();
					String simple_order_id = this.baseDaoSupport.getSequences("s_es_order");
					req.setSimple_order_id(simple_order_id);
					req.setOrder_id(o_id);
					req.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
					req.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
					req.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
					//req.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
					req.setLast_deal_time("sysdate");
					req.setTid_time("sysdate");
					req.setOut_tid(order_id);
					req.setShipping_quick(EcsOrderConsts.SHIPPING_QUICK_02);
					req.setDb_action(ConstsCore.DB_ACTION_INSERT);
					req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					req.store();
				}else if(Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_EXT.equals(attr_space_type)){
					//子订单表
					String o_item_id = (String)extMap.get("item_id");
					OrderItemExtBusiRequest req = new OrderItemExtBusiRequest();
					req.setOrder_id(o_id);
					req.setItem_id(o_item_id);
					req.setDb_action(ConstsCore.DB_ACTION_INSERT);
					req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					req.store();
				}
			}
		}catch(Exception ex){}
		
		//add by wui属性处理器处理,dubbo路由到本地执行
		OrderAttrHanderResp  handlerResp = execOrderAttrHanders(index,attr_space_type,values,inst_id,attr_sub_space_type,order_id,goods_id,pro_goods_id,goods_pro_id,product_pro_id );
		
		Map<String,List<AttrDef>> tableMap = handlerResp.getAttrDefTable();
		//插入属性实例表
		String order_from = (String)values.get("c_order_from");
		if(StringUtil.isEmpty(order_from))order_from = (String)values.get("order_from");
		Iterator it = tableMap.keySet().iterator();
		while(it.hasNext()){
			String tableName = (String) it.next();
			List<AttrDef> tableAttrList = tableMap.get(tableName);
			Map<String,String> tableValues = packTableMap(order_id,inst_id,goods_id,pro_goods_id,order_from,tableAttrList, values);
			if(extMap!=null && extMap.size()>0){
				tableValues.putAll(extMap);
			}
			if(tableValues.size()>0){
				tableValues.put("order_id", order_id);
				insertTable(tableName, tableValues);
			}
		}
		//增量
		logsServices.cacheAttrInstToRedis(handlerResp.getAttInstList());
		
		return handlerResp.getAttInstList();
	}
	
	/**
	 * add by wui
	 * @param attr_space_type
	 * @param values
	 * @param inst_id
	 * @param attr_sub_space_type
	 * @param order_id
	 * @param goods_id
	 * @param pro_goods_id
	 * @param goods_pro_id
	 * @param product_pro_id
	 * @return
	 */
	public OrderAttrHanderResp execOrderAttrHanders(Integer index,String attr_space_type,Map values,String inst_id,String attr_sub_space_type,String order_id,String goods_id,String pro_goods_id,String goods_pro_id,String product_pro_id ){
		OrderAttrHanderReq req = new OrderAttrHanderReq();
		req.setAttr_space_type(attr_space_type);
		req.setValues(values);
		req.setInst_id(inst_id);
		req.setAttr_sub_space_type(attr_sub_space_type);
		req.setOrder_id(order_id);
		req.setGoods_id(goods_id);
		req.setPro_goods_id(pro_goods_id);
		req.setGoods_pro_id(goods_pro_id);
		req.setProduct_pro_id(product_pro_id);
		req.setIndex(index);
		
		List<AttrDef> list = orderManager.queryAttrDef(req.getAttr_space_type(), req.getAttr_sub_space_type(),Const.COMM_SPACE_ID);
		List<AttrTable> tbList = orderManager.queryAttrTable(req.getAttr_space_type(), req.getAttr_sub_space_type(), Const.COMM_SPACE_ID);
		//插入属性实例表
		String order_from = (String)req.getValues().get("c_order_from");
		if(StringUtil.isEmpty(order_from))order_from = (String)req.getValues().get("order_from");
		OrderAttrHanderResp handlerResp = orderManager.groupAndInsertAttrByTable(req.getIndex(),list, req.getValues(), req.getOrder_id(), req.getInst_id(), req.getGoods_id(), req.getPro_goods_id(), order_from,tbList,req.getGoods_pro_id(),req.getProduct_pro_id());
		return handlerResp;
		
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
	private AttrInstBusiRequest packageAttrInst(AttrDef attrDef,String i_value,String o_value,String order_id,String inst_id,String value_desc){
		AttrInstBusiRequest attrInst = new AttrInstBusiRequest();
		//attrInst.setAttr_inst_id(this.baseDaoSupport.getSequences("s_es_attr_inst","10000",32));
		attrInst.setAttr_inst_id(UUIDUtil.getUUID());
		attrInst.setOrder_id(order_id);
		attrInst.setInst_id(inst_id);
		attrInst.setAttr_spec_id(attrDef.getAttr_spec_id());
		attrInst.setField_attr_id(attrDef.getField_attr_id());
		attrInst.setField_name(attrDef.getField_name());
		attrInst.setFiled_desc(attrDef.getField_desc());
		attrInst.setField_value(i_value);
		attrInst.setSec_field_value(o_value);
		attrInst.setField_value_desc(value_desc);
		attrInst.setCreate_date(DBTUtil.current());
		//add by wui首次规格数据填充
		attrInst.setSpec_is_null(attrDef.getIs_null());
		attrInst.setSpec_is_edit(attrDef.getIs_edit());
		return attrInst;
	}
	
	/**
	 * 封装属性对应横表的数据
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param tableAttrList
	 * @param values
	 * @return
	 */
	private Map<String,String> packTableMap(String order_id,String inst_id,String goods_id,String pro_goods_id,String order_from,List<AttrDef> tableAttrList,Map values){
		Map<String,String> tableValues = new HashMap<String,String>();
		if(tableAttrList!=null && tableAttrList.size()>0 && values!=null && values.size()>0){
			for(AttrDef ad:tableAttrList){
				String key = ad.getField_name();
				Object obj = values.get(key);
				if(obj!=null){
					String value = String.valueOf(obj);
					if(!StringUtil.isEmpty(value) && !StringUtil.isEmpty(ad.getTable_field_name())){
						tableValues.put(ad.getTable_field_name(), value);
					}
				}
			}
		}
		return tableValues;
	}
	
	/**
	 * 空值过滤处理
	 * @param keyValues
	 * @return
	 */
	private Map fiterNUllFromMap(Map keyValues){
		Iterator it = keyValues.keySet().iterator();
		Map filterMap = new HashMap();
//		StringBuffer keys = new StringBuffer("=======================================属性输出：：：：：：：：：：");
		while (it.hasNext()) {
			String key = (String)it.next();
			String value = (String) keyValues.get(key);
			if(!StringUtil.isEmpty(value))
				filterMap.put(key, value);
			
//			keys.append(key).append(value).append(",");
		}
//		logger.info(keys.toString());
		return filterMap;
	}
	/**
	 * 插入属性对应的横表数据
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param tableName
	 * @param keyValues
	 */
	//add by wui强制数据库获取
	public static ConcurrentHashMap filterTableMap = new ConcurrentHashMap();
	static{
		filterTableMap.put("es_order", "yes");
		filterTableMap.put("es_order_items", "yes");
		filterTableMap.put("es_delivery", "yes");
	}
	public void insertTable(String tableName,Map<String,String> keyValues)throws Exception{
		if(keyValues==null || keyValues.size()==0)return ;
		String pk = getTablePKName(tableName);
		if(StringUtil.isEmpty(pk))throw new Exception("请在es_table_manage 表配置"+tableName+"表主键");
		pk = pk.toLowerCase();
		String pkValue = keyValues.get(pk);
		if(StringUtil.isEmpty(pkValue))throw new Exception(tableName+"表主键"+pk+"值为空,请传值");
		String sql = "select count(*) from "+tableName+" where "+pk+"=?";
		int count =0;
//		count = this.baseDaoSupport.queryForInt(sql, pkValue);
		String order_id =keyValues.get("order_id");
		count = this.baseDaoSupport.queryForInt(sql, pkValue);
		Map tempKeyMap = new HashMap();
		//如果在存在更新数据，如果不存在新增数据
		if(count>0){
			if("es_order_items_prod_ext".equalsIgnoreCase(tableName)){//防止报 违反唯一约束条件 异常
				tempKeyMap.put("item_prod_inst_id", keyValues.get("item_prod_inst_id"));
				tempKeyMap.put("item_id", keyValues.get("item_id"));
				keyValues.remove("item_prod_inst_id");
				keyValues.remove("item_id");
			}
			
			//add by wui修改为设置主键
			Map where = new HashMap(); //pk+"='"+pkValue+"'")
			where.put(pk, pkValue);
			keyValues.remove(pk);//删除主键，主键不需要更新
			tempKeyMap.put(pk, pkValue);
			
			//add by wui ,更新去除空值，空值不需要更新，优化性能
			fiterNUllFromMap(keyValues); 
			this.baseDaoSupport.update(tableName, keyValues, where);//(tableName, keyValues, ;
		}else{
			this.baseDaoSupport.insert(tableName, keyValues, keyValues);
		}
		
		keyValues.putAll(tempKeyMap);
		if(tableName.equalsIgnoreCase("es_order_ext"))
		{
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			com.ztesoft.common.util.BeanUtils.copyProperties(orderExtBusiRequest, keyValues);
			logsServices.cacheOrderTree(orderTree);
		}else if(tableName.equalsIgnoreCase("es_order"))
		{
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
			com.ztesoft.common.util.BeanUtils.copyProperties(orderBusiRequest, keyValues);
			logsServices.cacheOrderTree(orderTree);
		}else if(tableName.equalsIgnoreCase("es_order_items_ext")){
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
			com.ztesoft.common.util.BeanUtils.copyProperties(orderItemExtBusiRequest, keyValues);
			orderTree.getOrderItemBusiRequests().get(0).setOrderItemExtBusiRequest(orderItemExtBusiRequest);
			logsServices.cacheOrderTree(orderTree);
		}else if(tableName.equalsIgnoreCase("es_order_items_prod_ext")){
			keyValues.putAll(tempKeyMap);
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			List<OrderItemProdBusiRequest> orderItemProdBusiRequests = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			for (OrderItemProdBusiRequest orderItemProdBusiRequest:orderItemProdBusiRequests) {
				if(orderItemProdBusiRequest.getItem_prod_inst_id().equals(keyValues.get("item_prod_inst_id"))){
					OrderItemProdExtBusiRequest orderItemProdExtBusiRequest = new OrderItemProdExtBusiRequest();
					com.ztesoft.common.util.BeanUtils.copyProperties(orderItemProdExtBusiRequest, keyValues);
					orderItemProdBusiRequest.setOrderItemProdExtBusiRequest(orderItemProdExtBusiRequest);
				}
			}
			logsServices.cacheOrderTree(orderTree);
		}else if(tableName.equalsIgnoreCase("es_payment_logs")){
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			OrderPayBusiRequest orderPayBusiRequest =new OrderPayBusiRequest();
			com.ztesoft.common.util.BeanUtils.copyProperties(orderPayBusiRequest, keyValues);
			orderTree.getOrderPayBusiRequests().add(orderPayBusiRequest);
			logsServices.cacheOrderTree(orderTree);
		}else if(tableName.equalsIgnoreCase("es_delivery")){
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(keyValues.get("order_id"));
			OrderDeliveryBusiRequest orderDeliveryBusiRequest =new OrderDeliveryBusiRequest();
			com.ztesoft.common.util.BeanUtils.copyProperties(orderDeliveryBusiRequest, keyValues);
			orderDeliveryBusiRequest.setDelivery_type("0");
//			orderDeliveryBusiRequest.setMember_id(member_id);
			orderTree.getOrderDeliveryBusiRequests().add(orderDeliveryBusiRequest);
			logsServices.cacheOrderTree(orderTree);
		}
	}
	
	private Map<String,List<AttrTable>> groupAttrTable(List<AttrTable> list){
		if(list!=null && list.size()>0){
			Map<String,List<AttrTable>> map = new HashMap<String,List<AttrTable>>();
			for(AttrTable at:list){
				List<AttrTable> ats = map.get(at.getAttr_def_id());
				if(ats==null){
					ats = new ArrayList<AttrTable>();
					map.put(at.getAttr_def_id(), ats);
				}
				ats.add(at);
			}
			return map;	
		}else{
			return null;
		}
	}
	

	@Override
	public void insertAttrInst(AttrInst inst) {
		this.baseDaoSupport.insert("es_attr_inst", inst);
	}
	
	@Override
	public void batchInsertAttrInst(List<AttrInstBusiRequest> instList) {
		if(instList==null || instList.size()==0)return ;
		String order_id = instList.get(0).getOrder_id();
//		String sql = SF.orderSql("BATCHINSERTATTRINST");
//		List mapList = new ArrayList();
//		for (int i = 0; i < instList.size(); i++) {
//			Map map = new HashMap();
//			String attrString []  = new String []{instList.get(i).getAttr_inst_id(),
//					instList.get(i).getOrder_id(),instList.get(i).getOrder_item_id(),instList.get(i).getInst_id(),
//					instList.get(i).getAttr_spec_id(),instList.get(i).getField_attr_id(),instList.get(i).getField_name(),
//					instList.get(i).getFiled_desc(),instList.get(i).getField_value(),instList.get(i).getField_value_desc()
//					,instList.get(i).getModify_field_value(),instList.get(i).getSec_field_value(),instList.get(i).getSec_filed_value_desc(),ManagerUtils.getSourceFrom()};
//			mapList.add(attrString);
//		}
//		String attr_inst_from_redis =cacheUtil.getConfigInfo(EcsOrderConsts.ATTR_INST_FROM_REDIS);
//		if(ConstsCore.CONSTS_YES.equals(attr_inst_from_redis)){
//			try {
//				logsServices.cacheAttrInstToRedis(instList);
//			} catch (ApiBusiException e) {
//				e.printStackTrace();
//			}
//		}else{
//			this.baseDaoSupport.batchExecute(sql, mapList);//(sql, instList);
//		}
//		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//		orderTree.getAttrInstBusiRequests().clear();
//		orderTree.getAttrInstBusiRequests().addAll(instList); //add by wui
//		try {
//			logsServices.cacheOrderTree(orderTree);
//		} catch (ApiBusiException e) {
//			e.printStackTrace();
//		}
	}
	


	
//	/**
//	 * 根据订单实例数据查询属性定议信息
//	 * @作者 MoChunrun
//	 * @创建日期 2014-9-22 
//	 * @param attr_inst_id
//	 * @return
//	 */
//	private AttrDef getAttrDefByAttrInstId(String attr_inst_id){
//		String sql = SF.orderSql("AttrDefByAttrInstId");
//		List<AttrDef> list = this.baseDaoSupport.queryForList(sql, AttrDef.class, attr_inst_id);
//		return list.size()>0?list.get(0):null;
//	}
	
	/**
	 * 修改订单属性实例数据
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param attr_inst_id
	 * @param value
	 */
	private void updateAttrInstValue(String order_id,String attr_inst_id,String value,String valdesc){
		String sql = SF.orderSql("UPDATE_ATTRINST_BY_ID");
		if(valdesc==null)valdesc="";
		this.baseDaoSupport.execute(sql, value,valdesc,attr_inst_id);
		
		
	}
	
	private String getTablePKName(String tableName){
		String tbSql = "select * from es_table_manage t where upper(t.table_name)=? ";
		List<Map> list = this.baseDaoSupport.queryForList(tbSql, tableName.trim().toUpperCase());
		if(list==null || list.size()==0)return null;
		Map map = list.get(0);
		String pk = (String) map.get("pk_name");
		return pk;
	}
	
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
	
	
	
	@Override
	public AttrDef getAttrDef(String field_attr_id){
		String sql = SF.orderSql("QUERY_ATTR_DEF_BY_ID");
		List<AttrDef> list = this.baseDaoSupport.queryForList(sql, AttrDef.class, field_attr_id,ManagerUtils.getSourceFrom());
		return (list==null || list.size()==0)?null:list.get(0);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String getAttrInstValue(String order_id, String field_name) {
		String sql = SF.orderSql("ATTR_INST_GET");
		List<Map> field_values = this.baseDaoSupport.queryForList(sql, order_id,field_name);
		Map field = (field_values==null && field_values.size()==0) ? new HashMap() : field_values.get(0);
		String sec_field_value = Const.getStrValue(field, "sec_field_value");
		return sec_field_value;
	}

	@Override
	public void resetAttrCache() {
		CACHE_LOCAL.set(true);
		//订单属性缓存
		this.queryAttrDef(Const.ATTR_DEF_SPACE_TYPE_ORDER_EXT, null, Const.COMM_SPACE_ID);
		this.queryAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_EXT, null, Const.COMM_SPACE_ID);
		//支付属性
		this.queryAttrDef(Const.ATTR_DEF_SPACE_TYPE_ORDER_PAY_EXT, null, Const.COMM_SPACE_ID);
		this.queryAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_PAY_EXT, null, Const.COMM_SPACE_ID);
		//物流属性
		this.queryAttrDef(Const.ATTR_DEF_SPACE_TYPE_ORDER_DELIVERY, null, Const.COMM_SPACE_ID);
		this.queryAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_DELIVERY, null, Const.COMM_SPACE_ID);
		
		String sqlAttrTable = SF.orderSql("QUERYATTRTABLECACHE");
		List<AttrTableCache> attrTableList = this.baseDaoSupport.queryForList(sqlAttrTable, AttrTableCache.class, ManagerUtils.getSourceFrom());
		for(AttrTableCache at:attrTableList){
			CacheUtils.addCache(CacheUtils.ATTR_TABLE_KEY+at.getField_name(), at);
			CacheUtils.addCache(CacheUtils.ATTR_TABLE_KEY_SOURCE+at.getDef_field_name(), at);
		}
		
		String sql = SF.orderSql("LISTGOODSTYPE");
		List<GoodsType> list = this.baseDaoSupport.queryForList(sql, GoodsType.class);
		//商品，货品属性
		if(list!=null && list.size()>0){
			for(GoodsType gt:list){
				String spact_type = Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_EXT;
				if("product".equals(gt.getType())){
					spact_type = Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_PRO_EXT;
				}
				if(!StringUtil.isEmpty(gt.getType_code())){
					this.queryAttrDef(spact_type, gt.getType_code(), Const.COMM_SPACE_ID);
					this.queryAttrTable(spact_type, gt.getType_code(), Const.COMM_SPACE_ID);
				}
			}
		}
		
		String sqlattr = SF.orderSql("LISTATTRDEF");
		List<AttrDef> defList = this.baseDaoSupport.queryForList(sqlattr, AttrDef.class, Const.COMM_SPACE_ID);
		if(defList!=null && defList.size()>0){
			for(AttrDef def:defList){
				this.queryAttrInstTables(def.getField_attr_id());
			}
		}
	}

	public ILogsServices getLogsServices() {
		return logsServices;
	}

	public void setLogsServices(ILogsServices logsServices) {
		this.logsServices = logsServices;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
}
