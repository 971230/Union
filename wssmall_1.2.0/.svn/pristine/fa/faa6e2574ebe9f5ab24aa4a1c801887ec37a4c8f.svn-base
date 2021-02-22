package zte.net.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.params.resp.RuleConfigGetResp;

public class RuleConfigGetReq extends ZteRequest<RuleConfigGetResp> {

	private String rule_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.ruleconfig.get.id";
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

}
