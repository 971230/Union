package com.ztesoft.net.mall.core.model;


/**
 * 订单异常状态定义实体
 * @author hu.yi
 * @date 2014.03.04
 */
public class OrderException {

	private String exception_id;
	private String exception_code;
	private String exception_name;
	private String comments;
	private String source_from;
	private String create_date;
	
	
	
	public String getException_id() {
		return exception_id;
	}
	public void setException_id(String exception_id) {
		this.exception_id = exception_id;
	}
	public String getException_code() {
		return exception_code;
	}
	public void setException_code(String exception_code) {
		this.exception_code = exception_code;
	}
	public String getException_name() {
		return exception_name;
	}
	public void setException_name(String exception_name) {
		this.exception_name = exception_name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}
