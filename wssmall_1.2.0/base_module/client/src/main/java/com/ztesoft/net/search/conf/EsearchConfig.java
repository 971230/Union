package com.ztesoft.net.search.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class EsearchConfig {
	private static final String config_pro = "EsearchConfig.properties";
	private static Properties properties;
	static{
		properties = getConfigFile();
	}
	
	public EsearchConfig(){
		
	}
	
	public static String get(String key){
		return properties.getProperty(key) ;
	}
	
	private static Properties getConfigFile() {
		return getConfigFileProperties(config_pro);
	}


	private static Properties getConfigFileProperties(String configPro) {
		InputStream is;
		Properties properties = new Properties();
		try {
			String configPath = System.getProperty("CONFIG");
			is = getOutFileInputStream(configPath+configPro);
			if(is == null){
				is = getFileInputStream(configPath+configPro);
			}
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return properties;
	}


	private static InputStream getFileInputStream(String string) {
		return EsearchConfig.class.getClassLoader().getResourceAsStream(string);
	}


	private static InputStream getOutFileInputStream(String string) throws Exception {
		File file = new File(string);
		return new FileInputStream(file);
	}
	
}
