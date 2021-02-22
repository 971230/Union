package com.ztesoft.net.cache.redis.jedis.tests;

import org.junit.Assert;
import org.junit.Test;

import com.ztesoft.net.cache.redis.jedis.BuilderFactory;

public class BuilderFactoryTest extends Assert {
    @Test
    public void buildDouble() {
        Double build = BuilderFactory.DOUBLE.build("1.0".getBytes());
        assertEquals(new Double(1.0), build);
    }
}