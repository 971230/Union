package com.ztesoft.net.mall.core.action.order.orderc;

import com.ztesoft.net.mall.core.model.DeliveryFlow;

public class OrderHandleParam {

	private String orderId;
	private String orderStatus;
	private String hint;
	private DeliveryFlow flow;
	private String type_code;
	private String [] flow_group_id;
	private String [] flow_user_id;
	private int flag_status;
	private String flow_def_id;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public DeliveryFlow getFlow() {
		return flow;
	}
	public void setFlow(DeliveryFlow flow) {
		this.flow = flow;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public int getFlag_status() {
		return flag_status;
	}
	public void setFlag_status(int flag_status) {
		this.flag_status = flag_status;
	}
	public String[] getFlow_group_id() {
		return flow_group_id;
	}
	public void setFlow_group_id(String[] flow_group_id) {
		this.flow_group_id = flow_group_id;
	}
	public String[] getFlow_user_id() {
		return flow_user_id;
	}
	public void setFlow_user_id(String[] flow_user_id) {
		this.flow_user_id = flow_user_id;
	}
	public String getFlow_def_id() {
		return flow_def_id;
	}
	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}
	
}
