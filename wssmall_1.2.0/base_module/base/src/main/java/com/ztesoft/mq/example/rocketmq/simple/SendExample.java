package com.ztesoft.mq.example.rocketmq.simple;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventSendCallback;
import com.ztesoft.mq.client.EventSendResult;
import com.ztesoft.mq.client.rocketMQ.RocketMQEventTemplateGeneral;
import com.ztesoft.mq.client.rocketMQ.RocketSendEventTemplate;

public class SendExample extends SimpleExample {
	private static Logger logger = Logger.getLogger(SendExample.class);
	private String action = "SimpleTest";
	private String context = buildMessage(1048576);

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
			template = RocketMQEventTemplateGeneral
					.createRocketMQSendTemplate(this.TEST_PRODUCER_POOL);
			for (int i = 0; i < 32; i++) {
				EventSendResult result = template.send(this.topic, action, i, context);
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

	public void executeAyncHashSend() {
		RocketSendEventTemplate template = null;
		try {
			template = RocketMQEventTemplateGeneral
					.createRocketMQSendTemplate(this.TEST_PRODUCER_POOL);
			for (int i = 0; i < 32; i++) {
				template.send(this.topic, action, i, context + " : " + i,
						null, new EventSendCallback() {

							@Override
							public void onSuccess(EventSendResult sendResult) {
								// TODO Auto-generated method stub
								logger.info("send success-status:"
										+ sendResult.toString());
							}

							@Override
							public void onException(Throwable e) {
								// TODO Auto-generated method stub
								logger.info("send failed:"
										+ e.getMessage());
							}

						});
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
	
	private String buildMessage(final int messageSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageSize; i += 8) {
            sb.append("hello baby");
        }
        return sb.toString();
    }

	public static void main(String[] args) {
		SendExample example = null;
		String sendType = "0";
		logger.info(args.length);
		if (args.length < 1) {
			example = new SendExample();
		} else if (args.length == 1) {
			sendType = args[0];
			example = new SendExample();
		} else {
			sendType = args[0];
			String actionTest = args[1];
			String context = args[2];
			example = new SendExample(actionTest, context);
		}

		if (sendType.equals("0")) {
			example.executeSyncSend();
		} else if (sendType.equals("1")) {
			example.executeAyncHashSend();
		}
	}
}
