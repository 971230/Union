package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 打印模配置
 * @作者 MoChunrun
 * @创建日期 2013-11-7 
 * @版本 V 1.0
 */
public class Printtmplconfig implements Serializable {

	private String config_id;
	private String config_name;
	private String tmpl_json;
	private String create_time;
	private String bean_name;
	public String getConfig_id() {
		return config_id;
	}
	public void setConfig_id(String config_id) {
		this.config_id = config_id;
	}
	public String getConfig_name() {
		return config_name;
	}
	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}
	public String getTmpl_json() {
		return tmpl_json;
	}
	public void setTmpl_json(String tmpl_json) {
		this.tmpl_json = tmpl_json;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getBean_name() {
		return bean_name;
	}
	public void setBean_name(String bean_name) {
		this.bean_name = bean_name;
	}
	
	
}
