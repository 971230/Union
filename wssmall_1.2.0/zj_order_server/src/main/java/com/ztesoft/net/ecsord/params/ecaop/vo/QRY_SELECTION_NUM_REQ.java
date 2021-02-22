package com.ztesoft.net.ecsord.params.ecaop.vo;

public class QRY_SELECTION_NUM_REQ {

	private String STAFF_ID;//操作员ID（接口会对工号进行合理性校验。同时会校验工号对应的省分、地市、部门、渠道、渠道类型和 其他入参是否 匹配）
	private String PROVINCE_CODE;//PROVINCE_CODE
	private String CITY_CODE;//地市编码
	private String DISTRICT_CODE;//区县编码（工号如果有区县编码，必填。如果工号没有区县编码，不填）
	private String CHANNEL_ID;//CHANNEL_ID
	private String CHANNEL_TYPE;//渠道类型（不校验，如果传入就使用。如果不传入。默认为工号对应的编码）
	private String SYS_CODE;//机构系统编码 详见附录“机构系统编码”
	private String SELECTED_STAFF_ID;//选占工号[入参新增选占工号字段]
	private String POOL_CODE;//池id
	private String SERIAL_NUMBER;//业务号码
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
	public String getSELECTED_STAFF_ID() {
		return SELECTED_STAFF_ID;
	}
	public void setSELECTED_STAFF_ID(String sELECTED_STAFF_ID) {
		SELECTED_STAFF_ID = sELECTED_STAFF_ID;
	}
	public String getPOOL_CODE() {
		return POOL_CODE;
	}
	public void setPOOL_CODE(String pOOL_CODE) {
		POOL_CODE = pOOL_CODE;
	}
	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}
	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}
	public PARAVO getPARA() {
		return PARA;
	}
	public void setPARA(PARAVO pARA) {
		PARA = pARA;
	}
	
	
	
	


}
