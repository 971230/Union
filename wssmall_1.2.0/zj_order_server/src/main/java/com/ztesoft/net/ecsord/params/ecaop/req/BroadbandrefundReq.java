package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;

public class BroadbandrefundReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private MSGPKGVO MSGPKG;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public MSGPKGVO getMSGPKG() {
		MSGPKGVO new_MSGPKG = new MSGPKGVO();
		PKGHEADVO PKG_HEAD = new PKGHEADVO();
		PKGBODYVO PKG_BODY = new PKGBODYVO();
		PKG_HEAD.setACTION_ID("M10001");
		PKG_BODY.setORDER_CODE(CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOut_tid());
		PKG_BODY.setORDER_STATUS("06");
		new_MSGPKG.setPKG_BODY(PKG_BODY);
		new_MSGPKG.setPKG_HEAD(PKG_HEAD);
		MSGPKG=new_MSGPKG;
		return MSGPKG;
	}
	public void setMSGPKG(MSGPKGVO mSGPKG) {
		MSGPKG = mSGPKG;
	}
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.broadbandrefundReq";
	}
	
}
