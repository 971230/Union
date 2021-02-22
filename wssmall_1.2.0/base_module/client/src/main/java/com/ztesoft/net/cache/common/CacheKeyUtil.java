package com.ztesoft.net.cache.common;
/**
 * @author Reason.Yea 
 * @version 创建时间：2014-2-12
 */
public class CacheKeyUtil {

	public static String getKey(int nameSpace, String key) {
		key =nameSpace+"_"+key;
		return key;
	}
}
