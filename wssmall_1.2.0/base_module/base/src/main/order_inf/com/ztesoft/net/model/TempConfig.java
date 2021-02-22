package com.ztesoft.net.model;


/**
 * 模板配置实体
 * @author hu.yi
 * @date 2013.9.26
 */
public class TempConfig implements java.io.Serializable {
	
	private String temp_id;
	private String temp_cols;				//模板列对应的列及列名
	private String temp_notes;				//模板注释
	private String temp_cols_contents;		//模板样板数据对应的列及列名
	private String temp_name;				//模板名
	private String state;					//模板状态
	
	
	public String getTemp_id() {
		return temp_id;
	}
	public void setTemp_id(String temp_id) {
		this.temp_id = temp_id;
	}
	public String getTemp_cols() {
		return temp_cols;
	}
	public void setTemp_cols(String temp_cols) {
		this.temp_cols = temp_cols;
	}
	public String getTemp_notes() {
		return temp_notes;
	}
	public void setTemp_notes(String temp_notes) {
		this.temp_notes = temp_notes;
	}
	public String getTemp_cols_contents() {
		return temp_cols_contents;
	}
	public void setTemp_cols_contents(String temp_cols_contents) {
		this.temp_cols_contents = temp_cols_contents;
	}
	public String getTemp_name() {
		return temp_name;
	}
	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}