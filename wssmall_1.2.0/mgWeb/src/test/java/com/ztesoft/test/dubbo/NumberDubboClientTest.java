package com.ztesoft.test.dubbo;

import junit.framework.Assert;

import org.testng.annotations.Test;

import params.ZteResponse;
import zte.params.number.req.NumberSynInfoReq;
import zte.params.number.resp.NumberSynInfoResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class NumberDubboClientTest extends DubboClientTest{
	@Test
	public void queryGoodsOrg(){
		NumberSynInfoReq req = new NumberSynInfoReq();
		req.setBatch_id("201405072432000135");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, NumberSynInfoResp.class);
		logger.info("异常返回---"+response.getError_code());
		Assert.assertEquals(response.getError_code(), "0");
	}
}
