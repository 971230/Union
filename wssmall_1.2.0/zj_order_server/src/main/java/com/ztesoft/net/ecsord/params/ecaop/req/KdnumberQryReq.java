package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class KdnumberQryReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "宽带账号", type = "String", isNecessary = "Y", desc = "宽带账号")
	private String serial_num;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "", type = "Object", isNecessary = "Y", desc = "")
	private Object msg;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_num() {
	    if(StringUtils.isNotEmpty(this.serial_num)){
	        return serial_num;
	    }else{
           serial_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_account();
           return serial_num;
	    }
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOperator_id() {
	    
	    if(StringUtils.isNotEmpty(this.operator_id)){
            return operator_id;
        }else{
            operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
            return operator_id;
        }
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
	    if(StringUtils.isNotEmpty(this.office_id)){
            return office_id;
        }else{
            office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
            return office_id;
        }
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public Object getMsg() {
	    if(this.msg != null){
	       return msg; 
	    }else {
	        Map map = new HashMap();
	        map.put("QRY_TYPE", "2");
	        map.put("SERVICE_CLASS_CODE", "0040");
	        JSONObject jsonObject = JSONObject.fromObject(map);
	        msg = jsonObject;
	        return msg;
        }
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.kdnumberQryReq";
	}
	
}
