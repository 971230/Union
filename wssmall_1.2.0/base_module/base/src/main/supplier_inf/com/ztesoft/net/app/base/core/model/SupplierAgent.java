package com.ztesoft.net.app.base.core.model;

/**
 * 供货商代理商
 * 
 * @author chenlijun
 * 
 */
public class SupplierAgent {

	private String agent_id;
	private String agent_name;
	private String agent_type;
	private String agent_certificate_number;
	private String agent_attachment;
	private String register_time;
	private String record_state;

	private String supplier_id;

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_type() {
		return agent_type;
	}

	public void setAgent_type(String agent_type) {
		this.agent_type = agent_type;
	}

	public String getAgent_certificate_number() {
		return agent_certificate_number;
	}

	public void setAgent_certificate_number(String agent_certificate_number) {
		this.agent_certificate_number = agent_certificate_number;
	}

	public String getAgent_attachment() {
		return agent_attachment;
	}

	public void setAgent_attachment(String agent_attachment) {
		this.agent_attachment = agent_attachment;
	}

	public String getRegister_time() {
		return register_time;
	}

	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}

	public String getRecord_state() {
		return record_state;
	}

	public void setRecord_state(String record_state) {
		this.record_state = record_state;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

}
