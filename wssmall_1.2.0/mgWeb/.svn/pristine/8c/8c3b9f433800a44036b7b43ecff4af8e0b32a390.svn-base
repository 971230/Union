package com.ztesoft.test.dubbo;

import junit.framework.Assert;

import org.testng.annotations.Test;

import params.ZteResponse;
import params.adminuser.req.AdminUserPageReq;
import params.adminuser.req.MessageDetailReq;
import params.adminuser.req.MessageListReq;
import params.adminuser.resp.AdminUserPageResp;
import params.adminuser.resp.MessageDetaiResp;
import params.adminuser.resp.MessageListResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class SysDubboClientTest extends DubboClientTest{
	//成功
	@Test(enabled=true)
	public void qryAdminUser(){
		AdminUserPageReq adminUserPageReq=new AdminUserPageReq();
		adminUserPageReq.setPageNo("1");
		adminUserPageReq.setPageZise("10");
		adminUserPageReq.setUsername("dianxin");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(adminUserPageReq, AdminUserPageResp.class);
		logger.info("------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(),"0");
	}
	//-------------成功-----------
	@Test(enabled=true)
	public void listMsg(){
		MessageListReq messageListReq=new MessageListReq();
		messageListReq.setNum(1);
		messageListReq.setSenderState(0);
		messageListReq.setReciverState(1);
		messageListReq.setTopic("ddddd");
		messageListReq.setPageNo(1);
		messageListReq.setPageSize(10);
//		messageListReq.setStarttime("2013/5/10 8:44:45");
//		messageListReq.setEndtime("");
		messageListReq.setType("");
		messageListReq.setUser_id("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(messageListReq, MessageListResp.class);
		logger.info("打印---"+response.getError_msg());
		Assert.assertEquals(response.getError_code(),"成功");
	}
	//成功
	@Test(enabled=true)
	public void getMessageDetail(){
		MessageDetailReq messageDetailReq=new MessageDetailReq();
		messageDetailReq.setUser_id("201305074000000209");
		messageDetailReq.setM_id("201305103675001271");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(messageDetailReq, MessageDetaiResp.class);
		Assert.assertEquals(response.getError_code(),"成功");
	}
}
