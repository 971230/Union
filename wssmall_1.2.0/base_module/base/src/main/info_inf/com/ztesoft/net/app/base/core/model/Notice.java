package com.ztesoft.net.app.base.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

public class Notice implements Serializable{
     private String notice_id;//公告ID
     private String title;//公告标题
     private String content;//公告内容
     private String begin_time;//生效时间
     private String end_time;//失效时间
     private String create_time;//创建时间
     private String user_id;//创建公告用户
     private String pubilsh_org_id;//创建公告的组织ID
     private int    state;//状态  0 有效 1 无效
     private String source_from;
     private String nlcount;
     private String update_time;
     
     private String notice_time;
     
     
     //非数据库字段
     private String org_name;
     private String user_name;
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getPubilsh_org_id() {
		return pubilsh_org_id;
	}
	public void setPubilsh_org_id(String pubilsh_org_id) {
		this.pubilsh_org_id = pubilsh_org_id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	@NotDbField
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	@NotDbField
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@NotDbField
	public String getNlcount() {
		return nlcount;
	}
	@NotDbField
	public void setNlcount(String nlcount) {
		this.nlcount = nlcount;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	@NotDbField
	public String getNotice_time() {
		return notice_time;
	}
	@NotDbField
	public void setNotice_time(String notice_time) {
		this.notice_time = notice_time;
	}
     
     
}
