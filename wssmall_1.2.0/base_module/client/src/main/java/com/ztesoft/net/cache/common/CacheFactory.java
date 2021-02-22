package com.ztesoft.net.cache.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.conf.CacheValues;
import com.ztesoft.net.cache.ehcache.EhCacheImpl;
import com.ztesoft.net.cache.memcached.DyXmCache;
import com.ztesoft.net.cache.memcached.SpyCache;
import com.ztesoft.net.cache.memcached.XmCache;
import com.ztesoft.net.cache.redis.RedisCache;
import com.ztesoft.net.cache.tair.TairCache;
import com.ztesoft.net.cache.zredis.ZredisCache;


/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public final class CacheFactory {
	private static Logger logger = Logger.getLogger(CacheFactory.class);
	public static INetCache getCache(){
		return getCacheByType(CacheValues.CORE_CACHE_TYPE);
	}
	
	public static INetCache getCacheByType(String cacheType){
		//未配置cache类型，则默认使用配置级别
		if(cacheType==null || cacheType.equals("")){
			cacheType=CacheValues.CORE_CACHE_TYPE;
		}
		INetCache cache = null;
		if(cacheType.equals(CacheValues.CACHE_TYPE_MEMCACHED)){//memcached
			//支持2种cached驱动
			if(CacheValues.MEMCACHED_SPY.equals( CacheValues.MEMCACHED_CONTEXT)){
				cache= SpyCache.getCache();
			}else{
				cache= XmCache.getCache();
			}
		}else if(cacheType.equals(CacheValues.CACHE_TYPE_EHCACHE)){//ehcache
			cache = EhCacheImpl.getCache();
		}else if(cacheType.equals(CacheValues.CACHE_TYPE_TAIR)){//tair
			cache = TairCache.getCache();
		}else if(cacheType.equals(CacheValues.CACHE_TYPE_REDIS)){//redis
//			cache =RedisCache.getCache();//此处不能用单例模式
			 cache = new RedisCache();
		}else if(cacheType.equals(CacheValues.CACHE_TYPE_DY_MEMCACHED)){//redis
			cache =DyXmCache.getCache();
		}else if(cacheType.equals(CacheValues.CACHE_TYPE_ZREDIS)){//zredis
			cache =ZredisCache.getCache();
		}
		return cache;
	}
	
	public static NetFastCache getFastCache(){
		NetFastCache fastCache = new NetFastCache();;
		return fastCache;
	}
	
	public static RedisCache getRedisCache(){
		return RedisCache.getCache();
	}
	
	public static void main(String[] args) throws InterruptedException {
		RedisCache cache = getRedisCache();
		String key = "Hello";
		HashMap val = new HashMap();
//		val.put("ORDER_ATTR_PRIM_KEYS_FIXZBWO201412101429388710", "2eeb40e4ebc846e7a33a69fd7b8e869e");
//		val.put("bb", "23333");
//		String val = "world";
		cache.set("ORDER_ATTR_PRIM_KEYS_FIXZBWO201412101429388710", "2eeb40e4ebc846e7a33a69fd7b8e869e");
		logger.info(cache.get(0,"ORDER_ATTR_PRIM_KEYS_FIXZBWO201412101429388710"));
//		
//		Object m = cache.getMapFieldVal(0, key, "aa");
//		logger.info(m);
//		
//		cache.setMapFieldVal(0, key, "aa", "0000000", 10);
//		cache.setMapFieldVal(0, key, "bb", "11111111", 10);
//		Object f = cache.getMapFieldVal(0, key, "aa");
//		logger.info(f);
//		
//		Object b = cache.get(0, key);
//		logger.info(b);
		
		
//		logger.info(":::"+cache.get("look"));
//		HashMap map =new HashMap();
//		map.put("aaa","111");
//		map.put("xx",(new ArrayList()).add("xxxxxxxxxxx"));
//		
//		cache.set("look", map);
//		
//		HashMap lookMap = (HashMap) cache.get("look");
//		logger.info(":::"+lookMap);
//		
//		
//		map.put("aaa", "333");
//		
//		cache.set("look", map);
//		lookMap = (HashMap) cache.get("look");
//		logger.info(":::"+lookMap);
		
//		INetCache cache = getCache();
//		INetCache cache = getCacheByType(CacheValues.CACHE_TYPE_EHCACHE);
		
//		long t1 = System.currentTimeMillis();
//		HashMap map = getObj();
//		int t = 1000;
//		for (int i = 0; i < t; i++) {
//			cache.set(i+"_time", map);
//		}
//		long t2 = System.currentTimeMillis();
//		for (int i = 0; i < t; i++) {
//			HashMap cur=(HashMap) cache.get(i+"_time");
//			String c = (String) cur.get("aaa");
//			logger.info("::::::::::::::::::::::::::::::::::::::::::::::::::::::>"+c);
//		}
//		long t3 = System.currentTimeMillis();
//		
//		logger.info("===========set:::::"+(t2-t1));
//		logger.info("===========get:::::"+(t3-t2));
	}
	
	public static HashMap getObj(){
		HashMap map = new HashMap();
		map.put("aaa","111");
		map.put("xx",(new ArrayList()).add("xxxxxxxxxxx"));
		return map;
	}
}
