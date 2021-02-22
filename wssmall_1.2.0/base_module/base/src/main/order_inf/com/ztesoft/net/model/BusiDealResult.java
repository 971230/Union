package com.ztesoft.net.model;

import java.io.Serializable;

import params.ZteResponse;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class BusiDealResult implements Serializable {

	private static final long serialVersionUID = -8323534161775596017L;
	
	public String error_code = EcsOrderConsts.BUSI_DEAL_RESULT_0000;
	
	public String error_msg;
	
	private ZteResponse response;

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public ZteResponse getResponse() {
		return response;
	}

	public void setResponse(ZteResponse response) {
		this.response = response;
	}
}
