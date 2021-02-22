package com.rop.core.memcached.config;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;


public class Config {
	private static final String config_properties="CacheConfig.properties" ;
	private static Properties properties ;
	static{
		properties = getConfigFile()  ;
	}
	
	
	public static String get(String key){
		return properties.getProperty(key) ;
	}
	
	private Config(){
		
	}
	
	public  static Properties getConfigFileProperties(String fileName) {
		InputStream is;
		Properties configFile  = new Properties() ;
		try {
			is = getFileInputStream( fileName);
			configFile.load(is) ;
			is.close() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConfigException(e) ;
		}
		
		return configFile ;
	}
	

	private static InputStream getFileInputStream(String fileName) throws Exception {
		return Config.class.getClassLoader().getResourceAsStream(fileName) ;
	}
	
	public  static Properties getConfigFile()  {
		return getConfigFileProperties(config_properties) ;
	}
	
	public static void main(String[] args){
		Properties p = getConfigFile() ;
		int i = 0 ; 
	}
	

	public static void writeBackWhileAPPExit() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					FileWriter fw = new FileWriter("d:\\t.log");
					//logger.info("Im going to end");
					fw.write("the application ended! "
							+ (new Date()).toString());
					fw.close();
				} catch (IOException ex) {

				}
			}
		});
	}
}
