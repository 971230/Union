package com.ztesoft.net.mall.core.model;


/**
 * 标签实体
 * @author wu.i
 *2013-10-23下午
 */
public class Tab implements java.io.Serializable {
	private Integer tab_id;
	private String tab_name;
	private String tab_stype_code;
	
	public Tab(Integer tab_id,String tab_name){
		this.tab_id = tab_id;
		this.tab_name = tab_name;
	}
	
	public Tab(Integer tab_id,String tab_name,String tab_stype_code){
		this.tab_id = tab_id;
		this.tab_name = tab_name;
		this.tab_stype_code =tab_stype_code;
	}
	
	public String getTab_stype_code() {
		return tab_stype_code;
	}
	public void setTab_stype_code(String tab_stype_code) {
		this.tab_stype_code = tab_stype_code;
	}
	
	public String getTab_name() {
		return tab_name;
	}
	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}
	public Integer getTab_id() {
		return tab_id;
	}
	public void setTab_id(Integer tab_id) {
		this.tab_id = tab_id;
	}
	
	
	
}