package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class AllAgreement implements Serializable {
   private String agt_id;
   private String agt_rel_id;
   private String file_url;
   private String create_time;
   private String state;
   private String agt_rel_desc;
   private String agt_rel_table;
public String getAgt_id() {
	return agt_id;
}
public void setAgt_id(String agt_id) {
	this.agt_id = agt_id;
}

public String getFile_url() {
	return file_url;
}
public void setFile_url(String file_url) {
	this.file_url = file_url;
}
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getAgt_rel_id() {
	return agt_rel_id;
}
public void setAgt_rel_id(String agt_rel_id) {
	this.agt_rel_id = agt_rel_id;
}
public String getAgt_rel_desc() {
	return agt_rel_desc;
}
public void setAgt_rel_desc(String agt_rel_desc) {
	this.agt_rel_desc = agt_rel_desc;
}
public String getAgt_rel_table() {
	return agt_rel_table;
}
public void setAgt_rel_table(String agt_rel_table) {
	this.agt_rel_table = agt_rel_table;
}
 
}
