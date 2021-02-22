package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CmcSmsSendReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "企业编号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String spCode;
	@ZteSoftCommentAnnotationParam(name = "用户名称", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String LoginName;
	@ZteSoftCommentAnnotationParam(name = "用户密码", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String Password;
	@ZteSoftCommentAnnotationParam(name = "短信内容", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String MessageContent;
	@ZteSoftCommentAnnotationParam(name = "手机号码(多个号码用”,”分隔)", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String UserNumber;
	@ZteSoftCommentAnnotationParam(name = "流水号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String SerialNumber;
	@ZteSoftCommentAnnotationParam(name = "预约发送时间", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String ScheduleTime;
	@ZteSoftCommentAnnotationParam(name = "提交时检测方式", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String f;
	
	@ZteSoftCommentAnnotationParam(name="发送类型",type="String",isNecessary="Y",desc="发送类型")
	private String send_type;
	

	@ZteSoftCommentAnnotationParam(name="批量发送",type="String",isNecessary="Y",desc="批量发送")
	private List<ES_WORK_SMS_SEND> listpojo;
	
	

	public List<ES_WORK_SMS_SEND> getListpojo() {
		return listpojo;
	}

	public void setListpojo(List<ES_WORK_SMS_SEND> listpojo) {
		this.listpojo = listpojo;
	}

	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	
	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getMessageContent() {
		return MessageContent;
	}

	public void setMessageContent(String messageContent) {
		MessageContent = messageContent;
	}

	public String getUserNumber() {
		return UserNumber;
	}

	public void setUserNumber(String userNumber) {
		UserNumber = userNumber;
	}

	public String getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}

	public String getScheduleTime() {
		return ScheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		ScheduleTime = scheduleTime;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}


	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "yeion.user.message.send";
	}

}
