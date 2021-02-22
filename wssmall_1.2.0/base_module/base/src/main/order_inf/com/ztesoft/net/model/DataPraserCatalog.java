/**
 * 
 */
package com.ztesoft.net.model;

/**
 * @author ZX
 * @version 2015-08-26
 * @see ES_DATA_PRASER_CATALOG
 *
 */
public class DataPraserCatalog {
	
	private String catalog_id; //字典目录ID
	private String catalog_code; //字典目录编码
	private String catalog_name; //字典目录名字
	private String catalog_desc; //字典目录描述
	private String sub_catalog_id; //父级目录ID
	private String source_from;//数据库来源
	private String COL1;
	private String COL2;
	private String COL3;
	private String COL4;
	private String COL5;
	
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
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCOL1() {
		return COL1;
	}
	public void setCOL1(String cOL1) {
		COL1 = cOL1;
	}
	public String getCOL2() {
		return COL2;
	}
	public void setCOL2(String cOL2) {
		COL2 = cOL2;
	}
	public String getCOL3() {
		return COL3;
	}
	public void setCOL3(String cOL3) {
		COL3 = cOL3;
	}
	public String getCOL4() {
		return COL4;
	}
	public void setCOL4(String cOL4) {
		COL4 = cOL4;
	}
	public String getCOL5() {
		return COL5;
	}
	public void setCOL5(String cOL5) {
		COL5 = cOL5;
	}
	
}
