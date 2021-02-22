package com.ztesoft.net.cache.memcached;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.common.CacheFactory;

/**
 * @author Reason.Yea 
 * @version 创建时间：Jan 8, 2014
 */
public class ShutdownCallback {
	private static Logger logger = Logger.getLogger(ShutdownCallback.class);
	public static void regist(){
		logger.info("regist app CALLBACK==================>") ;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("go to exit app==================>") ;
				CacheFactory.getCache().close() ;
				logger.info("EXIT app==================>") ;
			}
		});
	}
}
