package zte.net.params.resp;

import params.ZteResponse;

import com.ztesoft.net.auto.rule.fact.AutoFact;

public class CataloguePlanExeResp extends ZteResponse {

	private AutoFact fact;

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
	}
	
}
