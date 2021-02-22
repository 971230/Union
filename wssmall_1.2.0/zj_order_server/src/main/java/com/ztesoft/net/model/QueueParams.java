package com.ztesoft.net.model;

import java.io.Serializable;

public class QueueParams  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;	//内部订单号
	private String queue_switch;	
	private String queue_status;
	private String queue_no;	
	private String card_mate_code;	
	private String card_mate_status;
	private String start_time;
	private String end_time;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getQueue_switch() {
		return queue_switch;
	}
	public void setQueue_switch(String queue_switch) {
		this.queue_switch = queue_switch;
	}
	public String getQueue_status() {
		return queue_status;
	}
	public void setQueue_status(String queue_status) {
		this.queue_status = queue_status;
	}
	public String getQueue_no() {
		return queue_no;
	}
	public void setQueue_no(String queue_no) {
		this.queue_no = queue_no;
	}
	public String getCard_mate_code() {
		return card_mate_code;
	}
	public void setCard_mate_code(String card_mate_code) {
		this.card_mate_code = card_mate_code;
	}
	public String getCard_mate_status() {
		return card_mate_status;
	}
	public void setCard_mate_status(String card_mate_status) {
		this.card_mate_status = card_mate_status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}	
	
	
	
}
