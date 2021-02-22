package com.ztesoft.net.auto.rule.exe;

import java.util.concurrent.Callable;

import org.springframework.beans.BeanUtils;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;

/**
 * 规则执行任务
 * @作者 MoChunrun
 * @创建日期 2014-11-27 
 * @版本 V 1.0
 */
public class RuleTask implements Callable{
	
	private PlanRule rule;
	private AutoFact fact;
	private IAutoRuleCaller caller;
	private IAutoRule autoRuleImpl;
	private RuleThreadStatus threadStatus;

	public RuleTask(PlanRule rule, AutoFact fact,IAutoRuleCaller caller,IAutoRule autoRuleImpl) {
		this.rule = rule;
		this.fact = fact;
		this.caller = caller;
		this.autoRuleImpl = autoRuleImpl;
	}

	@Override
	public Object call() throws Exception {
		//threadStatus.setCheck_auto_local(false);
		threadStatus.setAllmutexrule(false);
		RuleThreadStatus rs = new RuleThreadStatus();
		BeanUtils.copyProperties(threadStatus, rs);
		//设置线程变量
		
		if(!autoRuleImpl.hasAllMutexRuleExe(rule.getPid(), rule.getPlan_id(), fact.getObj_id(),rule.getRule_id())){
			RuleExeLog log = caller.getRuleExecuteLog(rule.getPlan_id(),rule.getRule_id(), fact.getObj_id());
			boolean flag = caller.exeRuleTree(rule,fact,log,false,rs);
			return flag;
		}else{
			//不执行当前规则
			return false;
		}
	}

	public PlanRule getRule() {
		return rule;
	}

	public void setRule(PlanRule rule) {
		this.rule = rule;
	}

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
	}

	public IAutoRuleCaller getCaller() {
		return caller;
	}

	public void setCaller(IAutoRuleCaller caller) {
		this.caller = caller;
	}

	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}

	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}

	public RuleThreadStatus getThreadStatus() {
		return threadStatus;
	}

	public void setThreadStatus(RuleThreadStatus threadStatus) {
		this.threadStatus = threadStatus;
	}

}
