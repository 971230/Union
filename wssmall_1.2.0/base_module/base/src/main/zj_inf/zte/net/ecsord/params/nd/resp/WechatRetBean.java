package zte.net.ecsord.params.nd.resp;

import java.io.Serializable;

public class WechatRetBean implements Serializable {

	/**
	 * 微信服务号平台返回的错误码
	 */
	private String resp_code;
	
	/**
	 * 微信服务号平台返回的错误信息
	 */
	private String resp_msg;
	
	private String code;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
