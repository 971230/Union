package zte.net.ecsord.params.wyg.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.SmsSendResp;

public class Sms3NetSendReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="来源子系统",type="String",isNecessary="Y",desc="来源子系统")
	private String source_system;
	
	@ZteSoftCommentAnnotationParam(name="优先级",type="String",isNecessary="Y",desc="优先级")
	private String priority_level; 
	
	@ZteSoftCommentAnnotationParam(name="接收号码",type="String",isNecessary="Y",desc="接收号码")
	private String mobile_number;
	
	@ZteSoftCommentAnnotationParam(name="发送内容",type="String",isNecessary="Y",desc="发送内容")
	private String sms_content; 
	
	@ZteSoftCommentAnnotationParam(name="重试次数",type="String",isNecessary="Y",desc="重试次数")
	private String repeat_time;
	
	@ZteSoftCommentAnnotationParam(name="网关类型",type="Integer",isNecessary="Y",desc="网关类型")
	private String send_gate_type; 
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.infservices.send3NetSms";
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getPriority_level() {
		return priority_level;
	}

	public void setPriority_level(String priority_level) {
		this.priority_level = priority_level;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getSms_content() {
		return sms_content;
	}

	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}

	public String getRepeat_time() {
		return repeat_time;
	}

	public void setRepeat_time(String repeat_time) {
		this.repeat_time = repeat_time;
	}

	public String getSend_gate_type() {
		return send_gate_type;
	}

	public void setSend_gate_type(String send_gate_type) {
		this.send_gate_type = send_gate_type;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
