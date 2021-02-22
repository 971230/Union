package com.ztesoft.test.examp;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-17 12:11
 * To change this template use File | Settings | File Templates.
 */
public class NgMain2Tests {
	private static Logger logger = Logger.getLogger(NgMain2Tests.class);
    @BeforeClass
    public void setUp(){
        logger.info("初始化执行.....");
    }

    @Test
    public void run(){
        logger.info("方法一");
    }



    @Test(invocationCount = 5,threadPoolSize = 500,timeOut = 1000)
    public void check(){
        boolean resutl=true;
        Assert.assertEquals(resutl, true);
        logger.info("线程ID:"+Thread.currentThread().getId());
    }


    @Test
    public void run_3(){
        logger.info("方法:run_3");
    }
}
