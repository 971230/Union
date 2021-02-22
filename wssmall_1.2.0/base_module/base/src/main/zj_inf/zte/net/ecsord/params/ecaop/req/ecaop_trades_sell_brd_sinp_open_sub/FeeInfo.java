package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class FeeInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "收费项描述", type = "String", isNecessary = "Y", desc = "收费项描述")
	private String feeDes;

	@ZteSoftCommentAnnotationParam(name = "减免原因", type = "String", isNecessary = "N", desc = "减免原因")
	private String reliefResult;

	@ZteSoftCommentAnnotationParam(name = "收费项科目", type = "String", isNecessary = "Y", desc = "收费项科目")
	private String feeCategory;

	@ZteSoftCommentAnnotationParam(name = "收费项编码，以省分现有编码为准", type = "String", isNecessary = "Y", desc = "收费项编码，以省分现有编码为准")
	private String feeId;

	@ZteSoftCommentAnnotationParam(name = "减免金额，单位：厘", type = "String", isNecessary = "Y", desc = "减免金额，单位：厘")
	private String reliefFee;

	@ZteSoftCommentAnnotationParam(name = "应收费用正整数，单位：厘", type = "String", isNecessary = "Y", desc = "应收费用正整数，单位：厘")
	private String origFee;

	@ZteSoftCommentAnnotationParam(name = "自动算费－N，手工算费－Y", type = "String", isNecessary = "Y", desc = "自动算费－N，手工算费－Y")
	private String calculateTag;

	@ZteSoftCommentAnnotationParam(name = "实收金额，单位：厘", type = "String", isNecessary = "Y", desc = "实收金额，单位：厘")
	private String realFee;

	public String getFeeDes() {
		return this.feeDes;
	}

	public void setFeeDes(String v) {
		this.feeDes = v;
	}

	public String getReliefResult() {
		return this.reliefResult;
	}

	public void setReliefResult(String v) {
		this.reliefResult = v;
	}

	public String getFeeCategory() {
		return this.feeCategory;
	}

	public void setFeeCategory(String v) {
		this.feeCategory = v;
	}

	public String getFeeId() {
		return this.feeId;
	}

	public void setFeeId(String v) {
		this.feeId = v;
	}

	public String getReliefFee() {
		return this.reliefFee;
	}

	public void setReliefFee(String v) {
		this.reliefFee = v;
	}

	public String getOrigFee() {
		return this.origFee;
	}

	public void setOrigFee(String v) {
		this.origFee = v;
	}

	public String getCalculateTag() {
		return this.calculateTag;
	}

	public void setCalculateTag(String v) {
		this.calculateTag = v;
	}

	public String getRealFee() {
		return this.realFee;
	}

	public void setRealFee(String v) {
		this.realFee = v;
	}

}
