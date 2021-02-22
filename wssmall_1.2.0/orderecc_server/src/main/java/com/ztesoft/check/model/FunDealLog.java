package com.ztesoft.check.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FunDealLog implements Serializable{

	private String log_id;
	private String biz_id;
	private String fun_id;
	private String tache_code;
	private String order_id;
	private String exe_time;
	private Timestamp req_time;
	private Timestamp resp_time;
	private String clazz;
	private String impl;
	private String result_code;
	private String result_msg;
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	public String getFun_id() {
		return fun_id;
	}
	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public Timestamp getReq_time() {
		return req_time;
	}
	public void setReq_time(Timestamp req_time) {
		this.req_time = req_time;
	}
	public void setReq_time(String req_time) {
		
	}
	public Timestamp getResp_time() {
		return resp_time;
	}
	public void setResp_time(Timestamp resp_time) {
		this.resp_time = resp_time;
	}
	public void setResp_time(String resp_time) {
		
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	
	
}
