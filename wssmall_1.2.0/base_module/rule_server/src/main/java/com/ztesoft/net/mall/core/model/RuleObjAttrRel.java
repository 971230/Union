package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则属性对应关系
 */
public class RuleObjAttrRel implements Serializable {
	
	private static final long serialVersionUID = 3612039616796289184L;
	
	private String rule_id;
	private String attr_id;
	private String obj_id;
	private String obj_code;
	private String obj_name;
	private String attr_code;
	private String attr_name;
	private String ele_type;
	private String ele_value;
	private String cond_type;			//规则条件类型，final_cond：不参与计算的条件， cal_cond:参与计算的条件
	private String source_from;
	
	private String rule_name; // 规则名称（ZX Add 2014-12-17）
	
	@NotDbField
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
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
	public String getEle_type() {
		return ele_type;
	}
	public void setEle_type(String ele_type) {
		this.ele_type = ele_type;
	}
	public String getEle_value() {
		return ele_value;
	}
	public void setEle_value(String ele_value) {
		this.ele_value = ele_value;
	}
	public String getObj_name() {
		return obj_name;
	}
	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCond_type() {
		return cond_type;
	}
	public void setCond_type(String cond_type) {
		this.cond_type = cond_type;
	}
	public String getObj_code() {
		return obj_code;
	}
	public void setObj_code(String obj_code) {
		this.obj_code = obj_code;
	}
}
