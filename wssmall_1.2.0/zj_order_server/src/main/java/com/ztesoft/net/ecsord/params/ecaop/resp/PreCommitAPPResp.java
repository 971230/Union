package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class PreCommitAPPResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="接口返回代码",type="String",isNecessary="Y",desc="1失败、0成功表示接口调用结果")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name="业务返回结果",type="String",isNecessary="Y",desc="实际业务的完成结果:0 bss预提交成功,1 bss预提交失败")
	private String bus_resp;
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
