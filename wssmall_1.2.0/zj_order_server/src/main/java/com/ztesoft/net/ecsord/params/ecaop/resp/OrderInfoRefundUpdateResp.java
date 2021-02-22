package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class OrderInfoRefundUpdateResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "返回代码 1失败、0成功")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "返回描述")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name = "退单状态", type = "String", isNecessary = "Y", desc = "退单状态")
	private String resp_status;
	
	
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
	public String getResp_status() {
		return resp_status;
	}
	public void setResp_status(String resp_status) {
		this.resp_status = resp_status;
	}
	
}
