package com.ztesoft.test.dubbo;

import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;
import zte.net.iservice.params.user.req.UserLoginReq;
import zte.net.iservice.params.user.resp.UserLoginResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;
import commons.CommonTools;

public class AccountDubboClientTest extends DubboClientTest{
	//--------------------找不到服务------------
	@Test
	public void userLogin(){
		UserLoginReq userLoginReq=new UserLoginReq();
		userLoginReq.setUserName("admin");
		userLoginReq.setUerPwd("123");
		userLoginReq.setProduct_id("201403024579001887");
		userLoginReq.setService_code("3");
		userLoginReq.setUserSessionId(CommonTools.getUserSessionId());
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(userLoginReq, UserLoginResp.class);
		logger.info("异常订单返回---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
}
