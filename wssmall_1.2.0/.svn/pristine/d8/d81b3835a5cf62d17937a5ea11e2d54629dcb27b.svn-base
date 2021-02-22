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

public class PlanGetReq extends ZteRequest<PlanQueryResp> {

	@ZteSoftCommentAnnotationParam(name="环节编码",type="String",isNecessary="Y",desc="环节编码")
	private String tache_code;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(tache_code))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "tache_code不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.getByTache";
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	
}
