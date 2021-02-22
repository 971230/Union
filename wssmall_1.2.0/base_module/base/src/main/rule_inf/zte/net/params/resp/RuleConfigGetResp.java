package zte.net.params.resp;

import params.ZteResponse;

import com.ztesoft.net.auto.rule.vo.PlanRule;

public class RuleConfigGetResp extends ZteResponse {

	private PlanRule rule;

	public PlanRule getRule() {
		return rule;
	}

	public void setRule(PlanRule rule) {
		this.rule = rule;
	}
	
	
}
