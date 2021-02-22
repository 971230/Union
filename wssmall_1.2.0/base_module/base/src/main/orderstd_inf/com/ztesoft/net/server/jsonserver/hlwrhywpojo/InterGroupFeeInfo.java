package com.ztesoft.net.server.jsonserver.hlwrhywpojo;

public class InterGroupFeeInfo {
	private String FeeDes;//费用名称  Bss: fee_desc
	private String FeeID;//账本ID  subject_id
	private String OrigFee;//应收费用  need_amount
	private String ReliefFee;//减免费用  deration_amount
	private String RealFee;//实收费用  real_amount
	private String fee_fule_id ;//费用规则id  fee_rule_id
	private String is_aop_fee ;
	
	
	public String getIs_aop_fee() {
		return is_aop_fee;
	}
	public void setIs_aop_fee(String is_aop_fee) {
		this.is_aop_fee = is_aop_fee;
	}
	public String getFeeDes() {
		return FeeDes;
	}
	public void setFeeDes(String feeDes) {
		FeeDes = feeDes;
	}
	public String getFeeID() {
		return FeeID;
	}
	public void setFeeID(String feeID) {
		FeeID = feeID;
	}
	public String getOrigFee() {
		return OrigFee;
	}
	public void setOrigFee(String origFee) {
		OrigFee = origFee;
	}
	public String getReliefFee() {
		return ReliefFee;
	}
	public void setReliefFee(String reliefFee) {
		ReliefFee = reliefFee;
	}
	public String getRealFee() {
		return RealFee;
	}
	public void setRealFee(String realFee) {
		RealFee = realFee;
	}
	public String getFee_fule_id() {
		return fee_fule_id;
	}
	public void setFee_fule_id(String fee_fule_id) {
		this.fee_fule_id = fee_fule_id;
	}
}
