package com.ztesoft.mq.example.rocketmq.batch;

import org.apache.log4j.Logger;

import com.ztesoft.mq.client.DelayLevel;
import com.ztesoft.mq.client.ErrorHandlerResult;
import com.ztesoft.mq.client.ErrorHandlerStatus;
import com.ztesoft.mq.client.MessageErrorHandler;
import com.ztesoft.mq.client.exception.EventHandleException;

public class PushErrorExample implements MessageErrorHandler {
	private static Logger logger = Logger.getLogger(PushErrorExample.class);
	@Override
	public ErrorHandlerResult handleError(EventHandleException t) {
		// TODO Auto-generated method stub
		System.out
				.println("error_type:" + t.getErrorType() + " error_code:"
						+ t.getErrorCode() + "event_msg:"
						+ t.getEventMessage().toString() + " err_msg:"
						+ t.getMessage());

		if ("simplesendback".equals(t.getErrorCode())) {
			return new ErrorHandlerResult(ErrorHandlerStatus.SENDBACK,
					DelayLevel.ONE_MINUTE);
		} else if ("bitchsendback".equals(t.getErrorCode())) {
			return new ErrorHandlerResult(ErrorHandlerStatus.BATCHSENDBACK);
		}
		logger.info(t.getMessage());
		return new ErrorHandlerResult(ErrorHandlerStatus.CONTINUE);
	}

}
