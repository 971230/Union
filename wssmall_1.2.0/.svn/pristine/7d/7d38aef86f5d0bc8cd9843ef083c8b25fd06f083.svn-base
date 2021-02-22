package com.ztesoft.api.mq;



import com.ztesoft.mq.client.ErrorHandlerResult;
import com.ztesoft.mq.client.ErrorHandlerStatus;
import com.ztesoft.mq.client.MessageErrorHandler;
import com.ztesoft.mq.client.exception.EventHandleException;

public class PushError implements MessageErrorHandler {

	@Override
	public ErrorHandlerResult handleError(EventHandleException t) {
		// TODO Auto-generated method stub
		System.out
				.println("消费异常:" + t.getErrorType() + " error_code:"
						+ t.getErrorCode() + "event_msg:"
						+ t.getEventMessage().toString() + " err_msg:"
						+ t.getMessage());

		return new ErrorHandlerResult(ErrorHandlerStatus.CONTINUE);
	}

}
