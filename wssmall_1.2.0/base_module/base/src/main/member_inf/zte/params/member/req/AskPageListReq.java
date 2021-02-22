package zte.params.member.req;

import java.util.Date;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.member.resp.AskPageListResp;

public class AskPageListReq extends ZteRequest<AskPageListResp> {

	@ZteSoftCommentAnnotationParam(name="问题关键字",type="String",isNecessary="N",desc="问题关键字")
	private String keyword;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="开始时间")
	private Date startTime;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="结束时间")
	private Date endTime;
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
		return "zte.memberService.ask.page";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
