package com.ztesoft.parameters.sms;

import com.ztesoft.parameters.AbstractRopRequest;

import javax.validation.constraints.NotNull;

/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 */
public class SmsRequest extends AbstractRopRequest {
	
	@NotNull
	private String acc_nbr;
	
	@NotNull
	private String content;
	
	private String sms_flag;

	public String getSms_flag() {
		return sms_flag;
	}

	public void setSms_flag(String sms_flag) {
		this.sms_flag = sms_flag;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
