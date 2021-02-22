package com.ztesoft.test.examp;


import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-17 11:19
 * To change this template use File | Settings | File Templates.
 */
public class NgMainTests {
	private static Logger logger = Logger.getLogger(NgMainTests.class);
    @BeforeClass
    public void setUp(){
        logger.info("初始化执行.....");
    }

    @Test
    public void run(){
        logger.info("方法一");
    }


    @Test
    public void check(){
        boolean resutl=false;
        Assert.assertEquals(resutl,true);
    }


    @Test
    public void run_3(){
        logger.info("方法:run_3");
    }
}
