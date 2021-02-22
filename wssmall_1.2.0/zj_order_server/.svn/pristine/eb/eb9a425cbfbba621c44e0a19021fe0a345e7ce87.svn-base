package com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class OrderInfoBackfillReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	private ORDER_INFO_BACKFILL_REQ ORDER_INFO_BACKFILL_REQ;

	public ORDER_INFO_BACKFILL_REQ getORDER_INFO_BACKFILL_REQ() {
		return ORDER_INFO_BACKFILL_REQ;
	}

	public void setORDER_INFO_BACKFILL_REQ(ORDER_INFO_BACKFILL_REQ oRDER_INFO_BACKFILL_REQ) {
		ORDER_INFO_BACKFILL_REQ = oRDER_INFO_BACKFILL_REQ;
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
		return "orderInfoBackfill";
	}

}