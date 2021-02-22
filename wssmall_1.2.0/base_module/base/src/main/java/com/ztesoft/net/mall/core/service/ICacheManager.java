package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.CacheConfig;

public interface ICacheManager {
        public String getCacheByKey(String key,String key_config_id,String cache_type);
        
        public List getCacheAddressListByCacheType(String cache_type);
        
        public CacheConfig getCacheConfigById(String cache_config_id);
        
}
