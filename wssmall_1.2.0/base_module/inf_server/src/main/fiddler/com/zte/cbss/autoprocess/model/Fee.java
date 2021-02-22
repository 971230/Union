package com.zte.cbss.autoprocess.model;

/**
 * 费用信息实体
 * @author tanghaoyang
 *
 */
public class Fee {
	private String operateType;
	private String feeTypeCode;
	private String payTag;
	private String tradeId;
	private long maxDerateFee;
	private String feeitemCode;
	private String feeMode;
	private long oldfee;
	private long fee;
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getFeeTypeCode() {
		return feeTypeCode;
	}
	public void setFeeTypeCode(String feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}
	public String getPayTag() {
		return payTag;
	}
	public void setPayTag(String payTag) {
		this.payTag = payTag;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public long getMaxDerateFee() {
		return maxDerateFee;
	}
	public void setMaxDerateFee(long maxDerateFee) {
		this.maxDerateFee = maxDerateFee;
	}
	public String getFeeitemCode() {
		return feeitemCode;
	}
	public void setFeeitemCode(String feeitemCode) {
		this.feeitemCode = feeitemCode;
	}
	public String getFeeMode() {
		return feeMode;
	}
	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}
	public long getOldfee() {
		return oldfee;
	}
	public void setOldfee(long oldfee) {
		this.oldfee = oldfee;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	
	
}
