package zte.params.member.req;


import params.ZteRequest;
import zte.params.member.resp.MemberByMobileResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

public class MemberByMobileReq extends ZteRequest<MemberByMobileResp> {

	@ZteSoftCommentAnnotationParam(name="电话号码",type="String",isNecessary="N",desc="电话号码")
	private String phone_no;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(phone_no))CommonTools.addFailError("phone_no不能为空");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.member.getMemberByMobile";
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

}
