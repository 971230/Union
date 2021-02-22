package com.ztesoft.net.mall.core.model;

/**
 * 短信验证码返回对象
 * @作者 MoChunrun
 * @创建日期 2013-10-31 
 * @版本 V 1.0
 */
public class SmsCodeResp {

	private String phoneNo;
	private String code;
	private boolean success;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
