package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则常量表
 * @作者 MoChunrun
 * @创建日期 2014-4-10 
 * @版本 V 1.0
 */
public class RuleConfigConstAudit implements Serializable {

	private String rule_id;
	private String list_id;
	private String action_id;
	private String obj_id;
	private String attr_id;
	private String const_value;
	private String const_name;
	private String batch_id;
	private String batch_time;
	private String status;
	private String attr_name;
	
	private String attr_code;
	private String obj_code;
	
	private String ele_type;
	
	@NotDbField
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	@NotDbField
	public String getObj_code() {
		return obj_code;
	}
	public void setObj_code(String obj_code) {
		this.obj_code = obj_code;
	}
	@NotDbField
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getConst_value() {
		return const_value;
	}
	public void setConst_value(String const_value) {
		this.const_value = const_value;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getBatch_time() {
		return batch_time;
	}
	public void setBatch_time(String batch_time) {
		this.batch_time = batch_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@NotDbField
	public String getEle_type() {
		return ele_type;
	}
	public void setEle_type(String ele_type) {
		this.ele_type = ele_type;
	}
	public String getConst_name() {
		return const_name;
	}
	public void setConst_name(String const_name) {
		this.const_name = const_name;
	}
	
}
