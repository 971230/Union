/**
 * 
 */
package com.ztesoft.net.model;

/**
 * @author ZX
 * @version 2015-08-26
 * @see ES_DATA_PRASER_DICT
 *
 */
public class DataPraseDict {
	
	private String dict_id;//字典ID
	private String catalog_id;//字典目录ID
	private String dict_desc;//字典描述
	private String dict_name;//字典名称
	private String dict_match_content;//字典匹配内容，用"|"分割（例：号码异常|号码占用|号码占用失败）
	private String dict_match_type;//字典匹配类型
	private String source_from;//数据来源
	private String COL1;
	private String COL2;
	private String COL3;
	private String COL4;
	private String COL5;
	
	public String getDict_id() {
		return dict_id;
	}
	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getDict_desc() {
		return dict_desc;
	}
	public void setDict_desc(String dict_desc) {
		this.dict_desc = dict_desc;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getDict_match_content() {
		return dict_match_content;
	}
	public void setDict_match_content(String dict_match_content) {
		this.dict_match_content = dict_match_content;
	}
	public String getDict_match_type() {
		return dict_match_type;
	}
	public void setDict_match_type(String dict_match_type) {
		this.dict_match_type = dict_match_type;
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
