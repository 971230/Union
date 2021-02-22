package com.ztesoft.net.server.jsonserver.zbpojo;

/*****************************************
 * 
 * @author duanshaochu
 * 费用明细
 */
public class CenterMallFeeInfo {

	//收费项编码
	private String feeID = "";
	//收费项描述
	private String feeDes = "";
	//应收金额，单位为厘
	private String origFee = "";
	//减免金额，单位为厘
	private String reliefFee = "";
	//减免原因
	private String reliefResult = "";
	//实收金额，单位为厘
	private String realFee = "";
	public String getFeeID() {
		return feeID;
	}
	public void setFeeID(String feeID) {
		this.feeID = feeID;
	}
	public String getFeeDes() {
		return feeDes;
	}
	public void setFeeDes(String feeDes) {
		this.feeDes = feeDes;
	}
	public String getOrigFee() {
		return origFee;
	}
	public void setOrigFee(String origFee) {
		this.origFee = origFee;
	}
	public String getReliefFee() {
		return reliefFee;
	}
	public void setReliefFee(String reliefFee) {
		this.reliefFee = reliefFee;
	}
	public String getReliefResult() {
		return reliefResult;
	}
	public void setReliefResult(String reliefResult) {
		this.reliefResult = reliefResult;
	}
	public String getRealFee() {
		return realFee;
	}
	public void setRealFee(String realFee) {
		this.realFee = realFee;
	}
	
	
	
}
