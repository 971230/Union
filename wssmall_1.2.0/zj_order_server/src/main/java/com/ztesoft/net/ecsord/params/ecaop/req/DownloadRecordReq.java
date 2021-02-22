package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class DownloadRecordReq extends ZteRequest{

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
	@ZteSoftCommentAnnotationParam(name = "记录唯一标识", type = "String", isNecessary = "Y", desc = "应用平台侧的“记录唯一标识”")
	private String callOnlyMark;
	@ZteSoftCommentAnnotationParam(name = "接入方", type = "String", isNecessary = "Y", desc = "由IVR平台分配")
	private String accessParty;
	@ZteSoftCommentAnnotationParam(name = "通话开始时间", type = "String", isNecessary = "Y", desc = "yyyy-mm-dd hh24:mi:ss")
	private String callBeginTime;
	
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


	public String getCallOnlyMark() {
		return callOnlyMark;
	}

	public void setCallOnlyMark(String callOnlyMark) {
		this.callOnlyMark = callOnlyMark;
	}

	public String getAccessParty() {
		return accessParty;
	}

	public void setAccessParty(String accessParty) {
		this.accessParty = accessParty;
	}

	

	public String getCallBeginTime() {
		return callBeginTime;
	}

	public void setCallBeginTime(String callBeginTime) {
		this.callBeginTime = callBeginTime;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.downloadRecordReq";
	}

}
