package com.ztesoft.net.cache.redis.jedis.tests.commands;

import org.junit.Test;

import com.ztesoft.net.cache.redis.jedis.BinaryJedis;
import com.ztesoft.net.cache.redis.jedis.HostAndPort;
import com.ztesoft.net.cache.redis.jedis.tests.HostAndPortUtil;

public class ConnectionHandlingCommandsTest extends JedisCommandTestBase {
    protected static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);

    @Test
    public void quit() {
        assertEquals("OK", jedis.quit());
    }

    @Test
    public void binary_quit() {
        BinaryJedis bj = new BinaryJedis(hnp.getHost());
        assertEquals("OK", bj.quit());
    }
}