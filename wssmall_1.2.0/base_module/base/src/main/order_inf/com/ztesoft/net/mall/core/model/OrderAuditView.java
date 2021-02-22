package com.ztesoft.net.mall.core.model;

/**
 * 订单审核
 * 
 * @author wui
 */
public class OrderAuditView extends OrderAudit {

	
	String from_username = "";
	String to_username = "";
	String to_mgr_username = "";
	String apply_message = "";// 申请原因
	String state_name = "";
	String audit_type_name = "";
	String founder;
	
	public String getFrom_username() {
		return from_username;
	}
	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}
	public String getTo_username() {
		return to_username;
	}
	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
	public String getTo_mgr_username() {
		return to_mgr_username;
	}
	public void setTo_mgr_username(String to_mgr_username) {
		this.to_mgr_username = to_mgr_username;
	}
	@Override
	public String getApply_message() {
		return apply_message;
	}
	@Override
	public void setApply_message(String apply_message) {
		this.apply_message = apply_message;
	}
	@Override
	public String getState_name() {
		return state_name;
	}
	@Override
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	@Override
	public String getAudit_type_name() {
		return audit_type_name;
	}
	@Override
	public void setAudit_type_name(String audit_type_name) {
		this.audit_type_name = audit_type_name;
	}
	public String getFounder() {
		return founder;
	}
	public void setFounder(String founder) {
		this.founder = founder;
	}
	
	

}