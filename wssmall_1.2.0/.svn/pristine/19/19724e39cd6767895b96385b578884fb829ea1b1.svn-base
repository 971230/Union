package com.ztesoft.ibss.ct.config;

import com.powerise.ibss.util.WBCacheUtil;
import com.ztesoft.ibss.common.util.SqlMapExe;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Reason.Yea
 * @version Created Feb 8, 2012
 */
public class ConfigUtil {
	public static String SPLIT_KEY = "`";
	public static String CtConfigCatalog = "CTCONFIGCATALOG";
	public static String IpLanCodeCatalog = "IPLANCODECATALOG";
	public static String LanNameCatalog = "LANNAMECATALOG";
	public static String ActionRuleCatalog = "ACTIONRULECATALOG";
	
	public static String ServConfigCatalog = "SERVCONFIGCATALOG";
	public static String CardDefLanCodeCatalog = "CARDDEFLANCODECATALOG";
	public static String PageAcceptCatalog = "PAGEGUIDECATALOG";
	public static String PageAOP = "PageAOP";
	public static String ChargeRuleCatalog = "CHARGERULECATALOG";
	public static String PageStaticCatalog = "PAGESTATICCATALOG";	
	public static String ChargeProductCatalog = "CHARGEPRODUCTCATALOG";	
	public static String tvlsmBureauCatalog = "TVLSMBUREAUCATALOG";	
	public static String ServKindCatalog = "SERVKINDCATALOG";	
	
	public static String tvlsmBureauCatalogNode = "TVLSMBUREAUCATALOG";

	/**
	 * 规则key
	 * @param rule_action
	 * @param rule_sql_type
	 * @param precess_type
	 * @return
	 */
	public static String getRuleKey(String rule_action,String rule_sql_type,String precess_type){
		String key = rule_action+SPLIT_KEY+rule_sql_type+SPLIT_KEY+precess_type;
		return key;
	}
	/**
	 * ct_config表缓存
	 * @param conn
	 */
	public static void cacheCtConfig(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = "select CT_KEY, CT_VALUE from CT_CONFIG ";
		List ctConfigList = sqlExe.queryForMapList(sql);
		for (Iterator iterator = ctConfigList.iterator(); iterator.hasNext();) {
			HashMap ctConfig = (HashMap) iterator.next();
			String key =(String) ctConfig.get("ct_key"); 
			WBCacheUtil.putCache(CtConfigCatalog, key, ctConfig.get("ct_value"));
		}
	}
	
	/**
	 * ip和本地网关联缓存
	 * @param conn
	 */
	public static void cacheIpLanCode(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = "select a.begin_ip begin_ip,a.end_ip end_ip,a.area_code area_code,b.city_no city_no,b.city_name city_name from TVLWB_IP_AREA_CODE a,TVLWB_CITY_INFO b where a.area_code = b.area_code  ";
    	List ipLanCodeList =sqlExe.queryForMapList(sql);
    	WBCacheUtil.putCache(IpLanCodeCatalog, IpLanCodeCatalog,ipLanCodeList);
	}
    /**
     * 本地网区域表tvlwb_city_info
     * @param conn
     */
    public static void cacheLanName(Connection conn) {
    	SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = "SELECT area_code,city_name FROM tvlwb_city_info ";
    	List lanList = sqlExe.queryForMapList(sql);
    	for (Iterator iterator = lanList.iterator(); iterator.hasNext();) {
    		HashMap lan = (HashMap) iterator.next();
    		String key =(String) lan.get("area_code");
    		WBCacheUtil.putCache(LanNameCatalog, key, lan.get("city_name"));
    	}
    }
	
    
    /**
	 * servP_no表缓存
	 * @param conn
	 */
	public static void cacheServNoConfig(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = "select a.* from twb_serv a  where a.serv_flag in (1,2) and a.sequ='0' and a.state='1'";
		List configList = sqlExe.queryForMapList(sql);
		for (Iterator iterator = configList.iterator(); iterator.hasNext();) {
			HashMap config = (HashMap) iterator.next();
			String key =(String) config.get("serv_no"); 
			WBCacheUtil.putCache(ServConfigCatalog, key, config);
		}
	}
	
	/**
	 * card_def表缓存,key为card_type,value为卡片列表
	 * @param conn
	 */
	public static void cacheCardDefConfig(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = "select a.* from twb_card_def a  where 1=1 order by a.card_type,a.order_id";
		List configList = sqlExe.queryForMapList(sql);
		Map cardMap = WBCacheUtil.listToMapByFieldName(configList, "card_type");
		for (Iterator iterator = cardMap.keySet().iterator(); iterator.hasNext();) {
			String key =(String) iterator.next();
			List cards = (List)cardMap.get(key);
			WBCacheUtil.putCache(CardDefLanCodeCatalog, key, cards);
		}
	}
	
	
	/**
	 * 业务受理导航配置缓存
	 * page_define,wizard
	 * @param conn
	 */
	public static void cachePageAcceptConfig(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = " select a.*,b.* from page_wizard a,page_define b where a.path = b.code order by serv_no, order_id  ";
		List configList = sqlExe.queryForMapList(sql);
		Map cardMap = WBCacheUtil.listToMapByFieldName(configList, "serv_no");
		for (Iterator iterator = cardMap.keySet().iterator(); iterator.hasNext();) {
			String key =(String) iterator.next();
			List cards = (List)cardMap.get(key);
			WBCacheUtil.putCache(PageAcceptCatalog, key, cards);
		}
	}

	public static void cacheServKind(Connection conn) {
		SqlMapExe sqlExe = new SqlMapExe(conn);
		String sql = " select a.serv_kind,a.serv_kind_name,url,user_flag,kind_title,kind_keywords,kind_desc from twb_serv_kind a ";
		List configList = sqlExe.queryForMapList(sql);
		Map kindMap = WBCacheUtil.listToMapByFieldName(configList, "serv_kind");
		for (Iterator iterator = kindMap.keySet().iterator(); iterator.hasNext();) {
			String key =(String) iterator.next();
			HashMap kind = (HashMap) ((List)kindMap.get(key)).get(0);
			WBCacheUtil.putCache(ServKindCatalog, key, kind);
		}
		
	}
}
