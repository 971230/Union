package com.ztesoft.net.mall.core.model;

/**
 * 外系统订单查询参数
 * 
 * @author wui
 */
public class OrderOuterQueryParam implements java.io.Serializable {
	private String state;
	private String type;
	private String batch_id;
	private String order_id;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}