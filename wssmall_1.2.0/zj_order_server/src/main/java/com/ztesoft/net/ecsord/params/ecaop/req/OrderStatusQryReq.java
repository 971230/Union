package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderStatusQryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String bms_accept_id;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getBms_accept_id() {
	    if(StringUtils.isEmpty(this.bms_accept_id)){
	        bms_accept_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBssOrderId();
	    }
		return bms_accept_id;
	}
	public void setBms_accept_id(String bms_accept_id) {
		this.bms_accept_id = bms_accept_id;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZJInfServices.orderStatusQry";
	}
}