package params.member.resp;

import com.ztesoft.net.app.base.core.model.MemberLv;
import params.ZteResponse;

/**
 * 会员等级查询出参
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class MemberLvResp extends ZteResponse {
	
	private MemberLv memberLv;
	
	public MemberLv getMemberLv() {
		return memberLv;
	}

	public void setMemberLv(MemberLv memberLv) {
		this.memberLv = memberLv;
	}

	
}
