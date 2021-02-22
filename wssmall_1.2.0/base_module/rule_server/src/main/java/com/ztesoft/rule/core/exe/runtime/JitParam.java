package com.ztesoft.rule.core.exe.runtime;

import java.util.Map;

import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.exe.IRuleExecutorBuilder;
import com.ztesoft.rule.core.ext.IPlanCtrl;


/**
 * Runtime入参
 * @author easonwu 
 * @creation Dec 18, 2013
 * 
 */
public class JitParam {
	//如没提供，则用默认【对象构造】
	private Class<? extends IRuleConfigs> ruleConfigsClass ;
	//如没提供，则用默认【对象构造】
	private Class<? extends IRuleExecutorBuilder> ruleExecutorBuilderClass ;
	//如没提供，则用默认【对象构造】
	private Class<? extends IPlanCtrl> planCtrl ;
	
	//传递的参数对象
	private Map data ;
	
	
	public Map getData() {
		return data;
	}
	
	public void setData(Map data) {
		this.data = data;
	}
	
	public Class<? extends IRuleConfigs> getRuleConfigsClass() {
		return ruleConfigsClass;
	}
	public void setRuleConfigsClass(Class<? extends IRuleConfigs> ruleConfigsClass) {
		this.ruleConfigsClass = ruleConfigsClass;
	}
	public Class<? extends IRuleExecutorBuilder> getRuleExecutorBuilderClass() {
		return ruleExecutorBuilderClass;
	}
	public void setRuleExecutorBuilderClass(
			Class<? extends IRuleExecutorBuilder> ruleExecutorBuilderClass) {
		this.ruleExecutorBuilderClass = ruleExecutorBuilderClass;
	}
	public Class<? extends IPlanCtrl> getPlanCtrl() {
		return planCtrl;
	}
	public void setPlanCtrl(Class<? extends IPlanCtrl> planCtrl) {
		this.planCtrl = planCtrl;
	}
	
}
