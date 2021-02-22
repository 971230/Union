package com.ztesoft.net.cache.zredis;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.zredis.ZredisCache;
/**
 * @Description
 * @author  zhangJun
 * @date    2016年1月20日
 * @version 1.0
 */
public class RedisTest implements Serializable{
	private static Logger logger = Logger.getLogger(Serializable.class);
	private static final long serialVersionUID = 1L;
	private String name="张三";
	private int age=1;
	private INetCache cache =null;
	
	public static void main(String[] args) {
		INetCache cache =ZredisCache.getCache();
		RedisTest rt=new RedisTest();
		rt.setCache(cache);
		rt.setStr();
		rt.setObj();
	}

	
	public void setStr() {
		String key="setStr";
		String vlaue="XXXXXXXX1";
		cache.set(key, vlaue);
		logger.info("key:"+key+"value:"+cache.get(key));
	}
	public void setObj() {
		String key="setObj";
		
		RedisTest vlaue=new RedisTest();
		vlaue.setAge(3);
		vlaue.setName("李四");
		cache.set(key, vlaue);
		
		RedisTest myObj=(RedisTest) cache.get(key);
		logger.info("key:"+key+"value:"+myObj.getAge());
		logger.info("key:"+key+"value:"+myObj.getName());
	}
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public INetCache getCache() {
		return cache;
	}


	public void setCache(INetCache cache) {
		this.cache = cache;
	}


	
	
	
	
	
	
	
   
	
	
}
