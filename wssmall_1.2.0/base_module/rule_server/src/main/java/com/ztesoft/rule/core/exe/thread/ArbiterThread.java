package com.ztesoft.rule.core.exe.thread;

import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.util.ServiceException;


/**
 * 
 * 
 * 仲裁程序,配合心跳程序运行
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class ArbiterThread extends Thread {
	//10s检测一次
	public static final int CHECK_INTERVAL = 10*1000 ;
	private IRuleDBAccess ruleDBAccess ;
	
	//处理逻辑
	@Override
	public void run(){
		while(true){
			try{
				sleepTimes() ;//间隔1Min
				ruleDBAccess.updateFailRes() ;
			}catch(Exception e ){
				e.printStackTrace() ;
			}
		}
	}


	private void sleepTimes(){
		try {
			Thread.sleep(CHECK_INTERVAL) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new ServiceException(e) ;
		}
	}
	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}


	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}
}
