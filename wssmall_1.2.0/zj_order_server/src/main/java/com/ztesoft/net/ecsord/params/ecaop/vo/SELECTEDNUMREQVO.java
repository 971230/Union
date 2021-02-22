package com.ztesoft.net.ecsord.params.ecaop.vo;

public class SELECTEDNUMREQVO {

	private String STAFF_ID;//操作员ID（接口会对工号进行合理性校验。同时会校验工号对应的省分、地市、部门、渠道、渠道类型和 其他入参是否 匹配）
	private String PROVINCE_CODE;//PROVINCE_CODE
	private String CITY_CODE;//地市编码
	private String DISTRICT_CODE;//区县编码（工号如果有区县编码，必填。如果工号没有区县编码，不填）
	private String CHANNEL_ID;//CHANNEL_ID
	private String CHANNEL_TYPE;//渠道类型（不校验，如果传入就使用。如果不传入。默认为工号对应的编码）
	private String SYS_CODE;//机构系统编码 详见附录“机构系统编码”
	private String SERIAL_NUMBER;//业务号码
	private String BUSINESS_TYPE;//业务分类01：公众02：集团
	private String PRO_KEY;//资源关键字（参考名词解释）参考业务需求描述
	private String CERT_TYPE_CODE;//证件类型见附录证件类别编码（参考业务需求描述）
	private String CERT_CODE;//证件号码（参考业务需求描述）
	private String CUST_NAME;//客户姓名（不校验，不使用）
	private String CONTACT_PHONE;//联系电话（不校验，不使用）
	private String SELECTION_TIME;//选占保留时间，单位分钟，默认30分钟，最长不超过****分钟（可录入自定义时间）（选占保留时间只能输入数字。）（2次选占时，资源的选占时长仍保持原有时长不变，参考业务需求描述）
	private PARAVO PARA;//保留字段（暂未使用）
	
	public String getSTAFF_ID() {
		return STAFF_ID;
	}
	public void setSTAFF_ID(String sTAFF_ID) {
		STAFF_ID = sTAFF_ID;
	}
	public String getPROVINCE_CODE() {
		return PROVINCE_CODE;
	}
	public void setPROVINCE_CODE(String pROVINCE_CODE) {
		PROVINCE_CODE = pROVINCE_CODE;
	}
	public String getCITY_CODE() {
		return CITY_CODE;
	}
	public void setCITY_CODE(String cITY_CODE) {
		CITY_CODE = cITY_CODE;
	}
	public String getDISTRICT_CODE() {
		return DISTRICT_CODE;
	}
	public void setDISTRICT_CODE(String dISTRICT_CODE) {
		DISTRICT_CODE = dISTRICT_CODE;
	}
	public String getCHANNEL_ID() {
		return CHANNEL_ID;
	}
	public void setCHANNEL_ID(String cHANNEL_ID) {
		CHANNEL_ID = cHANNEL_ID;
	}
	public String getCHANNEL_TYPE() {
		return CHANNEL_TYPE;
	}
	public void setCHANNEL_TYPE(String cHANNEL_TYPE) {
		CHANNEL_TYPE = cHANNEL_TYPE;
	}
	public String getSYS_CODE() {
		return SYS_CODE;
	}
	public void setSYS_CODE(String sYS_CODE) {
		SYS_CODE = sYS_CODE;
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
	public String getSELECTION_TIME() {
		return SELECTION_TIME;
	}
	public void setSELECTION_TIME(String sELECTION_TIME) {
		SELECTION_TIME = sELECTION_TIME;
	}
	public PARAVO getPARA() {
		return PARA;
	}
	public void setPARA(PARAVO pARA) {
		PARA = pARA;
	}
	


}
