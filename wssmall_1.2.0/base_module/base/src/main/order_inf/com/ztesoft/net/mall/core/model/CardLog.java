package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;


/**
 * 流量卡、充值卡导入日志
 * 
 * @author wui
 */
public class CardLog implements java.io.Serializable {
	private String batch_id;
	private String cardtype;
	private String cardtheme;
	private Long cardnumber;
	private String startcardno;
	private String endcardno;
	private String startdate;
	private String stopdate;
	private String reservestopdate;
	private String facevalue;
	private String log_id;
	private String goods_id;	//对应商品id
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardtheme() {
		return cardtheme;
	}
	public void setCardtheme(String cardtheme) {
		this.cardtheme = cardtheme;
	}
	
	public String getStartcardno() {
		return startcardno;
	}
	public void setStartcardno(String startcardno) {
		this.startcardno = startcardno;
	}
	public String getEndcardno() {
		return endcardno;
	}
	public void setEndcardno(String endcardno) {
		this.endcardno = endcardno;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStopdate() {
		return stopdate;
	}
	public void setStopdate(String stopdate) {
		this.stopdate = stopdate;
	}
	public String getReservestopdate() {
		return reservestopdate;
	}
	public void setReservestopdate(String reservestopdate) {
		this.reservestopdate = reservestopdate;
	}
	public String getFacevalue() {
		return facevalue;
	}
	public void setFacevalue(String facevalue) {
		this.facevalue = facevalue;
	}
	public Long getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(Long cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	@NotDbField
	public String getGoods_id() {
		return goods_id;
	}
	
	@NotDbField
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

}