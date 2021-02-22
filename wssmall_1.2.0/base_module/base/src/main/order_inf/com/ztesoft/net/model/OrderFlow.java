package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 订单处理流程
 * @作者 MoChunrun
 * @创建日期 2014-6-10 
 * @版本 V 1.0
 */
public class OrderFlow implements Serializable {

	private String flow_id;
	private String name;
	private String type_code;
	private String object_id;
	private String create_time;
	private String service_type;
	
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
}
