package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BusiHandleCheckAPPResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;

	@ZteSoftCommentAnnotationParam(name="商品ID返回结果",type="list",isNecessary="Y",desc=" ")
	private String scheme_list_return;
	
	

	public String getScheme_list_return() {
		return scheme_list_return;
	}

	public void setScheme_list_return(String scheme_list_return) {
		this.scheme_list_return = scheme_list_return;
	}

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

	
}
