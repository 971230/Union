package com.ztesoft.net.cache.zredis;
import java.io.Serializable;

import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.net.cache.common.CacheKeyUtil;
import com.ztesoft.net.cache.common.INetCache;
/**
 * @Description
 * @author  zhangJun
 * @date    2016年1月20日
 * @version 1.0
 */
public class ZredisCache implements INetCache {
	
	private static final ZredisCache inst = new ZredisCache() ;
	
	public static INetCache getCache(){
		return inst ;
	}
	private ZredisCache() {
	}
	@Override
	public Serializable get(String key) {
		Serializable obj =  this.get(defaltNameSpace,key);
		return obj;
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		try{
			Serializable obj = ZredisClient.getInstance().r_get(CacheKeyUtil.getKey(nameSpace, key));
			return obj;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	
	@Override
	public void set(String key, Serializable value) {
		this.set(defaltNameSpace,key,value);
	}
	
	@Override
	public void set(int nameSpace, String key, Serializable value) {
		this.set(nameSpace, key, value, -1);
	}

	
	@Override
	public void set(int nameSpace, String key, Serializable value,
			int expireTime) {
		try {
			ZredisClient.getInstance().r_set(CacheKeyUtil.getKey(nameSpace, key), value, expireTime*1000);//redis原有的时间单位是ms
		}catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	
	@Override
	public void delete(String key) {
		ZredisClient.getInstance().r_delete(key);
	}

	
	@Override
	public void delete(int nameSpace, String key) {
		try {
			ZredisClient.getInstance().r_delete(CacheKeyUtil.getKey(nameSpace, key));
		} catch (Exception e) {
			throw new CacheException(e);
		}
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
		throw new CacheException("method not implemented!");
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		// TODO Auto-generated method stub
		try {
			ZredisClient.getInstance().r_set(CacheKeyUtil.getKey(nameSpace, key), value, expireTime*1000);//redis原有的时间单位是ms
		}catch (Exception e) {
			throw new CacheException(e);
		}
		return true;
	}

}
