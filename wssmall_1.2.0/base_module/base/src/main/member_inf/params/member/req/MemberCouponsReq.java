package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.member.resp.MemberCouponsResp;

/**
 * 会员优惠券查询
 * 
 */
public class MemberCouponsReq extends ZteRequest<MemberCouponsResp> {
	
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="N",desc="默认当前登陆的会员")
	private String member_id;

	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页")
	private int pageNo;
	
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="分页大小")
	private int pageSize;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "zte.memberServer.member.qryMemberCouponsPage";
	}
}
