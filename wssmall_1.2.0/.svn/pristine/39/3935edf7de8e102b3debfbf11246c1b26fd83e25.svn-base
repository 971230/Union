package com.ztesoft.mq.example.rocketmq.transaction;

import com.ztesoft.mq.client.common.SpringContextUtil;

public class TransactionExample {
	public String TEST_PRODUCER_POOL = "transactionSendController"; // 事务消息生产者池，见.../rocketClient.xml
	public String TEST_CONSUMER_POOL = "normalReceiveController"; // 消费者池，使用pull方式主动消费，见.../rocketClient.xml
	public String topic = "ZTETransactionExample";
	
	public void init() {
		SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/transaction/rocketClient.xml");
	}
}
