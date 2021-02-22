package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

import zte.net.ecsord.params.zb.vo.Par;

public class TradeQueryPayInfoVO {

	private String payAmount;
	private String payTypeId;
	private String paySerailNo;
	private List<Par> para;
	
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getPaySerailNo() {
		return paySerailNo;
	}
	public void setPaySerailNo(String paySerailNo) {
		this.paySerailNo = paySerailNo;
	}
	public List<Par> getPara() {
		return para;
	}
	public void setPara(List<Par> para) {
		this.para = para;
	}
}
