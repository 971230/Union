package com.ztesoft.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import com.ztesoft.ibss.common.util.StrTools;

/**
 * 
 * 配置文件助手类
 * 
 * @author easonwu 2010-01-10
 * 
 */

public class ConfigFileHelper {
	private ConfigFileHelper() {

	}

	private static final String crm_params_properties = "crm_params.properties";

	private static final String log4j_properties = "log4j.properties";

	private static final String Resources_Informix_properties = "Resources.properties";

	private static final String Resources_ORA_properties = "Resources_ORA.properties";

	private static final String smgp_properties = "smgp.properties";

	private static InputStream getFileInputStream(String fileName)
			throws Exception {

		// return ConfigFileHelper.class.getClassLoader().getResourceAsStream(
		// fileName);
		//
		return new FileInputStream(fileName);

	}

	public static Properties getConfigFileProperties(String fileName)
			throws Exception {
		InputStream is = getFileInputStream(fileName);
		Properties configFile = new Properties();
		configFile.load(is);
		is.close();

		// 合并配置到system参数中
		mergeProps(configFile);
		return configFile;
	}

	public static Properties getCrmParamConfigFile() throws Exception {

		return getCrmParamConfigFile(crm_params_properties);
	}

	public static Properties getCrmParamConfigFile(String filePath)
			throws Exception {
		if (StrTools.isEmpty(filePath)) {
			filePath = crm_params_properties;
		}
		return getConfigFileProperties(filePath);
	}

	public static Properties getLog4JConfigFile() throws Exception {
		return getConfigFileProperties(log4j_properties);
	}

	public static Properties getInformixDBConfigFile() throws Exception {
		return getConfigFileProperties(Resources_Informix_properties);
	}

	public static Properties getOracleDBConfigFile() throws Exception {
		return getConfigFileProperties(Resources_ORA_properties);
	}

	public static Properties getSmgpConfigFile() throws Exception {
		return getConfigFileProperties(smgp_properties);
	}

	private static void mergeProps(Properties props) {
		for (Entry<Object, Object> entry : props.entrySet()) {
			System.setProperty((String) entry.getKey(),
					(String) entry.getValue());
		}
	}
}
