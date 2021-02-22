package params.member.resp;

import com.ztesoft.net.app.base.core.model.MemberLv;
import params.ZteResponse;

public class MemberLvByIdResp extends ZteResponse{
	
	private MemberLv memberLv;

	public MemberLv getMemberLv() {
		return memberLv;
	}

	public void setMemberLv(MemberLv memberLv) {
		this.memberLv = memberLv;
	}
	
}
