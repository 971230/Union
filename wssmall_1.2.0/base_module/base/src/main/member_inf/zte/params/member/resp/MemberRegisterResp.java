package zte.params.member.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;

import params.ZteResponse;

public class MemberRegisterResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="注册会员信息",type="String",isNecessary="Y",desc="注册会员信息",hasChild=true)
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
