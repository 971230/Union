package com.ztesoft.net.mall.core.workflow.util;

public class ModInfoAudit {
	
	private Integer flow_id ;
	private String flow_name ;
	private String flow_code ;
	private Integer flow_inst_id;
	private String flow_state ;
	
	private Integer item_id;
	private Integer item_inst_id ;
	private String item_name;
	
	private String apply_name;
	private String apply_dep;
	private String apply_date;
	private String audit_name;
	private String audit_dep;
	private String audit_state;
	private String audit_note;
	private String processor;
	private String apply_url;
	private String ref_obj_id;
	private Integer audit_buttons;
	private String audit_date;

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public Integer getAudit_buttons() {
		return audit_buttons;
	}

	public void setAudit_buttons(Integer audit_buttons) {
		this.audit_buttons = audit_buttons;
	}

	public Integer getFlow_inst_id() {
		return flow_inst_id;
	}

	public void setFlow_inst_id(Integer flow_inst_id) {
		this.flow_inst_id = flow_inst_id;
	}

	public String getRef_obj_id() {
		return ref_obj_id;
	}

	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}

	public Integer getItem_id() {
		return item_id;
	}
	
	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public Integer getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(Integer flow_id) {
		this.flow_id = flow_id;
	}
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
	public String getFlow_code() {
		return flow_code;
	}
	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}
	public Integer getItem_inst_id() {
		return item_inst_id;
	}
	public void setItem_inst_id(Integer item_inst_id) {
		this.item_inst_id = item_inst_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getApply_name() {
		return apply_name;
	}
	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}
	public String getApply_dep() {
		return apply_dep;
	}
	public void setApply_dep(String apply_dep) {
		this.apply_dep = apply_dep;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public String getAudit_name() {
		return audit_name;
	}
	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}
	public String getAudit_dep() {
		return audit_dep;
	}
	public void setAudit_dep(String audit_dep) {
		this.audit_dep = audit_dep;
	}
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	public String getAudit_note() {
		return audit_note;
	}
	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}

	public String getApply_url() {
		return apply_url;
	}

	public void setApply_url(String apply_url) {
		this.apply_url = apply_url;
	}
	
	public String getFlow_state() {
		return flow_state;
	}

	public void setFlow_state(String flow_state) {
		this.flow_state = flow_state;
	}
}
