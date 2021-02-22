package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.MemberPwdModifyResp;

public class MemberPwdModifyReq extends ZteRequest<MemberPwdModifyResp> {

	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="用户ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="原密码",type="String",isNecessary="Y",desc="原密码")
	private String oldPwd;
	@ZteSoftCommentAnnotationParam(name="新密码",type="String",isNecessary="Y",desc="新密码")
	private String newPwd;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.password.modify";
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
