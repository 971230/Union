package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Fee implements Serializable{

	@ZteSoftCommentAnnotationParam(name="收费项编码",type="String",isNecessary="Y",desc="FeeID：收费项编码")
	private String FeeID;
	
	private String FeeId;

	@ZteSoftCommentAnnotationParam(name="收费项科目",type="String",isNecessary="Y",desc="FeeDes：收费项科目")
	private String FeeCategory;
	
	@ZteSoftCommentAnnotationParam(name="收费项描述",type="String",isNecessary="Y",desc="FeeDes：收费项描述")
	private String FeeDes;

	@ZteSoftCommentAnnotationParam(name="应收金额",type="String",isNecessary="Y",desc="OrigFee：单位为厘")
	private double OrigFee;

	@ZteSoftCommentAnnotationParam(name="减免金额",type="String",isNecessary="Y",desc="ReliefFee：单位为厘")
	private double ReliefFee;

	@ZteSoftCommentAnnotationParam(name="最大减免金额",type="String",isNecessary="Y",desc="MaxRelief：单位为厘")
	private double MaxRelief;

	@ZteSoftCommentAnnotationParam(name="减免原因",type="String",isNecessary="N",desc="ReliefResult：减免原因")
	private String ReliefResult;

	@ZteSoftCommentAnnotationParam(name="实收金额",type="String",isNecessary="Y",desc="RealFee：单位为厘")
	private double RealFee;

	public String getFeeID() {
		return FeeID;
	}

	public void setFeeID(String feeID) {
		FeeID = feeID;
	}

	public String getFeeId() {
		return FeeId;
	}

	public void setFeeId(String feeId) {
		FeeId = feeId;
	}

	public String getFeeDes() {
		return FeeDes;
	}

	public String getFeeCategory() {
		return FeeCategory;
	}

	public void setFeeCategory(String feeCategory) {
		FeeCategory = feeCategory;
	}

	public double getMaxRelief() {
		return MaxRelief;
	}

	public void setMaxRelief(double maxRelief) {
		MaxRelief = maxRelief;
	}

	public void setFeeDes(String feeDes) {
		FeeDes = feeDes;
	}

	public double getOrigFee() {
		return OrigFee;
	}

	public void setOrigFee(double origFee) {
		OrigFee = origFee;
	}

	public double getReliefFee() {
		return ReliefFee;
	}

	public void setReliefFee(double reliefFee) {
		ReliefFee = reliefFee;
	}

	public String getReliefResult() {
		return ReliefResult;
	}

	public void setReliefResult(String reliefResult) {
		ReliefResult = reliefResult;
	}

	public double getRealFee() {
		return RealFee;
	}

	public void setRealFee(double realFee) {
		RealFee = realFee;
	}
	
}
