package com.ztesoft.net.cache.common;

import com.tangosol.io.pof.PortableObject;

public interface ICoherenceCache {

	/**
	 * 根据Key值获取数据
	 * 
	 * @param key
	 *            要获取的数据的key
	 * @return 数据
	 */
	public PortableObject get(String key);

	/**
	 * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
	 * 如果是新增，则有效时间为0，即不失效
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, PortableObject value);

	/**
	 * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
	 * 如果是新增，则有效时间为0，即不失效
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void set(String cacheName, String key, PortableObject value);
	
	/**
	 * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
	 * 如果是新增，则有效时间为0，即不失效
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void set(String cacheName, String key, PortableObject value, long expireTime) ;
	
	/**
	 * 设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
	 * 如果是新增，则有效时间为0，即不失效
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, PortableObject value,long expireTime) ;
	

	/**
	 * 根据key值删除相应的值
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 根据CacheName和key值删除相应的值
	 * 
	 * @param cacheName
	 * @param key
	 */
	public void delete(String cacheName, String key);

	/**
	 * 清除CacheName下所有cache
	 */
	public void clear(String cacheName);

}
