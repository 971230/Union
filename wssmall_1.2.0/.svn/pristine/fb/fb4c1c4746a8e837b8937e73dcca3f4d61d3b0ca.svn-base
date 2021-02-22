package zte.service;

import org.apache.log4j.Logger;

import com.ztesoft.rule.core.util.RuleActionUtil;

public class RuleTimerStartService{
	private static Logger logger = Logger.getLogger(RuleTimerStartService.class);
	public RuleTimerStartService(){
//		start();
	}

	private void start() {
		logger.info("==========正在开启规则定时任务======================");
		try{
			new Thread(new Runnable() {
				@Override
				public void run() {
					RuleActionUtil.timerPlanRuleStart();
				}
			}).start();
			logger.info("==========开启规则定时任务成功======================");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.info("==========开启规则定时任务失败======================");
		}
	}

}
