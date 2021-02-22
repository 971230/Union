package com.ztesoft.net.cache.redis.jedis.tests.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.redis.jedis.HostAndPort;
import com.ztesoft.net.cache.redis.jedis.Jedis;
import com.ztesoft.net.cache.redis.jedis.tests.HostAndPortUtil;

public class GetSetBenchmark {
	private static Logger logger = Logger.getLogger(GetSetBenchmark.class);
	private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);
    private static final int TOTAL_OPERATIONS = 100000;

    public static void main(String[] args) throws UnknownHostException,
	    IOException {
	Jedis jedis = new Jedis(hnp.getHost(), hnp.getPort());
	jedis.connect();
	jedis.auth("foobared");
	jedis.flushAll();

	long begin = Calendar.getInstance().getTimeInMillis();

	for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
	    String key = "foo" + n;
	    jedis.set(key, "bar" + n);
	    jedis.get(key);
	}

	long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

	jedis.disconnect();

	logger.info(((1000 * 2 * TOTAL_OPERATIONS) / elapsed) + " ops");
    }
}