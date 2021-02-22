package com.ztesoft.mq.example.rocketmq.batch;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.EventBatchListenHandler;
import com.ztesoft.mq.client.EventMessageExt;
import com.ztesoft.mq.client.common.SpringContextUtil;
import com.ztesoft.mq.client.exception.EventHandleErrorType;
import com.ztesoft.mq.client.exception.EventHandleException;

public class ReceivePushExample implements EventBatchListenHandler {
	private static Logger logger = Logger.getLogger(ReceivePushExample.class);
	AtomicLong consumeTimes = new AtomicLong(0);

	@Override
	public void executeEvent(List<EventMessageExt> es) throws EventHandleException {
		// TODO Auto-generated method stub
		this.consumeTimes.incrementAndGet();
		logger.info("Receive Messages:");
		int i = 0;
		for (EventMessageExt e : es) {
			logger.info(i++ + "--" + e.toString());
		}
		logger.info("end!");

		if (consumeTimes.get() > 3 ) {
			throw new EventHandleException(
					EventHandleErrorType.HANDLE_MESSAGE_FAILED,
					"bitchsendback", "plan send back bitch message");
		} else {
			return;
		}
	}

	public static void main(String[] args) {
		try {
			SpringContextUtil.initClassPathApplicationContext("/com/ztesoft/mq/example/rocketmq/batch/receive.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
