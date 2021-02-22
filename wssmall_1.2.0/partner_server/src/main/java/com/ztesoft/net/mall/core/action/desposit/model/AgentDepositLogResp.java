package com.ztesoft.net.mall.core.action.desposit.model;

public class AgentDepositLogResp implements java.io.Serializable{
	
	private String requestId;
	private String unionOrgCode;
	private String result;
	private OperDetail[] operDetail;
	Integer totalPage;
	Integer curPage;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getUnionOrgCode() {
		return unionOrgCode;
	}
	public void setUnionOrgCode(String unionOrgCode) {
		this.unionOrgCode = unionOrgCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public OperDetail[] getOperDetail() {
		return operDetail;
	}
	public void setOperDetail(OperDetail[] operDetail) {
		this.operDetail = operDetail;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

}
