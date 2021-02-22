package com.ztesoft.rule.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCache {
	//用于缓存系统参数
	public static Map<String, Object> LocalCache = new ConcurrentHashMap<String, Object>();
	
	public static void put(String key , Object value){
		LocalCache.put(key, value) ;
	}
	
	public static final Object get(String key){
		return LocalCache.get(key) ;
	}
	
	
}
