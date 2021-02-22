package com.ztesoft.net.ecsord.params.ecaop.vo;

public class OrderStatusQryVO {

	private String order_status;//0 预订单生成; 4 已作废;8 正在执行;9 已竣工

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
}
