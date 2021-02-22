package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WeiBoShortUrlReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "采用OAuth授权方式为必填参数，OAuth授权后获得", type = "String", isNecessary = "Y", desc = "采用OAuth授权方式为必填参数，OAuth授权后获得")
	private String access_token;
	@ZteSoftCommentAnnotationParam(name = "需要转换的长链接，需要URLencoded，最多不超过20个。", type = "String", isNecessary = "Y", desc = "需要转换的长链接，需要URLencoded，最多不超过20个。")
	private String url_long;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getUrl_long() {
		return url_long;
	}
	public void setUrl_long(String url_long) {
		this.url_long = url_long;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.weiBoShortUrlReq";
	}
}
