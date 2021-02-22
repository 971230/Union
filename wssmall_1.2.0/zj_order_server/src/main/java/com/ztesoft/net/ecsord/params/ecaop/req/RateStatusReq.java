package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class RateStatusReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "流水号", type = "String", isNecessary = "Y", desc = "流水号")
	private String serial_num;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "参数主体", type = "String", isNecessary = "Y", desc = "参数主体")
	private String msg;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerial_num() {
		serial_num = notNeedReqStrOrderId;
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOperator_id() {
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			operator_id = OUT_OPERATOR;
		}else{
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtil.isEmpty(OUT_OFFICE)){
			office_id = OUT_OFFICE;
		}else{
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getMsg() {
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		Map  map = new HashMap();
		map.put("SERVICE_TYPE", "1");
		map.put("QUE_TYPE", "3");
		//map.put("QUE_TYPE_INFO", ordertree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num());
		map.put("QUE_TYPE_INFO", ordertree.getOrderAdslBusiRequest().get(0).getAdsl_number());
		//map.put("AREA_CODE", "");
		map.put("PROVINCE_CODE", "36");
		String city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (city.trim().length()!=3) {
			city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", city);
		}
		map.put("EPARCHY_CODE", city);
		/*List PARA = new ArrayList();
		map.put("PARA", PARA);*/
		JSONObject obj = JSONObject.fromObject(map);
		String MSG = JSON.toJSONString(obj);
		msg = MSG;
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.net.ecsord.params.ecaop.req.RateStatusQry";
	}
	
}
