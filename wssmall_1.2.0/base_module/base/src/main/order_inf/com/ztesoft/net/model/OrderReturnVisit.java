package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 订单回访记录
 * @作者 MoChunrun
 * @创建日期 2014-6-30 
 * @版本 V 1.0
 */
public class OrderReturnVisit implements Serializable {

	private String visit_id;
	private String type_id;
	private String content;
	private String create_time;
	private String user_id;
	private String order_id;
	private String todo_list_id;
	private String typename;
	private String flow_name;
	private String user_name;
	private String return_method;
	private String method_name;
	
	@NotDbField
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getReturn_method() {
		return return_method;
	}
	public void setReturn_method(String return_method) {
		this.return_method = return_method;
	}
	public String getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(String visit_id) {
		this.visit_id = visit_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getTodo_list_id() {
		return todo_list_id;
	}
	public void setTodo_list_id(String todo_list_id) {
		this.todo_list_id = todo_list_id;
	}
	@NotDbField
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	@NotDbField
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
