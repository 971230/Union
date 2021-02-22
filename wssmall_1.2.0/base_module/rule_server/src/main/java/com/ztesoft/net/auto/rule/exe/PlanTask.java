package com.ztesoft.net.auto.rule.exe;

import java.util.concurrent.Callable;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.Plan;

public class PlanTask implements Callable{

	private Plan plan;
	private AutoFact fact;
	private IAutoRuleCaller caller;
	private IAutoRule autoRuleImpl;
	private RuleThreadStatus threadStatus;

	public PlanTask(Plan plan, AutoFact fact, IAutoRuleCaller caller,
			IAutoRule autoRuleImpl) {
		super();
		this.plan = plan;
		this.fact = fact;
		this.caller = caller;
		this.autoRuleImpl = autoRuleImpl;
	}

	@Override
	public Object call() throws Exception {
		//threadStatus.setCheck_auto_local(false);
		//threadStatus.setAllmutexrule(false);
		if(!autoRuleImpl.isPlanHasRuleExecute(plan.getCatalogue_id(),plan.getPlan_id(), fact.getObj_id())){
			//如果没有方案被执行过则执行方案
			RuleThreadStatus rs = new RuleThreadStatus();
			rs.setAllmutexrule(true);
			caller.exePlan(plan.getPlan_id(), fact, false, false, 0,false,rs,null);
			rs.setAllmutexrule(false);
			return false;
		}else{
			//如果已经执行了 则不等其它线程的执行结果
			return true;
		}
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
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
