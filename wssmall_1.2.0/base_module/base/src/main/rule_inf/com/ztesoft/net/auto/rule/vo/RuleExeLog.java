package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;

/**
 * 规则执行日志表
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public class RuleExeLog implements Serializable {

	private String log_id;
	private String rule_id;
	private String create_time;
	private Integer exe_result;
	private String result_msg;
//	private String req_fact;
//	private String resp_data;
	private String source_from;
	private String plan_id;
	private String obj_id;
	private Integer children_error;
	private String children_info;
	private String engine_path;
	private String flow_inst_id;
	private String fact_class;
	private Long time_num;
	
	public Long getTime_num() {
		return time_num;
	}
	public void setTime_num(Long time_num) {
		this.time_num = time_num;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Integer getExe_result() {
		return exe_result;
	}
	public void setExe_result(Integer exe_result) {
		this.exe_result = exe_result;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
//	public String getReq_fact() {
//		return req_fact;
//	}
//	public void setReq_fact(String req_fact) {
//		this.req_fact = req_fact;
//	}
//	public String getResp_data() {
//		return resp_data;
//	}
//	public void setResp_data(String resp_data) {
//		this.resp_data = resp_data;
//	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public Integer getChildren_error() {
		return children_error;
	}
	public void setChildren_error(Integer children_error) {
		this.children_error = children_error;
	}
	public String getChildren_info() {
		return children_info;
	}
	public void setChildren_info(String children_info) {
		this.children_info = children_info;
	}
	public String getEngine_path() {
		return engine_path;
	}
	public void setEngine_path(String engine_path) {
		this.engine_path = engine_path;
	}
	public String getFlow_inst_id() {
		return flow_inst_id;
	}
	public void setFlow_inst_id(String flow_inst_id) {
		this.flow_inst_id = flow_inst_id;
	}
	public String getFact_class() {
		return fact_class;
	}
	public void setFact_class(String fact_class) {
		this.fact_class = fact_class;
	}
}
