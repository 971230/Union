package com.ztesoft.net.model;

/**
 * 翼支付对账信息
 * @作者 MoChunrun
 * @创建日期 2013-10-22 
 * @版本 V 1.0
 */
public class DepositBill extends BaseBill {

	private String transaction_id;
	private String party_id;
	private String unionOrgCode;
	private String pay_amount;
	private String busi_type;
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getParty_id() {
		return party_id;
	}
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public String getUnionOrgCode() {
		return unionOrgCode;
	}
	public void setUnionOrgCode(String unionOrgCode) {
		this.unionOrgCode = unionOrgCode;
	}
	public String getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	
}
