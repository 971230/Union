package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;

public class BandUserDataReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "查询类型", type = "String", isNecessary = "Y", desc = "查询类型:1服务号码 2上网账号")
	private String query_type="1";
	@ZteSoftCommentAnnotationParam(name = "服务号码或上网账号", type = "String", isNecessary = "Y", desc = "服务号码或上网账号")
	private String query_data;
	@ZteSoftCommentAnnotationParam(name = "新速率编码", type = "String", isNecessary = "Y", desc = "新速率编码")
	private String rate;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getQuery_data() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
	}

	public void setQuery_data(String query_data) {
		this.query_data = query_data;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZJInfServices.bandUserDataReq";
	}

}
