package com.ztesoft.net.eop.sdk.context;

import java.io.InputStream;
import java.util.Properties;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
/**
 * @Description
 * @author  zhangJun
 * @date    2015年7月8日
 * @version 1.0
 */
public class MqEnvGroupConfigSetting {
	
	public static String MQ_ENV_GROUP = "";
	public static String ORD_STD_EXEC = "";
	public static String ORD_STANDING_EXEC = "";
	public static String ORD_EXP_EXEC = "";
	
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
		MQ_ENV_GROUP =  props.getProperty("mq.env_group");//无配置不设置默认值
		ORD_STD_EXEC = props.getProperty("ord.std.exec");
		ORD_STANDING_EXEC = props.getProperty("ord.standing.exec");
		ORD_EXP_EXEC = props.getProperty("ord.exp.exec");
	}
}
