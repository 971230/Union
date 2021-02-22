package zte.params.member.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;

public class MemberByMobileResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="会员信息",type="String",isNecessary="N",desc="会员信息")
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
