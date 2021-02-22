package com.ztesoft.autoprocess.base;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClientPool {
	private static final Log log = LogFactory.getLog(ClientPool.class);
	/** 线程安全的map,用于保存客户端对象 */
	public static ConcurrentHashMap<String, ZBSystemClient> activeClientMap = new ConcurrentHashMap<String, ZBSystemClient>();
	
	/** 线程安全的map,用于未登录客户端对象 */
	public static ConcurrentHashMap<String, ZBSystemClient> unActiveClientMap = new ConcurrentHashMap<String, ZBSystemClient>();

	/**
	 * 获取一个客户端对象
	 * 
	 * @param name
	 */
	public static ZBSystemClient get(String name) {
		return activeClientMap.get(name);
	}

	/**
	 * 往客户端池中添加一个客户端对象
	 * 
	 * @param name
	 * @param value
	 */
	public static void add(String name, ZBSystemClient value) {
		if (activeClientMap.get(name) != null) {
			log.warn("ClientPool中已经存在用户：" + name);
			return;
		}

		activeClientMap.put(name, value);
	}

	/**
	 * 删除客户端池中的一个对象
	 * 
	 * @param name
	 */
	public static void remove(String name) {
		activeClientMap.remove(name);
	}

	/**
	 * 关闭指定的一个客户端对象，释放相关资源
	 * 
	 * @param entry
	 */
	public static void close(Entry<String, ZBSystemClient> entry) {
		log.info("关闭连接" + entry.getKey());
		activeClientMap.remove(entry.getKey());
		ZBSystemClient client=entry.getValue();
		client.shutdown();
		client.isOnline=false;
	}
	public static void close(String username,ZBSystemClient client) {
		log.info("============关闭连接=============");
		activeClientMap.remove(username);
		client.shutdown();
		client.isOnline=false;
	}
}
