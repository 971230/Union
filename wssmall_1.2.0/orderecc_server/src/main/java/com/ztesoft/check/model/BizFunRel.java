package com.ztesoft.check.model;

import java.io.Serializable;

public class BizFunRel implements Serializable{
	private String biz_id;
	private String fun_id;
	private String tache_code;
	private String order_id;
	private String exe_method;
	private String exe_time;
	private String create_time;
	private String status;
	
	//非数据库字段
	private String check;

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

	public String getExe_method() {
		return exe_method;
	}

	public void setExe_method(String exe_method) {
		this.exe_method = exe_method;
	}

	public String getExe_time() {
		return exe_time;
	}

	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
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

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
	
	
}
