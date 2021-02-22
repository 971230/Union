package com.ztesoft.net.server.jsonserver.hspojo;

import java.io.Serializable;

public class HSMallOrderReqItemsDetail implements Serializable {

	private String ITEM = "";	//出库单行项目
	private String LGORT = "";	//库位
	private String MATNR = "";	//商品编码
	private String SOBKZ = "";	//经销/代销
	private String MENGE = "";	//数量
	private String MEINS = "";	//单位，固定值：PCS	
	private String delivDate = "";	//B2C订单编号

	public String getITEM() {
		return ITEM;
	}

	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}

	public String getLGORT() {
		return LGORT;
	}

	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getSOBKZ() {
		return SOBKZ;
	}

	public void setSOBKZ(String sOBKZ) {
		SOBKZ = sOBKZ;
	}

	public String getMENGE() {
		return MENGE;
	}

	public void setMENGE(String mENGE) {
		MENGE = mENGE;
	}

	public String getMEINS() {
		return MEINS;
	}

	public void setMEINS(String mEINS) {
		MEINS = mEINS;
	}

	public String getDelivDate() {
		return delivDate;
	}

	public void setDelivDate(String delivDate) {
		this.delivDate = delivDate;
	}
	
}
