package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;


/**
 * 订单审核
 * @author wui
 */
public class OrderAudit implements java.io.Serializable {
	private String order_id;
	private Integer sequ;
	private String from_userid;
	private String to_userid;
	private String to_mgr_userid;
	private String create_date;
	private String state_date;
	private String state;
	private String apply_message;
	private String deal_message;
	private String mgr_deal_message;
	
	
	private String P_audit_message;
	private String p_audit_state;
	private String audit_type;
	
	private String audit_type_name;
	private String state_name;
	
	
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	public String getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDeal_message() {
		return deal_message;
	}
	public void setDeal_message(String deal_message) {
		this.deal_message = deal_message;
	}
	public String getTo_mgr_userid() {
		return to_mgr_userid;
	}
	public void setTo_mgr_userid(String to_mgr_userid) {
		this.to_mgr_userid = to_mgr_userid;
	}
	public String getApply_message() {
		return apply_message;
	}
	public void setApply_message(String apply_message) {
		this.apply_message = apply_message;
	}
	public String getMgr_deal_message() {
		return mgr_deal_message;
	}
	public void setMgr_deal_message(String mgr_deal_message) {
		this.mgr_deal_message = mgr_deal_message;
	}
	
	@NotDbField
	public String getP_audit_message() {
		return P_audit_message;
	}
	@NotDbField
	public void setP_audit_message(String p_audit_message) {
		P_audit_message = p_audit_message;
	}
	@NotDbField
	public String getP_audit_state() {
		return p_audit_state;
	}
	@NotDbField
	public void setP_audit_state(String p_audit_state) {
		this.p_audit_state = p_audit_state;
	}
	public Integer getSequ() {
		return sequ;
	}
	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}
	public String getState_date() {
		return state_date;
	}
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getAudit_type() {
		return audit_type;
	}
	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}
	@NotDbField
	public String getAudit_type_name() {
		return audit_type_name;
	}
	@NotDbField
	public void setAudit_type_name(String audit_type_name) {
		this.audit_type_name = audit_type_name;
	}
	
	@NotDbField
	public String getState_name() {
		
		return state_name;
	}
	@NotDbField
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	
	
	
}