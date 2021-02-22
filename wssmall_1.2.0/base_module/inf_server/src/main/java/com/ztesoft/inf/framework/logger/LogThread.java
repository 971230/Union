package com.ztesoft.inf.framework.logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.ztesoft.inf.framework.commons.TransactionalBeanManager;

public class LogThread extends Thread {

	public static final int TIME_OUT = 5 * 1000;
	
	private static final int MAX_QUEUE_SIZE =500;

	private static final Logger logger = Logger.getLogger(LogThread.class);

	private static  Queue<AppLogContext> logQueue = new ConcurrentLinkedQueue<AppLogContext>();

	private volatile static boolean shutdownRequested = false;

	public volatile static boolean START_UP = false;

	static {
		if (!LogThread.isStartUP()) {
			LogThread.startUP();
		}
	}
	private LogThread() {
	}

	@Override
	public void run() {
		try {
			while (!shutdownRequested) {
				logToDB(getLog());
			}
		} finally {
			doShutdown();
		}
	}

	public static void addLogQueue(AppLogContext obj) {
		//logger.info(" MAX_QUEUE_SIZE = " + logQueue.size());
//		if (logQueue.size() >= MAX_QUEUE_SIZE) {
//			final AppLogContext logObj = obj;
//			new Thread(new Runnable() {
//				public void run() {
//					logToDB(logObj);
//				}
//			}).start();
//		} else {
			logQueue.add(obj);
//		}
	}


	private static AppLogContext getLog() {
		while (logQueue.isEmpty()) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		AppLogContext obj = logQueue.poll();
		//logger.info(" AF getLog QUEUE_SIZE = " + logQueue.size());
		return obj;
	}


	private static void logToDB(AppLogContext obj) {
		try {
			if(obj==null) {
				return;
			}
			AppLogContext appLogContext = TransactionalBeanManager.createTransactional(AppLogContext.class);
			appLogContext.setAppLogger(obj.getAppLogger());
			appLogContext.setLogObj(obj.getLogObj());
			appLogContext.logToDB();
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.info(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
		}
	}
	/**
	 * 队列中日志数量超过阀值后进行写表操作
	 * 
	 * *//*
	private synchronized static  void  logToDB(boolean forced){
		//没有达到最大数量不写rizh
		if (!forced&&logQueue.size() <= MAX_QUEUE_SIZE) {
			return ;
		} 
		boolean useXa = false;
		boolean commit = false;
		UserTransaction usTran = null;
		try {	
			if (useXa) {
				usTran = SysSet.getUserTransaction();
				SysSet.tpBegin(usTran);
			}
			for(int i=0;i<=MAX_QUEUE_SIZE;i++){
				AppLogContext appLogContext=getLog();
				try{
					if(appLogContext==null){
						continue;
					}
					appLogContext.logToDB();//异常catch掉避免因为某一条日志信息导致所有的日志不能提交数据库
				}catch (Throwable e) {
				}
			}
			commit = true;
		} catch (Exception e) {
			logger.info(e.getMessage());
		} catch (Throwable e) {
			logger.info(e.getMessage());
		} finally {
			try {
				if (commit) {
					if (useXa)
						SysSet.tpCommit(usTran);
					else
						ConnectionContext.getContext().allCommit();
				} else {
					if (useXa)
						SysSet.tpRollback(usTran);
					else
						ConnectionContext.getContext().allRollback();
				}
				if (usTran != null) {
					logger.debug("关闭UserTransaction");
					usTran = null;
				}
			} catch (Exception e) {
				logger.info("关闭数据库连接时，出现异常" + LogHelper.getStackMsg(e));
			}finally{
				try {
					ConnectionContext.getContext().allCloseConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	*/
	
	
	

	public boolean isShutdownRequested() {
		return shutdownRequested;
	}

	public void shutdown() {
//		logToDB(true);
		
		shutdownRequested = true;
//		this.interrupt();
	}

	public synchronized static boolean isStartUP() {
		return START_UP;
	}

	public synchronized static void startUP() {
		if (START_UP)
			return;
		START_UP = true;
		LogThread logThread = new LogThread();
		logThread.setPriority(Thread.MIN_PRIORITY);
		logThread.start();
	}

	private void doShutdown() {	
		logger.info(this.getName() + " is ShutDowned!");
	}
}
