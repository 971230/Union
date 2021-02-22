package com.ztesoft;

import org.apache.log4j.Logger;

import zte.net.ecsord.params.sf.req.OrderSearchServiceRequest;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;

public class TestService {
	private static Logger logger = Logger.getLogger(TestService.class);
	public static void main(String[] str) {
		OrderSearchServiceRequest req = new OrderSearchServiceRequest();
		req.setOrderid("123456789090");
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoSFResponse resp = client.execute(req,NotifyOrderInfoSFResponse.class);
		logger.info(resp.error_code);
		logger.info(resp.error_msg);
	}
}
