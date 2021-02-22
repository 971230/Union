package com.ztesoft.net.eop.sdk.context;

import java.io.InputStream;
import java.util.Properties;

import com.ztesoft.net.eop.sdk.utils.UploadUtilc;

public class AppInfoSetting {
	public static Properties props = new Properties();
	/*
	 * 从配置文件中读取相关配置<br/>
	 * 如果没有相关配置则使用默认
	 */
	static {
		try{
			InputStream in  = UploadUtilc.getResourceAsStream("app.properties");
			props.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
