package com.ztesoft.rule.core.module.cfg;

import java.util.Map;


public class MidDataConfig {
	
	private String mid_data_code ;
	private String plan_id ;
	private String cal_type ;
	private String cal_logic ;
	private String fact_java_class ;
	private String need_process_data ;
	
	private String list_cal_type ;
	private String list_cal_logic ;
	private String detail_cal_type ;
	private String detail_cal_logic ;
	
	//当前中间表对应的方案信息
	private BizPlan plan ;
	
	
	
	
	
	public MidDataConfig(){
		
	}


	public BizPlan getPlan() {
		return plan;
	}

	public void setPlan(BizPlan plan) {
		this.plan = plan;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public MidDataConfig(Map<String,String> config){
		
	}
	
	public String getList_cal_type() {
		return list_cal_type;
	}
	public void setList_cal_type(String list_cal_type) {
		this.list_cal_type = list_cal_type;
	}
	public String getList_cal_logic() {
		return list_cal_logic;
	}
	public void setList_cal_logic(String list_cal_logic) {
		this.list_cal_logic = list_cal_logic;
	}
	public String getDetail_cal_type() {
		return detail_cal_type;
	}
	public void setDetail_cal_type(String detail_cal_type) {
		this.detail_cal_type = detail_cal_type;
	}
	public String getDetail_cal_logic() {
		return detail_cal_logic;
	}
	public void setDetail_cal_logic(String detail_cal_logic) {
		this.detail_cal_logic = detail_cal_logic;
	}
	public String getMid_data_code() {
		return mid_data_code;
	}
	public void setMid_data_code(String mid_data_code) {
		this.mid_data_code = mid_data_code;
	}
	public String getCal_type() {
		return cal_type;
	}
	public void setCal_type(String cal_type) {
		this.cal_type = cal_type;
	}
	public String getCal_logic() {
		return cal_logic;
	}
	public void setCal_logic(String cal_logic) {
		this.cal_logic = cal_logic;
	}
	public String getFact_java_class() {
		return fact_java_class;
	}
	public void setFact_java_class(String fact_java_class) {
		this.fact_java_class = fact_java_class;
	}
	public String getNeed_process_data() {
		return need_process_data;
	}
	public void setNeed_process_data(String need_process_data) {
		this.need_process_data = need_process_data;
	}
	
	
}
