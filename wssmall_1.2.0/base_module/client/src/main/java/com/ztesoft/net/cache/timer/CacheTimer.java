package com.ztesoft.net.cache.timer;

import java.util.Timer;

import com.ztesoft.net.cache.common.INetCache;

/**
 * 定时任务缓存工具类
 * @author Reason.Yea 
 * @version 创建时间：Jan 22, 2014
 */
public class CacheTimer {
	
	private CacheTimer(){}
	/**
	 * @see cache
	 */
	public static void cache(String key,String sql, int expireTime){
		cache(INetCache.defaltNameSpace,key,sql,expireTime);
	}
	
	/**
	 * 根据SQL语句进行缓存，并自动动态刷新缓存数据
	 * @param nameSpace 命名空间
	 * @param key 缓存的key
	 * @param sql 待执行的查询sql语句，返回对象格式：List<Map<String,Object>>
	 * @param expireTime 超时时间为秒
	 */
	public static void cache(int nameSpace,String key,String sql, int expireTime){
		Timer timer = new Timer();
		//生成缓存定时任务
		CacheTask task = new CacheTask(nameSpace,key,sql,expireTime);
		//延后1毫秒开始定时执行
		timer.schedule(task, 1, expireTime*1000);
	}
}
