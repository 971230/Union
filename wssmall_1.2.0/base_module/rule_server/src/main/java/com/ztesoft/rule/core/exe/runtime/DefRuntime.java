package com.ztesoft.rule.core.exe.runtime;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.exe.IRuleExecutorBuilder;
import com.ztesoft.rule.core.exe.thread.HeartbeatThread;
import com.ztesoft.rule.core.util.LocalBeanFactory;


/**
 * 执行类型接口：
 * 1.定时执行,实现类为TimingRunner,执行
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public abstract class DefRuntime implements IRuntime{
	JitParam jitParam ;
	
	private IRuleConfigs ruleConfigs;
	private IRuleExecutorBuilder ruleExecutorBuilder;

//	public void setRuleConfigs(IRuleConfigs ruleConfigs) {
//		this.ruleConfigs = ruleConfigs;
//	}


//	public void setRuleExecutorBuilder(IRuleExecutorBuilder ruleExecutorBuilder) {
//		this.ruleExecutorBuilder = ruleExecutorBuilder;
//	}
	
	public JitParam getJitParam() {
		return jitParam;
	}
	@Override
	public void setJitParam(JitParam jitParam) {
		this.jitParam = jitParam;
	}
	
	@Override
	public IRuleConfigs getRuleConfigs() {
		if(ruleConfigs == null ){
			if(jitParam == null || jitParam.getRuleConfigsClass() == null ){
				ruleConfigs = SpringContextHolder.getBean("defRuleConfigs");
			}else{
				ruleConfigs = LocalBeanFactory.createRuleConfigs(jitParam.getRuleConfigsClass());
			}
		}
		
		return ruleConfigs ;
	}
	
	@Override
	public IRuleExecutorBuilder getRuleExecutorBuilder() {
		if(ruleExecutorBuilder == null ){
			if(jitParam==null || jitParam.getRuleConfigsClass() == null ){
				ruleExecutorBuilder = SpringContextHolder.getBean("defRuleExecutorBuilder");
			}else{
				ruleExecutorBuilder = LocalBeanFactory.createRuleExecutorBuilder(jitParam.getRuleExecutorBuilderClass());
			}
			ruleExecutorBuilder.setRuleConfigs(getRuleConfigs()) ;
		}
		
		return ruleExecutorBuilder ;
	}
	
	public abstract String getRunType() ;
	
	public abstract HeartbeatThread getHeartbeatThread() ;
}
