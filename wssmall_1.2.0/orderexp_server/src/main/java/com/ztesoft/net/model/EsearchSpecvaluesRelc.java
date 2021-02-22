package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 关键字关联归类实体
 * @author qin.yingxiong
 */
public class EsearchSpecvaluesRelc implements Serializable{

	private static final long serialVersionUID = 1L;
	//关键字id
	private String key_id;
	//归类id
	private String catalog_id;
	//创建时间
	private String create_time;
	//工号
	private String staff_no;
	
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStaff_no() {
		return staff_no;
	}
	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
}
