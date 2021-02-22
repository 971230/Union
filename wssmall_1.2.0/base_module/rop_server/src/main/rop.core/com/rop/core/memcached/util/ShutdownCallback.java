package com.rop.core.memcached.util;


import org.apache.log4j.Logger;

/**
 *
 */
public class ShutdownCallback {
    private static Logger logger = Logger.getLogger(ShutdownCallback.class);

    public static void regist() {
        logger.debug("regist app CALLBACK==================>");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
			public void run() {
                logger.debug("go to exit app==================>");
                //CacheFactory.getDefaultCache().close();
                logger.debug("EXIT app==================>");
            }
        });
    }
}
