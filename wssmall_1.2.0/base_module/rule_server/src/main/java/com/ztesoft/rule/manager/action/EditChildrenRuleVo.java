/**
 * 
 */
package com.ztesoft.rule.manager.action;

/**
 * @author ZX
 * EditChildrenRuleVo.java
 * 2014-10-6
 */
public class EditChildrenRuleVo {

	private String attr_children_id; // 规则ID
	private String attr_rule_code; // 子规则编码
	private String attr_rule_name; // 子规则名称
	private String attr_priority; // 优先级
	private String attr_rel; // 依赖关系
	private String attr_relyon; // 依赖关系值
	private String attr_status; // 状态	
	private String attr_autoexe; // 执行方式
	
	public String getAttr_autoexe() {
		return attr_autoexe;
	}
	public void setAttr_autoexe(String attr_autoexe) {
		this.attr_autoexe = attr_autoexe;
	}
	public String getAttr_children_id() {
		return attr_children_id;
	}
	public void setAttr_children_id(String attr_children_id) {
		this.attr_children_id = attr_children_id;
	}
	public String getAttr_rule_code() {
		return attr_rule_code;
	}
	public void setAttr_rule_code(String attr_rule_code) {
		this.attr_rule_code = attr_rule_code;
	}
	public String getAttr_rule_name() {
		return attr_rule_name;
	}
	public void setAttr_rule_name(String attr_rule_name) {
		this.attr_rule_name = attr_rule_name;
	}
	public String getAttr_priority() {
		return attr_priority;
	}
	public void setAttr_priority(String attr_priority) {
		this.attr_priority = attr_priority;
	}
	public String getAttr_rel() {
		return attr_rel;
	}
	public void setAttr_rel(String attr_rel) {
		this.attr_rel = attr_rel;
	}
	public String getAttr_relyon() {
		return attr_relyon;
	}
	public void setAttr_relyon(String attr_relyon) {
		this.attr_relyon = attr_relyon;
	}
	public String getAttr_status() {
		return attr_status;
	}
	public void setAttr_status(String attr_status) {
		this.attr_status = attr_status;
	}
	
}
