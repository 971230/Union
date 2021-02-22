package com.ztesoft.pass.web;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-07 09:23
 * To change this template use File | Settings | File Templates.
 */
public class MainWebTests {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private ApplicationContext context = null;
    String[] configure = null;

    @Before
    public void setUp() {
        configure = new String[]{"classpath*:spring/*.xml"};
        context = new ClassPathXmlApplicationContext(configure);
    }

    @Test
    public void init() {
        //logger.info("init:==>"+context);
        logger.debug("初始化测试：init==>{}",context);
    }
}
