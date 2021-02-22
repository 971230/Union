package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;

/**
 * 规则关系表
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public class RuleRel implements Serializable {

	private String rule_id;
	private String relyon_rule_id;
	private Integer priority;
	private Integer rel_type;
	private String create_time;
	private String plan_id;
	private String auto_exe;
	
	public String getAuto_exe() {
		return auto_exe;
	}
	public void setAuto_exe(String auto_exe) {
		this.auto_exe = auto_exe;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getRelyon_rule_id() {
		return relyon_rule_id;
	}
	public void setRelyon_rule_id(String relyon_rule_id) {
		this.relyon_rule_id = relyon_rule_id;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getRel_type() {
		return rel_type;
	}
	public void setRel_type(Integer rel_type) {
		this.rel_type = rel_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	
}
