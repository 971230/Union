//package com.ztesoft.common.util;
//
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.UserGetRequest;
//import com.taobao.api.response.UserGetResponse;
//
//public class APITest {
//	protected static String url = "http://gw.api.tbsandbox.com/router/rest";// 沙箱环境调用地址
//	// 正式环境需要设置为:http://gw.api.taobao.com/router/rest
//	protected static String appkey = "test";
//	protected static String appSecret = "test";
//
//	public static void testUserGet() {
//		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);// 实例化TopClient类
//		UserGetRequest req = new UserGetRequest();// 实例化具体API对应的Request类
//		req
//				.setFields("nick,sex,buyer_credit,seller_credit ,created,last_visit");
//		req.setNick("andyy_tan");
//		UserGetResponse response;
//		try {
//			response = client.execute(req); // 执行API请求并打印结果
//			logger.info("result:" + response.getBody());
//			logger.info("nick:" + response.getUser().getNick());
//		} catch (ApiException e) {
//			// deal error
//		}
//	}
//
//	public static void main(String[] args) {
//		APITest.testUserGet();
//	}
//
//}