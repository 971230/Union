package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderStatusQueryReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="busi_type：同步时业务类型")
	private String busi_type;
	@ZteSoftCommentAnnotationParam(name="订单中心订单号",type="String",isNecessary="Y",desc="order_id：订单同步时，订单中心返回外系统的订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="外系统订单号",type="String",isNecessary="Y",desc="out_order_id：订单同步时，外系统同步订单中心的订单号")
	private String out_order_id;
	
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
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
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.orderStatusQuery";
	}
}
