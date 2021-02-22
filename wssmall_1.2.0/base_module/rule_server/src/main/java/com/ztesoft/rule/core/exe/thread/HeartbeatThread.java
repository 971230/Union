package com.ztesoft.rule.core.exe.thread;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.util.Const;
import com.ztesoft.rule.core.util.ServiceException;


/**
 * 
 * 心跳检测程序：
 * 后台有个心跳线程程序，用于做心跳同步处理。
	每间隔1分钟，做一次心跳侦听处理。
	超过2分钟未同步，认为端口或者应用已经挂掉或退出。

 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class HeartbeatThread extends Thread {
	//1分钟,一分钟同步一次
	public static final int HEAT_BEAT_INTERVAL = 60*1000 ;
	//2分钟,默认为无效
	public static final int INTERVAL_THRESHOLD = 120*1000 ;
	
	
    //当前运行进程标识
	private long res_id ;
	
	
	private IRuleDBAccess ruleDBAccess ;
	
	//进程状态,当更新过程出错,则为false,或者退出时,为false
	public static boolean PROC_STATUS = true ;
	
	//处理逻辑
	@Override
	public void run(){
		long threadId = Thread.currentThread().getId() ;
		Map data = new HashMap() ;//inner_id host_name host_ip port
		data.put("inner_id",String.valueOf( threadId)) ;
		data.put("host_name","") ;
		data.put("host_ip","") ;
		data.put("port","80") ;
		
		res_id = ruleDBAccess.saveRes(data) ;
		
		try{
			while(true){
				sleepTimes() ;//间隔1Min
				ruleDBAccess.updateResStatus(res_id , Const.VALID_STATUS) ;
			}
		}catch(Exception e ){
			e.printStackTrace() ;
			ruleDBAccess.updateResStatus(res_id , Const.INVALID_STATUS) ;
		}finally{
			PROC_STATUS = false ;
		}
	}


	private void sleepTimes(){
		try {
			Thread.sleep(HEAT_BEAT_INTERVAL) ;
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


	public long getRes_id() {
		return res_id;
	}


	public void setRes_id(long res_id) {
		this.res_id = res_id;
	}
}
