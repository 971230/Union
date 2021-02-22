package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

public class AopSmsSendResp extends ZteResponse {
	private String code;
	
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
