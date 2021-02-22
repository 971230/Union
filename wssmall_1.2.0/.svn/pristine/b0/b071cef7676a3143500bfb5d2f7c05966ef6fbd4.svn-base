package com.ztesoft.face.comm;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author Reason.Yea
 * @version Created Feb 5, 2013
 */
public class FaceFactory {
	public static Map services = new HashMap();
	public static final String DEFAULT_SERVICES = "/com/ztesoft/face/serv_face_conf.properties";
	static {
		new FaceFactory().loadProperties();
	}
	//不允许实例化
	private FaceFactory(){};
	public Logger logger = Logger.getLogger(FaceFactory.class);
	
	private void loadProperties() {
		InputStream is = this.getClass().getResourceAsStream(DEFAULT_SERVICES);
		if(is != null) {
			logger.info("Found properties config, load service from properties: " + DEFAULT_SERVICES);
			Properties defaultProperties = new Properties();
			try {
				defaultProperties.load(is);
				registerAll(defaultProperties);
			} catch (IOException e) {
				System.err.println("Error when reading " + DEFAULT_SERVICES + ", ommited");
				e.printStackTrace();
			}
			logger.info("Load service from properties finished.");
		}
		
	}
	public static IServHandle get(String service){
		return (IServHandle) services.get(service.toUpperCase());
	}
	private void registerAll(Map services) {
		Iterator iter = services.keySet().iterator();
        while(iter.hasNext()) {
            String name = (String)iter.next();
            String serviceImpl = (String)services.get(name);
            register(name, serviceImpl);
        }
	}
	private void register(String serviceId, String serviceName) {
		IServHandle handle =null;
		if(!StringUtils.isEmpty(serviceName)){
			try {
				handle=(IServHandle) Class.forName(serviceName).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		services.put(serviceId.toUpperCase(), handle);
	}
}
