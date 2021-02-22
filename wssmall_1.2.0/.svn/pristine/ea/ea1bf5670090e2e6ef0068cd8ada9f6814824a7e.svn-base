package com.ztesoft.remote.params.adv.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class AdvDeleteReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="广告ID",type="String",isNecessary="Y",desc="aid：广告ID")
	private String aid;
	
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	@Override
	public void check() throws ApiRuleException {
		if(aid == null || "".equals(aid)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "aid：广告ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.delete";
	}

}
