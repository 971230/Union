package com.ztesoft.rule.core.exe.runtime;

import java.util.List;

import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.exe.thread.HeartbeatThread;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.module.cfg.RetryInfo;
import com.ztesoft.rule.core.util.Const;


/**
 * 重试执行：用于执行重算或者跑失败的单子
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public class RetryLoser  extends DefRuntime {
	
	@Override
	public void run() {
		
		List<RetryInfo> retryInfoList = getRuleConfigs().loadRetryInfo() ;
		
		for(RetryInfo ri : retryInfoList){
			String planCode = ri.getPlan_id();
			ConfigData configData = getRuleConfigs().getConfigData(planCode);
			configData.setJitContextParam(ri.toMap()) ;//设置传递参数,用途：1.加载参与者信息 2.加载中间表数据
			configData.setRunType(getRunType()) ;
			
			IRuleExecutor ruleExecutor = null;
			getRuleExecutorBuilder().setConfigData(configData);
			ruleExecutor = getRuleExecutorBuilder().build();
			ruleExecutor.execute();
		}
	}

	@Override
	public String getRunType() {
		return Const.RUN_TIME_TETRY;
	}

	@Override
	public HeartbeatThread getHeartbeatThread() {
		// TODO Auto-generated method stub
		return null;
	}

}
