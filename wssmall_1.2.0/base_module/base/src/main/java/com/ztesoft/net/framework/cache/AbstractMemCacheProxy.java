package com.ztesoft.net.framework.cache;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;


/**
 * 缓存代理抽象类
 * 改用memcache
 * 
 */
public abstract class AbstractMemCacheProxy<T> {
	protected final Logger logger = Logger.getLogger(getClass());
	private final String CORE_CACHE_TYPE = "";			//配置为空，默认取配置文件中的缓存类型（memcached）
	protected INetCache cache;

	public AbstractMemCacheProxy(String cacheName) {
		cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
	}
	
	
}
