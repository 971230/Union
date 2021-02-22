package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class ObjectQryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String service_type;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperator_id() {
	    if(StringUtil.isEmpty(this.operator_id)){
	        String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
	        if(!StringUtil.isEmpty(OUT_OPERATOR)){
	            operator_id = OUT_OPERATOR;
	        }else{
	            operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
	        }
	        return operator_id;
	    }else{
	        return this.operator_id;
	    }
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getService_type() {
	    if(StringUtil.isEmpty(this.service_type)){
	        service_type="6130";
	        return service_type;
	    }else{
	        return this.service_type;
	    }
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq";
	}
	
}
