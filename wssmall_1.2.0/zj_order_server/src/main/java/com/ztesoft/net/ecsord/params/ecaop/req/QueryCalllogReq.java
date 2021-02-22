package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class QueryCalllogReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "接口名称", type = "String", isNecessary = "Y", desc = "接口名称")
	private String service;
	@ZteSoftCommentAnnotationParam(name = "合作身份者ID", type = "String", isNecessary = "Y", desc = "合作身份者ID")
	private String partner;
	@ZteSoftCommentAnnotationParam(name = "参数编码字符集", type = "String", isNecessary = "Y", desc = "UTF-8")
	private String input_charset;
	@ZteSoftCommentAnnotationParam(name = "签名", type = "String", isNecessary = "Y", desc = "为合作身份者ID的md5加密后的32位字符串。")
	private String sign;
	@ZteSoftCommentAnnotationParam(name = "接入方", type = "String", isNecessary = "Y", desc = "由IVR平台分配")
	private String accessParty;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "由IVR平台分配")
	private String businessType;
	@ZteSoftCommentAnnotationParam(name = "主叫号码", type = "String", isNecessary = "Y", desc = "外呼的主叫号码，需呼叫双方时必传")
	private String caller;
	@ZteSoftCommentAnnotationParam(name = "被叫号码", type = "String", isNecessary = "Y", desc = "外呼的业务号码")
	private String callee;
	@ZteSoftCommentAnnotationParam(name = "话务传递信息", type = "String", isNecessary = "Y", desc = "“主动外呼IVR请求接口”的“传递信息”；“自动外呼IVR请求接口”的“记录唯一标示”")
	private String trafficInformation;
	@ZteSoftCommentAnnotationParam(name = "通话开始时间", type = "String", isNecessary = "Y", desc = "yyyy-mm-dd hh24:mi:ss")
	private String callbeginTime;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "yyyy-mm-dd hh24:mi:ss")
	private String callEndTime;
	@ZteSoftCommentAnnotationParam(name = "模板ID", type = "String", isNecessary = "Y", desc = "由IVR平台分配")
	private String template_id;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	@Override
	public String getSign() {
		return sign;
	}

	@Override
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAccessParty() {
		return accessParty;
	}

	public void setAccessParty(String accessParty) {
		this.accessParty = accessParty;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	public String getTrafficInformation() {
		return trafficInformation;
	}

	public void setTrafficInformation(String trafficInformation) {
		this.trafficInformation = trafficInformation;
	}

	public String getCallbeginTime() {
		return callbeginTime;
	}

	public void setCallbeginTime(String callbeginTime) {
		this.callbeginTime = callbeginTime;
	}

	public String getCallEndTime() {
		return callEndTime;
	}

	public void setCallEndTime(String callEndTime) {
		this.callEndTime = callEndTime;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.queryCalllogReq";
	}

}
