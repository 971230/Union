package com.ztesoft.remote.params.adv.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class AdvListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="staff_no：分销商ID")
	private String staff_no;
	
	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.list";
	}

}
