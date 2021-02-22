package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 订单流程定义
 * @作者 MoChunrun
 * @创建日期 2014-6-10 
 * @版本 V 1.0
 */
public class OrderFlowDef implements Serializable {

	private String flow_def_id;
	private String parent_id;
	private String flow_id;
	private String flow_name;
	private String flow_type;
	private String create_time;
	public String getFlow_def_id() {
		return flow_def_id;
	}
	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
	public String getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	
}
