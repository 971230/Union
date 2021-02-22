package com.rop.core.memcached.cache;

import java.io.Serializable;


public interface INetCache {
	
	/**
	 * 异步删除
	 * @param key
	 */
	public void deleteWithNoReply(String key) ;
	
	/**
	 * 同步删除
	 * @param key
	 */
	public void delete(String key) ;

	
	/**
	 * @param key
	 * @param cacheTime
	 * @param value
	 */
	public void setBean(String key, int cacheTime, IBean value) ;
	
	public IBean getBean(String key) ;
	
	/**
	 * @param key
	 * @param cacheTime
	 * @param value
	 */
	public void setObj(String key, int cacheTime, Serializable value) ;

    public void update(String key,int time,Serializable value);
	
	public Serializable getObj(String key);
	
	public void flush();
}
