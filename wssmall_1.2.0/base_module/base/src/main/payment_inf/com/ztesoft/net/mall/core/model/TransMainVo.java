package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;




/**
 * 
 * @author wu.i
 * 对账解析，第一条记录，
 *
 */
public class TransMainVo implements java.io.Serializable {

	

	//对账返回结果
	private Long icount;
	private String transDate;//对账日期
	private String transNum; //对账笔数
	private String transMoney;//对账金额
	private String shopNo;
	
	
	
	@NotDbField
	public Long getIcount() {
		return icount;
	}
	public void setIcount(Long icount) {
		this.icount = icount;
	}
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
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	
	
}