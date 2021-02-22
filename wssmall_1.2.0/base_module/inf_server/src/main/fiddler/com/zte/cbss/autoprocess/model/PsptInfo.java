package com.zte.cbss.autoprocess.model;

/**
 * 身份证信息，检查身份证时生成
 * @author 张浩
 * @version 1.0.0(2014-5-20)
 */
public class PsptInfo {

	private String birthday;		//出生日期
	private String identityName;    //客户姓名
	private String sex;				//性别 1：男，0：女
	private String identityNo;		//证件编号
	private String addressInfo;		//家庭地址
	private String licencelssAuth;
	private String nationality;		//国籍 01：中国
	private String validityStart;
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdentityName() {
		return identityName;
	}
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	public String getLicencelssAuth() {
		return licencelssAuth;
	}
	public void setLicencelssAuth(String licencelssAuth) {
		this.licencelssAuth = licencelssAuth;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getValidityStart() {
		return validityStart;
	}
	public void setValidityStart(String validityStart) {
		this.validityStart = validityStart;
	}
}
