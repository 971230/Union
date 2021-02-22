package com.ztesoft.gray.model;

import java.io.Serializable;

public class RouterGrategy implements Serializable {
	private String cfg_id; 	//序号
	private String env;		//适用环境
	private String env_code; //环境编码
	private String env_type;		//环境分类
	private String env_url;		//适用环境
	private String grategy_name;	//策略名称
	private String policyid;	//策略id
	private String run_status;	//运行状态：00A_运行;00X_挂起
	private String gray_moment;	//灰度时机
	private String policy;		//策略配置
	private String instruction;	//策略配置说明
	private String remarks;		//运行功能说明
	private String create_time;
	private String update_time;
	public String getCfg_id() {
		return cfg_id;
	}
	public void setCfg_id(String cfg_id) {
		this.cfg_id = cfg_id;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getEnv_code() {
		return env_code;
	}
	public void setEnv_code(String env_code) {
		this.env_code = env_code;
	}
	public String getEnv_type() {
		return env_type;
	}
	public void setEnv_type(String env_type) {
		this.env_type = env_type;
	}
	public String getEnv_url() {
		return env_url;
	}
	public void setEnv_url(String env_url) {
		this.env_url = env_url;
	}
	public String getGrategy_name() {
		return grategy_name;
	}
	public void setGrategy_name(String grategy_name) {
		this.grategy_name = grategy_name;
	}
	public String getPolicyid() {
		return policyid;
	}
	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}
	public String getRun_status() {
		return run_status;
	}
	public void setRun_status(String run_status) {
		this.run_status = run_status;
	}
	public String getGray_moment() {
		return gray_moment;
	}
	public void setGray_moment(String gray_moment) {
		this.gray_moment = gray_moment;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	
}
