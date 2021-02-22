package com.ztesoft.test.rop.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;

public class RopClientTest {

	public ApplicationContext context = null;
    public Logger logger = LoggerFactory.getLogger(getClass());
    public String url="http://localhost:8083/rop/router";
    public String appKey="wssmall_fj",secret="123456"; //wssmall_fj

    @BeforeClass
    public void setUp() throws Exception {
    	  String configs[]=new String[]{"classpath:spring/*.xml,classpath:dubbo/reference/*.xml"};
          context = new ClassPathXmlApplicationContext(configs);
          context.containsBean("apiContextHolder");
    }

    
    public  ZteClient getRopZteClient(){
    	return ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_FJ);
    }

}
