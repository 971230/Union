package com.ztesoft.net.mall.core.action.desposit.model;

import com.ztesoft.net.framework.database.NotDbField;

public class OperDetail implements java.io.Serializable{
	
	private String accNbr;
	private String busiType;
	private String operDate;
	private String amout;
	private String beAmount;
	private String afAmount;
	private String isCompare;
	private String state;
	private String operate; 	//标志所做操作
	
	
	
	private String state_name;
	
	public String getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getOperDate() {
		return operDate;
	}
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	public String getAmout() {
		return amout;
	}
	public void setAmout(String amout) {
		this.amout = amout;
	}
	public String getBeAmount() {
		return beAmount;
	}
	public void setBeAmount(String beAmount) {
		this.beAmount = beAmount;
	}
	public String getAfAmount() {
		return afAmount;
	}
	public void setAfAmount(String afAmount) {
		this.afAmount = afAmount;
	}
	public String getIsCompare() {
		return isCompare;
	}
	public void setIsCompare(String isCompare) {
		this.isCompare = isCompare;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState_name() {
		
		String stateName = "";
		if("00A".equals(state)){
			stateName = "正常";
		}else if("00B".equals(state)){
			stateName = "被回退";
		}else if("00C".equals(state)){
			stateName = "回退";
		}
		return stateName;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	@NotDbField
	public String getOperate() {
		return operate;
	}
	
	@NotDbField
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	

}
