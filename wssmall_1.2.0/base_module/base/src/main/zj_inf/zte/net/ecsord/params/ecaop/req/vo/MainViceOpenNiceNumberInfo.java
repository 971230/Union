package zte.net.ecsord.params.ecaop.req.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenNiceNumberInfo {
	
	@ZteSoftCommentAnnotationParam(name = "号码等级", type = "String", isNecessary = "Y", desc = "")
	private String classId;
	@ZteSoftCommentAnnotationParam(name = "号码预存:分", type = "String", isNecessary = "Y", desc = "")
	private String advancePay;
	@ZteSoftCommentAnnotationParam(name = "靓号最低消费：厘（classid不为9的时候，必传）", type = "String", isNecessary = "Y", desc = "")
	private String lowFee;
	@ZteSoftCommentAnnotationParam(name = "靓号协议时长（classid不为9的时候，必传）", type = "String", isNecessary = "Y", desc = "")
	private String protocolTime;
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getAdvancePay() {
		return advancePay;
	}
	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}
	public String getLowFee() {
		return lowFee;
	}
	public void setLowFee(String lowFee) {
		this.lowFee = lowFee;
	}
	public String getProtocolTime() {
		return protocolTime;
	}
	public void setProtocolTime(String protocolTime) {
		this.protocolTime = protocolTime;
	}


}
