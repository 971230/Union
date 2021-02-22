package com.rop.core.memcached.cache;

import com.ztesoft.net.cache.common.INetCache;

/**
 * Cache 工厂
 *
 * @author ye.yucheng
 */
public class CacheFactory {

    private CacheFactory() {

    }
    public static INetCache getDefaultCache() {
    	return com.ztesoft.net.cache.common.CacheFactory.getCache();
//        if (Boolean.valueOf(Config.get("uba.memcached.enable"))) {
//            Object obj = SpringContextHolder.getBean("xmCache");
//            if (null != obj) {
//                return (INetCache) obj;
//            }
//            return null;
//        }
//        return MockCache.getInstance();
    }
}
