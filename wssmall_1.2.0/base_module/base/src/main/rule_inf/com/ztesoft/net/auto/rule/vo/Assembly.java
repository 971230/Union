package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;

/**
 * 业务组件
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public class Assembly implements Serializable {

	private String ass_id;
	private String ass_name;
	private String ass_code;
	private String exe_path;
	private Integer status;
	private String create_time;
	private String hint;
	public String getAss_id() {
		return ass_id;
	}
	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}
	public String getAss_name() {
		return ass_name;
	}
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}
	public String getAss_code() {
		return ass_code;
	}
	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}
	public String getExe_path() {
		return exe_path;
	}
	public void setExe_path(String exe_path) {
		this.exe_path = exe_path;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}

	
}
