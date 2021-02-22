package com.ztesoft.config;

import java.util.Enumeration;
import java.util.Properties;

public class ParamsConfig {

	/** 是否从配置文件进行数据Load */
	public static boolean CONFIG_LOADED = false;
	public static final String DB_TYPE_INFORMIX = "INFORMIX";
	public static final String DB_TYPE_ORACLE = "ORACLE";

	// 系统数据库类型
	public static String DATABASE_TYPE = "";
	private static Properties params = null;
	private static ParamsConfig paramsConfig = null;
	// 自动获取的web-inf绝对路径
	public static String WEB_INF_PATH = "CRM_WEB_INF_PATH";

	// 自动获取的web-inf绝对路径
	public static String SHOW_SQL = "true";

	// 自动获取的web-inf绝对路径
	public static String SHOW_METHOD_TIME = "true";

	// 分页记录数最大限制数
	public static int MAX_PAGE_SIZE = 30;

	// 分页记录数默认值
	public static int DEFAULT_PAGE_SIZE = 20;

	// 查询最大记录数限制
	public static int MAX_QUERY_SIZE = 3000;

	public static void setMaxQuerySize(String size) {
		try {
			if (null != size && !"".equals(size)) {
				MAX_QUERY_SIZE = Integer.parseInt(size);
			}
		} catch (Exception e) {
		}

	}

	public static int getMaxQuerySize() {
		return MAX_QUERY_SIZE;
	}

	// 上传文件大小(一般设置10M左右)
	public static long MAX_UPLOAD_SIZE = 10000;

	public static void setMaxUploadSize(String size) {
		try {
			if (null != size && !"".equals(size)) {
				MAX_UPLOAD_SIZE = Long.parseLong(size);
			}
		} catch (Exception e) {
		}

	}

	/**
	 * 是否是调试模式,true为调试模式,调用*BOTest类和invoker.invokeTest;
	 * false为生产模式,调用*BO类和invoker.invoke
	 * 
	 * @return
	 */
	public static boolean isDebug() {
		boolean flag = false;
		String isDebug = ParamsConfig.getInstance().getParamValue("IS_DEBUG");

		if ("true".equalsIgnoreCase(isDebug)) {
			flag = true;
		}
		return flag;
	}

	public static long getMaxUploadSize() {
		return MAX_UPLOAD_SIZE;
	}

	// 不需要登陆而直接可以访问的页面
	public static String[] NOT_FILTER_PAFGES = new String[0];

	public static void setNotFilterPages(String pages) {
		try {
			if (null != pages) {
				NOT_FILTER_PAFGES = pages.split("(,)");
			}
		} catch (Exception e) {
		}
	}

	// 不需要登陆而直接可以访问的页面
	public static String[] getNotFilterPages() {

		return NOT_FILTER_PAFGES;

	}

	/**
	 * 获取接口服务器的IP地址
	 * 
	 * @return
	 */
	public static String getInterfaceIp() {
		return ParamsConfig.getInstance().getParamValue("IF_RMI_ADD");
		// return "136.5.8.119:8888";
	}

	/**
	 * 调试状态
	 * 
	 * @return
	 */
	public static boolean getDebugState() {
		return true;
	}

	/**
	 * 获取接口服务器的IP地址
	 * 
	 * @return
	 */
	public static String getIIOPInterfaceIp() {
		return ParamsConfig.getInstance().getParamValue("IIOP_RMI_ADD");
		// return "136.5.8.119:8888";
	}

	/**
	 * 获取IOM服务器的IP地址
	 * 
	 */
	public static String getIomIp() {
		return ParamsConfig.getInstance().getParamValue("IOM_TACHE_INFO_IP");
	}

	public ParamsConfig() {
		if (params == null) {
			initParams("");
			DATABASE_TYPE = getParamValue("DATABASE_TYPE");

		}
	}

	/**
	 * 获取实例对象
	 * 
	 * @return
	 */
	public static ParamsConfig getInstance() {
		if (paramsConfig == null) {
			paramsConfig = new ParamsConfig();
		}
		return paramsConfig;
	}

	/**
	 * 初始化参数，在系统初始化时载入。或者定时任务刷新。
	 * 
	 * @param path
	 */
	public static void initParams(String path) {

		// try {
		try {
			params = ConfigFileHelper.getInfParamConfigFile();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// trim()
		Enumeration enu = params.keys();
		String key = "";
		String val = "";
		while (enu.hasMoreElements()) {
			key = (String) enu.nextElement();
			val = params.getProperty(key);
			if (val != null && !"".equals(val)) {
				params.put(key, val.trim());
			}
		}

		// 主动刷新特定的常量。
		if (null != params.getProperty("MAX_UPLOAD_SIZE")) {
			setMaxUploadSize(params.getProperty("MAX_UPLOAD_SIZE"));
		}
		if (null != params.getProperty("NOT_FILTER_PAFGES")) {
			setNotFilterPages(params.getProperty("NOT_FILTER_PAFGES"));
		}
		if (null != params.getProperty("SHOW_SQL")) {
			SHOW_SQL = params.getProperty("SHOW_SQL");
		}
		if (null != params.getProperty("SHOW_METHOD_TIME")) {
			SHOW_METHOD_TIME = params.getProperty("SHOW_METHOD_TIME");
		}
		if (null != params.getProperty("MAX_QUERY_SIZE")) {
			setMaxQuerySize(params.getProperty("MAX_QUERY_SIZE"));
		}
	}

	/**
	 * 获取参数值
	 * 
	 * @param name
	 *            参数名称
	 * @return
	 */
	public String getParamValue(String name) {
		return params.getProperty(name);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParamsConfig.getInstance();
		ParamsConfig.initParams(
				"D:/workspaces/crmworkspace/VsopWeb/VsopWeb/WEB-INF/");

	}

	/*
	 * 更新内存中的系统参数
	 */
	public void updateProperty(String paramCode) throws Exception {
		Properties tempProperty = ConfigFileHelper.getInfParamConfigFile();
		Enumeration enu = tempProperty.keys();
		String key = "";
		String val = "";
		while (enu.hasMoreElements()) {
			key = (String) enu.nextElement();
			val = tempProperty.getProperty(key);
			if (val != null && !"".equals(val)) {
				tempProperty.put(key, val.trim());
			}
		}
		String paramValue = tempProperty.getProperty(paramCode);
		// 如果存在这个参数，才去刷新参数的信息
		if (paramValue != null) {
			params.setProperty(paramCode, paramValue);
		}
	}

}
