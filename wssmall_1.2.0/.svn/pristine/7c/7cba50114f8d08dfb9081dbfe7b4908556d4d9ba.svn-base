package com.ztesoft.mq.example.rocketmq.order;

import com.ztesoft.mq.client.common.SpringContextUtil;

public class OrderExample {
	public String TEST_PRODUCER_POOL = "normalSendController"; // 事务消息生产者池，见.../rocketClient.xml
	public String TEST_CONSUMER_POOL = "normalReceiveController"; // 消费者池，使用pull方式主动消费，见.../rocketClient.xml
	public String topic = "ZTEOrderExample";
	
	public void init() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/order/rocketClient.xml");
	}
}
