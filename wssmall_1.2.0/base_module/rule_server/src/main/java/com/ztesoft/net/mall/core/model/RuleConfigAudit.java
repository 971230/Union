package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 规则基本信息
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleConfigAudit implements Serializable {

	private String rule_id;
	private String rule_code;
	private String rule_name;
	private String resource_type;
	private String impl_type;
	private String rule_script;
	private String exp_date;
	private String eff_date;
	private String rule_desc;
	private String create_date;
	private String create_user;
	private String status_date;
	private String status_user;
	private String status_cd;
	private String audit_status;
	private String batch_id;
	private String batch_time;
	private String audit_user_id;
	private String audit_user_name;
	private String list_id;
	
	private String rule_sys_flag;				//系统标识
	
	private String pid;
	private String ass_id;
	
	private String obj_id;
	private String never_run_flag;
		
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getAss_id() {
		return ass_id;
	}
	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getRule_code() {
		return rule_code;
	}
	public void setRule_code(String rule_code) {
		this.rule_code = rule_code;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getImpl_type() {
		return impl_type;
	}
	public void setImpl_type(String impl_type) {
		this.impl_type = impl_type;
	}
	public String getRule_script() {
		return rule_script;
	}
	public void setRule_script(String rule_script) {
		this.rule_script = rule_script;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getRule_desc() {
		return rule_desc;
	}
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
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
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getAudit_status() {
		return audit_status;
	}
	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
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
	public String getAudit_user_id() {
		return audit_user_id;
	}
	public void setAudit_user_id(String audit_user_id) {
		this.audit_user_id = audit_user_id;
	}
	public String getAudit_user_name() {
		return audit_user_name;
	}
	public void setAudit_user_name(String audit_user_name) {
		this.audit_user_name = audit_user_name;
	}
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	
	@NotDbField
	public String getRule_sys_flag() {
		return rule_sys_flag;
	}
	public void setRule_sys_flag(String rule_sys_flag) {
		this.rule_sys_flag = rule_sys_flag;
	}
	
	@NotDbField
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	
	@NotDbField
	public String getNever_run_flag() {
		return never_run_flag;
	}
	public void setNever_run_flag(String never_run_flag) {
		this.never_run_flag = never_run_flag;
	}
	
	
}
