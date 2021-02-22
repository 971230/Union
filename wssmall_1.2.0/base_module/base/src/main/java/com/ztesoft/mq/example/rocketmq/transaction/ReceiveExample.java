package com.ztesoft.mq.example.rocketmq.transaction;

import java.util.Set;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventMessageExt;
import com.ztesoft.mq.client.EventQueue;
import com.ztesoft.mq.client.rocketMQ.RocketMQEventTemplateGeneral;
import com.ztesoft.mq.client.rocketMQ.RocketReceiveEventTemplate;

public class ReceiveExample extends TransactionExample {
	private static Logger logger = Logger.getLogger(ReceiveExample.class);
	private String action = "SimpleTransactionTest";

	public ReceiveExample() {
		this.init();
	}

	public void executeSyncReceive() {
		RocketReceiveEventTemplate template = null;
		try {
			template = RocketMQEventTemplateGeneral
					.createRocketMQReceiveTemplate(this.TEST_CONSUMER_POOL);

			Set<EventQueue> queues = template
					.searchQueuer(this.topic);
			for (EventQueue queue : queues) {
				logger.info(".....receive from queue "
						+ queue.getQueueId());
				EventMessageExt messageExt = template.receiveEventMessage(
						queue, action);

				if (messageExt != null) {
					logger.info("receive message: "
							+ messageExt.toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				RocketMQEventTemplateGeneral.closeRocketMQReceiveTemplate(
						this.TEST_CONSUMER_POOL, template);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ReceiveExample example = new ReceiveExample();
		example.executeSyncReceive();
	}
}