package com.ztesoft.rule.core.exe.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.module.cfg.ConfigData;

/**
 * 方案下参与者执行
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class PartnerThread  implements Runnable {
	private static Logger logger = Logger.getLogger(PartnerThread.class);
	private CyclicBarrier barrier;
	private ConfigData  configData ;
	private IRuleExecutor ruleExecutor ;
	private int mold ;
//	
//	
	public PartnerThread(int mold ,CyclicBarrier barrier ,IRuleExecutor ruleExecutor ,ConfigData configData) {
		this.mold = mold ;
		this.barrier = barrier;
		this.ruleExecutor = ruleExecutor ;
		this.configData = configData ;
	}

	@Override
	public void run() {
		logger.info("run---"+mold);  
		ruleExecutor.coreProcess(configData , mold ) ;
		
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		//logger.info(Thread.currentThread().getName() + ":终于可以吃饭啦！");

	}
}
