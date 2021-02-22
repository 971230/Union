package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.UNIBSSBODYRELVO;

import params.ZteRequest;

public class RelSelectionNumReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "资源中心能力名", type = "String", isNecessary = "Y", desc = "资源中心服务名")
	private String api;
	@ZteSoftCommentAnnotationParam(name = "资源中心服务名", type = "String", isNecessary = "Y", desc = "资源中心服务名")
	private String service_name;
	@ZteSoftCommentAnnotationParam(name = "资源中心系统名", type = "String", isNecessary = "Y", desc = "资源中心系统名")
	private String system_id;
	@ZteSoftCommentAnnotationParam(name = "资源中心报文体", type = "String", isNecessary = "Y", desc = "资源中心报文体")
	private UNIBSSBODYRELVO unibssbody;
	
	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getApi() {
		return StringUtil.isEmpty(api)?"numberCenter":api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getService_name() {
		return StringUtil.isEmpty(service_name)?"relSelectionNum":service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getSystem_id() {
		return StringUtil.isEmpty(system_id)?"resourceCenter":system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	

	public UNIBSSBODYRELVO getUnibssbody() {
		return unibssbody;
	}

	public void setUnibssbody(UNIBSSBODYRELVO unibssbody) {
		this.unibssbody = unibssbody;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.relselectionnum";
	}
}
