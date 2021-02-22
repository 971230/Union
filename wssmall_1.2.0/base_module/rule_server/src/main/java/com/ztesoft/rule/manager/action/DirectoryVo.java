package com.ztesoft.rule.manager.action;

import java.io.Serializable;

/**
 * 目录表VO对象
 * @author ZX
 *
 */
public class DirectoryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id; // ID
	private String pid; // 父目录ID
	private String name; // 目录名称
	private int type; // 目录类型
	private String hint; // 描述
	private String create_time; // 目录创建时间
	private String source_from; // 资源来源
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	
	
}
