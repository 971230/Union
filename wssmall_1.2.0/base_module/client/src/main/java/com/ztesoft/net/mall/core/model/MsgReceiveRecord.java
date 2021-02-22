package com.ztesoft.net.mall.core.model;

import java.util.Date;







/**
 * MQ接收的消息
 */
public class MsgReceiveRecord implements java.io.Serializable {
	
	private String record_id;
	private Date create_date; 
	private String topic;
	private String action_name;
	private int msg_key; 
	private String receive_obj;
	private String source_from;
	
	
	
	
	
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}
	public int getMsg_key() {
		return msg_key;
	}
	public void setMsg_key(int msg_key) {
		this.msg_key = msg_key;
	}
	
	public String getReceive_obj() {
		return receive_obj;
	}
	public void setReceive_obj(String receive_obj) {
		this.receive_obj = receive_obj;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	

	
}