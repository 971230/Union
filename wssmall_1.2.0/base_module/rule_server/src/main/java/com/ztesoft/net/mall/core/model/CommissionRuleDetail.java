package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 规则汇总记录
 * @作者 MoChunrun
 * @创建日期 2013-12-18 
 * @版本 V 1.0
 */
public class CommissionRuleDetail implements Serializable {

	private String rule_detail_id;
	private String create_time;
	private String status;
	private String cml_user_type;
	private String service_code;
	private String service_name;
	private Double amount;
	private String cml_user_id;
	private String cml_user_name;
	private String cml_org_id;
	private String cml_org_name;
	private String cml_lan_code;
	private String cml_lan_name;
	private String detail_id;
	private String detail_type;
	private String rel_order_id;
	private String comments;
	private String rule_id;
	private String plan_id;
	private String eff_time;
	private String exp_time;
	private String source_from;
	private String pay_user_id;
	
	public String getRule_detail_id() {
		return rule_detail_id;
	}
	public void setRule_detail_id(String rule_detail_id) {
		this.rule_detail_id = rule_detail_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCml_user_type() {
		return cml_user_type;
	}
	public void setCml_user_type(String cml_user_type) {
		this.cml_user_type = cml_user_type;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCml_user_id() {
		return cml_user_id;
	}
	public void setCml_user_id(String cml_user_id) {
		this.cml_user_id = cml_user_id;
	}
	public String getCml_user_name() {
		return cml_user_name;
	}
	public void setCml_user_name(String cml_user_name) {
		this.cml_user_name = cml_user_name;
	}
	public String getCml_org_id() {
		return cml_org_id;
	}
	public void setCml_org_id(String cml_org_id) {
		this.cml_org_id = cml_org_id;
	}
	public String getCml_org_name() {
		return cml_org_name;
	}
	public void setCml_org_name(String cml_org_name) {
		this.cml_org_name = cml_org_name;
	}
	public String getCml_lan_code() {
		return cml_lan_code;
	}
	public void setCml_lan_code(String cml_lan_code) {
		this.cml_lan_code = cml_lan_code;
	}
	public String getCml_lan_name() {
		return cml_lan_name;
	}
	public void setCml_lan_name(String cml_lan_name) {
		this.cml_lan_name = cml_lan_name;
	}
	public String getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}
	public String getDetail_type() {
		return detail_type;
	}
	public void setDetail_type(String detail_type) {
		this.detail_type = detail_type;
	}
	public String getRel_order_id() {
		return rel_order_id;
	}
	public void setRel_order_id(String rel_order_id) {
		this.rel_order_id = rel_order_id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getEff_time() {
		return eff_time;
	}
	public void setEff_time(String eff_time) {
		this.eff_time = eff_time;
	}
	public String getExp_time() {
		return exp_time;
	}
	public void setExp_time(String exp_time) {
		this.exp_time = exp_time;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getPay_user_id() {
		return pay_user_id;
	}
	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}

}
