package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
 
public class AuditOrderCancelReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no：序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="订单中心订单号",type="String",isNecessary="Y",desc="order_id：订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String deal_operator="";
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String deal_office_id="";
	@ZteSoftCommentAnnotationParam(name = "操作类型", type = "String", isNecessary = "Y", desc = "操作类型:"
			+ "0 退单（订单中心翻转状态）"
			+ "1 支付退款（调支付中心退款，订单中心翻转状态）"
			+ "2 补单（订单中心翻转状态）"
			+ "3 补款（订单中心翻转状态）"
			+ "4 退现金（订单中心翻转状态）")
	private String oper_type ; 
	 
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
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
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
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.auditOrderCancel";
	}

}
