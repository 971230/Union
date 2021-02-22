package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class PreCommitAPPReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="办理操作员",type="String",isNecessary="Y",desc="办理操作员")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="发起方系统标识")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="业务类型")
	private String busi_type;
	@ZteSoftCommentAnnotationParam(name="订单中心订单号",type="String",isNecessary="Y",desc="订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="办理操作员",type="String",isNecessary="Y",desc="办理操作员")
	private String deal_operator;
	@ZteSoftCommentAnnotationParam(name="办理操作点",type="String",isNecessary="Y",desc="办理操作点")
	private String deal_office_id;
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource_system() {
		return source_system;
	}
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
	public String getReceive_system() {
		return receive_system;
	}
	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDeal_operator() {
		return deal_operator;
	}
	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}
	public String getDeal_office_id() {
		return deal_office_id;
	}
	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.ztesoft.net.ecsord.params.ecaop.req.PreCommitReq";
	}
	
	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setNotNeedReqStrOrderId((map.get("notNeedReqStrOrderId") == null?"":(map.get("notNeedReqStrOrderId").toString())));
		setSerial_no((map.get("serial_no") == null?"":(map.get("serial_no").toString())));
		setTime((map.get("time") == null?"":(map.get("time").toString())));
		setSource_system((map.get("source_system") == null?"":(map.get("source_system").toString())));
		setReceive_system((map.get("receive_system") == null?"":(map.get("receive_system").toString())));
		setBusi_type((map.get("busi_type") == null?"":(map.get("busi_type").toString())));
		setOrder_id((map.get("order_id") == null?"":(map.get("order_id").toString())));
		setDeal_operator((map.get("deal_operator") == null?"":(map.get("deal_operator").toString())));
		setDeal_office_id((map.get("deal_office_id") == null?"":(map.get("deal_office_id").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("notNeedReqStrOrderId",getNotNeedReqStrOrderId());
		map.put("serial_no",getSerial_no());
		map.put("time",getTime());
		map.put("source_system",getSource_system());
		map.put("receive_system",getReceive_system());
		map.put("busi_type",getBusi_type());
		map.put("order_id",getOrder_id());
		map.put("deal_operator",getDeal_operator());
		map.put("deal_office_id",getDeal_office_id());
		return map;
	}
}
