package com.ztesoft.rule.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.exe.runtime.IRuntime;
import com.ztesoft.rule.core.exe.runtime.JitParam;
import com.ztesoft.rule.core.exe.runtime.TimingRunner;

public class RuleActionUtil {
	private static Logger logger = Logger.getLogger(RuleActionUtil.class);
	/**
	 * 执行实时方案规则
	 * @作者 MoChunrun
	 * @创建日期 2014-2-28 
	 * @param rule_code
	 * @param params
	 */
	public static void executePlanRule(String plan_code,Map params)throws Exception{
		if(StringUtils.isEmpty(plan_code))throw new Exception("plan_code is null");
		if(params==null)
			params = new HashMap();
		IRuntime timingRunner = (IRuntime)SpringContextHolder.getBean("directCaller");
		params.put("plan_code", plan_code);
		JitParam jitParam = new JitParam();
		jitParam.setData(params);
		timingRunner.setJitParam(jitParam);
		timingRunner.run();
	}
	
	/**
	 * 启动规则定时任务
	 * @作者 MoChunrun
	 * @创建日期 2014-2-28
	 */
	public static void timerPlanRuleStart(){
		try{
			IRuntime timingRunner = (IRuntime)SpringContextHolder.getBean("timingRunner");
			timingRunner.run();
			logger.info("========================规则定时任务启动成功========================");
		}catch(Exception ex){
			logger.info("========================规则定时任务启动失败========================");
			ex.printStackTrace();
		}
	}
	
	public static void stopTimerPlanRule(){
		TimingRunner.timerFlag = false;
	}
	
}
