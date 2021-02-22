package com.ztesoft.test.examp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-17 15:30
 * To change this template use File | Settings | File Templates.
 */
public class GoodsTests {
	private static Logger logger = Logger.getLogger(GoodsTests.class);
    @Test(invocationCount = 10,threadPoolSize = 500,enabled = false)
    public void query(){
        logger.info("查询商品===>线程ID:"+Thread.currentThread().getId());
    }

    @Test(invocationCount = 5,threadPoolSize = 500)
    public void queryInfo(){
        boolean result=true;
        Assert.assertEquals(result,false);

        logger.info("查询商品===>线程ID:"+Thread.currentThread().getId());
    }
}
