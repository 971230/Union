package com.ztesoft.test.dubbo;

import junit.framework.Assert;

import org.testng.annotations.Test;

import params.ZteResponse;
import zte.params.number.req.NumberChangeStatusReq;
import zte.params.number.resp.NumberChangeStatusResp;
import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class NumberDubboClientTest2 extends DubboClientTest{
	@Test
	public void queryGoodsOrg(){
		NumberChangeStatusReq req = new NumberChangeStatusReq();
		req.setNo("18818805700");
		req.setStatus_old("002");
		req.setStatus_new("003");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, NumberChangeStatusResp.class);
		logger.info("异常返回---"+response.getError_code());
		Assert.assertEquals(response.getError_code(), "0");
	}
	
	
}
