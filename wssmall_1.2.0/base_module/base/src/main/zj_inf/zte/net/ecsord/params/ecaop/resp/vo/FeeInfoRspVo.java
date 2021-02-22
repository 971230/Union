package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class FeeInfoRspVo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "收费项编码，以省分现有编码为准", type = "String", isNecessary = "Y", desc = "feeId")
	private String feeId;
	@ZteSoftCommentAnnotationParam(name = "收费项科目", type = "String", isNecessary = "Y", desc = "feeCategory")
	private String feeCategory;
	@ZteSoftCommentAnnotationParam(name = "收费项描述", type = "String", isNecessary = "Y", desc = "feeDes")
	private String feeDes;
	@ZteSoftCommentAnnotationParam(name = "最大减免金额正整数单位：厘", type = "String", isNecessary = "Y", desc = "maxRelief")
	private Double maxRelief;
	@ZteSoftCommentAnnotationParam(name = "应收费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "origFee")
	private Double origFee;
	
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
}
