package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/*
 * 货主
 */
public class ProductCompany implements Serializable {

	private String  comp_id      ;//主键
	private String  comp_code    ;//货主编码
	private String  comp_name    ;//货主名称
	private String  source_from   ;
	public String getComp_id() {
		return comp_id;
	}
	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}
	public String getComp_code() {
		return comp_code;
	}
	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}
	public String getComp_name() {
		return comp_name;
	}
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
