package util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigUtil {
	private static Logger logger = Logger.getLogger(ConfigUtil.class);
	private Properties properties;
	
	public ConfigUtil(String path){
		URL url = ConfigUtil.class.getResource(path);
		properties = new Properties();
		try {
			properties.load(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public String getString(String key){
		return properties.getProperty(key);
	}
	

	public Integer getInt(String key){
		return Integer.valueOf(getString(key));
	}
	
	public Double getDouble(String key){
		return Double.valueOf(getString(key));
	}
	
	public Float getFloat(String key){
		return Float.valueOf(getString(key));
	}
	
	public boolean getBoolean(String key){
		return Boolean.valueOf(getString(key));
	}
	
	public static void main(String[] args) {
		ConfigUtil cu = new ConfigUtil("/resource/order.properties");
		logger.info(cu.getString("order.right.url"));
	}
	
}
