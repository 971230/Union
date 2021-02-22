package com.ztesoft.mmt.guide.asyn;

import org.apache.log4j.Logger;

import com.lmax.disruptor.ExceptionHandler;

public class AsynServiceException  implements ExceptionHandler {
	private static Logger logger = Logger.getLogger(AsynServiceException.class);
	@Override
	public void handleEventException(Throwable ex, long seq, Object obj) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		logger.info("seq=" + seq + ",obj=" + obj);
		
		
	}

	@Override
	public void handleOnShutdownException(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleOnStartException(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

}
