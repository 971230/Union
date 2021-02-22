package com.ztesoft.inf.framework.logger;


public class AppLogContext {

	public static final int TIME_OUT = 5 * 1000; 

	private AppLogger appLogger;
	
	private Object logObj;
	
	public AppLogContext(){
	}
	
	public AppLogContext(AppLogger appLogger,Object logObj) {
		this.appLogger = appLogger;
		this.logObj = logObj;
	}

	public AppLogger getAppLogger() {
		return appLogger;
	}

	public void setAppLogger(AppLogger appLogger) {
		this.appLogger = appLogger;
	}

	public Object getLogObj() {
		return logObj;
	}

	public void setLogObj(Object logObj) {
		this.logObj = logObj;
	}

	public void logToDB() throws Exception {
	/*	TimeOutException timeOutException = new TimeOutException("记录日志超时:log="+logObj);
		TimeOutThread timeOutThread = new TimeOutThread(TIME_OUT, timeOutException);
		timeOutThread.start();*/
		appLogger.log(logObj);
		//timeOutThread.cancel();
	}
	
}
