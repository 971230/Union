package com.ztesoft.mq.example.rocketmq.order;

import com.ztesoft.mq.client.DelayLevel;
import com.ztesoft.mq.client.ErrorHandlerResult;
import com.ztesoft.mq.client.ErrorHandlerStatus;
import com.ztesoft.mq.client.MessageErrorHandler;
import com.ztesoft.mq.client.exception.EventHandleException;

public class PushErrorExample implements MessageErrorHandler {

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

		return new ErrorHandlerResult(ErrorHandlerStatus.CONTINUE);
	}
}
