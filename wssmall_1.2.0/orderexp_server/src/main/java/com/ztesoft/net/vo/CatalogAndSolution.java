package com.ztesoft.net.vo;

import java.io.Serializable;

public class CatalogAndSolution implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//解决方案ID
	private String solution_id;
	//解决方案名称
	private String solution_name;
	//解决方案描述
	private String solution_desc;
	//归类id
	private String catalog_id;
	//归类编码
    private String catalog_code;
    //归类名称
    private String catalog_name;
    
	public String getSolution_id() {
		return solution_id;
	}
	public void setSolution_id(String solution_id) {
		this.solution_id = solution_id;
	}
	public String getSolution_desc() {
		return solution_desc;
	}
	public void setSolution_desc(String solution_desc) {
		this.solution_desc = solution_desc;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getCatalog_code() {
		return catalog_code;
	}
	public void setCatalog_code(String catalog_code) {
		this.catalog_code = catalog_code;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public String getSolution_name() {
		return solution_name;
	}
	public void setSolution_name(String solution_name) {
		this.solution_name = solution_name;
	}
}
