package zte.params.member.req;


import params.ZteRequest;
import zte.params.member.resp.MemberIsExistsResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

public class MemberIsExistsReq extends ZteRequest<MemberIsExistsResp> {

	@ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="N",desc="用户名")
	private String uname;
	@ZteSoftCommentAnnotationParam(name="电话号码",type="String",isNecessary="N",desc="电话号码")
	private String phone_no;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(uname) && ApiUtils.isBlank(phone_no))CommonTools.addFailError("uname 与 phone_no不能同时为空");
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.member.isexists";
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

}
