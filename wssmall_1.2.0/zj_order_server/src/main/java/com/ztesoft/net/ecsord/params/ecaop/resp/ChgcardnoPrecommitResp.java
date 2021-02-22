package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ChgcardnoPrecommitResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="00000表示成功")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="错误描述")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name="Bss预提交返回单号",type="String",isNecessary="Y",desc="Bss预提交返回单号")
	private String bss_pre_order_id;
	


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

	public String getBss_pre_order_id() {
		return bss_pre_order_id;
	}

	public void setBss_pre_order_id(String bss_pre_order_id) {
		this.bss_pre_order_id = bss_pre_order_id;
	}


}
