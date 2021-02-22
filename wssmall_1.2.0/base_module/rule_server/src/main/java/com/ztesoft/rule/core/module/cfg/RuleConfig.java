package com.ztesoft.rule.core.module.cfg;

import org.drools.builder.ResourceType;


/**
 * 规则-Rule表数据
 * @author easonwu
 *
 */
public class RuleConfig {
	//规则基本信息
	private String rule_id;
	private String plan_id;
	private String rule_code;
	private String rule_name;
	private String priority;
	private String resource_type;
	private String impl_type;
	private String rule_script;
	private String exp_date;
	private String eff_date;
	
	//规则属性信息
//	private RuleAttr ruleAttr ;
	
	private String no_loop;
	private String ruleflow_group;
	private String activation_group;
	private String agenda_group;
	private String auto_focus;
	
	
	private ResourceType localResourceType ;


	public ResourceType getResourceType() {
		if(localResourceType == null )
			localResourceType =  ResourceType.getResourceType(resource_type.toUpperCase());
		
		return localResourceType ;
	}
	
	
	public ResourceType getLocalResourceType() {
		return localResourceType;
	}


	public void setLocalResourceType(ResourceType localResourceType) {
		this.localResourceType = localResourceType;
	}


	public String getNo_loop() {
		return no_loop;
	}

	public void setNo_loop(String no_loop) {
		this.no_loop = no_loop;
	}

	public String getRuleflow_group() {
		return ruleflow_group;
	}

	public void setRuleflow_group(String ruleflow_group) {
		this.ruleflow_group = ruleflow_group;
	}

	public String getActivation_group() {
		return activation_group;
	}

	public void setActivation_group(String activation_group) {
		this.activation_group = activation_group;
	}

	public String getAgenda_group() {
		return agenda_group;
	}

	public void setAgenda_group(String agenda_group) {
		this.agenda_group = agenda_group;
	}

	public String getAuto_focus() {
		return auto_focus;
	}

	public void setAuto_focus(String auto_focus) {
		this.auto_focus = auto_focus;
	}

	public RuleConfig(){
		
	}
	
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
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
	
}
