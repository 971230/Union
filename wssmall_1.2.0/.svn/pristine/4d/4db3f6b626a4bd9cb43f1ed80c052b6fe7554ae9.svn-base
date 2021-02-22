package com.ztesoft.common.util;

import org.apache.log4j.Logger;

/**
 * 
 * <p>Description: 系统常量</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-1
 * @Version       : 1.0
 */
public class CrmConstants {
	private static Logger logger = Logger.getLogger(CrmConstants.class);
	
	public static final String DB_TYPE_INFORMIX = "INFORMIX";
	public static final String DB_TYPE_ORACLE = "ORACLE";	

	//系统默认日期格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	//数据库默认日期时间格式
	public static final String DATE_TIME_FORMAT_DB = "yyyy-MM-dd HH24:mi:ss";
	//系统默认日期时间格式
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	//23位时间格式，带毫秒
	public static final String DATE_TIME_FORMAT23 = "yyyy-MM-dd HH:mm:ss.SSS";
	//8位日期格式
	public static final String DATE_FORMAT_8 = "yyyyMMdd";
	//14位日期时间格式
	public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

	//默认失效时间
	public static final String DEFAULT_EXPIRED_DATE = "2030-1-1 00:00:00";
	
	public static final String JUMPED_TACHE_STYPE = "102";
	public static final String SYN_MATRIX_TEMPLATE_FILED = "103";
	
	//环节配置页面，“环节流转参数”的“流转参数值”。
	public static final String NOT_REACHABLE = "not_reachable";
	
	//系统数据库类型
	public static String CRM_DATABASE_TYPE = "ORACLE";
	
	//是否写容灾库开关，true表示写
	public static String WIRTE_BAKUOS = "false";
		
	//系统运行省份
	public static String RUN_PROVINCE = null;
	public static String RUN_PROVINCE_CN = null;
	public static String RUN_PROVINCE_CODE = null;
	public static String RUN_PROVINCE_REGION_CODE = null;
	public static final String GX_PROVINCE = "gx";//广西
	public static final String YN_PROVINCE = "yn";//云南
	public static final String CQ_PROVINCE = "cq";//重庆
	public static final String HN_PROVINCE = "hn";//湖南
	public static final String GS_PROVINCE = "gs";//甘肃
	public static final String XJ_PROVINCE = "xj";//新疆
	public static final String JX_PROVINCE = "jx";//江西
	
	//自动获取的web-inf绝对路径
	public static String WEB_INF_PATH = "CRM_WEB_INF_PATH";
	
	//自动获取的web-inf绝对路径
	public static String SHOW_SQL = "true";
	
	//自动获取的web-inf绝对路径
	public static String SHOW_METHOD_TIME = "true";	
	
	//分页记录数最大限制数
	public static int MAX_PAGE_SIZE = 30;
	
	//分页记录数默认值
	public static int DEFAULT_PAGE_SIZE = 20;
	
	//查询最大记录数限制
	public static int MAX_QUERY_SIZE = 3000;
	
	//计费模型1.0
	public static String HB_VERSION_1 = "1";
	
	//计费模型2.8
	public static String HB_VERSION_2 = "2";
	
	
	// #系统项目编码如TIANJIN,CHONGQING等  
	public static String TJ_PROJECT_CODE = "TIANJIN";
	public static String CQ_PROJECT_CODE = "CONGQING";
	
	//集团产品下发流程ID
	public static String GROUP_PROD_RELEASE_SERV = "";
	
    //集团销售品下发流程ID
	public static String GROUP_PPRODOFFER_RELEASE_SERV = "";
	
    //标准矩阵ID，集团下发产品销售品适用矩阵
	public static  String COMMON_MATRIX_ID = "";
	
	//计费模型版本(1代表1.0、2代表2.8)
	public static String HB_VERSION = "";
	
	//销售品业务视图版本(2.2、2.8)
	public static String G_OFFER_APPLY_VISION = "";;//销售品业务视图当前版本号
	public static final String G_OFFER_APPLY_VISION_22 = "2.2";//PPM2.2版销售品业务视图
	public static final String G_OFFER_APPLY_VISION_28 = "2.8";//PPM2.8版销售品业务视图
	
	public static final String SYS_CT10000_PWD = "CT10000_PWD";	//2009-05-04增加为10000登录配置的密码
	
	public static void setMaxQuerySize(String size){
		try {
			if(null!=size && !"".equals(size))
				MAX_QUERY_SIZE = Integer.parseInt(size);
		} catch (Exception e) {
		}
		 
	}
    public static int getMaxQuerySize(){				
		return MAX_QUERY_SIZE;
	}
	
	//上传文件大小(一般设置10M左右)	
	public static long MAX_UPLOAD_SIZE = 10000;
	
	public static void setMaxUploadSize(String size){
		try {
			if(null!=size && !"".equals(size))
				MAX_UPLOAD_SIZE = Long.parseLong(size);
		} catch (Exception e) {
		}
		 
	}
    public static long getMaxUploadSize(){				
		return MAX_UPLOAD_SIZE;
	}
	
    //不需要登陆而直接可以访问的页面
    public static String[] NOT_FILTER_PAFGES = new String[0];
    
	public static void setNotFilterPages(String pages){
		try {
			if(null != pages)
				NOT_FILTER_PAFGES = pages.split("(,)");
		} catch (Exception e) {
		}
	}
	
    public static void setDBType(String DBType){
    	if(null!= DBType && !"".equals(DBType))
    		CRM_DATABASE_TYPE = DBType.trim().toUpperCase();
    }
    
    public static void setRunProvince(String province){
    	if(null!= province && !"".equals(province))
    		RUN_PROVINCE = province.trim().toLowerCase();
    }
    
    public static void setRunProvinceCode(String provinceCode){
    	if(null!= provinceCode && !"".equals(provinceCode))
    		RUN_PROVINCE_CODE = provinceCode;
    }
    public static void setRunProvinceRegionCode(String regionCode){
    	if(null!= regionCode && !"".equals(regionCode))
    		RUN_PROVINCE_REGION_CODE = regionCode;
    }
    public static void setRunProvinceCn(String province) {
    	if(null!= province && !"".equals(province))
    		RUN_PROVINCE_CN = province;
	}
    
    
	
	public static String getRUN_PROVINCE_CN() {
		return RUN_PROVINCE_CN;
	}
	
	
	
	public static boolean  isGuangXiProvince() {
    	if (RUN_PROVINCE == null) {
    		logger.warn("没有设置系统变量：运行省份。");
		}
		return GX_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
	}
	
	public static boolean  isGanSuProvince() {
		if (RUN_PROVINCE == null) {
			logger.warn("没有设置系统变量：运行省份。");
		}
		return GS_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
	}
    
    public static boolean  isYunNanProvince() {
    	if (RUN_PROVINCE == null) {
    		logger.warn("没有设置系统变量：运行省份。");
		}
    	return YN_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
    }
    
    public static boolean  isChongQingProvince() {
    	if (RUN_PROVINCE == null) {
    		logger.warn("没有设置系统变量：运行省份。");
		}
    	return CQ_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
    }
    
    public static boolean  isHuNanProvince() {
    	if (RUN_PROVINCE == null) {
    		logger.warn("没有设置系统变量：运行省份。");
    	}
    	return HN_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
    }
    public static boolean  isXinJiangProvince() {
    	if (RUN_PROVINCE == null) {
    		logger.warn("没有设置系统变量：运行省份。");
    	}
    	return XJ_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
    }
	public static boolean isJiangXiProvince() {
		if (RUN_PROVINCE == null) {
			logger.warn("没有设置系统变量：运行省份。");
		}
		return JX_PROVINCE.equalsIgnoreCase(RUN_PROVINCE);
	}
	//不需要登陆而直接可以访问的页面
	public static String[] getNotFilterPages(){		
		
		return NOT_FILTER_PAFGES;
		
	}
	                     

	/**
	 * 调试状态
	 * @return
	 */
	public static boolean getDebugState(){
	  return true;	
	}

	
	
	public static String getGroupProdOfferReleaseService() {
		return GROUP_PPRODOFFER_RELEASE_SERV;
	}
	public static void setGroupProdOfferReleaseService(
			String group_prod_offer_release_flow) {
		GROUP_PPRODOFFER_RELEASE_SERV = group_prod_offer_release_flow;
	}
	public static String getGroupProdReleaseService() {
		return GROUP_PROD_RELEASE_SERV;
	}
	public static void setGroupProdReleaseService(String group_prod_release_flow) {
		GROUP_PROD_RELEASE_SERV = group_prod_release_flow;
	}
	public static String getCommonMatrixId() {
		return COMMON_MATRIX_ID;
	}
	public static void setCommonMatrixId(String common_matrix_id) {
		COMMON_MATRIX_ID = common_matrix_id;
	}
	public static String getHbVersion() {
		return HB_VERSION;
	}
	public static void setHbVersion(String hb_version) {
		HB_VERSION = hb_version;
	}
	
	//获取销售品业务视图版本
	public static String getOfferApplyVersion() {
		return G_OFFER_APPLY_VISION;
	}
	
	//是否销售品业务视图2.2版本
	public static boolean isOfferApplyVersion22() {
		return G_OFFER_APPLY_VISION_22.equals(G_OFFER_APPLY_VISION);
	}
	
	//是否销售品业务视图2.8版本
	public static boolean isOfferApplyVersion28() {
		return G_OFFER_APPLY_VISION_28.equals(G_OFFER_APPLY_VISION);
	}
	
	//设置销售品业务视图版本
	public static void setOfferApplyVersion(String version) {
		if (G_OFFER_APPLY_VISION_22.equals(version) || G_OFFER_APPLY_VISION_28.equals(version)) {
			G_OFFER_APPLY_VISION = version;
		}
	}
}
