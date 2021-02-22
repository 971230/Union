package com.ztesoft.rule.manager.action;

import org.apache.commons.lang.StringUtils;

public class RuleNodeVo {

	private String rule_id; // 规则ID
	private String rule_code; // 规则编号
	private String rule_name; // 规则名称
	private String plan_id; // 方案ID
	private String pid; // 父节点
	private String ass_id; // 组件ID
	private String priority; // 优先级
	private String auto_exe; // 是否自动执行0-自动，1-手动
	private String rel_type; // 依赖关系 0-并行 1-依赖  2-互斥 3-全部互斥
	
	public String getRel_type() {
		return rel_type;
	}
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	public String getAuto_exe() {
		return auto_exe;
	}
	public void setAuto_exe(String auto_exe) {
		if (StringUtils.isNotBlank(auto_exe) && auto_exe.equals("0")) {
			this.auto_exe = "自动";
		} else {
			this.auto_exe = "手动";
		}
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
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}	
	
}
