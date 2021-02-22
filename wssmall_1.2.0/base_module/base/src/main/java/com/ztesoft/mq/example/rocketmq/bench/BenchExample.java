package com.ztesoft.mq.example.rocketmq.bench;

import com.ztesoft.mq.client.common.SpringContextUtil;

public class BenchExample {
	public final String TEST_PRODUCER_POOL = "benchSendController"; // 生产者池，见../rocketClient.xml
	public final String TEST_CONSUMER_POOL = "benchReceiveController"; // 消费者池，使用pull方式主动消费，见../rocketClient.xml
	public final String topic = "BenchExample"; //主题需要管理员在broker先创建
	
	public void initSend() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/bench/sendClient.xml");
	}
	
	public void initReceive() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/bench/receiveClient.xml");
	}
	
	public void initReceiveBatch() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/bench/receiveBatchClient.xml");
	}
}
