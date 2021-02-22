package com.ztesoft.check.model;

import java.io.Serializable;

public class BizFatorCfg implements Serializable{
	private String factor_id;
	private String factor_tpye;
	private String attr_code;
	private String attr_name;
	private String ele_type;
	private String stype_code;
	private String status;
	private String create_date;
	private String create_user;
	private String remark;
	
	//非数据库字段
	private String opt_type;
	private String pre_log;
	public String getFactor_id() {
		return factor_id;
	}
	public void setFactor_id(String factor_id) {
		this.factor_id = factor_id;
	}
	public String getFactor_tpye() {
		return factor_tpye;
	}
	public void setFactor_tpye(String factor_tpye) {
		this.factor_tpye = factor_tpye;
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
	public String getStype_code() {
		return stype_code;
	}
	public void setStype_code(String stype_code) {
		this.stype_code = stype_code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
