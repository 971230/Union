package com.ztesoft.common.util;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;



public class SysIbssUtils {
	private static Logger log=Logger.getLogger(SysIbssUtils.class);
	
	public static class KeyWord{
		public static final String ADMIN_IP="ADMIN_IP";
	}
	
	
	/*
	 * 获取ibss.xml中配置的ip
	 * */
	public static String getAdminIP() {
		try {
		  return SysSet.getSystemSet(SysIbssUtils.KeyWord.ADMIN_IP);
		} catch (FrameException e) {
			log.debug("获取ibss.xml中配置的ip信息出错：======》"+e.getMessage());
		}
		return "";
	}
	

}
