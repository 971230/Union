package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.framework.cache.ICache;
import com.ztesoft.net.mall.core.model.CacheConfig;
import com.ztesoft.net.mall.core.service.ICacheManager;

public class CacheManagerImpl extends BaseSupport implements ICacheManager {
	
	public String getCacheByType(String cache_type, String key, String nameSpace,String cache_key_name) {
		String returnStr = "";
		INetCache cache  = com.ztesoft.net.cache.common.CacheFactory.getCacheByType(cache_type);
		
		
		if(cache==null){
			 returnStr = "没有该缓存类型";
			 return returnStr;
		}
		Object obj = new Object();
		if(StringUtil.isEmpty(nameSpace)){
			obj = cache.get(key);
		}else{
			obj = cache.get(Integer.parseInt(nameSpace), key);
		}
		if(obj==null&&!StringUtil.isEmpty(cache_key_name)){
			ICache<Object> cache1 = CacheFactory.getCache(cache_key_name);
			obj = cache1.get(key);
		}
		returnStr = JsonUtil.toJson(obj);
		return returnStr;
	}
    
	public  CacheConfig getConfig(String id){
		CacheConfig cacheConfig = new CacheConfig();
		String sql = "select * from es_cache_config where cache_config_id = ? "; 
		cacheConfig = (CacheConfig) this.baseDaoSupport.queryForObject(sql, CacheConfig.class, id);
		return cacheConfig;
	}
	

	@Override
	public String getCacheByKey(String key, String key_config_id,String cache_type) {
		CacheConfig cacheConfig = this.getConfig(key_config_id);
		String returnStr = "";
		if(cacheConfig!=null){
			String nameSpace = cacheConfig.getNamespace();
			String preCacheKey = cacheConfig.getPre_cache_key();
			String bakCacheKey = cacheConfig.getBak_cache_key();
			String cache_key = key;
			if(!StringUtil.isEmpty(preCacheKey)){
				cache_key = preCacheKey + key;
			}
			if(!StringUtil.isEmpty(bakCacheKey)){
				cache_key = cache_key + bakCacheKey;
			}
			returnStr = this.getCacheByType(cache_type, cache_key, nameSpace,cacheConfig.getCache_key_name());
			
		}else{
			returnStr = "未找到该缓存配置";
		}
		return returnStr;
	}

	@Override
	public List getCacheAddressListByCacheType(String cache_type) {
		String sql ="select * from es_cache_address_config where cache_type =? ";
		List list = this.baseDaoSupport.queryForList(sql, cache_type);
		return list;
	}

	@Override
	public CacheConfig getCacheConfigById(String cache_config_id) {
		String sql = "select * from es_cache_config where cache_config_id = '"+cache_config_id+"' ";
		CacheConfig cacheConfig = new CacheConfig();
		cacheConfig = (CacheConfig) this.baseDaoSupport.queryForObject(sql, CacheConfig.class);
		return cacheConfig;
	}
	
}
