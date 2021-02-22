package com.ztesoft.test.http;

import org.junit.Test;

import com.ztesoft.test.http.base.HttpClientTest;

public class OrderHttpClientTest extends HttpClientTest{
	
	/* 通过 */
	@SuppressWarnings("unchecked")
	@Test
	public void addOrder(){
		  String param_json = "{\"@type\":\"java.util.HashMap\",\"class\":\"zte.params.order.req.OrderAddReq\",\"create_type\":\"1\",\"params\":null,\"paramsl\":[{\"@type\":\"java.util.HashMap\",\"acc_nbr\":\"18911111111\",\"app_code\":\"wssmall_fj\",\"goods_num\":\"1\",\"name\":\"吴辉\",\"payment_id\":\"2\",\"product_id\":\"201402246578001832\"}],\"serverUrl\":\"\",\"service_code\":\"3\",\"sessionId\":\"\",\"ship_amount\":0D,\"userSessionId\":\"93c5075814564c71824c57ded56efb4c\",\"warehousePurorder\":null,\"zteRequest\":null}";
		//String param_json = "{\"@type\":\"java.util.HashMap\",\"class\":\"zte.params.order.req.OrderAddReq\",\"create_type\":\"1\",\"params\":null,\"paramsl\":[{\"@type\":\"java.util.HashMap\",\"acc_nbr\":\"18911111111\",\"app_code\":\"wssmall_fj\",\"goods_num\":\"1\",\"name\":\"吴辉\",\"product_id\":\"201402246578001832\"}],\"serverUrl\":null,\"service_code\":\"3\",\"sessionId\":\"\",\"ship_amount\":null,\"userSessionId\":\"427bae829206401287441b75929e48bc\",\"warehousePurorder\":null,\"zteRequest\":null}\";
		String method="zte.orderService.order.add";
		String urlStr= doPost(url, param_json, method, appKey, secret, "");
		System.out.print(urlStr);
	}
}
