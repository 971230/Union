package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;
import java.util.List;

public class PlanRule implements Serializable {

	//规则信息================================
	private String rule_id;
	private String rule_code;
	private String rule_name;
	private String resource_type;
	private String rule_script;
	private String status_cd;
	private String pid;
	private String action_id;//这什值需要在执行的时后才set进去
	//规则信息================================
	
	//规则关系信息==============================
	private Integer rel_type;
	private String relyon_rule_id;
	private String plan_id;
	private Integer auto_exe;
	private Integer priority;
	//规则关系信息==============================
	
	//规则组件信息===========================
	private String exe_path;
	
	private boolean execute;//是否执行了业务组件
	
	//方案信息================================
	String tache_code;					//方案对应的环节编码
	
	public boolean isExecute() {
		return execute;
	}
	public void setExecute(boolean execute) {
		this.execute = execute;
	}
	/**
	 * 规则常量
	 */
	private List<RuleConsts> consts;
	
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
	public String getRule_script() {
		return rule_script;
	}
	public void setRule_script(String rule_script) {
		this.rule_script = rule_script;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getRel_type() {
		return rel_type;
	}
	public void setRel_type(Integer rel_type) {
		this.rel_type = rel_type;
	}
	public String getRelyon_rule_id() {
		return relyon_rule_id;
	}
	public void setRelyon_rule_id(String relyon_rule_id) {
		this.relyon_rule_id = relyon_rule_id;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getExe_path() {
		return exe_path;
	}
	public void setExe_path(String exe_path) {
		this.exe_path = exe_path;
	}
	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public Integer getAuto_exe() {
		return auto_exe;
	}
	public void setAuto_exe(Integer auto_exe) {
		this.auto_exe = auto_exe;
	}
	public List<RuleConsts> getConsts() {
		return consts;
	}
	public void setConsts(List<RuleConsts> consts) {
		this.consts = consts;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	
}
