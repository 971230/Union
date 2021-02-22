package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.util.List;

/**
 * @author lzf
 * version 1.0<br/>
 * 2010-6-17&nbsp;下午02:31:00
 */
public class Tag implements java.io.Serializable{
	
	private String tag_id;
	private String tag_name;
	private String tag_type;
	private String tag_code;
	private String tag_desc;
	private String tag_status;
	private String tag_value;
	private Integer rel_count;
	private String ower_type;
	private String source_from;
	private String cat_type;
	private String cat_type_name;

	private String sort;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getTag_value() {
		return tag_value;
	}
	public void setTag_value(String tag_value) {
		this.tag_value = tag_value;
	}

	private List tagGoodsList; //标签关联商品
	
	public String getOwer_type() {
		return ower_type;
	}
	public String getTag_type() {
		return tag_type;
	}
	public void setTag_type(String tag_type) {
		this.tag_type = tag_type;
	}
	public String getTag_code() {
		return tag_code;
	}
	public void setTag_code(String tag_code) {
		this.tag_code = tag_code;
	}
	public String getTag_desc() {
		return tag_desc;
	}
	public void setTag_desc(String tag_desc) {
		this.tag_desc = tag_desc;
	}
	public String getTag_status() {
		return tag_status;
	}
	public void setTag_status(String tag_status) {
		this.tag_status = tag_status;
	}
	public void setOwer_type(String ower_type) {
		this.ower_type = ower_type;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCat_type() {
		return cat_type;
	}
	public void setCat_type(String cat_type) {
		this.cat_type = cat_type;
	}
	public String getTag_id() {
		return tag_id;
	}
	public void setTag_id(String tagId) {
		tag_id = tagId;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tagName) {
		tag_name = tagName;
	}
	public Integer getRel_count() {
		return rel_count;
	}
	public void setRel_count(Integer relCount) {
		rel_count = relCount;
	}
	
	@NotDbField
	public List getTagGoodsList() {
		return tagGoodsList;
	}
	@NotDbField
	public void setTagGoodsList(List tagGoodsList) {
		this.tagGoodsList = tagGoodsList;
	}
	@NotDbField
	public String getCat_type_name() {
		return cat_type_name;
	}
	@NotDbField
	public void setCat_type_name(String cat_type_name) {
		this.cat_type_name = cat_type_name;
	}
	
}