package zte.params.orderctn.req;

import java.util.Map;

import params.ZteRequest;
import zte.params.orderctn.resp.OrderCtnResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderCtnReq extends ZteRequest<OrderCtnResp> {

	private static final long serialVersionUID = 7600206282527276301L;

	@ZteSoftCommentAnnotationParam(name = "内部服务编码", type = "String", isNecessary = "Y", desc = "内部服务编码")
	private String serviceCode;

	@ZteSoftCommentAnnotationParam(name = "外部服务编码", type = "String", isNecessary = "Y", desc = "外部服务编码")
	private String outServiceCode;

	@ZteSoftCommentAnnotationParam(name = "请求报文", type = "String", isNecessary = "Y", desc = "请求报文")
	private String reqMsgStr;

	@ZteSoftCommentAnnotationParam(name = "请求报文版本", type = "String", isNecessary = "Y", desc = "请求报文版本")
	private String version;

	@ZteSoftCommentAnnotationParam(name = "请求Map", type = "Map", isNecessary = "Y", desc = "请求Map")
	private Map<String, Object> reqParamsMap;

	@ZteSoftCommentAnnotationParam(name = "ip", type = "String", isNecessary = "Y", desc = "ip")
	private String ip;

	@ZteSoftCommentAnnotationParam(name = "是否模版转换", type = "String", isNecessary = "Y", desc = "是否模版转换")
	private boolean isTemplateCoversion; 
	
	@ZteSoftCommentAnnotationParam(name = "消费类型", type = "String", isNecessary = "N", desc = "d:dubbo调用/m:mq")
	private String rpcType;
	
	@ZteSoftCommentAnnotationParam(name = "数字符串", type = "String", isNecessary = "N", desc = "淘宝数字符串")
	private String params;
	
	@ZteSoftCommentAnnotationParam(name = "外部订单号", type = "String", isNecessary = "N", desc = "外部订单号")
	private String outOrderId;

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getReqMsgStr() {
		return reqMsgStr;
	}

	public void setReqMsgStr(String reqMsgStr) {
		this.reqMsgStr = reqMsgStr;
	}

	@Override
	public String getVersion() {
		return version;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Object> getReqParamsMap() {
		return reqParamsMap;
	}

	public void setReqParamsMap(Map<String, Object> reqParamsMap) {
		this.reqParamsMap = reqParamsMap;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOutServiceCode() {
		return outServiceCode;
	}

	public void setOutServiceCode(String outServiceCode) {
		this.outServiceCode = outServiceCode;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteOrderCtnOpenService.orderCtn";
	}

	public boolean getIsTemplateCoversion() {
		return isTemplateCoversion;
	}

	public void setIsTemplateCoversion(boolean isTemplateCoversion) {
		this.isTemplateCoversion = isTemplateCoversion;
	}
	
	public String getRpcType() {
		return rpcType;
	}

	public void setRpcType(String rpcType) {
		this.rpcType = rpcType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
}
