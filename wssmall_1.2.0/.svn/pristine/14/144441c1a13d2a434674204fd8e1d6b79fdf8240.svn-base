package com.ztesoft.rule.core.exe.runtime;

import java.util.Map;

import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.exe.thread.HeartbeatThread;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.util.Const;

/**
 * 
 * 直接调用：用于由应用程序指定调用的业务方案
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public class DirectCaller extends DefRuntime {

	

	@Override
	/**
	 * 
	 * Object param 为Map类型,其中必然有{plan_code:xx}属性
	 * 
	 */
	public void run() {
		Map mParam = jitParam.getData() ;
		
		String planCode = (String) mParam.get("plan_code");
		ConfigData configData = getRuleConfigs().getConfigData(planCode);
		configData.setJitContextParam(mParam) ;//设置传递参数,用途：1.加载参与者信息 2.加载中间表数据
		configData.setRunType(getRunType()) ;
		
		getRuleExecutorBuilder().setConfigData(configData);
		IRuleExecutor ruleExecutor = getRuleExecutorBuilder().build();
		ruleExecutor.execute();
	}

	@Override
	public String getRunType() {
		return Const.RUN_TIME_DIRECT ;
	}

	@Override
	public HeartbeatThread getHeartbeatThread() {
		// TODO Auto-generated method stub
		return null;
	}

}
