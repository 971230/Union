package com.zte.cbss.autoprocess.model.data;

/**
 * BSS校验请求数据
 * @author 张浩
 * @version 1.0.0
 */
public class BSSCheckData {

	private String CHECK_MODE = "8";	  //检查类型，默认：用户证件
	private String EPARCHY_CODE = "0757"; //地域编号，默认：佛山
	private String ID_TYPE_CODE = "1";    //证件类型，默认：身份证
	private String PSPT_ID;		 		  //证件号	 
	private String SERIAL_NUMBER;	      //用户电话号码
	
	public String getCHECK_MODE() {
		return CHECK_MODE;
	}
	public void setCHECK_MODE(String cHECK_MODE) {
		CHECK_MODE = cHECK_MODE;
	}
	public String getEPARCHY_CODE() {
		return EPARCHY_CODE;
	}
	public void setEPARCHY_CODE(String ePARCHY_CODE) {
		EPARCHY_CODE = ePARCHY_CODE;
	}
	public String getID_TYPE_CODE() {
		return ID_TYPE_CODE;
	}
	public void setID_TYPE_CODE(String iD_TYPE_CODE) {
		ID_TYPE_CODE = iD_TYPE_CODE;
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
}
