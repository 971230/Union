package com.ztesoft.net.cache.redis.jedis.tests.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.redis.jedis.HostAndPort;
import com.ztesoft.net.cache.redis.jedis.Jedis;
import com.ztesoft.net.cache.redis.jedis.Pipeline;
import com.ztesoft.net.cache.redis.jedis.tests.HostAndPortUtil;

public class PipelinedGetSetBenchmark {
	private static Logger logger = Logger.getLogger(PipelinedGetSetBenchmark.class);
    private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);
    private static final int TOTAL_OPERATIONS = 200000;

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        Jedis jedis = new Jedis(hnp.getHost(), hnp.getPort());
        jedis.connect();
        jedis.auth("foobared");
        jedis.flushAll();

        long begin = Calendar.getInstance().getTimeInMillis();

        Pipeline p = jedis.pipelined();
        for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
            String key = "foo" + n;
            p.set(key, "bar" + n);
            p.get(key);
        }
        p.sync();

        long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        jedis.disconnect();

        logger.info(((1000 * 2 * TOTAL_OPERATIONS) / elapsed) + " ops");
    }
}