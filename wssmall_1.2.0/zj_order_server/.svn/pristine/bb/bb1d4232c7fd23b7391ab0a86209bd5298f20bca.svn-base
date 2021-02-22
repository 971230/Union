package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;

public class O2OStatusUpdateReq extends ZteRequest{

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
		if(null==MSGPKG){
			MSGPKGVO new_MSGPKG = new MSGPKGVO();
			PKGHEADVO PKG_HEAD = new PKGHEADVO();
			PKGBODYVO PKG_BODY = new PKGBODYVO();
			PKG_HEAD.setACTION_ID("M10001");
			PKG_BODY.setORDER_CODE(CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOut_tid());
			String refund_deal_type = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getRefund_deal_type();
			String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
			String active_flag = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderRealNameInfoBusiRequest().getActive_flag();
			if(!StringUtil.isEmpty(refund_deal_type)&&StringUtil.equals(refund_deal_type, "02")){
				PKG_BODY.setORDER_STATUS("09");
			}else if(!StringUtil.isEmpty(flow_trace_id)&&StringUtil.equals(flow_trace_id, "H")){
				PKG_BODY.setORDER_STATUS("04");
			}else if(!StringUtil.isEmpty(active_flag)&&(StringUtil.equals(active_flag, "2")||StringUtil.equals(active_flag, "3"))){
				PKG_BODY.setORDER_STATUS("05");
			}
			new_MSGPKG.setPKG_BODY(PKG_BODY);
			new_MSGPKG.setPKG_HEAD(PKG_HEAD);
			MSGPKG=new_MSGPKG;
		}
		
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
		return "com.zte.unicomService.zj.broadband.o2OStatusUpdateReq";
	}
}
