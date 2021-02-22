package com.ztesoft.api.mq;

import java.util.Random;

import org.apache.log4j.Logger;

import params.ZteRequest;

import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.mq.client.EventSendCallback;
import com.ztesoft.mq.client.EventSendResult;
import com.ztesoft.mq.client.EventTemplateGeneral;
import com.ztesoft.mq.client.EventTransactionExecuter;
import com.ztesoft.mq.client.EventTransactionTemplate;
import com.ztesoft.mq.client.rocketMQ.RocketMQEventTemplateGeneral;
import com.ztesoft.mq.client.rocketMQ.RocketSendEventTemplate;
import com.ztesoft.net.eop.sdk.context.MqConfigSetting;
import com.ztesoft.net.mall.core.model.MsgSendTryRecord;
import com.ztesoft.net.mall.core.utils.IJSONUtil;

/**
 * @Description 生产者
 * @author zhangJun
 * @date 2015-3-12
 * @version 1.0
 */
public class MqSend {

	private static Logger logger = Logger.getLogger(MqSend.class);

	/**
	 * 普通发送
	 * 
	 * @param request
	 * @return
	 */

	public boolean executeSend(ZteRequest request) {
		return executeSend(request, null, null);
	}

	public boolean executeSend(ZteRequest request, String sourceFrom) {
		return executeSend(request, sourceFrom, null);
	}

	/**
	 * 发送消息
	 * 
	 * @param request
	 *            请求
	 * @param sourceFrom
	 *            来源
	 * @param action
	 *            回调接口
	 * @return true 发送成功，false 发送失败
	 */
	public boolean executeSend(final ZteRequest request, String sourceFrom, final EventSendCallback action) {
		boolean flag = false;
		// ExecutorService exec = Executors.newSingleThreadExecutor();
		RocketSendEventTemplate template = null;
		int key = 0;
		// 发送消息
		String topic = request.getMqTopic();
		if (StringUtil.isEmpty(topic)) {
			topic = MqConfigSetting.MQ_TOPIC_COMMON;
		}
		key = getRandomkey();
		try {
			logger.info("[MqSend] MQ发送消息反馈: 主题:[" + topic + "]");
			template = RocketMQEventTemplateGeneral.createRocketMQSendTemplate(ApiConsts.MQ_PRODUCER_POOL);
			EventSendResult result = template.send(topic, request.getClass().getSimpleName(), key, request);
			logger.info("[MqSend] MQ发送消息反馈: 主题:[" + topic + "] 返回对象:" + result.toString());
			flag = true;// 不抛异常就成功
			// exec.execute(new ExecuteCallbackTask(action, true, result));
		} catch (Exception e) {
			// exec.execute(new ExecuteCallbackTask(action, false, e));
			e.printStackTrace();
		} finally {
			if (template != null) {
				try {
					RocketMQEventTemplateGeneral.closeRocketMQSendTemplate(ApiConsts.MQ_PRODUCER_POOL, template);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// exec.shutdown();
		}

		return flag;
	}

	/**
	 * 定时任务再次发送
	 * 
	 * @param request
	 * @return true 发送成功
	 */

	public boolean executeSendFailMsg(MsgSendTryRecord msgSend) {
		boolean flag = false;
		RocketSendEventTemplate template = null;
		int key = 0;
		// 发送消息
		try {
			if (msgSend.getSend_obj() != null && !msgSend.getSend_obj().equals("")) {
				ZteRequest ZteReq = IJSONUtil.jsonToBean(msgSend.getSend_obj(), ZteRequest.class);
				template = RocketMQEventTemplateGeneral.createRocketMQSendTemplate(ApiConsts.MQ_PRODUCER_POOL);
				EventSendResult result = template.send(msgSend.getTopic(), msgSend.getAction_name(),
						msgSend.getMsg_key(), ZteReq);
				flag = true;// 不抛异常就成功
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (template != null) {
				try {
					RocketMQEventTemplateGeneral.closeRocketMQSendTemplate(ApiConsts.MQ_PRODUCER_POOL, template);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 事务发送 暂时不用
	 * 
	 * @param request
	 * @param transactionCallback
	 * @param CallbackParameter
	 * @return
	 */
	private boolean executeTransactionSend(ZteRequest request, EventTransactionExecuter transactionCallback,
			final Object CallbackParameter) {
		boolean flag = false;
		EventTransactionTemplate template = null;
		try {
			template = EventTemplateGeneral.createTransactionSendTemplate(ApiConsts.MQ_TX_PRODUCER_POOL);
			template.sendInTransaction(request.getMqTopic(), request.getClass().getSimpleName(), this.getRandomkey(),
					request, transactionCallback, CallbackParameter);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (template != null) {
				try {
					EventTemplateGeneral.closeTransactionSendTemplate(ApiConsts.MQ_TX_PRODUCER_POOL, template);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	private int getRandomkey() {
		Random rd = new Random(); // 随机数用于均衡消息到不同的队列，一个队列只对应一个消费者
		return rd.nextInt(999999999);
	}

	/**
	 * 
	 * 执行回调操作的任务
	 *
	 */
	private static class ExecuteCallbackTask implements Runnable {
		EventSendCallback action;
		boolean isSuccess;
		EventSendResult result;
		Throwable e;

		public ExecuteCallbackTask(EventSendCallback action, boolean isSuccess, EventSendResult result) {
			this.action = action;
			this.isSuccess = isSuccess;
			this.result = result;
		}

		public ExecuteCallbackTask(EventSendCallback action, boolean isSuccess, Throwable e) {
			this.action = action;
			this.isSuccess = isSuccess;
			this.e = e;
		}

		@Override
		public void run() {
			try {
				if (action != null) {
					if (isSuccess) {
						action.onSuccess(result);
					} else {
						action.onException(e);
					}
				}
			} catch (Exception e) {
				// 记日志
			}
		}

	}

}
