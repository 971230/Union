package com.ztesoft.net.cache.zredis;

import com.ztesoft.net.cache.conf.Config;
import com.ztesoft.net.eop.sdk.context.CacheConfigSetting;

/**
 * @Description
 * @author  zhangJun
 * @date    2016年1月20日
 * @version 1.0
 */
public class ZredisCacheValues {
	
	public static String SERVER_LIST = CacheConfigSetting.get("zredis.serverList");
	public static String CLUSTER_NAME = CacheConfigSetting.get("zredis.clusterName");
	public static String CLIENT_NAME = CacheConfigSetting.get("zredis.clientName");
	public static int MAX_TOTAL = Integer.parseInt(CacheConfigSetting.get("zredis.maxTotal"));   	// max total connections in a pool
	public static int MAX_IDLE = Integer.parseInt(CacheConfigSetting.get("zredis.maxIdle"));    	// max idle connections in a pool
	public static int MIN_IDLE = Integer.parseInt(CacheConfigSetting.get("zredis.minIdle"));     	// min idle connections in a pool
	public static int CONN_TIMEOUT = Integer.parseInt(CacheConfigSetting.get("zredis.connTimeout")); // connect timeout
	public static int SO_TIMEOUT = Integer.parseInt(CacheConfigSetting.get("zredis.soTimeout"));   // read timeout
	public static int MAX_REDIRECTIONS = Integer.parseInt(CacheConfigSetting.get("zredis.maxRedirections"));// max redirections 
	public static int MAX_SCANCOUNT = Integer.parseInt(CacheConfigSetting.get("zredis.maxScanCount"));// scan count
	
	public static long MAX_WAITMILLIS = Long.parseLong(CacheConfigSetting.get("zredis.maxWaitMillis")) ;
	public static boolean TEST_ON_BORROW = Boolean.parseBoolean(Config.get("zredis.testOnBorrow")) ;;
	public static boolean TEST_ON_RETURN = Boolean.parseBoolean(Config.get("zredis.testOnReturn")) ;;

}



