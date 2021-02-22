package com.ztesoft.net.model;

import org.apache.log4j.Logger;


public class BillHeader extends BaseBill {
	private static Logger logger = Logger.getLogger(BillHeader.class);
	private String transDate;
	private String transNum;
	private String transMoney;
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransNum() {
		return transNum;
	}
	public void setTransNum(String transNum) {
		this.transNum = transNum;
	}
	public String getTransMoney() {
		return transMoney;
	}
	public void setTransMoney(String transMoney) {
		this.transMoney = transMoney;
	}
	
	public static void main(String[] args) {
		BillHeader b = new BillHeader();
		b.setTransDate("20131010");
		b.setTransMoney("1000000");
		b.setTransNum("80");
		logger.info(b);
	}
}
