package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

import zte.net.ecsord.params.zb.vo.Par;

public class TradeQueryRefundInfoVO {

	private String refundAmount;
	private String payTypeId;
	private String refundSerailNo;
	private List<Par> para;
	
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getRefundSerailNo() {
		return refundSerailNo;
	}
	public void setRefundSerailNo(String refundSerailNo) {
		this.refundSerailNo = refundSerailNo;
	}
	public List<Par> getPara() {
		return para;
	}
	public void setPara(List<Par> para) {
		this.para = para;
	} 
}
