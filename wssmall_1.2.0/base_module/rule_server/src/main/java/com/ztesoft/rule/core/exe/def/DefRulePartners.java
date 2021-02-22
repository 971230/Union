package com.ztesoft.rule.core.exe.def;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.exe.IRulePartners;
import com.ztesoft.rule.core.module.cfg.BizPlan;


/**
 * 默认规则参与人加载
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefRulePartners implements IRulePartners {

	private IRuleDBAccess ruleDBAccess ;
	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

	@Override
	public List<Map> loadPartners(BizPlan plan) {
		return ruleDBAccess.loadPartners(plan);
	}

	@Override
	public List<Map> loadPartners(BizPlan plan, int pi, int ps) {
		return ruleDBAccess.loadPartners(plan, pi, ps);
	}

	@Override
	public int partnersSize(BizPlan plan) {
		return ruleDBAccess.partnersSize(plan);
	}

	@Override
	public int partnersSize(BizPlan plan , int mold) {
		return ruleDBAccess.partnersSize(plan  , mold);
	}

	@Override
	public List<Map> loadPartners(BizPlan plan, int mold, int pi, int ps) {
		return ruleDBAccess.loadPartners(plan,mold , pi, ps);
	}
	@Override
	public int getPlanEntityCount(String plan_id){
		return ruleDBAccess.getPlanEntityCount(plan_id);
	}
}
