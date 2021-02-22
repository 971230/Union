package com.ztesoft.rule.core.exe.runtime;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.timer.CheckTimerServer;
import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.exe.thread.HeartbeatThread;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.util.Const;
import com.ztesoft.rule.core.util.ThreadUtil;

/**
 * 
 * 定时执行：用于处理定时执行的业务方案
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public class TimingRunner extends DefRuntime {
	private static Logger logger = Logger.getLogger(TimingRunner.class);
	private HeartbeatThread heartbeatThread ;
	
	//无数据,默认睡眠5分钟
	public static final long defal_sleep_times = 10*1000 ;//5*60*1000 ;
	
	//状态监测时间间隔,默认睡眠10s
	public static final long status_check_times = 10*1000 ;
	
	public static boolean timerFlag = true;
	
	
	@Override
	/**
	 * 执行规则
	 */
	public void run() {
//		long resId = heartbeatThread.getRes_id() ;
		long resId = 900 ;
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
  			return ;
		while (timerFlag) {//遍历处理
			try{
				
				logger.info("2---------") ;
				ConfigData configData = getRuleConfigs().apply(resId);
				logger.info("configData---------"+configData) ;
				if(configData == null ){//无数据,默认睡眠5分钟
					ThreadUtil.sleepTimes(defal_sleep_times);
					continue ;
				}
				
				configData.setRunType(getRunType());
				
				getRuleExecutorBuilder().setConfigData(configData);
				IRuleExecutor ruleExecutor = getRuleExecutorBuilder().build();
				ruleExecutor.execute();
				
				//子线程未处理完毕,继续等待
				while(!configData.isFinshed()){
					ThreadUtil.sleepTimes(status_check_times);
				}
				logger.info("1---------") ;
			}catch(Throwable t){
				t.printStackTrace() ;
			}
		}
		
	}

	@Override
	public String getRunType() {
		return Const.RUN_TIME_TIMMING;
	}

	@Override
	public HeartbeatThread getHeartbeatThread() {
		return heartbeatThread;
	}

}
