package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则对象属性
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleObjAttr implements Serializable {

	private String attr_id;
	private String obj_id;
	private String attr_code;
	private String attr_name;
	private String attr_type;
	private String status_cd;
	private String create_date;
	private String create_user;
	private String status_date;
	private String status_user;
	private String remark;
	private String ele_type;
	private String stype_code;
	
	/**
	 * 用于判断是否已选中
	 */
	private String rule_id;
	
	@NotDbField
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getStatus_user() {
		return status_user;
	}
	public void setStatus_user(String status_user) {
		this.status_user = status_user;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEle_type() {
		return ele_type;
	}
	public void setEle_type(String ele_type) {
		this.ele_type = ele_type;
	}
	public String getStype_code() {
		return stype_code;
	}
	public void setStype_code(String stype_code) {
		this.stype_code = stype_code;
	}
	
}
