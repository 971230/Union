package com.ztesoft.net.cache.memcached;

import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheException;
import com.ztesoft.net.cache.common.CacheKeyUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class DyXmCache implements INetCache {
	private static Logger logger = Logger.getLogger(DyXmCache.class);
	
	private DyXmCache(){
	}

	private static final DyXmCache inst = new DyXmCache() ;
	
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
		DyXmCacheContext.close();
	}

	@Override
	public void delete(String key) {
		delete(defaltNameSpace,key);
	}

	@Override
	public void delete(int nameSpace, String key) {
		try {
			DyXmCacheContext.getClient().delete(CacheKeyUtil.getKey(nameSpace, key));
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
//			if(true)
//				throw new Exception("内存数据获取溢出");
			return DyXmCacheContext.getClient().get(CacheKeyUtil.getKey(nameSpace, key));
		} catch (Exception e) {
			e.printStackTrace();
			try{
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				support.execute("insert into es_cache_exception (id,create_time,source_from) values(?,sysdate,?)", System.currentTimeMillis(),ManagerUtils.getSourceFrom());
			}catch(Exception e1){
				
			}
//			support.update(req.getInsertSql(), req.getParams(),req.getKeyMap());
//			throw new CacheException(e);
			return null;
		}
	}

	@Override
	public void set(String key, Serializable value) {
		set(defaltNameSpace,key, value);
	}

	@Override
	public void set(int nameSpace, String key, Serializable value) {
		try {
//			if(true)
//				throw new Exception("内存数据设置溢出");
			DyXmCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key), 0, value);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new CacheException(e);
		}
	}

	@Override
	public void set(int nameSpace, String key, Serializable value,int expireTime) {
		try {
			DyXmCacheContext.getClient().set(CacheKeyUtil.getKey(nameSpace, key),expireTime, value);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new CacheException(e);
		}
	}
	
	@Override
	public boolean add(int nameSpace, String key, Serializable value,
			int expireTime) {
		boolean flag = false;
		try {
			flag = DyXmCacheContext.getClient().add(CacheKeyUtil.getKey(nameSpace, key),expireTime, value);
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
