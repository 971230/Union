package com.ztesoft.net.auto.rule.vo;

import java.io.Serializable;

/**
 * 目录
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public class Catalogue implements Serializable {

	private String id;
	private String pid;
	private String name;
	private Integer type;
	private String hint;
	private String create_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}
