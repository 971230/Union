package com.ztesoft.net.utils;

import params.req.OrderExpMarkProcessedReq;
import params.resp.OrderExpMarkProcessedResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.DefaultZteRopClient;
import com.ztesoft.api.ZteClient;

public class Test {

	public static void main(String[] args) {
		markProcessed();
	}
	
	private static ZteClient getClient() {
		return new DefaultZteRopClient("http://localhost:8098/router",AppKeyEnum.APP_KEY_WSSMALL_ECSORD.getAppKey(),AppKeyEnum.APP_KEY_WSSMALL_ECSORD.getAppSec(),"1.0");
	}
	
	/**
	 * 业务环节功能校验自测
	 */
	public static void markProcessed() {
		OrderExpMarkProcessedReq req = new OrderExpMarkProcessedReq();
		req.setRel_obj_id("FS201503163858143638");
		req.setRel_obj_type("order");
		req.setDeal_result("已处理 201512181342");
		req.setDeal_staff_no("-1");
		OrderExpMarkProcessedResp resp = getClient().execute(req, OrderExpMarkProcessedResp.class);
		System.out.println("result code:"+resp.getError_code());
		System.out.println("result message:"+resp.getError_msg());
	}	
}
