package com.ztesoft.net.mall.core.action.desposit.model;

public class AgentDepositRequDto implements java.io.Serializable{
	private  String amount ; 
	private  String busiType  ;
	private  String requestId ;
	private  String requestTime  ;
	private  String sysType;
	private  String unionOrgCode;
	 
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getSysType() {
		return sysType;
	}
	public void setSysType(String sysType) {
		this.sysType = sysType;
	}
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
	
}
