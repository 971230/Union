package com.ztesoft.net.server.jsonserver.zbpojo;

public class CenterMallGoodsPhoneInfo {

	//订购号码
	private String phoneNum = "";
	//号码网别
	private String NumNet  = "";
	//靓号信息
	private CenterMallGoodsNiceInfo niceInfo = null;
	//操作状态
	private String operatorState = "";
	//资源预占关键字
	private String proKey = "";
	//号码状态标识
	private String occupiedFlag = "";
	//号码状态标识时间
	private String occupiedTime = "";
	//号码资源预占关键字类型
	private String proKeyMode = "";
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public CenterMallGoodsNiceInfo getNiceInfo() {
		return niceInfo;
	}
	public void setNiceInfo(CenterMallGoodsNiceInfo niceInfo) {
		this.niceInfo = niceInfo;
	}
	public String getOperatorState() {
		return operatorState;
	}
	public void setOperatorState(String operatorState) {
		this.operatorState = operatorState;
	}
	public String getProKey() {
		return proKey;
	}
	public void setProKey(String proKey) {
		this.proKey = proKey;
	}
	public String getOccupiedFlag() {
		return occupiedFlag;
	}
	public void setOccupiedFlag(String occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}
	public String getOccupiedTime() {
		return occupiedTime;
	}
	public void setOccupiedTime(String occupiedTime) {
		this.occupiedTime = occupiedTime;
	}
	public String getProKeyMode() {
		return proKeyMode;
	}
	public void setProKeyMode(String proKeyMode) {
		this.proKeyMode = proKeyMode;
	}
	public String getNumNet() {
		return NumNet;
	}
	public void setNumNet(String numNet) {
		NumNet = numNet;
	}
	
}
