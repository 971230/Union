package com.ztesoft.common.util;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ztesoft.ibss.common.util.StrTools;

public class CrmParamsConfig {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CrmParamsConfig.class);
	private Properties params = new Properties();
	private static CrmParamsConfig paramsConfig = null;

	public CrmParamsConfig() {
	}

	/**
	 * 获取实例对象
	 * 
	 * @return
	 */
	public static CrmParamsConfig getInstance() {
		if (paramsConfig == null) {
			paramsConfig = new CrmParamsConfig();
		}
		return paramsConfig;
	}

	/**
	 * 初始化参数，在系统初始化时载入。或者定时任务刷新。
	 * 
	 * @param path
	 */
	public void initParams(String path) {

		try {
			if (StrTools.isEmpty(path)) {
				// 配置参数载入
				path = System.getProperty("CONFIG");
				if (!path.endsWith("/")) {
					path += "/";
				}
				path += "workflow.properties";
			}
			params = ConfigFileHelper.getCrmParamConfigFile(path);
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
			CrmConstants
					.setMaxUploadSize(params.getProperty("MAX_UPLOAD_SIZE"));
		}
		if (null != params.getProperty("NOT_FILTER_PAFGES")) {
			CrmConstants.setNotFilterPages(params
					.getProperty("NOT_FILTER_PAFGES"));
		}
		if (null != params.getProperty("SHOW_SQL")) {
			CrmConstants.SHOW_SQL = params.getProperty("SHOW_SQL");
		}
		if (null != params.getProperty("SHOW_METHOD_TIME")) {
			CrmConstants.SHOW_METHOD_TIME = params
					.getProperty("SHOW_METHOD_TIME");
		}
		if (null != params.getProperty("MAX_QUERY_SIZE")) {
			CrmConstants.setMaxQuerySize(params.getProperty("MAX_QUERY_SIZE"));
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
		CrmParamsConfig.getInstance().initParams(
				"D:/workspaces/crmworkspace/CrmWeb/CrmWeb/WEB-INF/");

	}

	/*
	 * 更新内存中的系统参数
	 */
	public void updateProperty(String paramCode) throws Exception {
		Properties tempProperty = ConfigFileHelper.getCrmParamConfigFile();
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