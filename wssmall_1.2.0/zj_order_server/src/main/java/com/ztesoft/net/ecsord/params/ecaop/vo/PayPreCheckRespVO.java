package com.ztesoft.net.ecsord.params.ecaop.vo;

public class PayPreCheckRespVO {
	private String order_id ;
	
	private String order_pay_status ;//01不可支付;02 可支付; 
	
	private String order_pay_msg ;//不能支付时，返回具体信息。成功时，返回”可支付;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_pay_status() {
		return order_pay_status;
	}

	public void setOrder_pay_status(String order_pay_status) {
		this.order_pay_status = order_pay_status;
	}

	public String getOrder_pay_msg() {
		return order_pay_msg;
	}

	public void setOrder_pay_msg(String order_pay_msg) {
		this.order_pay_msg = order_pay_msg;
	}
	
	
}
