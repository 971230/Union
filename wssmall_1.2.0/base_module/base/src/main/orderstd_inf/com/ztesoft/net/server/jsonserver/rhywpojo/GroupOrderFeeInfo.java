package com.ztesoft.net.server.jsonserver.rhywpojo;

public class GroupOrderFeeInfo {
	//账本ID
	private String feeID;
	//费用名称
	private String feeDes;
	//应收金额
	private String origFee;
	//减免金额
	private String reliefFee;
	//实收费用
	private String realFee;
	//费用规则id
	private String fee_rule_id;
	
	private String is_aop_fee;
	
	
	public String getIs_aop_fee() {
		return is_aop_fee;
	}
	public void setIs_aop_fee(String is_aop_fee) {
		this.is_aop_fee = is_aop_fee;
	}
	public String getFee_rule_id() {
		return fee_rule_id;
	}
	public void setFee_rule_id(String fee_rule_id) {
		this.fee_rule_id = fee_rule_id;
	}
	public String getFeeID() {
		return feeID;
	}
	public void setFeeID(String feeID) {
		this.feeID = feeID;
	}
	public String getFeeDes() {
		return feeDes;
	}
	public void setFeeDes(String feeDes) {
		this.feeDes = feeDes;
	}
	public String getOrigFee() {
		return origFee;
	}
	public void setOrigFee(String origFee) {
		this.origFee = origFee;
	}
	public String getReliefFee() {
		return reliefFee;
	}
	public void setReliefFee(String reliefFee) {
		this.reliefFee = reliefFee;
	}
	public String getRealFee() {
		return realFee;
	}
	public void setRealFee(String realFee) {
		this.realFee = realFee;
	}
	
	
	
}
