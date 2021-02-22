/**
 * 
 */
package com.ztesoft.common.query.util;

import java.util.HashMap;

import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

/**
 * @author 许锐豪
 * 
 */
public class DcSystemParamUtil {
	// 用于缓存系统参数
	public static HashMap sysParamsCache = new HashMap();
	private static ICommClientBO commClientBO;
	private static ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO =  SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO ;
	}

	/**
	 * 根据系统参数编码获取系统参数 该方法只能在BO里面调用 不经过框架
	 * 
	 * @param param_code
	 * @return
	 */
	public static String getSysParamByCache(String paramCode) {
		if (sysParamsCache.get(paramCode) != null) {
			return sysParamsCache.get(paramCode).toString();
		} else {// 如果没有，则到数据库里面查询
			String paramVal = getParamValFromDb(paramCode);
			setVariable(paramCode, paramVal);
			return paramVal;
		}
	}

	/**
	 * 根据参数code查询数据库
	 * 
	 * @param paramCode
	 * @return
	 */
	public static String getParamValFromDb(String paramCode) {
		String sql = "select param_val from dc_system_param where param_code ='"
				+ paramCode + "'";
		String paramVal = "";
		try {
			paramVal =getICommClientBO().queryForString(sql);
			setVariable(paramCode,paramVal);
		} catch (Exception e) {

		}
		return paramVal;
	}

	/**
	 * 设置缓存数据。
	 * 
	 * @param key
	 * @param value
	 */
	private static void setVariable(String key, String value) {
		sysParamsCache.put(key, value);
	}
}
