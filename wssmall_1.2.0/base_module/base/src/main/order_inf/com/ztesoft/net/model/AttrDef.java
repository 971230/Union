package com.ztesoft.net.model;

import com.ztesoft.net.framework.database.NotDbField;


/**
 * 扩展属性配置实体
 * @author hu.yi
 * @date 2013.9.26
 */
public class AttrDef implements java.io.Serializable {
	
	private String attr_spec_id;
	private String attr_spec_type;
	private String sub_attr_spec_type;
	private String field_name;
	private String field_desc;
	private String field_attr_id;
	private String field_type;
	private String default_value;
	private String default_value_desc;
	private String sec_field_name;
	private String sec_field_desc;
	private String lan_code;
	private String is_edit;
	private String is_null;
	private String check_message;
	private String field_attr_format;
	private String page_url;
	private String col_span;
	private String cell_count;
	private String hide_card;
	private String width;
	private String lay_out;
	private Integer sequ;
	private String source_from;
	private String rel_table_name;
	private String owner_table_fields;
	private String o_field_name;
	
	private String hander_id;
	private String handler_class;
	
	private String inst_id;//数据实例横表Id
	private String attr_code;
	private String order_id;
	
	private String table_field_name;//对应数据库字段名 订单归集
	@NotDbField
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	@NotDbField
	public String getTable_field_name() {
		return table_field_name;
	}
	public void setTable_field_name(String table_field_name) {
		this.table_field_name = table_field_name;
	}
	@NotDbField
	public String getHandler_class() {
		return handler_class;
	}
	public void setHandler_class(String handler_class) {
		this.handler_class = handler_class;
	}
	
	@NotDbField
	public String getInst_id() {
		return inst_id;
	}
	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}
	@NotDbField
	public String getHander_id() {
		return hander_id;
	}
	public void setHander_id(String hander_id) {
		this.hander_id = hander_id;
	}
	public String getAttr_spec_id() {
		return attr_spec_id;
	}
	public void setAttr_spec_id(String attr_spec_id) {
		this.attr_spec_id = attr_spec_id;
	}
	public String getAttr_spec_type() {
		return attr_spec_type;
	}
	public void setAttr_spec_type(String attr_spec_type) {
		this.attr_spec_type = attr_spec_type;
	}
	public String getSub_attr_spec_type() {
		return sub_attr_spec_type;
	}
	public void setSub_attr_spec_type(String sub_attr_spec_type) {
		this.sub_attr_spec_type = sub_attr_spec_type;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_desc() {
		return field_desc;
	}
	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}
	public String getField_attr_id() {
		return field_attr_id;
	}
	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	public String getDefault_value_desc() {
		return default_value_desc;
	}
	public void setDefault_value_desc(String default_value_desc) {
		this.default_value_desc = default_value_desc;
	}
	public String getSec_field_name() {
		return sec_field_name;
	}
	public void setSec_field_name(String sec_field_name) {
		this.sec_field_name = sec_field_name;
	}
	public String getSec_field_desc() {
		return sec_field_desc;
	}
	public void setSec_field_desc(String sec_field_desc) {
		this.sec_field_desc = sec_field_desc;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	public String getIs_edit() {
		return is_edit;
	}
	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}
	public String getCheck_message() {
		return check_message;
	}
	public void setCheck_message(String check_message) {
		this.check_message = check_message;
	}
	public String getField_attr_format() {
		return field_attr_format;
	}
	public void setField_attr_format(String field_attr_format) {
		this.field_attr_format = field_attr_format;
	}
	public String getPage_url() {
		return page_url;
	}
	public void setPage_url(String page_url) {
		this.page_url = page_url;
	}
	public String getCol_span() {
		return col_span;
	}
	public void setCol_span(String col_span) {
		this.col_span = col_span;
	}
	public String getCell_count() {
		return cell_count;
	}
	public void setCell_count(String cell_count) {
		this.cell_count = cell_count;
	}
	public String getHide_card() {
		return hide_card;
	}
	public void setHide_card(String hide_card) {
		this.hide_card = hide_card;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getLay_out() {
		return lay_out;
	}
	public void setLay_out(String lay_out) {
		this.lay_out = lay_out;
	}
	public Integer getSequ() {
		return sequ;
	}
	public void setSequ(Integer sequ) {
		this.sequ = sequ;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getRel_table_name() {
		return rel_table_name;
	}
	public void setRel_table_name(String rel_table_name) {
		this.rel_table_name = rel_table_name;
	}
	public String getOwner_table_fields() {
		return owner_table_fields;
	}
	public void setOwner_table_fields(String owner_table_fields) {
		this.owner_table_fields = owner_table_fields;
	}
	public String getO_field_name() {
		return o_field_name;
	}
	public void setO_field_name(String o_field_name) {
		this.o_field_name = o_field_name;
	}
	public String getAttr_code() {
		return attr_code;
	}
	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}
	
}