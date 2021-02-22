package params.member.resp;

import params.ZteResponse;

import java.util.List;

public class MemberLevelListResp extends ZteResponse{
	
	private List memberLvList;

	public List getMemberLvList() {
		return memberLvList;
	}

	public void setMemberLvList(List memberLvList) {
		this.memberLvList = memberLvList;
	}
	
}
