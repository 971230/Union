package com.ztesoft.net.mall.core.model;

public class UsersMenu {
   private String menu_id;
   private String user_id;
   private String menu_name; 
   private int sort;
   private String  create_time;
   private String disabled;
   private String menu_url;
   private String img_path;
   private String source_from ;
public String getMenu_id() {
	return menu_id;
}
public void setMenu_id(String menu_id) {
	this.menu_id = menu_id;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}
public int getSort() {
	return sort;
}
public void setSort(int sort) {
	this.sort = sort;
}
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}
public String getDisabled() {
	return disabled;
}
public void setDisabled(String disabled) {
	this.disabled = disabled;
}
public String getMenu_url() {
	return menu_url;
}
public void setMenu_url(String menu_url) {
	this.menu_url = menu_url;
}
public String getImg_path() {
	return img_path;
}
public void setImg_path(String img_path) {
	this.img_path = img_path;
}
public String getSource_from() {
	return source_from;
}
public void setSource_from(String source_from) {
	this.source_from = source_from;
}
public String getMenu_name() {
	return menu_name;
}
public void setMenu_name(String menu_name) {
	this.menu_name = menu_name;
}
   
   
}
