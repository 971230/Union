package com.ztesoft.net.cache.redis.jedis.tests.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.redis.util.Hashing;

public class ShardedBenchmark {
	private static Logger logger = Logger.getLogger(ShardedBenchmark.class);
    private static final int TOTAL_OPERATIONS = 10000000;

    public static void main(String[] args) throws UnknownHostException,
            IOException {

        long begin = Calendar.getInstance().getTimeInMillis();

        for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
            String key = "foo" + n;
            Hashing.MD5.hash(key);
        }

        long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        logger.info(((1000 * TOTAL_OPERATIONS) / elapsed) + " MD5 ops");

        begin = Calendar.getInstance().getTimeInMillis();

        for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
            String key = "foo" + n;
            Hashing.MURMUR_HASH.hash(key);
        }

        elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        logger.info(((1000 * TOTAL_OPERATIONS) / elapsed)
                + " Murmur ops");

    }
}