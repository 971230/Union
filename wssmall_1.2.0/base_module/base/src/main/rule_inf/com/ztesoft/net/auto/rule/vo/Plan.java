package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Plan implements Serializable {

	@ZteSoftCommentAnnotationParam(name="方案ID",type="String",isNecessary="Y",desc="方案ID")
	private String plan_id;
	@ZteSoftCommentAnnotationParam(name="方案编码",type="String",isNecessary="Y",desc="方案编码")
	private String plan_code;
	@ZteSoftCommentAnnotationParam(name="方案名称",type="String",isNecessary="Y",desc="方案名称")
	private String plan_name;
	private String exec_time;
	private String exec_cycle;
	private String cycle_id;
	private String create_date;
	private String create_user;
	private String status_date;
	private String status_user;
	private String status_cd;
	private String financial_type;
	private String plan_type;
	private String year_type;
	private String ctrl_type;
	private String ctrl_val;
	private String run_type;
	private String service_type;
	@ZteSoftCommentAnnotationParam(name="方案开始行时间",type="String",isNecessary="Y",desc="方案开始时间")
	private String eff_date;
	@ZteSoftCommentAnnotationParam(name="方案结束时间",type="String",isNecessary="Y",desc="方案结束时间")
	private String exp_date;
	
	private String pay_user_id;
	private String catalogue_id;
	private String col3;					//存放方案归属环节（环节与方案对应关系为 一对一），可为空
	private String col2;					//存放方案归属环节（环节与方案对应关系为 一对一），可为空
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public String getExec_time() {
		return exec_time;
	}
	public void setExec_time(String exec_time) {
		this.exec_time = exec_time;
	}
	public String getExec_cycle() {
		return exec_cycle;
	}
	public void setExec_cycle(String exec_cycle) {
		this.exec_cycle = exec_cycle;
	}
	public String getCycle_id() {
		return cycle_id;
	}
	public void setCycle_id(String cycle_id) {
		this.cycle_id = cycle_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getStatus_user() {
		return status_user;
	}
	public void setStatus_user(String status_user) {
		this.status_user = status_user;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public String getFinancial_type() {
		return financial_type;
	}
	public void setFinancial_type(String financial_type) {
		this.financial_type = financial_type;
	}
	public String getPlan_type() {
		return plan_type;
	}
	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}
	public String getYear_type() {
		return year_type;
	}
	public void setYear_type(String year_type) {
		this.year_type = year_type;
	}
	public String getCtrl_type() {
		return ctrl_type;
	}
	public void setCtrl_type(String ctrl_type) {
		this.ctrl_type = ctrl_type;
	}
	public String getCtrl_val() {
		return ctrl_val;
	}
	public void setCtrl_val(String ctrl_val) {
		this.ctrl_val = ctrl_val;
	}
	public String getRun_type() {
		return run_type;
	}
	public void setRun_type(String run_type) {
		this.run_type = run_type;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
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
	public String getPay_user_id() {
		return pay_user_id;
	}
	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}
	public String getCatalogue_id() {
		return catalogue_id;
	}
	public void setCatalogue_id(String catalogue_id) {
		this.catalogue_id = catalogue_id;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	
}
