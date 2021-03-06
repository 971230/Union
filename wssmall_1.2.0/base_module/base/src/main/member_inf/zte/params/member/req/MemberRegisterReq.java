package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.database.NotDbField;
import params.ZteRequest;
import zte.params.member.resp.MemberRegisterResp;

public class MemberRegisterReq extends ZteRequest<MemberRegisterResp> {

	@ZteSoftCommentAnnotationParam(name="注册会员信息",type="String",isNecessary="Y",desc="注册会员信息",hasChild=true)
	private Member member;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
/*		if(StringUtil.isEmpty(this.userSessionId))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户session不能为空，请传入http request session值"));*/
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.member.register";
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
