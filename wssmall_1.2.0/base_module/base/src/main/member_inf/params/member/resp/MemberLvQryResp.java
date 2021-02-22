package params.member.resp;

import com.ztesoft.net.app.base.core.model.MemberLv;
import params.ZteResponse;

import java.util.List;

/**
 * 会员等级查询出参
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class MemberLvQryResp extends ZteResponse {

	private List<MemberLv> memberLvList;

	public List<MemberLv> getMemberLvList() {
		return memberLvList;
	}

	public void setMemberLvList(List<MemberLv> memberLvList) {
		this.memberLvList = memberLvList;
	}
	
}
