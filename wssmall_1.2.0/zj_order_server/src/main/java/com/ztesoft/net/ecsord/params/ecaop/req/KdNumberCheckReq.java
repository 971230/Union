package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import net.sf.json.JSONObject;

public class KdNumberCheckReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单中心订单ID", type = "String", isNecessary = "Y", desc = "order_id：订单中心订单ID")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "流水号", type = "String", isNecessary = "Y", desc = "serial_num：流水号")
	private String serial_num;

	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "operator_id：操作员")
	private String operator_id;

	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "office_id：操作点")
	private String office_id;

	@ZteSoftCommentAnnotationParam(name = "JSON参数", type = "String", isNecessary = "Y", desc = "msg：参数")
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
	        serial_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
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
	    }else{
	        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
	        String AUTH_ACCT_ID = "";// 宽带认证账号
	        String SERIAL_NUMBER = "";// 宽带统一编码
	        if (orderTree.getOrderAdslBusiRequest().size() > 0) {
	            AUTH_ACCT_ID = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_account();
	            SERIAL_NUMBER = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_number();
	        }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("SERVICE_CLASS_CODE", "0040");
	        map.put("AUTH_ACCT_ID", AUTH_ACCT_ID);
	        map.put("SERIAL_NUMBER", SERIAL_NUMBER);
	        map.put("PARA_LIST", new ArrayList());
	        
	        JSONObject json = JSONObject.fromObject(map);
	        msg = json;
	        return msg;
	    }
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.kdNumberCheck";
	}

}