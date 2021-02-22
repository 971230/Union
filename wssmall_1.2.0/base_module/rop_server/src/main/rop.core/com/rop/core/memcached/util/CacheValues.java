package com.rop.core.memcached.util;


import com.rop.core.memcached.config.Config;

public class CacheValues {
	
	public static final long MEMCACHE_TIMEOUT = Long.parseLong(Config.get("uba.memcached.timeout")) ;
	public static final long MEMCACHE_MULTIGETS_TIMEOUT = Long.parseLong(Config.get("uba.memcached.multiGetTimeout")) ;
	
	public static final int foreverSecond = 0;
	public static final int oneHourSecond = 3600;
	public static final int oneDaySecond = 86400;
	
	public static final String DOT_SEPERATE = "," ;
	
	public static final String T = "T" ;
	public static final String F = "F" ;
	
	//cache部分
	public static final String CACHE_CONFIG = "CONFIG" ;
	public static final String CACHE_INSERT_SQL = "DBSQL" ;
	public static final String CACHE_IMP_DATA = "IMP" ;

}



