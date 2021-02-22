package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.util.List;

public class SELECTEDNUMRSPVO {

	private String RESP_CODE;//应答码0000成功9999号码同步失败，没有收录到应答编码中。其他取值具体参考附录-应答编码
	private String LOW_COST;//月承诺最低通信费（分）
	private String RESP_DESC;//应答描述
	private String SERIAL_NUMBER;//号码
	private String BUSINESS_TYPE;//业务分类01：公众02：集团
	private String GOOD_LEVEL;//靓号等级1:靓号等级12:靓号等级23:靓号等级34:靓号等级45:靓号等级56:靓号等级699.普通号码
	private String GOOD_TYPE;//靓号类型如输入AABB，AABBCC也应返回
	private String ADVANCE_PAY;//ADVANCE_PAY
	private String ONLINE_LENGTH;//协议期，参考名词解释
	private String ICCID;//ICCID
	private String IMSI;//Imsi
	private String MOFFICE_ID;//局向
	private String SWITCH_ID;//交换机编码
	private String PRO_KEY;//资源关键字（参考名词解释）
	private String CERT_TYPE_CODE;//证件类型见附录证件类别编码
	private String CERT_CODE;//证件号码
	private String CUST_NAME;//客户姓名
	private String CONTACT_PHONE;//联系电话
	private List<PARAVO> PARA;//保留字段（暂未使用）
	
	public String getRESP_CODE() {
		return RESP_CODE;
	}
	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}
	public String getLOW_COST() {
		return LOW_COST;
	}
	public void setLOW_COST(String lOW_COST) {
		LOW_COST = lOW_COST;
	}
	public String getRESP_DESC() {
		return RESP_DESC;
	}
	public void setRESP_DESC(String rESP_DESC) {
		RESP_DESC = rESP_DESC;
	}
	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}
	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}
	public String getBUSINESS_TYPE() {
		return BUSINESS_TYPE;
	}
	public void setBUSINESS_TYPE(String bUSINESS_TYPE) {
		BUSINESS_TYPE = bUSINESS_TYPE;
	}
	public String getGOOD_LEVEL() {
		return GOOD_LEVEL;
	}
	public void setGOOD_LEVEL(String gOOD_LEVEL) {
		GOOD_LEVEL = gOOD_LEVEL;
	}
	public String getGOOD_TYPE() {
		return GOOD_TYPE;
	}
	public void setGOOD_TYPE(String gOOD_TYPE) {
		GOOD_TYPE = gOOD_TYPE;
	}
	public String getADVANCE_PAY() {
		return ADVANCE_PAY;
	}
	public void setADVANCE_PAY(String aDVANCE_PAY) {
		ADVANCE_PAY = aDVANCE_PAY;
	}
	public String getONLINE_LENGTH() {
		return ONLINE_LENGTH;
	}
	public void setONLINE_LENGTH(String oNLINE_LENGTH) {
		ONLINE_LENGTH = oNLINE_LENGTH;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getMOFFICE_ID() {
		return MOFFICE_ID;
	}
	public void setMOFFICE_ID(String mOFFICE_ID) {
		MOFFICE_ID = mOFFICE_ID;
	}
	public String getSWITCH_ID() {
		return SWITCH_ID;
	}
	public void setSWITCH_ID(String sWITCH_ID) {
		SWITCH_ID = sWITCH_ID;
	}
	public String getPRO_KEY() {
		return PRO_KEY;
	}
	public void setPRO_KEY(String pRO_KEY) {
		PRO_KEY = pRO_KEY;
	}
	public String getCERT_TYPE_CODE() {
		return CERT_TYPE_CODE;
	}
	public void setCERT_TYPE_CODE(String cERT_TYPE_CODE) {
		CERT_TYPE_CODE = cERT_TYPE_CODE;
	}
	public String getCERT_CODE() {
		return CERT_CODE;
	}
	public void setCERT_CODE(String cERT_CODE) {
		CERT_CODE = cERT_CODE;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getCONTACT_PHONE() {
		return CONTACT_PHONE;
	}
	public void setCONTACT_PHONE(String cONTACT_PHONE) {
		CONTACT_PHONE = cONTACT_PHONE;
	}
	public List<PARAVO> getPARA() {
		return PARA;
	}
	public void setPARA(List<PARAVO> pARA) {
		PARA = pARA;
	}


}
