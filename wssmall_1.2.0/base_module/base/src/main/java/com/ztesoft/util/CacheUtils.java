package com.ztesoft.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.iservice.IOrderServices;
import zte.params.order.req.RefreshOrderTreeAttrReq;

import com.ztesoft.cache.CacheFlag;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrTable;
import com.ztesoft.net.model.AttrTableCache;
import com.ztesoft.net.sqls.SF;

public class CacheUtils {
	private static Logger logger = Logger.getLogger(CacheUtils.class);
	public static final String ATTR_DEF_CACHE_ID = "ATTR_DEF_CACHE_KEY";//属性ID前缀
	
	public static final String ATTR_SPACE_TYPE = "DEF_";//按类型查询前缀
	
	public static final String ATTR_TABLE_SPACE_TYPE = "DTB_";//按类型查询前缀
	
	public static final String RULE_CSKEY = "CSKEY";//缓存常量
	
	public static final String RULE_COND_KEY = "RULE_COND";//缓存常量
	
	public static final String CATALOGUE_PLAN_KEY = "CATALOGUE_PLAN";//目录方案
	
	public static final String RULE_ACTION_KEY = "RULE_ACTION";//缓存常量
	
	public static final String PLAN_KEY = "PLAN_KEY";
	
	public static final String PLANRULEKEY = "PLANRULEKEY";//规则列表
	
	public static final String PLAN_RULE_KEY_ID = "PLAN_RULE_KEY";
	
	public static final String RULELGKEY_P_R_O = "RULELGKEY";//规则执行日志
	
	public static final String CPLANRULEKEY = "CPLANRULEKEY_";//子规则
	
	public static final String RULE_LOG_ID = "RULELGKEY";//按规则ID查询记录
	
	public static final String RELYPLANRULE = "RELYPLANRULE_";//查询依懒规则
	
	public static final String PLAN_RULE_KEY_P_R__E = "PLAN_RULE_KEY";
	
	public static final String ORDER_ROLE_DATA_KEY = "ORDER_ROLE_DATA_KEY";
	
	public static final String ATTR_DEF_TABLE_KEY = "ATTR_DEF_TABLE_KEY_";
	
	//表主银
	public static final String TABLE_PK_CONFIG = "TABLE_PK_CONFIG";
	public static final String TABLE_SEQ_CONDIG = "TABLE_SEQ_CONDIG";
	
	public static final String MALL_DATA_CONFIG = "MALL_DATA_CONFIG"; //es_mall_config缓存
	
	public static final String HAS_CACHE_FLAG = "HAS_CACHE_FLAG_OBJ";
	
	public static final String HAS_CACHE_ATTR_DEF = "HAS_CACHE_ATTR_DEF"; //订单属性是否缓存
	
	public static final String ATTR_TABLE_KEY = "ATTR_TABLE_KEY";
	public static final String ATTR_TABLE_KEY_SOURCE = "ATTR_TABLE_KEY_SOURCE";//按属性定议表属性名称获取缓存
	public static final String ATTR_TYPE = "ext";//按扩展表字段名称获取缓存
	
	public static final String PLAN_TACHE_CODE = "PLAN_TACHE_CODE";		//缓存方案键值(以环节编码为键值)
	
	
	private static Map<String, AttrDef> ATTR_DEF_MAP;
	private static Map<String, AttrDef> ATTR_DEF_FILED_MAP;
	private static Map<String, List<AttrTable>> ATTR_TABLE_MAP;
	private static Map<String, AttrTableCache> ATTR_TABLE_CACHE_MAP;
	
	public synchronized static void initCacheAttrDef(){
		String sql = SF.orderSql("ORDER_TREE_ATTR_LIST");
		sql += " and t.attr_spec_id='-999'";
		logger.info(sql);
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List defs = support.queryForList(sql, AttrDef.class,ManagerUtils.getSourceFrom());
		if(defs != null && defs.size() > 0) {
			Map<String, AttrDef> tempMap = new HashMap<String, AttrDef>();
			Map<String, AttrDef> tempFieldMap = new HashMap<String, AttrDef>();
			for(int i = 0; i < defs.size(); i++) {
				AttrDef def = (AttrDef)defs.get(i);
				String key = def.getField_name();
				tempMap.put(key+EcsOrderConsts.ATTR_SPEC_ID_999, def);
				tempFieldMap.put(ATTR_DEF_CACHE_ID + def.getField_attr_id(), def);
			}
			ATTR_DEF_MAP = Collections.unmodifiableMap(tempMap);
			ATTR_DEF_FILED_MAP = Collections.unmodifiableMap(tempFieldMap);
		}
	}
	
	public synchronized static void initCacheAttrTable(){
		String sql = "select * from es_attr_table";
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List attrTables = support.queryForList(sql, AttrTable.class);
		if(attrTables != null && attrTables.size() > 0) {
			Map<String, List<AttrTable>> tempMap = new HashMap<String, List<AttrTable>>();
			for(int i = 0; i < attrTables.size(); i++) {
				AttrTable attrTable = (AttrTable)attrTables.get(i);
				String key = attrTable.getAttr_def_id();
				List<AttrTable> tempList = tempMap.get(ATTR_DEF_TABLE_KEY+key);
				if(ListUtil.isEmpty(tempList)) {
					tempList = new ArrayList<AttrTable>();
				}
				tempList.add(attrTable);
				tempMap.put(ATTR_DEF_TABLE_KEY+key, tempList);
			}
			ATTR_TABLE_MAP = Collections.unmodifiableMap(tempMap);
		}
	}
	
	public static List<AttrTable> getCacheAttrTable(String attr_field_id){
		if(ATTR_TABLE_MAP != null && ATTR_TABLE_MAP.size() > 0) {
			return ATTR_TABLE_MAP.get(ATTR_DEF_TABLE_KEY+attr_field_id);
		}else {
			initCacheAttrTable();
			return ATTR_TABLE_MAP.get(ATTR_DEF_TABLE_KEY+attr_field_id);
		}
	}
	
	public synchronized static void initCacheAttrTableForType(){
		String sql = SF.orderSql("QUERYATTRTABLECACHE");
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List<AttrTableCache> attrTables = support.queryForList(sql, AttrTableCache.class, ManagerUtils.getSourceFrom());
		if(attrTables != null && attrTables.size() > 0) {
			Map<String, AttrTableCache> tempMap = new HashMap<String, AttrTableCache>();
			for(int i = 0; i < attrTables.size(); i++) {
				AttrTableCache attrTable = attrTables.get(i);
				tempMap.put(ATTR_TABLE_KEY+attrTable.getField_name(), attrTable);
				tempMap.put(ATTR_TABLE_KEY_SOURCE+attrTable.getDef_field_name(), attrTable);
			}
			ATTR_TABLE_CACHE_MAP = Collections.unmodifiableMap(tempMap);
		}
	}
	
	public static AttrTableCache getAttrTable(String field_name,String type){
		if(ATTR_TABLE_CACHE_MAP == null || ATTR_TABLE_CACHE_MAP.size() < 1) {
			initCacheAttrTableForType();
		}
		
		String str = ATTR_TABLE_KEY_SOURCE;
		if(ATTR_TYPE.equals(type)){
			str = ATTR_TABLE_KEY;
		}
		AttrTableCache at = ATTR_TABLE_CACHE_MAP.get(str+field_name);
		if(at ==null){
			initCacheAttrTableForType();
			at = getCache(str+field_name);
		}
		return at;
	}
	
	/**
	 * 获取是否缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-12-19 
	 * @return
	 */
	@Deprecated
	public static CacheFlag getHashCache(){
		CacheFlag c = getCache(HAS_CACHE_FLAG);
		return c;
	}
	
	/**
	 * 是否缓存订单属性
	 * @作者 MoChunrun
	 * @创建日期 2014-12-19 
	 * @return
	 */
	public static boolean isCacheAttrDef(){
		String str = getCache(HAS_CACHE_ATTR_DEF);
		if(!StringUtil.isEmpty(str) && "yes".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	public static Boolean getIsCheckTrace(){
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String isCheckTrace = cacheUtil.getConfigInfo("IS_CHECK_TRACE");
		if(!StringUtil.isEmpty(isCheckTrace)&&"1".equals(isCheckTrace)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public synchronized static void initAttrDefCache(){
		if(!isCacheAttrDef()){
			logger.info("==============初始化订单属性数据===========================");
			IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
			RefreshOrderTreeAttrReq attrReq = new RefreshOrderTreeAttrReq();
			orderServices.refreshOrderTreeAttr(attrReq); // 刷es_attr_def表
			addCache(HAS_CACHE_ATTR_DEF,"yes");
		}
	}
	

	/**
	 * 从缓存获取属性定义
	 * @作者 MoChunrun
	 * @创建日期 2014-11-11 
	 * @param key
	 * @return
	 */
	public static AttrDef getCacheAttrDef(String key){
//		initAttrDefCache();
		/*INetCache cache = CacheFactory.getCacheByType("");
		AttrDef def = (AttrDef) cache.get(Const.CACHE_SPACE, key);
		if(def ==null || StringUtil.isEmpty(def.getAttr_spec_id())){
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			String field_name =key.replaceAll("ATTR_DEF_CACHE_KEY", "");
			field_name =field_name.replaceAll("-999", "");
			String sql = SF.orderSql("ORDER_TREE_ATTR_LIST");
			AttrDef defq = (AttrDef) support.queryForObject(sql+"and  ( t.field_name=? or  t.field_attr_id=? )  and t.attr_spec_id='-999' and rownum<2", AttrDef.class,ManagerUtils.getSourceFrom(),field_name,field_name);
			if(defq !=null){
				cache.set(Const.CACHE_SPACE,key, defq);
				def= defq;
			}else{
				logger.info("属性字段名："+field_name+"值为空");
			}
		}
		return def;*/
		
		if(ATTR_DEF_MAP != null && ATTR_DEF_MAP.size() > 0) {
			return ATTR_DEF_MAP.get(key);
		}else {
			initCacheAttrDef();
			return ATTR_DEF_MAP.get(key);
		}
	}
	
//	"select t.*,h.handler_class from es_attr_def t left join es_attr_handler h on(h.handler_id=t.handler_id and h.source_from=t.source_from) "+
//	" where t.field_attr_id=? and t.source_from=?";
	
	/**
	 * 按ID获取cache属性定义
	 * @作者 MoChunrun
	 * @创建日期 2014-11-11 
	 * @param id
	 * @return
	 */
	public static AttrDef getCacheAttrDefByFiledAttrId(String id){
//		initAttrDefCache();
		/*INetCache cache = CacheFactory.getCacheByType("");
		String key = ATTR_DEF_CACHE_ID + id;
		AttrDef def = getCacheAttrDef(key);
		if(def ==null || StringUtil.isEmpty(def.getAttr_spec_id())){
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = SF.orderSql("ORDER_TREE_ATTR_LIST");
			AttrDef defq = (AttrDef) support.queryForObject(sql+" and  t.field_attr_id=? and t.attr_spec_id='-999' and rownum<2", AttrDef.class,ManagerUtils.getSourceFrom(),id);
			if(defq !=null){
				def= defq;
				cache.set(Const.CACHE_SPACE,key, defq);
			}
		}
		return def;*/
		
		if(ATTR_DEF_FILED_MAP != null && ATTR_DEF_FILED_MAP.size() > 0) {
			return ATTR_DEF_FILED_MAP.get(CacheUtils.ATTR_DEF_CACHE_ID+id);
		}else {
			initCacheAttrDef();
			return ATTR_DEF_FILED_MAP.get(CacheUtils.ATTR_DEF_CACHE_ID+id);
		}
	}
	
	public static void addCache(String key,Serializable serial){
		INetCache cache = CacheFactory.getCacheByType("");
		cache.set(Const.CACHE_SPACE, key, serial);
	}
	
	public static void addCache(String key,Serializable serial,int expireTime){
		INetCache cache = CacheFactory.getCacheByType("");
		cache.set(Const.CACHE_SPACE, key, serial,expireTime);
	}
	
	public static void deleteCache(String key){
		INetCache cache = CacheFactory.getCacheByType("");
		cache.delete(Const.CACHE_SPACE,key);
	}
	
	public static void deleteCache(int namespace, String key){
		INetCache cache = CacheFactory.getCacheByType("");
		cache.delete(namespace,key);
	}
	
	public static <T> T getCache(String key){
		INetCache cache = CacheFactory.getCacheByType("");
		return (T) cache.get(Const.CACHE_SPACE, key);
	}
	
	public static <T> T getCache(int namespace,String key){
		INetCache cache = CacheFactory.getCacheByType("");
		return (T) cache.get(namespace, key);
	}
	
	
	public static void main(String[] args) {
		System.setProperty("CONFIG", "H:\\中兴\\电商平台\\src\\wssmall2.0\\base\\src\\main\\java\\config\\"); //此处替换成为本地配置 单元测试 ，此处替换本地配置add by wui
		//INetCache cache = CacheFactory.getCacheByType("");
		//cache.delete(Const.CACHE_SPACE, "LeagueInfo-999");
		//AttrDef def = getCacheAttrDef("LeagueInfo-999");
		//logger.info(def);
		//LeagueInfo
		
		//cache.delete(Const.CACHE_SPACE, CacheUtils.ATTR_DEF_CACHE_ID+"101050");
		//AttrDef attrDef = CacheUtils.getCacheAttrDef(CacheUtils.ATTR_DEF_CACHE_ID+"101050");
		//logger.info(attrDef);
	}
	
}