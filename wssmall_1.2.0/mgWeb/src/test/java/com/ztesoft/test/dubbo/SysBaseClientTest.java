package com.ztesoft.test.dubbo;

import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;

import com.ztesoft.api.ZteClient;

import zte.net.iservice.params.goodsOrg.req.GoodsOrgReq;
import zte.net.iservice.params.goodsOrg.resp.GoodsOrgResp;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class SysBaseClientTest extends DubboClientTest{
	//--------------------找不到服务------------
	@Test
	public void queryGoodsOrg(){
		GoodsOrgReq req = new GoodsOrgReq();
		req.setParty_id("2014032718274");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, GoodsOrgResp.class);
		logger.info("异常返回---"+response.getError_code());
		Assert.assertEquals(response.getError_code(), "0");
	}
}
