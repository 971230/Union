package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.GuestBookPageListResp;

public class GuestBookPageListReq extends ZteRequest<GuestBookPageListResp> {

	@ZteSoftCommentAnnotationParam(name="关键字",type="String",isNecessary="N",desc="关键字")
	private String keyword;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="N",desc="第几页 默认为1")
	private Integer pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="N",desc="每页记录数 默认50")
	private Integer pageSize=50;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.guestbook.page";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
