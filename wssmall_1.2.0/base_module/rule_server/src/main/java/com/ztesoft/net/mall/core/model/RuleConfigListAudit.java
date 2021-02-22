package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;

public class RuleConfigListAudit implements Serializable {

	private String list_id;
	private String rule_id;
	private String list_name;
	private Integer sort;
	private String batch_id;
	private String batch_time;
	private String status;
	
	private List<RuleCondAudit> ruleCondAuditList;
	private List<RuleObj> ruleObjList;
	private RuleActionAudit ruleActionAudit;
	private List<RuleConfigConstAudit> constAuditList;
	@NotDbField
	public List<RuleConfigConstAudit> getConstAuditList() {
		return constAuditList;
	}
	public void setConstAuditList(List<RuleConfigConstAudit> constAuditList) {
		this.constAuditList = constAuditList;
	}
	@NotDbField
	public List<RuleCondAudit> getRuleCondAuditList() {
		return ruleCondAuditList;
	}
	public void setRuleCondAuditList(List<RuleCondAudit> ruleCondAuditList) {
		this.ruleCondAuditList = ruleCondAuditList;
	}
	@NotDbField
	public RuleActionAudit getRuleActionAudit() {
		return ruleActionAudit;
	}
	public void setRuleActionAudit(RuleActionAudit ruleActionAudit) {
		this.ruleActionAudit = ruleActionAudit;
	}
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getList_name() {
		return list_name;
	}
	public void setList_name(String list_name) {
		this.list_name = list_name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public List<RuleObj> getRuleObjList() {
		return ruleObjList;
	}
	public void setRuleObjList(List<RuleObj> ruleObjList) {
		this.ruleObjList = ruleObjList;
	}
	
	
}
