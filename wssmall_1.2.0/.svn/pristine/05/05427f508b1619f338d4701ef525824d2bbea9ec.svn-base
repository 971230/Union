package com.ztesoft.net.framework.cache;


/**
 * Cache实现类工厂
 * @author kingapex
 * <p>2009-12-16 下午05:23:28</p>
 * @version 1.0
 */
public final class CacheFactory {
	
	public static final String APP_CACHE_NAME_KEY = "appCache";
	public static final String THEMEURI_CACHE_NAME_KEY = "themeUriCache";
	public static final String SITE_CACHE_NAME_KEY = "siteCache";
	public static final String WIDGET_CACHE_NAME_KEY ="widgetCache";
	public static final String CONFIG_INFO_CACHE_NAME_KEY = "configInfoCache";
	public static final String GOODS_INFO_CACHE_NAME_KEY = "goodsInfoCache";
	public static final String GOODS_TYPE_CACHE_NAME_KEY = "goodsTypeCache";
	public static final String DC_PUBLIC_CACHE_NAME_KEY = "dcPublicCache";
	public static final String SINGLE_LOGIN_CACHE_NAME_KEY = "sinLoginInfoCache";
	public static final String DICT_MANAGER_CACHE_NAME_KEY = "dictManagerCache";
	@SuppressWarnings("unchecked")
	public static <T> ICache<T> getCache(String name){
		ICache<T> ehCache = new EhCacheImpl(name);
		return ehCache;
	}
	
}
