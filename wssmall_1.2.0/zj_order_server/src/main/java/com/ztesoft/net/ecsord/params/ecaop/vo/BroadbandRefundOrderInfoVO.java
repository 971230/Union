package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BroadbandRefundOrderInfoVO {

	@ZteSoftCommentAnnotationParam(name = "订单编码", type = "String", isNecessary = "Y", desc = "订单编码")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "订单描述", type = "String", isNecessary = "Y", desc = "订单描述")
	private String orderDesc;
	@ZteSoftCommentAnnotationParam(name = "订单金额（分）", type = "Number", isNecessary = "Y", desc = "订单金额（分）")
	private String orderPrice;
	@ZteSoftCommentAnnotationParam(name = "BSS缴费流水（BSS专用）", type = "String", isNecessary = "Y", desc = "BSS缴费流水（BSS专用）")
	private String BssPaySerialNo;
	@ZteSoftCommentAnnotationParam(name = "费用信息", type = "Number", isNecessary = "Y", desc = "费用信息")
	private List<BroadbandRefundFeeVO> feeInfos;
	@ZteSoftCommentAnnotationParam(name = "发起方系统编码", type = "String", isNecessary = "Y", desc = "发起方系统编码")
	private String origSysId;
	@ZteSoftCommentAnnotationParam(name = "归属方系统编码", type = "String", isNecessary = "Y", desc = "归属方系统编码")
	private String homeSysId;
	@ZteSoftCommentAnnotationParam(name = "业务号码类型", type = "String", isNecessary = "Y", desc = "业务号码类型")
	private String busiKeyType;
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "Y", desc = "业务号码")
	private String busiKey;
	@ZteSoftCommentAnnotationParam(name = "业务操作类型", type = "String", isNecessary = "Y", desc = "业务操作类型")
	private String busiCode;
	@ZteSoftCommentAnnotationParam(name = "业务发生时间", type = "String", isNecessary = "Y", desc = "业务发生时间(格式：yyyy-MM-dd hh24:mi:ss)")
	private String busiOccurrTime;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getBssPaySerialNo() {
		return BssPaySerialNo;
	}
	public void setBssPaySerialNo(String bssPaySerialNo) {
		BssPaySerialNo = bssPaySerialNo;
	}
	public List<BroadbandRefundFeeVO> getFeeInfos() {
		return feeInfos;
	}
	public void setFeeInfos(List<BroadbandRefundFeeVO> feeInfos) {
		this.feeInfos = feeInfos;
	}
	public String getOrigSysId() {
		return origSysId;
	}
	public void setOrigSysId(String origSysId) {
		this.origSysId = origSysId;
	}
	public String getHomeSysId() {
		return homeSysId;
	}
	public void setHomeSysId(String homeSysId) {
		this.homeSysId = homeSysId;
	}
	public String getBusiKeyType() {
		return busiKeyType;
	}
	public void setBusiKeyType(String busiKeyType) {
		this.busiKeyType = busiKeyType;
	}
	public String getBusiKey() {
		return busiKey;
	}
	public void setBusiKey(String busiKey) {
		this.busiKey = busiKey;
	}
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getBusiOccurrTime() {
		return busiOccurrTime;
	}
	public void setBusiOccurrTime(String busiOccurrTime) {
		this.busiOccurrTime = busiOccurrTime;
	}
	
	
}
