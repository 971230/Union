package params.member.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.database.Page;
import params.ZteResponse;

import java.util.List;

/**
 * 用户查询返回参数
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class MemberQryResp extends ZteResponse {

	/**
	 * page的result存放的是Member对像
	 */
	@ZteSoftCommentAnnotationParam(name="会员分页列表",type="String",isNecessary="N",desc="会员分页列表")
	private Page memberPage;
	@ZteSoftCommentAnnotationParam(name="会员列表",type="String",isNecessary="N",desc="会员列表")
	private List memberList;
	@ZteSoftCommentAnnotationParam(name="会员所有信息",type="String",isNecessary="N",desc="按member_id查询时会员所有信息")
	private Member member;
	
	public Page getMemberPage() {
		return memberPage;
	}

	public void setMemberPage(Page memberPage) {
		this.memberPage = memberPage;
	}

	public List getMemberList() {
		return memberList;
	}

	public void setMemberList(List memberList) {
		this.memberList = memberList;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
