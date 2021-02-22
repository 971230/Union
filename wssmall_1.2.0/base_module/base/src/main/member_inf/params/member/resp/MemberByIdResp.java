package params.member.resp;

import com.ztesoft.net.app.base.core.model.Member;
import params.ZteResponse;

public class MemberByIdResp extends ZteResponse{
	
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
