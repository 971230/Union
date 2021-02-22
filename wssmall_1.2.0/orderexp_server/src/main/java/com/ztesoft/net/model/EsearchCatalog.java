package com.ztesoft.net.model;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 关键字归类信息实体
 * @author qin.yingxiong
 */
public class EsearchCatalog implements Serializable {

	private static final long serialVersionUID = 1L;
	private String catalog_id;//字典目录ID
	private String catalog_code;//字典目录编码
	private String catalog_name;//字典目录名字
	private String catalog_desc;//字典目录描述
	private String sub_catalog_id;//父级目录ID
	private String sub_catalog_name;//父级目录ID
	private String source_from;//数据库来源
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String solution_id;//解决方案id
	private String staff_no;//创建人
	private String disabled;//是否有效,1无效,0有效
	private String username;//创建人username
	
	private List<EsearchCatalog> childrenCatalog;
	private boolean hasChildren ;

	@NotDbField
	public boolean getHasChildren() {
		hasChildren = this.childrenCatalog==null|| this.childrenCatalog.isEmpty() ?false:true;
		return hasChildren;
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
	public String getCatalog_desc() {
		return catalog_desc;
	}
	public void setCatalog_desc(String catalog_desc) {
		this.catalog_desc = catalog_desc;
	}
	public String getSub_catalog_id() {
		return sub_catalog_id;
	}
	public void setSub_catalog_id(String sub_catalog_id) {
		this.sub_catalog_id = sub_catalog_id;
	}
	@NotDbField
	public String getSub_catalog_name() {
		return sub_catalog_name;
	}
	@NotDbField
	public void setSub_catalog_name(String sub_catalog_name) {
		this.sub_catalog_name = sub_catalog_name;
	}

	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getSolution_id() {
		return solution_id;
	}
	public void setSolution_id(String solution_id) {
		this.solution_id = solution_id;
	}
	public String getStaff_no() {
		return staff_no;
	}
	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public List<EsearchCatalog> getChildrenCatalog() {
		return childrenCatalog;
	}
	public void setChildrenCatalog(List<EsearchCatalog> childrenCatalog) {
		this.childrenCatalog = childrenCatalog;
	}
	@NotDbField
	public String getUsername() {
		return username;
	}
	@NotDbField
	public void setUsername(String username) {
		this.username = username;
	}
}