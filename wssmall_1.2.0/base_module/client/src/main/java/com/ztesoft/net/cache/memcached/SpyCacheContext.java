package com.ztesoft.net.cache.memcached;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.conf.CacheValues;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class SpyCacheContext {
	private static Logger logger = Logger.getLogger(SpyCacheContext.class);
	private static MemcachedClient client = null;

	private SpyCacheContext() {
		// 注册jvm关闭执行事件
		ShutdownCallback.regist();
	}

	static {
		init();
	}

	/**
	 * 
	 * 初始化Memcached连接
	 * 
	 */
	private static void init() {
		// 链接工厂创建器
		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();
		cfb.setFailureMode(FailureMode.Redistribute);
		cfb.setDaemon(true);
		cfb.setProtocol(Protocol.BINARY);
		cfb.setLocatorType(Locator.CONSISTENT);
		cfb.setOpTimeout(CacheValues.MEMCACHED_TIMEOUT);
		cfb.setUseNagleAlgorithm(false);
		// 新建客户端
		try {
			client = new MemcachedClient(cfb.build(), AddrUtil.getAddresses(CacheValues.MEMCACHED_SERVERS));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) throws InterruptedException,
	// ExecutionException {
	// //链接工厂创建器
	// ConnectionFactoryBuilder cfb=new ConnectionFactoryBuilder();
	// cfb.setFailureMode(FailureMode.Redistribute);
	// cfb.setDaemon(true);
	// cfb.setProtocol(Protocol.BINARY);
	//          
	// cfb.setLocatorType(Locator.CONSISTENT);
	// cfb.setOpTimeout(100000);
	// cfb.setUseNagleAlgorithm(false) ;
	// MemcachedClient client=null;
	// //新建客户端
	// try {
	// client=new
	// MemcachedClient(cfb.build(),AddrUtil.getAddresses("134.129.126.251:11000"));
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// OperationFuture<Boolean> ret =client.set("acc_nbr", 900, "18911111111");
	// if(ret.get()){
	// logger.info("sucess");
	// Object t = client.get("acc_nbr");
	// logger.info(t);
	// }else{
	// logger.info("false");
	// }
	// }
	/**
	 * 获取Memcached客户端
	 * 
	 * @return
	 */
	public static final MemcachedClient getClient() {
		return client;
	}

	/**
	 * 资源关闭
	 * 
	 * @param client
	 */
	public static void closeMemcachedClient(MemcachedClient client) {
		logger.info("===========>shut down ____");
		if (null != client)
			client.shutdown();
	}

	public static final void close() {
		closeMemcachedClient(client);
	}
}
