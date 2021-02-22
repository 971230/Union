package com.ztesoft.lucence;

import java.io.Serializable;

/**
 * 配置表
 * @作者 MoChunrun
 * @创建日期 2014-7-31 
 * @版本 V 1.0
 */
public class LucenceConfig implements Serializable {

	private String id;
	private String search_bean;
	private String last_update_time;
	private String create_time;
	private String link_url;
	private String data_type;
	private String data_type_code;
	private String run_status;
	private String source_from;
	private Integer is_new;
	private Integer exe_min;
	private String next_exe_time;
	private String result_msg;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSearch_bean() {
		return search_bean;
	}
	public void setSearch_bean(String search_bean) {
		this.search_bean = search_bean;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_type_code() {
		return data_type_code;
	}
	public void setData_type_code(String data_type_code) {
		this.data_type_code = data_type_code;
	}
	public String getRun_status() {
		return run_status;
	}
	public void setRun_status(String run_status) {
		this.run_status = run_status;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public Integer getIs_new() {
		return is_new;
	}
	public void setIs_new(Integer is_new) {
		this.is_new = is_new;
	}
	public Integer getExe_min() {
		return exe_min;
	}
	public void setExe_min(Integer exe_min) {
		this.exe_min = exe_min;
	}
	public String getNext_exe_time() {
		return next_exe_time;
	}
	public void setNext_exe_time(String next_exe_time) {
		this.next_exe_time = next_exe_time;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	
}
