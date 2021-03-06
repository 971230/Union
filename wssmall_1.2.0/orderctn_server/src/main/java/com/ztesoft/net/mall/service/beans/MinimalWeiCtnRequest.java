package com.ztesoft.net.mall.service.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MinimalWeiCtnRequest {
	
	@XmlElement(required=true, nillable=false)
	private String ORDER_ID;//接口访问流水
	
	
	@XmlElement(required=true, nillable=false)
	private String TRADE_TYPE_CODE;//业务类型
	
	@XmlElement(required=true, nillable=false)
	private String IN_MODE_CODE;//预受理接入方式
	
	@XmlElement(required=false, nillable=false)
	private String USER_ID;	//目标系统用户ID
	
	@XmlElement(required=true, nillable=false)
	private String CUST_ID;//目标系统客户ID

	@XmlElement(required=true, nillable=false)
	private String PSPT_TYPE_CODE;//客户证件类型

	@XmlElement(required=true, nillable=false)
	private String PSPT_ID;//客户证件号码

	@XmlElement(required=false, nillable=false)
	private String SERIAL_NUMBER;//号码

	@XmlElement(required=false, nillable=false)
	private String CUST_NAME;//客户名称

	@XmlElement(required=false, nillable=false)
	private String USER_NAME;//用户名称

	@XmlElement(required=true, nillable=false)
	private String INSTALL_ADDRESS;//装机地址

	@XmlElement(required=true, nillable=false)
	private String ACCEPT_DATE;//业务受理时间

	@XmlElement(required=true, nillable=false)
	private String ACCEPT_MONTH;//业务受理月份

	@XmlElement(required=true, nillable=false)
	private String DEVELOP_DEPART_ID;//业务发展渠道ID

	@XmlElement(required=true, nillable=false)
	private String DEVELOP_STAFF_ID;//业务发展人ID

	@XmlElement(required=true, nillable=false)
	private String DEVELOP_EPARCHY_CODE;//业务发展地市

	@XmlElement(required=true, nillable=false)
	private String DEVELOP_CITY_CODE;//业务发展区县

	@XmlElement(required=true, nillable=false)
	private String DEVELOP_DATE;//业务发展日期

	@XmlElement(required=true, nillable=false)
	private String TRADE_STAFF_ID;//业务受理员工ID

	@XmlElement(required=true, nillable=false)
	private String TRADE_DEPART_ID;//业务受理部门

	@XmlElement(required=true, nillable=false)
	private String TRADE_CITY_CODE;//业务受理区县

	@XmlElement(required=true, nillable=false)
	private String TRADE_EPARCHY_CODE;//业务受理地市

	@XmlElement(required=true, nillable=false)
	private String EPARCHY_CODE;//用户所属地市

	@XmlElement(required=true, nillable=false)
	private String CITY_CODE;//用户所属区县

	@XmlElement(required=true, nillable=false)
	private Integer OPER_FEE;//预受理工单总费用

	@XmlElement(required=true, nillable=false)
	private String FEE_STATE;//是否已经收费

	@XmlElement(required=true, nillable=false)
	private String FEE_TIME;//收费时间

	@XmlElement(required=true, nillable=false)
	private String FEE_STAFF_ID;//收费员工ID

	@XmlElement(required=true, nillable=false)
	private String CHK_TAG;//预受理工单状态

	@XmlElement(required=true, nillable=false)
	private String CONTACT;//联系人

	@XmlElement(required=true, nillable=false)
	private String CONTACT_PHONE;//联系电话

	@XmlElement(required=false, nillable=false)
	private String REMARK;//工单备注

	@XmlElement(required=true, nillable=false)
	private String RSRV_STR5;//是否CBSS业务工单

	@XmlElement(required=true, nillable=false)
	private String RSRV_STR3;//外围系统工单ID

	@XmlElement(required=true, nillable=false)
	private String RSRV_STR1;//主产品ID

	@XmlElement(required=true, nillable=false)
	private String RSRV_STR2;//主套餐资费ID

	@XmlElement(required=true, nillable=false)
	private String NET_TYPE_CODE;//网别编码

	@XmlElement(required=true, nillable=false)
	private String BRAND_CODE;//品牌编码

	@XmlElement(required=true, nillable=false)
	private String EXCH_CODE;//局向编码

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getTRADE_TYPE_CODE() {
		return TRADE_TYPE_CODE;
	}

	public void setTRADE_TYPE_CODE(String tRADE_TYPE_CODE) {
		TRADE_TYPE_CODE = tRADE_TYPE_CODE;
	}

	public String getIN_MODE_CODE() {
		return IN_MODE_CODE;
	}

	public void setIN_MODE_CODE(String iN_MODE_CODE) {
		IN_MODE_CODE = iN_MODE_CODE;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}

	public String getPSPT_TYPE_CODE() {
		return PSPT_TYPE_CODE;
	}

	public void setPSPT_TYPE_CODE(String pSPT_TYPE_CODE) {
		PSPT_TYPE_CODE = pSPT_TYPE_CODE;
	}

	public String getPSPT_ID() {
		return PSPT_ID;
	}

	public void setPSPT_ID(String pSPT_ID) {
		PSPT_ID = pSPT_ID;
	}

	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}

	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getINSTALL_ADDRESS() {
		return INSTALL_ADDRESS;
	}

	public void setINSTALL_ADDRESS(String iNSTALL_ADDRESS) {
		INSTALL_ADDRESS = iNSTALL_ADDRESS;
	}

	public String getACCEPT_DATE() {
		return ACCEPT_DATE;
	}

	public void setACCEPT_DATE(String aCCEPT_DATE) {
		ACCEPT_DATE = aCCEPT_DATE;
	}

	public String getACCEPT_MONTH() {
		return ACCEPT_MONTH;
	}

	public void setACCEPT_MONTH(String aCCEPT_MONTH) {
		ACCEPT_MONTH = aCCEPT_MONTH;
	}

	public String getDEVELOP_DEPART_ID() {
		return DEVELOP_DEPART_ID;
	}

	public void setDEVELOP_DEPART_ID(String dEVELOP_DEPART_ID) {
		DEVELOP_DEPART_ID = dEVELOP_DEPART_ID;
	}

	public String getDEVELOP_STAFF_ID() {
		return DEVELOP_STAFF_ID;
	}

	public void setDEVELOP_STAFF_ID(String dEVELOP_STAFF_ID) {
		DEVELOP_STAFF_ID = dEVELOP_STAFF_ID;
	}

	public String getDEVELOP_EPARCHY_CODE() {
		return DEVELOP_EPARCHY_CODE;
	}

	public void setDEVELOP_EPARCHY_CODE(String dEVELOP_EPARCHY_CODE) {
		DEVELOP_EPARCHY_CODE = dEVELOP_EPARCHY_CODE;
	}

	public String getDEVELOP_CITY_CODE() {
		return DEVELOP_CITY_CODE;
	}

	public void setDEVELOP_CITY_CODE(String dEVELOP_CITY_CODE) {
		DEVELOP_CITY_CODE = dEVELOP_CITY_CODE;
	}

	public String getDEVELOP_DATE() {
		return DEVELOP_DATE;
	}

	public void setDEVELOP_DATE(String dEVELOP_DATE) {
		DEVELOP_DATE = dEVELOP_DATE;
	}

	public String getTRADE_STAFF_ID() {
		return TRADE_STAFF_ID;
	}

	public void setTRADE_STAFF_ID(String tRADE_STAFF_ID) {
		TRADE_STAFF_ID = tRADE_STAFF_ID;
	}

	public String getTRADE_DEPART_ID() {
		return TRADE_DEPART_ID;
	}

	public void setTRADE_DEPART_ID(String tRADE_DEPART_ID) {
		TRADE_DEPART_ID = tRADE_DEPART_ID;
	}

	public String getTRADE_CITY_CODE() {
		return TRADE_CITY_CODE;
	}

	public void setTRADE_CITY_CODE(String tRADE_CITY_CODE) {
		TRADE_CITY_CODE = tRADE_CITY_CODE;
	}

	public String getTRADE_EPARCHY_CODE() {
		return TRADE_EPARCHY_CODE;
	}

	public void setTRADE_EPARCHY_CODE(String tRADE_EPARCHY_CODE) {
		TRADE_EPARCHY_CODE = tRADE_EPARCHY_CODE;
	}

	public String getEPARCHY_CODE() {
		return EPARCHY_CODE;
	}

	public void setEPARCHY_CODE(String ePARCHY_CODE) {
		EPARCHY_CODE = ePARCHY_CODE;
	}

	public String getCITY_CODE() {
		return CITY_CODE;
	}

	public void setCITY_CODE(String cITY_CODE) {
		CITY_CODE = cITY_CODE;
	}

	public Integer getOPER_FEE() {
		return OPER_FEE;
	}

	public void setOPER_FEE(Integer oPER_FEE) {
		OPER_FEE = oPER_FEE;
	}

	public String getFEE_STATE() {
		return FEE_STATE;
	}

	public void setFEE_STATE(String fEE_STATE) {
		FEE_STATE = fEE_STATE;
	}

	public String getFEE_TIME() {
		return FEE_TIME;
	}

	public void setFEE_TIME(String fEE_TIME) {
		FEE_TIME = fEE_TIME;
	}

	public String getFEE_STAFF_ID() {
		return FEE_STAFF_ID;
	}

	public void setFEE_STAFF_ID(String fEE_STAFF_ID) {
		FEE_STAFF_ID = fEE_STAFF_ID;
	}

	public String getCHK_TAG() {
		return CHK_TAG;
	}

	public void setCHK_TAG(String cHK_TAG) {
		CHK_TAG = cHK_TAG;
	}

	public String getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}

	public String getCONTACT_PHONE() {
		return CONTACT_PHONE;
	}

	public void setCONTACT_PHONE(String cONTACT_PHONE) {
		CONTACT_PHONE = cONTACT_PHONE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getRSRV_STR5() {
		return RSRV_STR5;
	}

	public void setRSRV_STR5(String rSRV_STR5) {
		RSRV_STR5 = rSRV_STR5;
	}

	public String getRSRV_STR3() {
		return RSRV_STR3;
	}

	public void setRSRV_STR3(String rSRV_STR3) {
		RSRV_STR3 = rSRV_STR3;
	}

	public String getRSRV_STR1() {
		return RSRV_STR1;
	}

	public void setRSRV_STR1(String rSRV_STR1) {
		RSRV_STR1 = rSRV_STR1;
	}

	public String getRSRV_STR2() {
		return RSRV_STR2;
	}

	public void setRSRV_STR2(String rSRV_STR2) {
		RSRV_STR2 = rSRV_STR2;
	}

	public String getNET_TYPE_CODE() {
		return NET_TYPE_CODE;
	}

	public void setNET_TYPE_CODE(String nET_TYPE_CODE) {
		NET_TYPE_CODE = nET_TYPE_CODE;
	}

	public String getBRAND_CODE() {
		return BRAND_CODE;
	}

	public void setBRAND_CODE(String bRAND_CODE) {
		BRAND_CODE = bRAND_CODE;
	}

	public String getEXCH_CODE() {
		return EXCH_CODE;
	}

	public void setEXCH_CODE(String eXCH_CODE) {
		EXCH_CODE = eXCH_CODE;
	}

	@Override
	public String toString() {
		return "{ORDER_ID=\"" + ORDER_ID
				+ "\", TRADE_TYPE_CODE=\"" + TRADE_TYPE_CODE + "\", IN_MODE_CODE=\""
				+ IN_MODE_CODE + "\", USER_ID=\"" + USER_ID + "\", CUST_ID=\""
				+ CUST_ID + "\", PSPT_TYPE_CODE=\"" + PSPT_TYPE_CODE + "\", PSPT_ID=\""
				+ PSPT_ID + "\", SERIAL_NUMBER=\"" + SERIAL_NUMBER + "\", CUST_NAME=\""
				+ CUST_NAME + "\", USER_NAME=\"" + USER_NAME + "\", INSTALL_ADDRESS=\""
				+ INSTALL_ADDRESS + "\", ACCEPT_DATE=\"" + ACCEPT_DATE
				+ "\", ACCEPT_MONTH=\"" + ACCEPT_MONTH + "\", DEVELOP_DEPART_ID=\""
				+ DEVELOP_DEPART_ID + "\", DEVELOP_STAFF_ID=\"" + DEVELOP_STAFF_ID
				+ "\", DEVELOP_EPARCHY_CODE=\"" + DEVELOP_EPARCHY_CODE
				+ "\", DEVELOP_CITY_CODE=\"" + DEVELOP_CITY_CODE
				+ "\", DEVELOP_DATE=\"" + DEVELOP_DATE + "\", TRADE_STAFF_ID=\""
				+ TRADE_STAFF_ID + "\", TRADE_DEPART_ID=\"" + TRADE_DEPART_ID
				+ "\", TRADE_CITY_CODE=\"" + TRADE_CITY_CODE
				+ "\", TRADE_EPARCHY_CODE=\"" + TRADE_EPARCHY_CODE
				+ "\", EPARCHY_CODE=\"" + EPARCHY_CODE + "\", CITY_CODE=\"" + CITY_CODE
				+ "\", OPER_FEE=\"" + OPER_FEE + "\", FEE_STATE=\"" + FEE_STATE
				+ "\", FEE_TIME=\"" + FEE_TIME + "\", FEE_STAFF_ID=\"" + FEE_STAFF_ID
				+ "\", CHK_TAG=\"" + CHK_TAG + "\", CONTACT=\"" + CONTACT
				+ "\", CONTACT_PHONE=\"" + CONTACT_PHONE + "\", REMARK=\"" + REMARK
				+ "\", RSRV_STR5=\"" + RSRV_STR5 + "\", RSRV_STR3=\"" + RSRV_STR3
				+ "\", RSRV_STR1=\"" + RSRV_STR1 + "\", RSRV_STR2=\"" + RSRV_STR2
				+ "\", NET_TYPE_CODE=\"" + NET_TYPE_CODE + "\", BRAND_CODE="
				+ BRAND_CODE + "\", EXCH_CODE=\"" + EXCH_CODE + "\"}";
	}
	
	
}


