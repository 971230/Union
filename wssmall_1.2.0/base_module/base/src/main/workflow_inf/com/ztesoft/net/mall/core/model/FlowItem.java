package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class FlowItem implements Serializable {
//	 item_id              number(9)                       not null,
//	   flow_id              number(9)                       not null,
//	   item_code            VARCHAR2(64)                    not null,
//	   item_name            VARCHAR2(64)                    not null,
//	   item_desc            VARCHAR2(500)                   not null,
//	   state                VARCHAR2(3)                     not null,
//	   processor            VARCHAR2(500)                   not null,
//	   audit_user           VARCHAR2(64),
//	   item_order           number(3)                       not null,

	
	private Integer flow_id ;
	private Integer item_id ;
	
	private String item_name ;
	private String item_code ;
	private String item_desc ;
	private String state ;
	
	private String processor ;
	private String audit_user ;
	private Integer item_order ;
	
	public Integer getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(Integer flow_id) {
		this.flow_id = flow_id;
	}
	public Integer getItem_id() {
		return item_id;
	}
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getAudit_user() {
		return audit_user;
	}
	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}
	public Integer getItem_order() {
		return item_order;
	}
	public void setItem_order(Integer item_order) {
		this.item_order = item_order;
	}
	
	
}
