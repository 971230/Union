package com.ztesoft.remote.params.article.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class DelArticleReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="工号",type="String",isNecessary="Y",desc="工号")
	private String staff_no;

	@Override
	public void check() throws ApiRuleException {

		if (ApiUtils.isBlank(staff_no)) {
			throw new ApiRuleException("-1", "工号【staff_no】不能为空!");
		}
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.article.delArticleByStaffNo";
	}

	public String getStaff_no() {
		return staff_no;
	}

	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}

}
