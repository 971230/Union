package com.ztesoft.net.mall.core.model;

public class CacheConfig {
       private String cache_config_id;//主键
       private String cache_name;//缓存名称
       private String cache_type;//缓存类型
       private String pre_cache_key;//缓存key的前缀
       private String bak_cache_key;//缓存key的后缀
       private String cache_desc;//缓存配置描述
       private String namespace;//缓存空间
       private String key_is_null;//key值是否为空
       private String cache_key_name;//Icache里面需要传入的参数值
       private String cache_key_desc;//缓存key值的详情描述
	public String getCache_config_id() {
		return cache_config_id;
	}
	public void setCache_config_id(String cache_config_id) {
		this.cache_config_id = cache_config_id;
	}
	public String getCache_name() {
		return cache_name;
	}
	public void setCache_name(String cache_name) {
		this.cache_name = cache_name;
	}
	public String getCache_type() {
		return cache_type;
	}
	public void setCache_type(String cache_type) {
		this.cache_type = cache_type;
	}
	public String getPre_cache_key() {
		return pre_cache_key;
	}
	public void setPre_cache_key(String pre_cache_key) {
		this.pre_cache_key = pre_cache_key;
	}
	public String getBak_cache_key() {
		return bak_cache_key;
	}
	public void setBak_cache_key(String bak_cache_key) {
		this.bak_cache_key = bak_cache_key;
	}
	public String getCache_desc() {
		return cache_desc;
	}
	public void setCache_desc(String cache_desc) {
		this.cache_desc = cache_desc;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getKey_is_null() {
		return key_is_null;
	}
	public void setKey_is_null(String key_is_null) {
		this.key_is_null = key_is_null;
	}
	public String getCache_key_name() {
		return cache_key_name;
	}
	public void setCache_key_name(String cache_key_name) {
		this.cache_key_name = cache_key_name;
	}
	public String getCache_key_desc() {
		return cache_key_desc;
	}
	public void setCache_key_desc(String cache_key_desc) {
		this.cache_key_desc = cache_key_desc;
	}
	
}
