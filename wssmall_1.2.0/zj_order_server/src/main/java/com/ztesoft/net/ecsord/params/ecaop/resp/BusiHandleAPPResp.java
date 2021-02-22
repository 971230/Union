package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BusiHandleAPPResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name="业务返回结果",type="String",isNecessary="Y",desc="0 可受理1 不可受理")
	private String bus_resp;
	
	@ZteSoftCommentAnnotationParam(name="BSS预提交返回单号",type="String",isNecessary="Y",desc="BSS预提交返回单号")
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

	public String getBus_resp() {
		return bus_resp;
	}

	public void setBus_resp(String bus_resp) {
		this.bus_resp = bus_resp;
	}

	public String getBss_pre_order_id() {
		return bss_pre_order_id;
	}

	public void setBss_pre_order_id(String bss_pre_order_id) {
		this.bss_pre_order_id = bss_pre_order_id;
	}
	
}
