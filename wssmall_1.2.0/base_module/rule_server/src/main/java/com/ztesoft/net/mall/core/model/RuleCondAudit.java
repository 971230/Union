package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则条件
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleCondAudit implements Serializable {

	private String cond_id;
	private String rule_id;
	private String obj_id;
	private String attr_id;
	private String attr_script;
	private Integer attr_index;
	private String opt_type;
	private String pre_log;
	private String z_obj_type;
	private String z_attr_id;
	private String z_obj_id;
	private String z_value;
	private String left_brackets;
	private String right_brackets;
	private String z_cvalue;
	
	private String attr_cname;
	
	private String list_id;
	private String batch_id;
	private String batch_time;
	private String status;
	
	private String ele_type;					//联通自动化系统，规则界面配置
	
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	@NotDbField
	public String getAttr_cname() {
		return attr_cname;
	}
	public void setAttr_cname(String attr_cname) {
		this.attr_cname = attr_cname;
	}
	public String getCond_id() {
		return cond_id;
	}
	public void setCond_id(String cond_id) {
		this.cond_id = cond_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
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
	public String getAttr_script() {
		return attr_script;
	}
	public void setAttr_script(String attr_script) {
		this.attr_script = attr_script;
	}
	public Integer getAttr_index() {
		return attr_index;
	}
	public void setAttr_index(Integer attr_index) {
		this.attr_index = attr_index;
	}
	public String getOpt_type() {
		return opt_type;
	}
	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}
	public String getPre_log() {
		return pre_log;
	}
	public void setPre_log(String pre_log) {
		this.pre_log = pre_log;
	}
	public String getZ_obj_type() {
		return z_obj_type;
	}
	public void setZ_obj_type(String z_obj_type) {
		this.z_obj_type = z_obj_type;
	}
	public String getZ_attr_id() {
		return z_attr_id;
	}
	public void setZ_attr_id(String z_attr_id) {
		this.z_attr_id = z_attr_id;
	}
	public String getZ_obj_id() {
		return z_obj_id;
	}
	public void setZ_obj_id(String z_obj_id) {
		this.z_obj_id = z_obj_id;
	}
	public String getZ_value() {
		return z_value;
	}
	public void setZ_value(String z_value) {
		this.z_value = z_value;
	}
	public String getLeft_brackets() {
		return left_brackets;
	}
	public void setLeft_brackets(String left_brackets) {
		this.left_brackets = left_brackets;
	}
	public String getRight_brackets() {
		return right_brackets;
	}
	public void setRight_brackets(String right_brackets) {
		this.right_brackets = right_brackets;
	}
	public String getZ_cvalue() {
		return z_cvalue;
	}
	public void setZ_cvalue(String z_cvalue) {
		this.z_cvalue = z_cvalue;
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
	
}
