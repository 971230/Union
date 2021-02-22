package com.ztesoft.util;

import java.io.Serializable;

import com.ztesoft.cache.CacheFlag;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.conf.CacheValues;

public class LocalCacheUtils {
	private static final String CORE_CACHE_TYPE = CacheValues.CACHE_TYPE_EHCACHE;
	//表主银
	public static final String TABLE_PK_CONFIG = "TABLE_PK_CONFIG";
	public static final String TABLE_SEQ_CONDIG = "TABLE_SEQ_CONDIG";
	
	public static final String HAS_CACHE_FLAG = "HAS_CACHE_FLAG_OBJ";
	
	/**
	 * 获取是否缓存
	 * @作者 MoChunrun
	 * @创建日期 2014-12-19 
	 * @return
	 */
	@Deprecated
	public static CacheFlag getHashCache(){
		CacheFlag c = getCache(HAS_CACHE_FLAG);
		return c;
	}
	
	public static void addCache(String key,Serializable serial){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		cache.set(Const.CACHE_SPACE, key, serial);
	}
	
	public static void addCache(String key,Serializable serial,int expireTime){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		cache.set(Const.CACHE_SPACE, key, serial,expireTime);
	}
	
	public static void deleteCache(String key){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		cache.delete(Const.CACHE_SPACE,key);
	}
	
	public static void deleteCache(int namespace, String key){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		cache.delete(namespace,key);
	}
	
	public static <T> T getCache(String key){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		return (T) cache.get(Const.CACHE_SPACE, key);
	}
	
	public static <T> T getCache(int namespace,String key){
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		return (T) cache.get(namespace, key);
	}
}