package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class OrderInfoRefundUpdateReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "唯一的接口流水号", type = "String", isNecessary = "Y", desc = "唯一的接口流水号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String busi_type;
	@ZteSoftCommentAnnotationParam(name = "订单中心订单号", type = "String", isNecessary = "N", desc = "订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "外系统订单号", type = "String", isNecessary = "Y", desc = "订单同步时，外系统同步订单中心的订单号")
	private String out_order_id;
	
	//deal_operator,deal_office_id,oper_type在接口报文中存在，但实际还未使用
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "N", desc = "办理操作员")
	private String deal_operator;
	
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "N", desc = "办理操作点")
	private String deal_office_id;
	
	@ZteSoftCommentAnnotationParam(name = "操作类型", type = "String", isNecessary = "N", desc = "0 退单 1 退款 2 先触发退单，再触发退款")
	private String oper_type;
	
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

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.orderInfoRefundUpdateReq";
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

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	
}
