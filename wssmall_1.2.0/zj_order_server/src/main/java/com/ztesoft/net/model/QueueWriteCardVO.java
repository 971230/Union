package com.ztesoft.net.model;

import java.io.Serializable;

public class QueueWriteCardVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2935319061745586255L;
	private String queue_code;// 队列编码
	private String order_id;// 内部订单号
	private String create_time;// 进入队列时间
	private String open_account_status;// 开户状态,0:未开户、1:开户中、2:开户完成
	private String open_account_time;// 开户时间
	private String write_card_status;// 写卡状态,0:未写卡、1:写卡中、2:写卡完成
	private String write_card_time;// 写卡时间
	private String exception_type;// 异常类型
	private String exception_msg;// 异常原因
	private String queue_status;// 队列状态,0:正常、1:等待回退
	private String source_from;
	
	public String getQueue_code() {
		return queue_code;
	}
	public void setQueue_code(String queue_code) {
		this.queue_code = queue_code;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOpen_account_status() {
		return open_account_status;
	}
	public void setOpen_account_status(String open_account_status) {
		this.open_account_status = open_account_status;
	}
	public String getOpen_account_time() {
		return open_account_time;
	}
	public void setOpen_account_time(String open_account_time) {
		this.open_account_time = open_account_time;
	}
	public String getWrite_card_status() {
		return write_card_status;
	}
	public void setWrite_card_status(String write_card_status) {
		this.write_card_status = write_card_status;
	}
	public String getWrite_card_time() {
		return write_card_time;
	}
	public void setWrite_card_time(String write_card_time) {
		this.write_card_time = write_card_time;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public String getException_msg() {
		return exception_msg;
	}
	public void setException_msg(String exception_msg) {
		this.exception_msg = exception_msg;
	}
	public String getQueue_status() {
		return queue_status;
	}
	public void setQueue_status(String queue_status) {
		this.queue_status = queue_status;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
