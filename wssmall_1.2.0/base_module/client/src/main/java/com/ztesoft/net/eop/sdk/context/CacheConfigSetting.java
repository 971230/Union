package com.ztesoft.net.eop.sdk.context;
import java.io.InputStream;
import java.util.Properties;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;

public class CacheConfigSetting {
	private static final String config_properties="CacheConfig.properties" ;
	private static Properties properties ;
	static{
		try{
			InputStream in  = UploadUtilc.getResourceAsStream(config_properties);
			properties = new Properties();
			properties.load(in);
		}catch(Exception e){	
			e.printStackTrace();
		}
		
	}
	
	
	public static String get(String key){
		return properties.getProperty(key) ;
	}
	
	private CacheConfigSetting(){
		
	}

	
	
}
