package com.ztesoft.net.model;

import java.io.Serializable;

public class OrderWarning implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4542444893263522275L;
	private String warning_id;
	private String group_name;
	private String flow_id;
	private String order_origin;
	private String order_from;
	private String product_type;
	private String memo;
	private String warning_time_1;
	private String warning_time_2;
	private String warning_time_3;
	private String warning_time_4;
	private String create_user;
	private String create_time;
	private String update_user;
	private String update_time;
	private String source_from;
	
	public String getWarning_id() {
		return warning_id;
	}

	public void setWarning_id(String warning_id) {
		this.warning_id = warning_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getOrder_origin() {
		return order_origin;
	}

	public void setOrder_origin(String order_origin) {
		this.order_origin = order_origin;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getWarning_time_1() {
		return warning_time_1;
	}

	public void setWarning_time_1(String warning_time_1) {
		this.warning_time_1 = warning_time_1;
	}

	public String getWarning_time_2() {
		return warning_time_2;
	}

	public void setWarning_time_2(String warning_time_2) {
		this.warning_time_2 = warning_time_2;
	}

	public String getWarning_time_3() {
		return warning_time_3;
	}

	public void setWarning_time_3(String warning_time_3) {
		this.warning_time_3 = warning_time_3;
	}

	public String getWarning_time_4() {
		return warning_time_4;
	}

	public void setWarning_time_4(String warning_time_4) {
		this.warning_time_4 = warning_time_4;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
