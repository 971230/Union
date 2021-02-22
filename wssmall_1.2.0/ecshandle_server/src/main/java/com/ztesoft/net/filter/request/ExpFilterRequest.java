package com.ztesoft.net.filter.request;

public class ExpFilterRequest extends FilterRequest {
	private String search_id;
	private String rel_obj_id;
	private String rel_obj_type;
	private String out_tid;
	private String write_flag;
	private String search_code;
	private String param;
	
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getRel_obj_id() {
		return rel_obj_id;
	}
	public void setRel_obj_id(String rel_obj_id) {
		this.rel_obj_id = rel_obj_id;
	}
	public String getRel_obj_type() {
		return rel_obj_type;
	}
	public void setRel_obj_type(String rel_obj_type) {
		this.rel_obj_type = rel_obj_type;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getWrite_flag() {
		return write_flag;
	}
	public void setWrite_flag(String write_flag) {
		this.write_flag = write_flag;
	}
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
}
