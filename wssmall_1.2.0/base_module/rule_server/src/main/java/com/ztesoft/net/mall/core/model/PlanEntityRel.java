package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 方案参与者
 * @作者 MoChunrun
 * @创建日期 2014-2-27 
 * @版本 V 1.0
 */
public class PlanEntityRel implements Serializable {

	private String rel_id;
	private String rel_type;
	private String entity_type;
	private String entity_id;
	private String plan_id;
	private String eff_date;
	private String exp_date;
	private String create_date;
	private String status_date;
	private String status_cd;
	private String create_user;
	private String status_user;
	private String entity_sql;
	private String source_from;
	
	public String getRel_id() {
		return rel_id;
	}
	public void setRel_id(String rel_id) {
		this.rel_id = rel_id;
	}
	public String getRel_type() {
		return rel_type;
	}
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	public String getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getStatus_user() {
		return status_user;
	}
	public void setStatus_user(String status_user) {
		this.status_user = status_user;
	}
	public String getEntity_sql() {
		return entity_sql;
	}
	public void setEntity_sql(String entity_sql) {
		this.entity_sql = entity_sql;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
}
