package com.ztesoft.net.mall.core.workflow.util;

public class ModInstVO {
//	i.action,i.table_name,i.field_name,i.new_change
	private String action ;
	private String table_name ;
	private String new_change ;
	private String field_name ;
	private String ref_obj_id ;
	
	public String getRef_obj_id() {
		return ref_obj_id;
	}
	public void setRef_obj_id(String ref_obj_id) {
		this.ref_obj_id = ref_obj_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getNew_change() {
		return new_change;
	}
	public void setNew_change(String new_change) {
		this.new_change = new_change;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	
}
