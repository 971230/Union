package com.ztesoft.mq.example.rocketmq.order;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventSingleListenHandler;
import com.ztesoft.mq.client.EventMessageExt;
import com.ztesoft.mq.client.common.SpringContextUtil;
import com.ztesoft.mq.client.exception.EventHandleErrorType;
import com.ztesoft.mq.client.exception.EventHandleException;

public class ReceivePushExample implements EventSingleListenHandler {
	private static Logger logger = Logger.getLogger(ReceivePushExample.class);
	AtomicLong consumeTimes = new AtomicLong(0);
	
	@Override
	public void executeEvent(EventMessageExt e) throws EventHandleException {
		// TODO Auto-generated method stub
		this.consumeTimes.incrementAndGet();
		logger.info("Receive Message" + e.toString());

		if (consumeTimes.get() == 3) {
			throw new EventHandleException(
					EventHandleErrorType.HANDLE_MESSAGE_FAILED,
					"bitchsendback", "plan send back bitch message");
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		try {
			SpringContextUtil
					.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/order/rocketClient.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}