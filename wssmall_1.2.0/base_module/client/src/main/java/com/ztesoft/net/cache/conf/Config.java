package com.ztesoft.net.cache.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheException;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class Config {
	private static Logger logger = Logger.getLogger(Config.class);
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
			String config = System.getProperty("CONFIG");
			is = getOutFileInputStream(config+fileName);
			if(is == null){
				is = getFileInputStream(config+fileName);
			}
			configFile.load(is) ;
			is.close() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CacheException(e);
		}
		
		return configFile ;
	}
	
	/**
	 * 工程下配置文件
	 */
	private static InputStream getFileInputStream(String fileName) throws Exception {
		return Config.class.getClassLoader().getResourceAsStream(fileName) ;
	}
	/**
	 * 独立配置的文件
	 */
	private static InputStream getOutFileInputStream(String fileName) throws Exception {
		File file = new File(fileName);
		return new FileInputStream(file);
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
					FileWriter fw = new FileWriter(CacheValues.CORE_CACHE_DEFAULT_WRITELOG);
					String info = "the application ended! " + (new Date()).toString();
					logger.info(info);
					fw.write(info);
					fw.close();
				} catch (IOException ex) {
					throw new CacheException(ex);
				}
			}
		});
	}
}
