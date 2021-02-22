package com.ztesoft.net.mall.core.model;

public class AttrTypeRela implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String busi_type_attr_rela_id;//主键
	
	private String busi_type_id;//业务ID
	
	private String table_name;//表名
	
	private String handler_class;//属性处理器
	
	private String sort;//属性执行顺序(数值越大越后处理)

	public String getBusi_type_attr_rela_id() {
		return busi_type_attr_rela_id;
	}

	public void setBusi_type_attr_rela_id(String busi_type_attr_rela_id) {
		this.busi_type_attr_rela_id = busi_type_attr_rela_id;
	}

	public String getBusi_type_id() {
		return busi_type_id;
	}

	public void setBusi_type_id(String busi_type_id) {
		this.busi_type_id = busi_type_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getHandler_class() {
		return handler_class;
	}

	public void setHandler_class(String handler_class) {
		this.handler_class = handler_class;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
