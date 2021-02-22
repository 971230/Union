package com.ztesoft.net.cache.common;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.cache.common.zte.FastCacheRequest;
import com.ztesoft.net.cache.conf.CacheValues;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-11-25
 */
public class NetFastCache implements INetCache {
	//快速cache，目前暂定为ecache
	public static INetCache fastCache = CacheFactory.getCacheByType(CacheValues.CACHE_TYPE_EHCACHE);
	
	//默认cache，主要指memcached和tair
	public static INetCache normalCache = CacheFactory.getCache();
	
	//默认cache是否为ecache，如果相同，防止重复取值和设值
	boolean theSame=CacheValues.CORE_CACHE_TYPE.equals(CacheValues.CACHE_TYPE_EHCACHE);
	ThreadPoolExecutor executor = ThreadPoolFactory.getSingleThreadPoolExector();
//	FastCacheRequest request = new FastCacheRequest();
	
	@Override
	public Serializable get(String key) {
		return get(CacheValues.CORE_CACHE_DEFAULT_NAMESPACE,key);
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		Serializable obj = fastCache.get(nameSpace,key);
		Serializable obj1 = normalCache.get(nameSpace,key);
		if(obj==null && !theSame){
			obj = normalCache.get(nameSpace,key);
		}
		return obj;
	}

	@Override
	public void set(String key, Serializable value) {
		this.set(CacheValues.CORE_CACHE_DEFAULT_NAMESPACE,key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
		this.set(CacheValues.CORE_CACHE_DEFAULT_NAMESPACE,key, value,CacheValues.CORE_CACHE_DEFAULT_SESSION_TIMEOUT);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value,
			int expireTime) {
		Object obj2 = fastCache.get(nameSpace, key);
		fastCache.delete(nameSpace, key);
		fastCache.set(nameSpace,key, value,expireTime);
		Object obj = fastCache.get(nameSpace, key);
		if(!theSame){
			FastCacheRequest request = new FastCacheRequest();
			request.setNameSpace(nameSpace);
			request.setValue(value);
			request.setKey(key);
			request.setExpireTime(expireTime);
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(request) {
				@Override
				public ZteResponse execute(ZteRequest request) {
					FastCacheRequest req =(FastCacheRequest)request;
					normalCache.set(req.getNameSpace(),req.getKey(), req.getValue(),req.getExpireTime());
					return new ZteResponse();
				}
			});
			ThreadPoolFactory.submit(taskThreadPool, executor);
		}
		
	}

	@Override
	public void delete(String key) {
		delete(CacheValues.CORE_CACHE_DEFAULT_NAMESPACE,key);
	}

	@Override
	public void delete(int nameSpace, String key) {
		fastCache.delete(nameSpace,key);
		if(!theSame){
			normalCache.delete(nameSpace,key);
		}
		
	}

	@Override
	public void clear() {
		clear(CacheValues.CORE_CACHE_DEFAULT_NAMESPACE);
	}

	@Override
	public void clear(int nameSpace) {
		fastCache.clear(nameSpace);
		if(!theSame){
			normalCache.clear(nameSpace);
		}
	}

	@Override
	public void close() {
		fastCache.close();
		if(!theSame){
			normalCache.close();
		}
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		// TODO Auto-generated method stub
		return false;
	}
}
