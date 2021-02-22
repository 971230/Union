package com.ztesoft.test.dubbo;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;

import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.params.req.SmsAddRequest;
import com.ztesoft.remote.params.req.SmsInfoRequest;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsAddResponse;
import com.ztesoft.remote.params.resp.SmsInfoResponse;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class SmsDubboClientTest extends DubboClientTest{
	@Test(enabled=false)
	//----成功-
	public void sendSms(){
		SmsAddRequest smsAddRequest=new SmsAddRequest();
		smsAddRequest.setMsg("今天的内容");
		smsAddRequest.setAccNbr("1010101000");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(smsAddRequest, SmsAddResponse.class);
		Assert.assertEquals(response.getError_code(),"成功");
	}
	public void sendSmss(){
		SmsTempleteRequest smsTempleteRequest=new SmsTempleteRequest();
		smsTempleteRequest.setCode("");
		smsTempleteRequest.setAccNbr("");
		Map map=new HashMap();
		smsTempleteRequest.setParams(map);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(smsTempleteRequest, SmsTempleteResponse.class);
		Assert.assertEquals(response.getError_code(),"成功");
	}
	@Test(enabled=true)
	public void sendSmsss(){
		SmsInfoRequest smsInfoRequest=new SmsInfoRequest();
		smsInfoRequest.setSend_no("20140113174122028980");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(smsInfoRequest, SmsInfoResponse.class);
		Assert.assertEquals(response.getError_msg(),"成功");
	}
}
