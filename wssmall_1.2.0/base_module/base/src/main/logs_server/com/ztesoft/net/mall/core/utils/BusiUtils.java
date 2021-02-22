package com.ztesoft.net.mall.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.MethodUtils;
import params.ZteBusiRequest;
import params.ZteRequest;
import zte.net.common.annontion.context.request.DefaultActionRequestDefine;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.common.annontion.context.request.RequestFieldDefinition;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.ZteBusiWraperRequest;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.ILogsServices;

import com.taobao.api.internal.util.StringUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.cache.coherence.CoherenceRamCache;
import com.ztesoft.net.framework.blog.IStoreProcesser;
import com.ztesoft.net.framework.blog.StoreProcesser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import commons.CommonTools;
import consts.ConstsCore;


public class BusiUtils {
	
	public static DefaultActionRequestDefine context = null;
	static{
		context = DefaultActionRequestDefine.getInstance();
	}
	

	/**
	 * 获取执行操作符insert or update 
	 * @param request
	 * @param popMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	public static String getPrimperySql(ZteBusiRequest request){
		StringBuffer whereCond =new StringBuffer("");
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(request);
		try {
			String prim_keys ="";
			if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
				prim_keys = serviceDefinition.getPrimary_keys();
			}
			if(!StringUtil.isEmpty(prim_keys))
			{
				String [] prim_keys_arr = prim_keys.split(",");
				for (int i = 0; i < prim_keys_arr.length; i++) {
					String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
					if(!StringUtil.isEmpty(value)){
						whereCond.append(" and ").append(prim_keys_arr[i].toLowerCase()).append(" = '").append(value+"'");
					}else{
						CommonTools.addFailError("getPrimperySql获取主键失败!");
					}
				}
			}
		}catch (Exception e) {
			CommonTools.addFailError("getPrimperySql--构造主键值出错");
			e.printStackTrace();
		}
		return whereCond.toString();
	}
	
	
	/**
	 * 获取执行操作符insert or update 
	 * @param request
	 * @param popMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public static String getDefencySql(ZteBusiRequest request) throws Exception{
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(request);
//		Map popMap =new HashMap();
//		BeanUtils.beanToMap(popMap, zteBusiRequest);
		StringBuffer whereCond =new StringBuffer("");
		String depency_keys ="";
		if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
			depency_keys = serviceDefinition.getDepency_keys();
		}
		if(!StringUtil.isEmpty(depency_keys))
		{
			String [] depency_keys_arr = depency_keys.split(",");
			for (int i = 0; i < depency_keys_arr.length; i++) {
				String value =  (String)MethodUtils.invokeMethod(request, "get"+depency_keys_arr[i].substring(0,1).toUpperCase()+depency_keys_arr[i].substring(1), null);
				if(!StringUtil.isEmpty(value)){
//					whereCond.append(" and ").append(depency_keys_arr[i].toLowerCase()).append(" = ").append(value);
					whereCond.append(" and ").append(depency_keys_arr[i].toLowerCase()).append(" = '").append(value+"'");
				}
			}
		}
		return whereCond.toString();
	}
	
	/**
	 * 获取执行操作符insert or update 
	 * @param request
	 * @param popMap
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public static Map getDefencyMapValues(ZteBusiRequest request) {
		Map paMap = new HashMap();
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(request);
		StringBuffer whereCond =new StringBuffer("");
		try {
		String depency_keys ="";
		if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
			depency_keys = serviceDefinition.getDepency_keys();
		}
		if(!StringUtil.isEmpty(depency_keys))
		{
			String [] depency_keys_arr = depency_keys.split(",");
			for (int i = 0; i < depency_keys_arr.length; i++) {
				String value =  (String)MethodUtils.invokeMethod(request, "get"+depency_keys_arr[i].substring(0,1).toUpperCase()+depency_keys_arr[i].substring(1), null);
				if(!StringUtil.isEmpty(value)){
					paMap.put(depency_keys_arr[i].toLowerCase(), value);
				}
			}
		}
		}catch (Exception e) {
			CommonTools.addFailError("getDefencyMapValues--构造主键值出错");
			e.printStackTrace();
		}
		return paMap;
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static Map getPrimperyMapValues(ZteBusiRequest request){
		StringBuffer whereCond =new StringBuffer("");
		Map paMap = new HashMap();
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(request);
		try {
			String prim_keys ="";
			if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
				prim_keys = serviceDefinition.getPrimary_keys();
			}
			if(!StringUtil.isEmpty(prim_keys))
			{
				String [] prim_keys_arr = prim_keys.split(",");
				for (int i = 0; i < prim_keys_arr.length; i++) {
					String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
					if(!StringUtil.isEmpty(value)){
						paMap.put(prim_keys_arr[i].toLowerCase(), value);
					}
				}
			}
		}catch (Exception e) {
			CommonTools.addFailError("getPrimperyMapValues--构造主键值出错");
			e.printStackTrace();
		}
		return paMap;
	}
	
	/**
	 * 构造缓存key
	 * @param beanName
	 * @param popMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  String genCacheKey(String beanName ,ZteBusiRequest request ){
		try{
			String prim_keys ="";
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(beanName);
			if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name())){
				prim_keys = serviceDefinition.getPrimary_keys();
			}
			String prim_keysavls = "";
			if(!StringUtil.isEmpty(prim_keys))
			{
				String [] prim_keys_arr = prim_keys.split(",");
				for (int i = 0; i < prim_keys_arr.length; i++) {
					String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
					if(!StringUtil.isEmpty(value)){
						prim_keysavls+=value;
					}
				}
			}
			return prim_keys+prim_keysavls;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 构造缓存key
	 * @param beanName
	 * @param popMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  String genCachePrimikey(String prim_keys,String field_value){
		try{
//			String prim_keys =field_name;
//			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(beanName);
//			if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name())){
//				prim_keys = serviceDefinition.getPrimary_keys();
//			}
			String prim_keysavls = "";
			if(!StringUtil.isEmpty(prim_keys))
			{
				String [] prim_keys_arr = prim_keys.split(",");
				for (int i = 0; i < prim_keys_arr.length; i++) {
					String value =  field_value.split(",")[i];
					if(!StringUtil.isEmpty(value)){
						prim_keysavls+=value;
					}
				}
			}
			return prim_keys+prim_keysavls;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 拼接sql语句
	 * @param simpleName
	 * @return
	 */
//	@SuppressWarnings({ "rawtypes", "unchecked"})
//	public static Map getUpdateMapFields(ZteBusiRequest request,RequestBeanDefinition<ZteBusiRequest> serviceDefinition,Map popMap,Map fieldMap){
//		List<String> changeFields = BusiUtils.getChageFields(BusiUtils.getOldZteBusiRequestFromOrderTree(request), request);
//		request.getChangeFiels().addAll(changeFields);
//		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(serviceDefinition.getService_name());
//		//循环二级节点设置处理
//		for (RequestFieldDefinition fieldDefinition:fieldDefinitions) { // && !StringUtil.isEmpty((String)popMap.get(fieldDefinition.getDname())
//			Object fV =popMap.get(fieldDefinition.getDname());
//			if(ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert()) && (null !=fV)){ //查询字段,字段值不为空
//				if(fV instanceof String)
//					if(StringUtil.isEmpty((String)popMap.get(fieldDefinition.getDname())))
//							continue;
//				if(fV== null || "null".equals(fV))
//					continue;
//				if(ConstsCore.NULL_VAL.equals(fV))
//					fV = ""; //传递空值处理逻辑
//				//过滤优化处理
//				if(!ListUtil.isEmpty(request.getChangeFiels())){
//					for (int i = 0; i < request.getChangeFiels().size(); i++) {
//						if(fieldDefinition.getDname().equals(request.getChangeFiels().get(i)))
//							fieldMap.put(fieldDefinition.getDname(),fV);
//					}
//				}else
//					fieldMap.put(fieldDefinition.getDname(),fV);
//			}
//		}
//		return fieldMap;
//	}
//	

	/**
	 * List<String> changeFields = BusiUtils.getChageFields(BusiUtils.getOldZteBusiRequestFromOrderTree(request), request);
		request.getChangeFiels().addAll(changeFields);
	 */
	/**
	 * 拼接sql语句
	 * @param simpleName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static List<String> getChangeValueFields(List<String> changeFields,List<RequestFieldDefinition> fieldDefinitions, ZteBusiRequest request) throws Exception{
		List<String> vChangeFieldsList = new ArrayList<String>();
		//循环二级节点设置处理
		
		for (String field_name :changeFields) { // && !StringUtil.isEmpty((String)popMap.get(fieldDefinition.getDname())
			RequestFieldDefinition  fieldDefinition=BusiUtils.getRequestFieldDefinitionByName(field_name, fieldDefinitions);
			if(fieldDefinition ==null)
				return changeFields;
			String name = fieldDefinition.getField().getName();
			if(StringUtil.isEmpty(name))
				name = fieldDefinition.getDname();
			Object fV =  MethodUtils.invokeMethod(request, "get"+name.substring(0,1).toUpperCase()+name.substring(1), null);
			if(ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert()) && (null !=fV)){ //查询字段,字段值不为空
				if(fV instanceof String)
					if(StringUtil.isEmpty((String)fV))
							continue;
				if(fV== null || "null".equals(fV))
					continue;
				if(ConstsCore.NULL_VAL.equals(fV))
					fV = ""; //传递空值处理逻辑
				//过滤优化处理
				vChangeFieldsList.add(fieldDefinition.getDname());
			}
		}
		return vChangeFieldsList;
	}
	
	/**
	 * 拼接sql语句
	 * @param simpleName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getWhereFields(RequestBeanDefinition<ZteBusiRequest> serviceDefinition,ZteBusiRequest request,Map whereMap) throws Exception{
		String prim_keys ="";
		if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
			prim_keys = serviceDefinition.getPrimary_keys();
		}
		if(!StringUtil.isEmpty(prim_keys))
		{
			String [] prim_keys_arr = prim_keys.split(",");
			for (int i = 0; i < prim_keys_arr.length; i++) {
				String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
				if(!StringUtil.isEmpty(value)){
					whereMap.put(prim_keys_arr[i], value);
				}else{
					CommonTools.addFailError("BusiUtils.getWhereFields获取主键值出错，不允许更新!");
				}
			}
		}
		return whereMap;
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static void dbToBusiEntryMapper(ResultSet rs,ZteBusiRequest zteRequest) throws Exception{
		
		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(zteRequest.getClass().getSimpleName());
		RequestFieldDefinition fieldDefinition= null;
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		StoreProcesser factory = new StoreProcesser();
		String table_name ="";
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition  = BusiUtils.getRequestServiceDefinition(zteRequest);
		List<Map<String,Object>> clob_names = new ArrayList<Map<String,Object>>();
		if(serviceDefinition !=null){
			table_name = serviceDefinition.getTable_name();
			clob_names = factory.getTabClobName(table_name.toUpperCase());
		}
		
		for (int i = 1; i <= columnCount; i++) {
			Object obj = null;
			String columnName = rsmd.getColumnName(i);
			String typename= rsmd.getColumnTypeName(i);
			fieldDefinition =getRequestFieldDefinitionByName(columnName,fieldDefinitions);
			if(fieldDefinition ==null)
				continue;
			String type = fieldDefinition.getField().getType().getName();
			String name = fieldDefinition.getField().getName();
			if(isExistsClob(clob_names,columnName) || "CLOB".equals(type) || "BLOB".equals(type)){
				if("CLOB".equals(type) || "BLOB".equals(type)){
					obj = rs.getClob(fieldDefinition.getQname());
					obj =changeClobToString((CLOB)obj);
				}else {
					obj = rs.getString(fieldDefinition.getQname());
				}
				if(obj ==null)
					continue;
				if(null != obj && !"".equals(obj) && (((String)obj).length()<100 && ((String)obj).length()>0)){//初步过滤
					if(StringUtil.isEmpty(table_name))
						table_name = rsmd.getTableName(i);
					IStoreProcesser netBlog = StoreProcesser.getProcesser(table_name,rsmd.getColumnName(i),ManagerUtils.getSourceFrom(),obj);
					obj = netBlog.getFileUrl((String)obj);
				}
			}else{
				if("NUMBER".equals(typename)){
					int scale = rsmd.getScale(i);
					int precision = rsmd.getPrecision(i);
					if(scale == 0){
						if(precision<10)
							obj = rs.getInt(i);
						else
							obj = rs.getLong(i);
					}else if(scale>0 || "weight".equals(name) || "goods_amount".equals(name) 
							|| "shipping_amount".equals(name) || "discount".equals(name)
							|| "taxes".equals(name) || "order_coupon".equals(name) 
							|| "price".equals(name) || "coupon_price".equals(name) 
							|| "pay_cost".equals(name) || "money".equals(name) 
							|| "order_amount".equals(name) || "paymoney".equals(name) || "protect_price".equals(name)
							|| "goods_price".equals(name) || "busi_money".equals(name) || "ess_money".equals(name)){
						obj = rs.getDouble(i);
					}else{
						obj =rs.getObject(i);
					}
					if("cancelTagPro".equals(name) ||"changeTagPro".equals(name) || "cancelTagChe".equals(name) ||"changeTagChe".equals(name) ||"classId".equals(name) ||"item_type".equals(name) || "paytype".equals(name) || "sequ".equals(name) ||"create_type".equals(name) || "ship_num".equals(name) || "print_status".equals(name) || "ship_status".equals(name) || "sku_num".equals(name))
						obj = rs.getInt(i);
					
					if(zteRequest instanceof OrderDeliveryBusiRequest && "weight".equals(name))
						obj = rs.getLong(i);
				}else if("DATE".equals(typename)){
					obj = rs.getDate(fieldDefinition.getQname());
					Object objTmp = rs.getTimestamp(fieldDefinition.getQname());
					if(objTmp !=null) //add by wui时间秒转换处理
					{
						DateFormat sdf = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
						String objStr = sdf.format(objTmp);
						objTmp = objStr;
					}
					obj = objTmp;//DateFormatUtils.formatDate((Date)obj1,CrmConstants.DATE_TIME_FORMAT);
				}else{
					obj = rs.getString(fieldDefinition.getQname());
				}
			}
			if(obj==null)
				continue;
			if("tm_resource_id".equals(name))
				obj =obj+"";
			try{
				if ("java.util.List".equals(type) || type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1){
					ZteBusiWraperRequest zteBusiWraperRequest = CommonTools.jsonToBean((String)obj, ZteBusiWraperRequest.class);
					MethodUtils.invokeMethod(zteRequest, "set"+name.substring(0,1).toUpperCase()+name.substring(1), zteBusiWraperRequest.getField_value());
				}else{
					MethodUtils.invokeMethod(zteRequest, "set"+name.substring(0,1).toUpperCase()+name.substring(1),obj);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	private static boolean  isExistsClob(List<Map<String,Object>> col_names, String colname) {
		if(!ListUtil.isEmpty(col_names)){
			for(Map colMap:col_names){
				if(colname.toUpperCase().equals(colMap.get("field_name")))
					return true;
			}
			
		}
		return false;
	}
	
	public static String changeClobToString(Clob clob) throws Exception {
		if(clob == null)
			return "";
		String clobValue = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		sb.append(s);
		s = br.readLine();
		}
		clobValue = sb.toString();
		return clobValue;
	}

	/**
	 * 获取Field定义
	 * @param name
	 * @param fieldDefinitions
	 * @return
	 */
	public static RequestFieldDefinition getRequestFieldDefinitionByName(String name ,List<RequestFieldDefinition> fieldDefinitions ){
		for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
			if(fieldDefinition.getDname().equalsIgnoreCase(name))
				return fieldDefinition;
		}
		return null;
	}
//	/**
//	 * 拼接sql语句
//	 * @param simpleName
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes" })
//	public static StringBuffer getUpdateSqlsByFieldDefines(RequestBeanDefinition<ZteBusiRequest> serviceDefinition,Map popMap,List whereList){
//		StringBuffer sqls = new StringBuffer("update  ").append(serviceDefinition.getTable_name()).append(" set ");
//		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(serviceDefinition.getService_name());
//		//循环二级节点设置处理
//		for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
//			if(ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert())) //查询字段
//				sqls.append(fieldDefinition.getDname()).append("=").append(" ,");
//		}
//		sqls.append(" where 1=1 ").append(getPrimperySql(serviceDefinition, popMap));
//		return sqls;
//	}
	
	/**
	 * 获取执行操作符insert or update 
	 * @param request
	 * @param popMap
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getDbOper(ZteBusiRequest request,Map popMap) throws Exception{
		String db_action = request.getDb_action();
//		if(popMap==null)
//		String db_action = (String) popMap.get("db_action");
		if(!StringUtil.isEmpty(db_action))
			return db_action;
		String db_action_insert =ConstsCore.DB_ACTION_UPDATE;//缺省为更新
		String prim_keys ="";
		RequestBeanDefinition<ZteRequest> serviceDefinition = context.getActionRequestServiceMap(request.getClass().getSimpleName());
		if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name()) && ConstsCore.STORE_TYPE_DB.equals(serviceDefinition.getStore_type())){//数据库存储
			prim_keys = serviceDefinition.getPrimary_keys();
		}
		if(!StringUtil.isEmpty(prim_keys))
		{
			String [] prim_keys_arr = prim_keys.split(",");
			for (int i = 0; i < prim_keys_arr.length; i++) {
//				String prim_keys_value =(String)popMap.get(prim_keys_arr[i]);
				String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
//				String prim_keys_value =(String)popMap.get(prim_keys_arr[i]); //优化后
				if(StringUtil.isEmpty(value)){
					db_action_insert = ConstsCore.DB_ACTION_INSERT;
					break;
				}
			}
		}
		return db_action_insert;
	}
	
	
	/**
	 * 拼接sql语句
	 * @param simpleName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getQuerySqlsByFieldDefines(ZteInstQueryRequest instQuery,String simpleName){
		ZteBusiRequest zteRequest= instQuery.getQueryObject();
		Map instParams = instQuery.getQueryParmas();
		String query_his_table = (String)instParams.get("query_his_table");
		//获取服务定义
		RequestBeanDefinition<ZteRequest> serviceDefinition = context.getActionRequestServiceMap(simpleName);
		//获取字段定义
		StringBuffer sqls = new StringBuffer("select ");
		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(simpleName);
		//循环二级节点设置处理
		for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
			if(ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert())) //查询字段
				sqls.append(fieldDefinition.getQname()).append(",");
		}
		sqls.append(" sysdate");
		if(ConstsCore.CONSTS_YES.equals(query_his_table))
			sqls.append(" from ").append(serviceDefinition.getTable_name()+"_his").append(" where 1=1 ");
		else
			sqls.append(" from ").append(serviceDefinition.getTable_name()).append(" where 1=1 ");
		return sqls.toString();
	}
	
	/**
	 * 获取服务定义
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RequestBeanDefinition<ZteBusiRequest> getRequestServiceDefinition(ZteBusiRequest request){
//		if(request ==null)
//			logger.info("222222222222222");
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(request.getClass().getSimpleName());
		return serviceDefinition;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RequestBeanDefinition<ZteBusiRequest> getRequestServiceDefinitionByTableName(String table_name){
		Map<String, RequestBeanDefinition> maps  = context.getActionRequestServiceMap();
		Iterator it =maps.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			RequestBeanDefinition serviceDefinition = maps.get(key);
			if(serviceDefinition.getTable_name().equals(table_name))
				return serviceDefinition;
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes"})
	public static List<RequestFieldDefinition> getRequestFieldsDefinition(ZteBusiRequest request){
		List<RequestFieldDefinition> fieldDefinitions = context.getActionRequestFieldsMap(request.getClass().getSimpleName());
		return fieldDefinitions;
	}
	
	/**
	 * 根据订单id更新订单树
	 * @param busiRequest
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OrderTreeBusiRequest updateCacheOrderTree(ZteBusiRequest busiRequest){
		RequestBeanDefinition<ZteBusiRequest> busiServiceBean = BusiUtils.getRequestServiceDefinition(busiRequest);
		String service_name = busiServiceBean.getService_name();
//		if("AttrPackageBusiRequest".equals(service_name))
//			service_name = "AttrZbPackageBusiRequest"; //add by wui转换服务名
		OrderTreeBusiRequest orderTreeBusiRequest = null;
		ZteBusiRequest declareBean = null;
		if(StringUtil.isEmpty(service_name))
			return null;
		
		try{
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(busiRequest);
			if(null == orderTreeBusiRequest) return null;
			declareBean = getOwnerDeclareBean(orderTreeBusiRequest,busiRequest, service_name);
			if(declareBean == null || orderTreeBusiRequest == null)
				return null;
			String busiPrimSql = getPrimperySql(busiRequest); //获取业务对象主键值
			List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(declareBean);
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
				if(!f_service_name.equals(service_name))
					continue;
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					ZteBusiRequest oldZteBusiRequest = getOldZteBusiRequestFromOrderTree(busiRequest);
					if(oldZteBusiRequest!=null){
						setZteRequestChangedFields(oldZteBusiRequest,busiRequest);
						busiRequest = oldZteBusiRequest;
					}
					MethodUtils.invokeMethod(declareBean, "set"+fname.substring(0,1).toUpperCase()+fname.substring(1), busiRequest); //更新处理
					break;//一次成功直接退出
				}else if("java.util.List".equals(type)) //判断插入还是更新
				{
//					synchronized ("yes") { //临时换同步，需要待确认
						List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(declareBean, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
						boolean isInsert = true;
						for (ZteBusiRequest subZteRequest:subZteRequests) {
							String subPrimSql = getPrimperySql(subZteRequest);
							if(busiPrimSql.equals(subPrimSql)){ //修改处理逻辑
//								subZteRequests.remove(subZteRequest);
								setZteRequestChangedFields(subZteRequest,busiRequest);
								isInsert = false;
								break;
							}
						}
						//新增则添加处理
						if(isInsert)
							subZteRequests.add(busiRequest);
						MethodUtils.invokeMethod(declareBean, "set"+fname.substring(0,1).toUpperCase()+fname.substring(1), subZteRequests);
						break; //一次成功直接退出
//					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(declareBean instanceof OrderTreeBusiRequest)
			return (OrderTreeBusiRequest)declareBean;
		return orderTreeBusiRequest;
	}
	
	/**
	 * 根据订单id更新订单树
	 * @param busiRequest
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static OrderTreeBusiRequest removeCacheOrderTree(ZteBusiRequest busiRequest){
		RequestBeanDefinition<ZteBusiRequest> busiServiceBean = BusiUtils.getRequestServiceDefinition(busiRequest);
		String service_name = busiServiceBean.getService_name();
//		if("AttrPackageBusiRequest".equals(service_name))
//			service_name = "AttrZbPackageBusiRequest"; //add by wui转换服务名
		OrderTreeBusiRequest orderTreeBusiRequest = null;
		ZteBusiRequest declareBean = null;
		if(StringUtil.isEmpty(service_name))
			return null;
		
		try{
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(busiRequest);
			declareBean = getOwnerDeclareBean(orderTreeBusiRequest,busiRequest, service_name);
			if(declareBean == null || orderTreeBusiRequest == null)
				return null;
			String busiPrimSql = getPrimperySql(busiRequest); //获取业务对象主键值
			List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(declareBean);
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
				if(!f_service_name.equals(service_name))
					continue;
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					
					MethodUtils.invokeMethod(declareBean, "set"+fname.substring(0,1).toUpperCase()+fname.substring(1), null); //更新处理
					break;//一次成功直接退出
				}else if("java.util.List".equals(type)) //判断插入还是更新
				{
//					synchronized ("yes") { //临时换同步，需要待确认
						List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(declareBean, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
						boolean isInsert = true;
						for (ZteBusiRequest subZteRequest:subZteRequests) {
							String subPrimSql = getPrimperySql(subZteRequest);
							if(busiPrimSql.equals(subPrimSql)){ //修改处理逻辑
								subZteRequests.remove(subZteRequest);
								break;
							}
						}
						MethodUtils.invokeMethod(declareBean, "set"+fname.substring(0,1).toUpperCase()+fname.substring(1), subZteRequests);
						break; //一次成功直接退出
//					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(declareBean instanceof OrderTreeBusiRequest)
			return (OrderTreeBusiRequest)declareBean;
		return orderTreeBusiRequest;
	}
	
	
	public static void setZteRequestChangedFields(ZteBusiRequest oldBusiRequest,ZteBusiRequest newBusiRequest){
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(newBusiRequest.getClass());
			List<String> changeFields = newBusiRequest.getChangeFiels();
			for (int i = 0; i < changeFields.size(); i++) {
				String propertyName = changeFields.get(i);
				if("es_attr_gift_param".equals(propertyName))
					propertyName ="giftParamBusiRequest";
				Object newValue = MethodUtils.invokeMethod(newBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				Object oldValue = MethodUtils.invokeMethod(oldBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				if(newValue ==null)
					continue;
				if(!newValue.equals(oldValue)){
					MethodUtils.invokeMethod(oldBusiRequest, "set"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), newValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getAllChageFields(ZteBusiRequest oldBusiRequest,ZteBusiRequest newBusiRequest,List<RequestFieldDefinition> fieldDefinitions ){
		List<String> changeFields = newBusiRequest.getChangeFiels();
		if(!ListUtil.isEmpty(changeFields)&& changeFields.get(0).equals(EcsOrderConsts.ALL_FIELDS))
		{	
			changeFields.remove(EcsOrderConsts.ALL_FIELDS);
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions)
				changeFields.add(fieldDefinition.getDname());
		}
		return changeFields;
	}
	public static List<String> getChageFields(ZteBusiRequest oldBusiRequest,ZteBusiRequest newBusiRequest){
		
		if(oldBusiRequest ==null)
			return new ArrayList<String>();
		BeanInfo beanInfo;
		List<String> changeFields = new ArrayList<String>();
		try {
		if(newBusiRequest instanceof AttrInstBusiRequest){
			String newValue = ((AttrInstBusiRequest)newBusiRequest).getField_value();
			String oldValue = ((AttrInstBusiRequest)oldBusiRequest).getField_value();
			if(!StringUtils.isEmpty(newValue)){
				if(!newValue.equals(oldValue)){
					changeFields.add("field_value");
				}
			}else if(!StringUtils.isEmpty(oldValue)){	//新值为空，则可能为清空
				if(!oldValue.equals(newValue)){
					changeFields.add("field_value");
				}
			}
			return changeFields;
		}
		beanInfo = Introspector.getBeanInfo(newBusiRequest.getClass());
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				Class propertyType = descriptor.getPropertyType();
				Object newValue = MethodUtils.invokeMethod(newBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				Object oldValue = MethodUtils.invokeMethod(oldBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				if(newValue ==null)
					continue;
				if(("is_dirty".equals(propertyName)|| "db_action".equals(propertyName) ||"changeFiels".equals(propertyName)))
					continue;
				if("java.lang.Class".equals(propertyType.getName()))
					continue;
				if ("java.lang.String".equals(propertyType.getName())) {
					if(newValue.equals(""))
						continue;
				}
				if(!newValue.equals(oldValue) && (!(propertyType.getName().equals("java.util.List")|| (propertyType.getName().indexOf("com.") > -1 || propertyType.getName().indexOf("zte.") > -1|| propertyType.getName().indexOf("service.") > -1))))
				{
					changeFields.add(propertyName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changeFields;
	}
	
	/**
	 * 获取对象对象修改前老数据
	 * @param busiRequest
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ZteBusiRequest getOldZteBusiRequestFromOrderTree(ZteBusiRequest busiRequest){
		
		//add by wui获取新旧值处理构造逻辑
		if(busiRequest instanceof AttrInstBusiRequest){
			ILogsServices logsServices = SpringContextHolder.getBean("logsServices");
			AttrInstBusiRequest oldBusiRequest = logsServices.getAttrInstBusiRequestByOrderId(((AttrInstBusiRequest) busiRequest).getOrder_id(), ((AttrInstBusiRequest) busiRequest).getField_name());
			return oldBusiRequest;
		}
		RequestBeanDefinition<ZteBusiRequest> busiServiceBean = BusiUtils.getRequestServiceDefinition(busiRequest);
		String service_name = busiServiceBean.getService_name();
		OrderTreeBusiRequest orderTreeBusiRequest = null;
		if(busiRequest instanceof OrderTreeBusiRequest)
			return CommonDataFactory.getInstance().getOrderTree(busiRequest);
		if(StringUtil.isEmpty(service_name))
			return null;
		try{
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(busiRequest);
			ZteBusiRequest declareBean = getOwnerDeclareBean(orderTreeBusiRequest,busiRequest, service_name);
			if(declareBean == null || orderTreeBusiRequest == null)
				return null;
			String busiPrimSql = getPrimperySql(busiRequest); //获取业务对象主键值
			List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(declareBean);
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
				if(!f_service_name.equals(service_name))
					continue;
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					return (ZteBusiRequest)MethodUtils.invokeMethod(declareBean, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
				}else if("java.util.List".equals(type)) //判断插入还是更新
				{
					List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(declareBean, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					for (ZteBusiRequest subZteRequest:subZteRequests) {
						String subPrimSql = getPrimperySql(subZteRequest);
						if(busiPrimSql.equals(subPrimSql)){
							return subZteRequest;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 订单树上获取与服务名匹配的字段名
	 * @param request
	 * @param service_name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ZteBusiRequest getOwnerDeclareBean(ZteBusiRequest request,ZteBusiRequest zteUpdateRequest,String service_name){
		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
		ZteBusiRequest declareBean = null;
		try{
			if(zteUpdateRequest instanceof OrderTreeBusiRequest)
				return zteUpdateRequest;
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
//				if(f_service_name.equals("OrderDeliveryBusiRequest"))
//					logger.info("1111111111");
				if(f_service_name.equalsIgnoreCase(service_name)){
					if(request == null) //为空，实例化对象
						request=org.springframework.beans.BeanUtils.instantiateClass(request.getClass());
					declareBean = request;
					return declareBean; //直接返回
				}
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					ZteBusiRequest subZteRequest = (ZteBusiRequest) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					declareBean = getOwnerDeclareBean(subZteRequest,zteUpdateRequest,service_name);
					if(declareBean !=null) //存在直接退出
						return declareBean;
				}else if("java.util.List".equals(type))
				{
					List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					//循环获取第一个即可
					//循环获取第一个即可
					if(subZteRequests ==null)
						continue;
					for (ZteBusiRequest subZteRequest:subZteRequests) { //获取依赖对象
						
						//同级关系，值不一致的才需要循环处理，否则跳过
						RequestBeanDefinition<ZteBusiRequest> subZteRequestDefinition = BusiUtils.getRequestServiceDefinition(subZteRequest);
						RequestBeanDefinition<ZteBusiRequest> zteUpdateRequestDefinition = BusiUtils.getRequestServiceDefinition(zteUpdateRequest);
						if(subZteRequestDefinition.getPrimary_keys().equals(zteUpdateRequestDefinition.getDepency_keys()))
						{
							String depencySql =getPrimperySql(subZteRequest);
							String primpekSql = getDefencySql(zteUpdateRequest);
							if(!depencySql.equals(primpekSql))
								continue;
						}
						declareBean = getOwnerDeclareBean(subZteRequest,zteUpdateRequest,service_name);
						if(declareBean !=null) //存在直接退出
							return declareBean;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return declareBean;
	}
	
	
	public static ZteBusiRequest getZteBusiRequestByServiceBean(ZteBusiRequest zteBusiRequest,RequestBeanDefinition tableBeanDefinition,String primpekSql){
		ZteBusiRequest declareBean = null;
		try{
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition(zteBusiRequest);
			List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(zteBusiRequest);
			if(serviceDefinition.getService_name().equals(tableBeanDefinition.getService_name())){
				declareBean= zteBusiRequest;
				return declareBean;
			}
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
				if(f_service_name.equalsIgnoreCase(tableBeanDefinition.getService_name())){
					Object subZteRequest =  MethodUtils.invokeMethod(zteBusiRequest, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					if(subZteRequest instanceof ZteBusiRequest)
						return (ZteBusiRequest)subZteRequest; //直接返回
					else{
//						logger.info("1111111111111111");
					}
				}
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					ZteBusiRequest subZteRequest = (ZteBusiRequest) MethodUtils.invokeMethod(zteBusiRequest, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					declareBean = getZteBusiRequestByServiceBean(subZteRequest,tableBeanDefinition,primpekSql);
					if(declareBean !=null) //存在直接退出
						return declareBean;
				}else if("java.util.List".equals(type))
				{
					List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(zteBusiRequest, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					boolean isExist = false;
					for (ZteBusiRequest subZteRequest:subZteRequests) {
						RequestBeanDefinition<ZteBusiRequest> subZteRequestDefinition = BusiUtils.getRequestServiceDefinition(subZteRequest);
						String depencySql =getPrimperySql(subZteRequest);
						if(!depencySql.equals(primpekSql))
							continue;
						isExist = true;
						declareBean = getZteBusiRequestByServiceBean(subZteRequest,tableBeanDefinition,primpekSql);
						if(declareBean !=null) //存在直接退出
							return declareBean;
					}
					//主键匹配不上，统一操作一把
					if(!isExist)
					{
						for (ZteBusiRequest subZteRequest:subZteRequests) {
							RequestBeanDefinition<ZteBusiRequest> subZteRequestDefinition = BusiUtils.getRequestServiceDefinition(subZteRequest);
							String depencySql =getPrimperySql(subZteRequest);
							String depencySqlN = depencySql.substring(0,depencySql.indexOf("="));
							String primpekSqlN = primpekSql.substring(0,primpekSql.indexOf("="));
							if(!depencySqlN.equals(primpekSqlN))
								continue;
							isExist = true;
							declareBean = getZteBusiRequestByServiceBean(subZteRequest,tableBeanDefinition,primpekSql);
							if(declareBean !=null) //存在直接退出
								return declareBean;
						}
					}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return declareBean;
	}
		
	
	
	public static DefaultActionRequestDefine getContext() {
		return context;
	}


	public static void setContext(DefaultActionRequestDefine context) {
		BusiUtils.context = context;
	}
	
	
	
	/**
	 * 构造缓存key
	 * @param beanName
	 * @param popMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  String genPofCacheKey(String beanName ,ZteBusiRequest request ){
		try{
			String prim_keys ="";
			RequestBeanDefinition<ZteBusiRequest> serviceDefinition = context.getActionRequestServiceMap(beanName);
			if(serviceDefinition !=null && !StringUtil.isEmpty(serviceDefinition.getTable_name())){
				prim_keys = serviceDefinition.getPrimary_keys();
			}
			String prim_keysavls = "";
			if(!StringUtil.isEmpty(prim_keys))
			{
				String [] prim_keys_arr = prim_keys.split(",");
				for (int i = 0; i < prim_keys_arr.length; i++) {
					String value =  (String)MethodUtils.invokeMethod(request, "get"+prim_keys_arr[i].substring(0,1).toUpperCase()+prim_keys_arr[i].substring(1), null);
					if(!StringUtil.isEmpty(value)){
						prim_keysavls+=value;
					}
				}
			}
			return prim_keys+prim_keysavls;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取对象对象修改前老数据
	 * @param busiRequest
	 */
	public static ZteBusiRequest getPofOldZteBusiRequestFromCache(ZteBusiRequest busiRequest){
		String order_id = getOrderIdByZteBusiRequest(busiRequest);
		if(StringUtil.isEmpty(order_id)) return null;
		return(ZteBusiRequest) CoherenceRamCache.getCache().get(busiRequest.getClass().getName() + "#" + order_id);
	}
	
	
	/**
	 * 订单树上获取与服务名匹配的字段名
	 * @param request
	 * @param service_name
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ZteBusiRequest getPofOwnerDeclareBean(ZteBusiRequest request,ZteBusiRequest zteUpdateRequest,String service_name){
		List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
		ZteBusiRequest declareBean = null;
		try{
			if(zteUpdateRequest instanceof OrderTreeBusiRequest)
				return zteUpdateRequest;
			for (RequestFieldDefinition fieldDefinition:fieldDefinitions) {
				String type = fieldDefinition.getField().getType().getName();
				String fname = fieldDefinition.getField().getName();
				String f_service_name = fieldDefinition.getService_name();
				if(f_service_name.equalsIgnoreCase(service_name)){
					if(request == null) //为空，实例化对象
						request=org.springframework.beans.BeanUtils.instantiateClass(request.getClass());
					declareBean = request;
					return declareBean; //直接返回
				}
				if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1|| type.indexOf("service.") > -1) {
					ZteBusiRequest subZteRequest = (ZteBusiRequest) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					declareBean = getPofOwnerDeclareBean(subZteRequest,zteUpdateRequest,service_name);
					if(declareBean !=null) //存在直接退出
						return declareBean;
				}else if("java.util.List".equals(type))
				{
					List<ZteBusiRequest> subZteRequests =  (List<ZteBusiRequest>) MethodUtils.invokeMethod(request, "get"+fname.substring(0,1).toUpperCase()+fname.substring(1), null);
					//循环获取第一个即可
					for (ZteBusiRequest subZteRequest:subZteRequests) { //获取依赖对象
						
						//同级关系，值不一致的才需要循环处理，否则跳过
						RequestBeanDefinition<ZteBusiRequest> subZteRequestDefinition = BusiUtils.getRequestServiceDefinition(subZteRequest);
						RequestBeanDefinition<ZteBusiRequest> zteUpdateRequestDefinition = BusiUtils.getRequestServiceDefinition(zteUpdateRequest);
						if(subZteRequestDefinition.getPrimary_keys().equals(zteUpdateRequestDefinition.getDepency_keys()))
						{
							String depencySql =getPrimperySql(subZteRequest);
							String primpekSql = getDefencySql(zteUpdateRequest);
							if(!depencySql.equals(primpekSql))
								continue;
						}
						declareBean = getPofOwnerDeclareBean(subZteRequest,zteUpdateRequest,service_name);
						if(declareBean !=null) //存在直接退出
							return declareBean;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return declareBean;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getPofChageFields(ZteBusiRequest oldBusiRequest,ZteBusiRequest newBusiRequest){
		
		if(oldBusiRequest ==null)
			return new ArrayList<String>();
		BeanInfo beanInfo;
		List<String> changeFields = new ArrayList<String>();
		try {
		if(newBusiRequest instanceof AttrInstBusiRequest){
			String newValue = ((AttrInstBusiRequest)newBusiRequest).getField_value();
			String oldValue = ((AttrInstBusiRequest)oldBusiRequest).getField_value();
			if(!StringUtils.isEmpty(newValue)){
				if(!newValue.equals(oldValue)){
					changeFields.add("field_value");
				}
			}else if(!StringUtils.isEmpty(oldValue)){	//新值为空，则可能为清空
				if(!oldValue.equals(newValue)){
					changeFields.add("field_value");
				}
			}
			return changeFields;
		}
		beanInfo = Introspector.getBeanInfo(newBusiRequest.getClass());
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				Class propertyType = descriptor.getPropertyType();
				Object newValue = MethodUtils.invokeMethod(newBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				Object oldValue = MethodUtils.invokeMethod(oldBusiRequest, "get"+propertyName.substring(0,1).toUpperCase()+propertyName.substring(1), null);
				if(newValue ==null)
					continue;
				if(("is_dirty".equals(propertyName)|| "db_action".equals(propertyName) ||"changeFiels".equals(propertyName)))
					continue;
				if("java.lang.Class".equals(propertyType.getName()))
					continue;
				if ("java.lang.String".equals(propertyType.getName())) {
					if(newValue.equals(""))
						continue;
				}
				if("java.util.Map".equals(propertyType.getName())){
					Map<String,Object> newMap = (HashMap<String, Object>) newValue;
					Map<String,Object> oldMap = (HashMap<String, Object>) oldValue;
					if(newMap.equals(oldMap))continue;
					for (Map.Entry<String, Object> entry : newMap.entrySet()) {
						 final String newMapKey = entry.getKey();
						 //旧对象内有对应key 并且新对象值不等于旧对象,做更新操作;
						 if(oldMap.containsKey(newMapKey) && !entry.getValue().equals(oldMap.get(newMapKey))){
							changeFields.add(propertyName+"#update");
						 }else if(!oldMap.containsKey(newMapKey) && null != AttrUtils.getInstance().getAttrTableByAttrTableFieldName(newMapKey, "")){
							 //新对象有key,但是旧对象没有key值,判断是否在数据库有配置,有则做插入
							 changeFields.add(propertyName+"#insert");
						 }
					}
				}
				if(!newValue.equals(oldValue) && (!(propertyType.getName().equals("java.util.Map") || !propertyType.getName().equals("java.util.List")|| (propertyType.getName().indexOf("com.") > -1 || propertyType.getName().indexOf("zte.") > -1|| propertyType.getName().indexOf("service.") > -1))))
				{
					changeFields.add(propertyName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changeFields;
	}
	
	/**
	 * 拼接sql语句
	 * @param simpleName
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static List<String> getPofChangeValueFields(List<String> changeFields, List<RequestFieldDefinition> fieldDefinitions, ZteBusiRequest request) throws Exception {
		List<String> vChangeFieldsList = new ArrayList<String>();
		for (String field_name : changeFields) {
			// 处理update操作时,需要做insert操作的标识
//			field_name = field_name.contains("#") ? field_name.substring(0, field_name.indexOf("#")) : field_name;
			RequestFieldDefinition fieldDefinition = BusiUtils.getRequestFieldDefinitionByName(field_name, fieldDefinitions);
			if (fieldDefinition == null && !request.getDyn_field().containsKey(field_name))
				continue;
			if (null != fieldDefinition) {
				String name = fieldDefinition.getField().getName();
				if (StringUtil.isEmpty(name))
					name = fieldDefinition.getDname();
				Object fV = MethodUtils.invokeMethod(request, "get" + name.substring(0, 1).toUpperCase() + name.substring(1), null);
				if (ConstsCore.CONSTS_YES.equals(fieldDefinition.getNeed_insert()) && (null != fV)) { // 查询字段,字段值不为空
					if (fV instanceof String)
						if (StringUtil.isEmpty((String) fV))
							continue;
					if (fV == null || "null".equals(fV))
						continue;
					if (ConstsCore.NULL_VAL.equals(fV))
						fV = ""; // 传递空值处理逻辑
					// 过滤优化处理
					vChangeFieldsList.add(fieldDefinition.getDname());
				}
			}else if(request.getDyn_field().containsKey(field_name)){
				if (ConstsCore.NULL_VAL.equals(request.getDyn_field().get(field_name))){
					request.getDyn_field().put(field_name,"");
					vChangeFieldsList.add(field_name);
				}
			}
		}
		return vChangeFieldsList;
	}
	
	public static String getOrderIdByZteBusiRequest(ZteBusiRequest zteBusiRequest) {
		try {
			String orderId = (String) MethodUtils.invokeMethod(zteBusiRequest, "getOrder_id", null);
			return orderId;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
