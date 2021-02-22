package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.CommentPageListResp;

public class CommentPageListReq extends ZteRequest<CommentPageListResp> {

	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="用户ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="N",desc="第几页 默认为1")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="N",desc="每页记录数 默认50")
	private int pageSize=50;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.comment.page";
	}

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

}
