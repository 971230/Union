package com.ztesoft.mq.example.rocketmq.batch;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventSendResult;
import com.ztesoft.mq.client.rocketMQ.RocketMQEventTemplateGeneral;
import com.ztesoft.mq.client.rocketMQ.RocketSendEventTemplate;

public class SendExample extends BatchExample {
	private static Logger logger = Logger.getLogger(SendExample.class);
	private String action = "BatchTest";
	private String context = "Hi, Happy new year, I come from ZTESoft, I'm a simple message";

	public SendExample() {
		this.initSend();
	}

	public SendExample(String action, String context) {
		this.action = action;
		this.context = context;
		this.initSend();
	}

	public void executeSyncSend() {
		RocketSendEventTemplate template = null;
		try {
			for (int i = 0; i < 1; i++) {//32
				template = RocketMQEventTemplateGeneral
						.createRocketMQSendTemplate(this.TEST_PRODUCER_POOL);
				EventSendResult result = template.send(this.topic, action, 1,
						context + " : " + i);

				logger.info(i + "--send success-status:" + result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (template != null) {
				try {
					RocketMQEventTemplateGeneral.closeRocketMQSendTemplate(
							this.TEST_PRODUCER_POOL, template);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		SendExample example = null;
		logger.info(args.length);
		if (args.length < 1) {
			example = new SendExample();
		} else {
			String actionTest = args[0];
			String context = args[1];
			example = new SendExample(actionTest, context);
		}

		example.executeSyncSend();
	}
}
