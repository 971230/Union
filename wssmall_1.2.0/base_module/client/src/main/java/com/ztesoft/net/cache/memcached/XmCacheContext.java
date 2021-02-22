package com.ztesoft.net.cache.memcached;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.google.code.yanf4j.core.impl.StandardSocketOption;
import com.ztesoft.net.cache.conf.CacheValues;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class XmCacheContext {
	
	private static MemcachedClient client = null ;
	
	public XmCacheContext(){
    	
	}
	
	static{
		init() ;
		//注册jvm关闭执行事件
		ShutdownCallback.regist() ;
	}
	
	/**
	 * 
	 * 初始化Memcached连接
	 * 
	 */
	public static void init(){
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil
				.getAddresses(CacheValues.MEMCACHED_SERVERS));
		builder.setSessionLocator(new KetamaMemcachedSessionLocator());
		//连接池
		builder.setConnectionPoolSize(CacheValues.MEMCACHED_POOLSIZE);  
		//设置失败模式 如果服务出现down的情况，会报出异常，直到服务正常
		builder.setFailureMode(true);
		//二进制文件  >> 文本性能更好
		builder.setCommandFactory(new BinaryCommandFactory());

		builder.setSocketOption(StandardSocketOption.SO_RCVBUF, 100 * 1024); // 设置接收缓存区为32K，默认16K
        builder.setSocketOption(StandardSocketOption.SO_SNDBUF, 16 * 1024); // 设置发送缓冲区为16K，默认为8K
        builder.setSocketOption(StandardSocketOption.TCP_NODELAY, false); // 启用nagle算法，提高吞吐量，默认关闭
        
		try {
			client = builder.build() ;
			client.setOpTimeout(CacheValues.MEMCACHED_TIMEOUT) ;
			
			//3、如果你对心跳检测不在意，也可以关闭心跳检测，减小系统开销
			client.setEnableHeartBeat(false);
			
			//Xmemcached默认会做两个优化：将连续的单个get合并成一个multi get批量操作获取，将连续的请求合并成socket发送缓冲区大小的buffer发送。
			//如果你对响应时间比较在意，那么可以将合并的因子减小，或者关闭合并buffer的优化：
			client.setMergeFactor(50);   //默认是150，缩小到50
			client.setOptimizeMergeBuffer(false);  //关闭合并buffer的优化
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取Memcached客户端
	 * @return
	 */
	public static final MemcachedClient getClient(){
		return client ;
	}
	
	
	/**
	 * 资源关闭
	 * @param client
	 */
	public static void closeMemcachedClient(MemcachedClient client){
		try {
			if(null !=client && !client.isShutdown())
				client.shutdown() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static final void close(){
		closeMemcachedClient(client) ;
	}
}
