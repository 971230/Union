package com.ztesoft.ibss.common.util;


/**
 * 
 * <p>Description: 系统常量</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-1
 * @Version       : 1.0
 */
public class CrmConstants {
	
	public static final String DB_TYPE_INFORMIX = "INFORMIX";
	public static final String DB_TYPE_ORACLE = "ORACLE";	

	//系统默认日期格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";
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
	
	//系统数据库类型
	public static final String CRM_DATABASE_TYPE = "oracle";
	
	//自动获取的web-inf绝对路径
	public static String WEB_INF_PATH = "CRM_WEB_INF_PATH";
	
	//自动获取的web-inf绝对路径
	public static String SHOW_SQL = "false";
	
	//自动获取的web-inf绝对路径
	public static String SHOW_METHOD_TIME = "true";	
	
	//分页记录数最大限制数
	public static int MAX_PAGE_SIZE = 30;
	
	//分页记录数默认值
	public static int DEFAULT_PAGE_SIZE = 20;
	
	//查询最大记录数限制
	public static int MAX_QUERY_SIZE = 3000;
	
	public static String PROJECT_CODE="";
	
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
    
    public static void setProjectCode(String code){
    	try {
    		if(null!=code && !"".equals(code))
    			PROJECT_CODE = code;
    	} catch (Exception e) {
    	}
    	
    }
    public static String getProjectCode(){				
    	return PROJECT_CODE;
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
	//不需要登陆而直接可以访问的页面
	public static String[] getNotFilterPages(){		
		
		return NOT_FILTER_PAFGES;
		
	}
	                     
	
	/**
	 * 获取接口服务器的IP地址
	 * @return
	 */
	public static String getInterfaceIp(){
		return null;
		//return CrmParamsConfig.getInstance().getParamValue("IF_RMI_ADD");
		//return "136.5.8.119:8888";
	}
	/**
	 * 调试状态
	 * @return
	 */
	public static boolean getDebugState(){
	  return true;	
	}
	
	/**
	 * 获取接口服务器的IP地址
	 * @return
	 */
	public static String getIIOPInterfaceIp(){
		return null;
		//return CrmParamsConfig.getInstance().getParamValue("IIOP_RMI_ADD");
		//return "136.5.8.119:8888";
	}
	
	/**
	 * 获取IOM服务器的IP地址
	 * 
	 */
	public static String getIomIp(){
		return null;
		//return CrmParamsConfig.getInstance().getParamValue("IOM_TACHE_INFO_IP");
	}

}
