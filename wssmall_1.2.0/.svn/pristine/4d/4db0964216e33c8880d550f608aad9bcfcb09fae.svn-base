package com.ztesoft.net.cache.redis.jedis.tests.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.ztesoft.net.cache.redis.util.SafeEncoder;

public class SafeEncoderBenchmark {
	private static Logger logger = Logger.getLogger(SafeEncoderBenchmark.class);
    private static final int TOTAL_OPERATIONS = 10000000;

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        long begin = Calendar.getInstance().getTimeInMillis();

        for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
            SafeEncoder.encode("foo bar!");
        }

        long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        logger.info(((1000 * TOTAL_OPERATIONS) / elapsed)
                + " ops to build byte[]");

        begin = Calendar.getInstance().getTimeInMillis();

        byte[] bytes = "foo bar!".getBytes();
        for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
            SafeEncoder.encode(bytes);
        }

        elapsed = Calendar.getInstance().getTimeInMillis() - begin;

        logger.info(((1000 * TOTAL_OPERATIONS) / elapsed)
                + " ops to build Strings");

    }
}