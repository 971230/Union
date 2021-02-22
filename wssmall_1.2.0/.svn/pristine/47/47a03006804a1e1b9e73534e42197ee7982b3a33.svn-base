package zte.net.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.net.params.resp.RuleExeLogDelResp;

public class RuleExeLogDelReq extends ZteRequest<RuleExeLogDelResp> {
	
	private String[] plan_id;
	private String rule_id;
	private String obj_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(obj_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "obj_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.rule.exe.log.del";
	}

	public String[] getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String[] plan_id) {
		this.plan_id = plan_id;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

}
