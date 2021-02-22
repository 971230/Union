package com.ztesoft.net.model;

import java.io.Serializable;

public class QueueCardMateVo implements Serializable{
	private String queue_card_mate_id;//主键
	private String queue_code;//队列编码
	private String card_mate_code;//写卡机编码
	private String describe;//描述
	private String card_mate_status;//写卡机状态
	private String create_time;//创建时间
	private String curr_order_id;//当前订单id
	private String source_from;//来源
	
	public String getQueue_code() {
		return queue_code;
	}
	public void setQueue_code(String queue_code) {
		this.queue_code = queue_code;
	}
	public String getCard_mate_code() {
		return card_mate_code;
	}
	public void setCard_mate_code(String card_mate_code) {
		this.card_mate_code = card_mate_code;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCard_mate_status() {
		return card_mate_status;
	}
	public void setCard_mate_status(String card_mate_status) {
		this.card_mate_status = card_mate_status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCurr_order_id() {
		return curr_order_id;
	}
	public void setCurr_order_id(String curr_order_id) {
		this.curr_order_id = curr_order_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getQueue_card_mate_id() {
		return queue_card_mate_id;
	}
	public void setQueue_card_mate_id(String queue_card_mate_id) {
		this.queue_card_mate_id = queue_card_mate_id;
	}
	
	
}
