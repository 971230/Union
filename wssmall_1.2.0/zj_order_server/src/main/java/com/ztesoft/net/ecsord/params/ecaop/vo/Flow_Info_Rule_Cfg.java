package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

public class Flow_Info_Rule_Cfg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7177117080686969972L;

	private String rule_id;
	
	private String rule_name;
	
	private String rule_index;
	
	private String rule_code;
	
	private String contact_admin;

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getRule_index() {
		return rule_index;
	}

	public void setRule_index(String rule_index) {
		this.rule_index = rule_index;
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

	public String getContact_admin() {
		return contact_admin;
	}

	public void setContact_admin(String contact_admin) {
		this.contact_admin = contact_admin;
	}
}
