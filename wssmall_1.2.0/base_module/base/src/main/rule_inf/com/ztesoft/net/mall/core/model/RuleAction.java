package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 规则动作
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleAction implements Serializable {

	private String action_id;
	private String rule_id;
	private String action_script;
	private Integer action_index;
	private String list_id;
	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getAction_script() {
		return action_script;
	}
	public void setAction_script(String action_script) {
		this.action_script = action_script;
	}
	public Integer getAction_index() {
		return action_index;
	}
	public void setAction_index(Integer action_index) {
		this.action_index = action_index;
	}
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	
}
