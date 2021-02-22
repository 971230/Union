package com.ztesoft.net.mall.core.model;

/**
 * 订单异常关联表
 * @author hu.yi
 * @date 2014.03.04
 */
public class OrderExcepCollect {

	private String exception_id;
	private String order_id;
	private String comments;
	private String source_from;
	private String create_date;
	
	
	
	public String getException_id() {
		return exception_id;
	}
	public void setException_id(String exception_id) {
		this.exception_id = exception_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
