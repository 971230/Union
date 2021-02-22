package com.ztesoft.net.eop.sdk.context;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
/**
 * @Description
 * @author  zhangJun
 * @date    2015年7月8日
 * @version 1.0
 */
public class MqConfigSetting {
	private static Logger logger = Logger.getLogger(MqConfigSetting.class);
	public static String MQ_TOPIC_COMMON =""; //MQ主题
	/*
	 * 从配置文件中读取相关配置
	 */
	static {
		try{
			InputStream in  = UploadUtilc.getResourceAsStream("mq.properties");
			Properties props = new Properties();
			props.load(in);
			init(props);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void init(Properties props ){
		MQ_TOPIC_COMMON = props.getProperty("mq.topic.common");//无配置不设置默认值
		logger.info(MQ_TOPIC_COMMON);
	}
}
