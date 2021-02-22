package com.ztesoft.mq.example.rocketmq.simple;

import com.ztesoft.mq.client.common.SpringContextUtil;

public class SimpleExample {
	public String TEST_PRODUCER_POOL = "normalSendController"; // 生产者池，见../rocketClient.xml
	public String TEST_CONSUMER_POOL = "simpleReceiveController"; // 消费者池，使用pull方式主动消费，见../rocketClient.xml
	public String topic = "ZTESimpleExample"; //主题需要管理员在broker先创建
	
	public void initSend() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/simple/send.xml");
	}
	
	public void initReceive() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/simple/receive.xml");
	}

}
