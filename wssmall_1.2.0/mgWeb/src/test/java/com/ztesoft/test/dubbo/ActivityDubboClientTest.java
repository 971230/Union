package com.ztesoft.test.dubbo;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import params.ZteResponse;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.params.activity.req.ActByIdReq;
import com.ztesoft.remote.params.activity.req.ActByTagIdReq;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.ActPageResp;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;
import com.ztesoft.test.dubbo.base.DubboClientTest;


public class ActivityDubboClientTest extends DubboClientTest{
	private static Logger logger = Logger.getLogger(ActivityDubboClientTest.class);
	
	/**
	 * 根据活动标识获取
	 */
	public void queryActById(){
		logger.info("查 queryActById");
		ActByIdReq req =  new ActByIdReq();
		req.setId("3");
		ZteClient client = this.getDubboZteClient();
        ZteResponse response= client.execute(req, ActResp.class);
        logger.info("此次调用结果:"+response.isResult()+",msg:"+response.getError_msg());
       
	}
	
	/**
	 * 根据标签标识获取
	 */
	public void queryActByTagId(){
		logger.info("查 queryActById");
		ActByTagIdReq req =  new ActByTagIdReq();
		req.setTagId("5");
		req.setPageNo(1);
		req.setPageSize(10);
		ZteClient client = this.getDubboZteClient();
        ZteResponse response= client.execute(req, ActPageResp.class);
        logger.info("此次调用结果:"+response.isResult()+",msg:"+response.getError_msg());
       
	}
	
	/**
	 * 根据活动标识获取活动基本信息
	 */
	@Test
	public void getPromotionMap() {
		logger.info("查 getPromotionMap");
		PromotionMapByIdReq req =  new PromotionMapByIdReq();
//		req.setActivity_id("201403268759000138");
		ZteClient client = this.getDubboZteClient();
        ZteResponse response = client.execute(req, PromotionMapByIdResp.class);
        logger.info("此次调用结果:"+response.isResult()+",msg:"+response.getError_msg());
       
	}
	
public static void main(String[] aa) {
		
		
		logger.info("111111");
		String configs[]=new String[]{"classpath*:spring/*.xml","classpath*:dubbo/reference/*.xml"};
		ApplicationContext context = new ClassPathXmlApplicationContext(configs);
		
		PromotionMapByIdReq req =  new PromotionMapByIdReq();
//		req.setActivity_id("201403268759000140");
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
        ZteResponse response = client.execute(req, PromotionMapByIdResp.class);
        logger.info("此次调用结果:"+response.isResult()+",msg:"+response.getError_msg());
        
        
		//Test.send("201403215014381");
	}
	
	
}
