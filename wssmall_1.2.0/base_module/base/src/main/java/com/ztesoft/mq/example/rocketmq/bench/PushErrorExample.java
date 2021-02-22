package com.ztesoft.mq.example.rocketmq.bench;

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

		return new ErrorHandlerResult(ErrorHandlerStatus.CONTINUE);
	}

}
