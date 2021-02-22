package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.params.req.ZteInstQueryRequest;

public class ActivitySpecReq extends ZteRequest<ZteResponse> {

	@ZteSoftCommentAnnotationParam(name="活动编码activity_code",type="String",isNecessary="Y",desc="活动编码activity_code")
	private String activity_code;

	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	@Override
	public void check() throws ApiRuleException {
		if(activity_code==null || "".equals(activity_code)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "activity_code不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.activitySpec.get";
	}
}
