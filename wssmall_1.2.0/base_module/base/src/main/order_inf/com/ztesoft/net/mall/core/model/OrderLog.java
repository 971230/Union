package com.ztesoft.net.mall.core.model;

/**
 * 订单日志
 * @author kingapex
 *2010-4-6下午05:24:16
 */
public class OrderLog {
	 	
	private String  log_id;		
	private String order_id;	
	private String op_id;		
	private String op_name;		
	private String message;		
	private String op_time;
	private String action;//订单确认、订单分派、订单发货、订单确认收货等
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String logId) {
		log_id = logId;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getOp_id() {
		return op_id;
	}
	public void setOp_id(String opId) {
		op_id = opId;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String opName) {
		op_name = opName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOp_time() {
		return op_time;
	}
	public void setOp_time(String opTime) {
		op_time = opTime;
	}
 
	
}
