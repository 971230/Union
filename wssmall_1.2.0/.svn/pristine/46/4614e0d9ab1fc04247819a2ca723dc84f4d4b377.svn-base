package com.ztesoft.net.mall.core.cache.common;

import java.io.Serializable;

import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public abstract class SysNetCache {
	
	private final String CORE_CACHE_TYPE = "";
	
	//包含cachefactory
	private INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
	
	//100以下是保留，最大值不能超过10000
	private final int NAMESPACE = 100;
	
	
	/**
	 * @title 发布组织缓存
	 * @desc 
	 */
	protected final String CACHE_GOODS_ORG = "CACHE_GOODS_ORG";
	
	/**
	 * 永久保留缓存
	 * @param key
	 * @param value
	 */
	public void set(String key, Serializable value){
		cache.set(NAMESPACE, key+ManagerUtils.getSourceFrom(), value);
	}
	
	/**
	 * 缓存设定有效时间time，没有设置时间，将永久保留缓存
	 * @param key
	 * @param value
	 * @param time 单位秒
	 */
	public void set(String key, Serializable value, int time){
		String source_from =ManagerUtils.getSourceFrom();
		cache.set(NAMESPACE, key+source_from, value, time);
	}
	
	public Object get(String key){
		String source_from =ManagerUtils.getSourceFrom();
        Object obj=cache.get(NAMESPACE, key+source_from);
        if(obj ==null)
        	obj=cache.get(NAMESPACE, key);
        if(obj ==null)
        {
        	//根据配置获取
        	ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
    		if(!StringUtil.isEmpty(planDBSourceFroms)){
	    		for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
	    			   obj=cache.get(NAMESPACE, key+plan_dbsourcefrom);
	    			   if(obj != null)
	    				 break;
	    		}
    		}
        }
		return obj;
	}
	
}
