package com.powerise.ibss.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.concurrent.ConcurrentHashMap;



/**
 * 缓存管理器
 * EhCacheManager.java Class Name : EhCacheManager.java<br>
 * Description :<br>
 * Copyright 2010 ztesoft<br>
 * Author : zhou.jundi<br>
 * Date : 2012-7-23<br>
 *
 * Last Modified :<br>
 * Modified by :<br>
 * Version : 1.0<br>
 */
public class EhCacheManager {
	
	final static Log log = LogFactory.getLog(EhCacheManager.class);
	
	//默认缓存名称
	public static final String DEFAULT_CACHE = "DEFAULT_CACHE";
	

	public static CacheManager manager;
	
	static{
		try {
//			manager = (CacheManager) SpringContextUtil
//					.getBean("defaultCacheManager");
			if(manager==null)
				manager = CacheManager.create();
		} catch (CacheException e) {
			log.fatal("Initialize cache manager failed.", e);
		}
	}

	/**
	 * 从缓存中获取对象
	 * @param cache_name
	 * @param key
	 * @return
	 */
	public static Serializable getObjectCached(String cache_name, Serializable key){
		Cache cache = getCache(cache_name);
		if(cache!=null){
			try {
				Element elem = cache.get(key);
				if(elem!=null && !cache.isExpired(elem))
					return elem.getValue();
			} catch (Exception e) {
				log.info("Get cache("+cache_name+") of "+key+" failed.", e);
			}
		}
		return null;
	}
	
	/**
	 * 把对象放入缓存中
	 * @param cache_name
	 * @param key
	 * @param value
	 */
	public synchronized static void putObjectCached(String cache_name, Serializable key, Object value){
		Cache cache = getCache(cache_name);
		if(cache!=null){
			try {
				cache.remove(key);
				Element elem = new Element(key, value);
				cache.put(elem);
			} catch (Exception e) {
				log.info("put cache("+cache_name+") of "+key+" failed.", e);
			}
		}
	}
	
	/**
	 * 获取指定名称的缓存
	 * @param arg0
	 * @return
	 * @throws IllegalStateException
	 */
	public static Cache getCache(String arg0) throws IllegalStateException {
		return manager.getCache(arg0);
	}
	
	public static List getAllCache() throws IllegalStateException {
		List caches = new ArrayList();
		String [] cacheNames = manager.getCacheNames();
		for(int i=0;i<cacheNames.length;i++){
			String cacheName = 	cacheNames[i];
			caches.add(getCache(cacheName));
		}
		return caches;
	}
	
	/**
	 * 停止缓存管理器
	 */
	public static  void shutdown(){
		if(manager!=null)
			manager.shutdown();
	}
	
	/**
	 * 清除指定缓存
	 * @param cache
	 * Author by zhou.jundi
	 */
	public static void clear(String cache){
		getCache(cache).removeAll();
	}
	
	/**
	 * 清除所有缓存
	 * @param cache
	 * Author by zhou.jundi
	 */
	public static void clearAll(){
		String [] caches = manager.getCacheNames();
		for(int i=0;i<caches.length;i++){
			getCache(caches[i]).removeAll();
		}
	}
	
	/**
	 * 删除指定标识的缓存
	 * @param cache
	 * @param key
	 * Author by zhou.jundi
	 */
	public synchronized static void removeKey(String cache,String key){
		getCache(cache).remove(key);
	}
	

	/**
	 * 获取缓冲中的信息
	 * @param cache
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 * @throws CacheException
	 */
	public static Element getElement(String cache, Serializable key) throws IllegalStateException, CacheException{
		Cache cCache = getCache(cache);
		return cCache.get(key);
	}

	
	
	/**
	 * 获取默认缓冲中的信息
	 * @param cache
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 * @throws CacheException
	 */
	public static Serializable getValue(Serializable key) throws IllegalStateException, CacheException{
		return getObjectCached(DEFAULT_CACHE, key);
	}
	

	/**
	 * 把对象放入缓存中
	 * @param cache_name
	 * @param key
	 * @param value
	 */
	public static void putObjectCached(Serializable key, Object value){
		putObjectCached(DEFAULT_CACHE, key, value);
	}

	
	/**
	 * 注册一个MAP到缓存
	 * @param cache
	 * @param key
	 * @return
	 * Author by zhou.jundi
	 */
	public static Map registerMap(String cache,String key){
//		ConcurrentHashMap map = new ConcurrentHashMap();
		HashMap map = new HashMap();
		putObjectCached(cache, key, map);
		return map;
	}
	
	/**
	 * 注册一个MAP到默认缓存
	 * @param key
	 * @return
	 * Author by zhou.jundi
	 */
	public static Map registerMap(String key){
		return registerMap(DEFAULT_CACHE, key);
	}
	


}

