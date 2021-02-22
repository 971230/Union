package com.ztesoft.net.cache.memcached;

import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.net.cache.common.CacheKeyUtil;
import com.ztesoft.net.cache.common.INetCache;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class XmCache implements INetCache {
	private static Logger logger = Logger.getLogger(XmCache.class);
	
	private XmCache(){
	}

	private static final XmCache inst = new XmCache() ;
	
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
		XmCacheContext.close();
	}

	@Override
	public void delete(String key) {
		delete(defaltNameSpace,key);
	}

	@Override
	public void delete(int nameSpace, String key) {
		try {
			XmCacheContext.getClient().delete(CacheKeyUtil.getKey(nameSpace, key));
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}


	@Override
	public Serializable get(String key) {
		return get(defaltNameSpace,key);
	}

	@Override
	public Serializable get(int nameSpace, String key) {
		try {
			Serializable obj =  XmCacheContext.getClient().get(CacheKeyUtil.getKey(nameSpace, key));
			return obj;
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void set(String key, Serializable value) {
		set(defaltNameSpace,key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
		try {
			XmCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key), 0, value);
			
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	@Override
	public void set(int nameSpace, String key, Serializable value,int expireTime) {
		try {
			XmCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key),expireTime, value);
			
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		boolean flag = false;
		try {
			flag = XmCacheContext.getClient().add(CacheKeyUtil.getKey(nameSpace, key),expireTime, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return flag;
	}
}
