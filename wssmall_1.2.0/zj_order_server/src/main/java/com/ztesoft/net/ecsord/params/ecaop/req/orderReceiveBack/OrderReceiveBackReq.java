package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class OrderReceiveBackReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	private ORDER_RECEIVE_BACK_REQ ORDER_RECEIVE_BACK_REQ;

	public void setORDER_RECEIVE_BACK_REQ(ORDER_RECEIVE_BACK_REQ ORDER_RECEIVE_BACK_REQ) {
		this.ORDER_RECEIVE_BACK_REQ = ORDER_RECEIVE_BACK_REQ;
	}

	public ORDER_RECEIVE_BACK_REQ getORDER_RECEIVE_BACK_REQ() {
		return ORDER_RECEIVE_BACK_REQ;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "order.Receive.Back";
	}

}