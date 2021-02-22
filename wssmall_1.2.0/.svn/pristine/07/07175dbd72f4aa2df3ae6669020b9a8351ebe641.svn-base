package com.ztesoft.mq.example.rocketmq.transaction;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventTemplateGeneral;
import com.ztesoft.mq.client.EventTransactionExecuter;
import com.ztesoft.mq.client.EventTransactionTemplate;

public class SendExample extends TransactionExample {
	private static Logger logger = Logger.getLogger(SendExample.class);
	private String action = "SimpleTransactionTest";
	private int orderId = 1;
	private String context = "Hi, I come from ZTESoft, I'm a transaction message xxx";
	private String isCommit = "true";

	public SendExample() {
		this.init();
	}

	public SendExample(String action, int orderId, String context,
			String isCommit) {
		this.action = action;
		this.orderId = orderId;
		this.context = context;
		this.isCommit = isCommit;
		this.init();
	}

	public void executeSend() {
		EventTransactionTemplate template = null;
		try {
			template = EventTemplateGeneral
					.createTransactionSendTemplate(this.TEST_PRODUCER_POOL);
			template.sendInTransaction(this.topic, action, orderId,
					context, new EventTransactionExecuter() {

						@Override
						public boolean executeTransaction(String topic,
								String action, int orderId,
								Object eventContent, Object transactionExtMsg) {
							// TODO Auto-generated method stub
							// 执行本地事务，例如数据提交等,
							if ("true".equals(isCommit)) {
								// 如果本地事务执行成功,
								// 返回true通知RocketMQ提交发送消息事务，
								// 消息将被容许消费
								System.out
										.println("commit send message, topic:"
												+ topic + " action:" + action
												+ " orderId:" + orderId
												+ " context:"
												+ eventContent.toString());
								return true;
							} else {
								// 如果本地事务执行失败,
								// 返回false通知RocketMQ回滚发送消息事务，
								// 消息将被从队列删除
								System.out
								.println("rollback send message, topic:"
										+ topic + " action:" + action
										+ " orderId:" + orderId
										+ " context:"
										+ eventContent.toString());
								return false;
							}
						}

					}, orderId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (template != null) {
				try {
					EventTemplateGeneral.closeTransactionSendTemplate(
							TEST_PRODUCER_POOL, template);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		logger.info("...........finish................");
	}

	public static void main(String[] args) {
		SendExample example = null;
		if (args.length < 1) {
			example = new SendExample();
		} else {
			String actionTest = args[0];
			String orderId = args[1];
			String context = args[2];
			String isCommit = args[3];
			example = new SendExample(actionTest, Integer.parseInt(orderId),
					context, isCommit);
		}

		example.executeSend();
	}
}
