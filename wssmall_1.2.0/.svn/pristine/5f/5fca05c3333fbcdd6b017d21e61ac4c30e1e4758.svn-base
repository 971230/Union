package com.ztesoft.form.comm;

import com.ztesoft.form.processor.Processor;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author copy wui
 */
public class FaceFactory {
	public static Map actions = new HashMap();
	public static final String DEFAULT_SERVICES = "/com/ztesoft/face/action_face_conf.properties";
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
	public static Processor get(String service){
		return (Processor) actions.get(service.toUpperCase());
	}
	private void registerAll(Map actions) {
		Iterator iter = actions.keySet().iterator();
        while(iter.hasNext()) {
            String name = (String)iter.next();
            String serviceImpl = (String)actions.get(name);
            register(name, serviceImpl);
        }
	}
	private void register(String serviceId, String serviceName) {
		Processor handle =null;
		if(!StringUtils.isEmpty(serviceName)){
			try {
				handle=(Processor) Class.forName(serviceName).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		actions.put(serviceId.toUpperCase(), handle);
	}
}
