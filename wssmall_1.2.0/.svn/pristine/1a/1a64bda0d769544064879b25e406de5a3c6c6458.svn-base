package com.ztesoft.mq.example.rocketmq.batch;

import com.ztesoft.mq.client.common.SpringContextUtil;

public class BatchExample {
	public String TEST_PRODUCER_POOL = "normalSendController"; // 生产者池，见../rocketClient.xml
	public String topic = "ZTESimpleExample"; //主题需要管理员在broker先创建
	
	public void initSend() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/batch/send.xml");
	}

	public void initReceive() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/batch/receive.xml");
	}
}
