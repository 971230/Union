package com.ztesoft.net.cache.memcached;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import net.spy.memcached.internal.OperationFuture;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.net.cache.common.CacheKeyUtil;
import com.ztesoft.net.cache.common.INetCache;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class SpyCache implements INetCache{
	private static Logger logger = Logger.getLogger(SpyCache.class);
	private SpyCache(){
	}
	
	private static final SpyCache inst = new SpyCache() ;
	
	public static INetCache getCache(){
		return inst ;
	}

	@Override
	public void clear() {
		throw new CacheException("method not implemented!");
	}

	@Override
	public void clear(int nameSpace) {
		throw new CacheException("method not implemented!");
	}

	@Override
	public void close() {
		SpyCacheContext.close();
	}

	@Override
	public void delete(String key) {
		delete(defaltNameSpace,key);
	}

	@Override
	public void delete(int nameSpace, String key) {
		SpyCacheContext.getClient().delete(CacheKeyUtil.getKey(nameSpace, key));	
	}

	@Override
	public Serializable get(String key) {
		return get(defaltNameSpace,key);
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		return (Serializable) SpyCacheContext.getClient().get(CacheKeyUtil.getKey(nameSpace, key));
	}

	@Override
	public void set(String key, Serializable value) {
		set(defaltNameSpace,key,value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
		SpyCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key), 0, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value, int expireTime) {
		SpyCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key), expireTime, value);
		
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value, int expireTime) {
		boolean flag = false;
		try {
			OperationFuture<Boolean> op = SpyCacheContext.getClient().add(CacheKeyUtil.getKey(nameSpace, key),expireTime, value);
			Boolean bl = op.get();
			flag = bl.booleanValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
