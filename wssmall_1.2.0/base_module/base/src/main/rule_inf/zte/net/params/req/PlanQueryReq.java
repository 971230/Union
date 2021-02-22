package zte.net.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.net.params.resp.PlanQueryResp;

public class PlanQueryReq extends ZteRequest<PlanQueryResp> {

	@ZteSoftCommentAnnotationParam(name="方案ID",type="String",isNecessary="Y",desc="方案ID")
	private String plan_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(plan_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "plan_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.query";
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

}
