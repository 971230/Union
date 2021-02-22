package com.ztesoft.net.model;

import java.io.Serializable;

public class ESearchData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2185538111955161318L;

	private String in_param;//入参
	
	private String out_param;//出参
	
	private String operation_code_id;
	
	private String operation_code;//类型
		
	public String getOperation_code() {
		return operation_code;
	}

	public void setOperation_code(String operation_code) {
		this.operation_code = operation_code;
	}

	public String getOperation_code_id() {
		return operation_code_id;
	}

	public void setOperation_code_id(String operation_code_id) {
		this.operation_code_id = operation_code_id;
	}
	
	
	private String obj_id;//业务主键id 目前定义为订单id
	
	private String keyword_id;//关键字id
	
	private String keyword_value;//关键字值
	
	private String class_id;//归类id

	private String class_name;//归类名称
	
	private String log_id;//日志id

	private String index;
	
	private String type;
	
	private String create_time;
	
	private String createTimeStr;
	
	private String busiTimeStr;
	
	private String busi_time;
	
	private String keyword_name;

	private String class_value;
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIn_param() {
		return in_param;
	}

	public void setIn_param(String in_param) {
		this.in_param = in_param;
	}

	public String getOut_param() {
		return out_param;
	}

	public void setOut_param(String out_param) {
		this.out_param = out_param;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getKeyword_id() {
		return keyword_id;
	}

	public void setKeyword_id(String keyword_id) {
		this.keyword_id = keyword_id;
	}

	public String getKeyword_value() {
		return keyword_value;
	}

	public void setKeyword_value(String keyword_value) {
		this.keyword_value = keyword_value;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getBusi_time() {
		return busi_time;
	}

	public void setBusi_time(String busi_time) {
		this.busi_time = busi_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getBusiTimeStr() {
		return busiTimeStr;
	}

	public void setBusiTimeStr(String busiTimeStr) {
		this.busiTimeStr = busiTimeStr;
	}

	public String getKeyword_name() {
		return keyword_name;
	}

	public void setKeyword_name(String keyword_name) {
		this.keyword_name = keyword_name;
	}

	public String getClass_value() {
		return class_value;
	}

	public void setClass_value(String class_value) {
		this.class_value = class_value;
	}
	
}
