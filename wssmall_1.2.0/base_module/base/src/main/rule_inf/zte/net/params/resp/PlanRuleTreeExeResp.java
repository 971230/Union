package zte.net.params.resp;

import com.ztesoft.net.auto.rule.fact.AutoFact;

import params.ZteResponse;

public class PlanRuleTreeExeResp extends ZteResponse {

	private AutoFact fact;
	private boolean ruleExecute;

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
	}

	public boolean isRuleExecute() {
		return ruleExecute;
	}

	public void setRuleExecute(boolean ruleExecute) {
		this.ruleExecute = ruleExecute;
	}
	
}
