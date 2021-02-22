package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

public class OpenDealApplyFeeInfoVo implements Serializable {

	private String feeId; // Y String(20) 收费项编码，以省分现有编码为准
	private String feeCategory; // Y String(1) 收费项科目
	private String feeDes; // Y String(120) 收费项描述
	private Double maxRelief; // N String(10) 最大减免金额正整数单位：厘
	private Double origFee; // Y String(10) 应收费用正整数单位：厘
//	private Double totalFee; // Y String(10) 总费用正整数单位：厘

	public String getFeeId() {
		return feeId;
	}

	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}

	public String getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}

	public String getFeeDes() {
		return feeDes;
	}

	public void setFeeDes(String feeDes) {
		this.feeDes = feeDes;
	}

	public Double getMaxRelief() {
		return maxRelief;
	}

	public void setMaxRelief(Double maxRelief) {
		this.maxRelief = maxRelief;
	}

	public Double getOrigFee() {
		return origFee;
	}

	public void setOrigFee(Double origFee) {
		this.origFee = origFee;
	}

//	public Double getTotalFee() {
//		return totalFee;
//	}
//
//	public void setTotalFee(Double totalFee) {
//		this.totalFee = totalFee;
//	}

}
