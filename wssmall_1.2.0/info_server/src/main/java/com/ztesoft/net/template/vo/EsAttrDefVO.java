package com.ztesoft.net.template.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.template.model.Catalogue;
import com.ztesoft.net.template.model.OrderTemplateNode;

/**
 * 
 * Copyright (c) 2015, 南京中兴软创科技股份有限公司 All rights reserved
 * 
 * @author : 张金叶
 * @createTime : 2015-3-3
 * @version : 1.0
 * @documentPath:
 */
public class EsAttrDefVO implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private String class_name;
	private String class_field_name;
	private String table_type;
	
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
	private Long sequ;
	private String source_from;
	private String rel_table_name;
	private String owner_table_fields;
	private String o_field_name;
	private String attr_code;
	private String handler_id;
	
	//------------节点树BEGIN---------------------
	private String id; // 节点ID
    private String super_id;// 父节点ID
    private String flag;//ADD MOD VIEW
    private List<OrderTemplateNode> nodeList;  //节点列表
    private OrderTemplateNode orderTemplateNode;  //节点
    private String orderTemplateId;  //模板ID
    private String nodeName;  //节点名称
    private Long nodeDepth;
    private String nodePath;
    private String pid;//父目录ID
    private List<Catalogue> catalogueList;//目录列表
    private Catalogue catalogue;
  //------------节点树END---------------------
    
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
	public Long getSequ() {
		return sequ;
	}
	public void setSequ(Long sequ) {
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
	public String getHandler_id() {
		return handler_id;
	}
	public void setHandler_id(String handler_id) {
		this.handler_id = handler_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSuper_id() {
		return super_id;
	}
	public void setSuper_id(String super_id) {
		this.super_id = super_id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<OrderTemplateNode> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<OrderTemplateNode> nodeList) {
		this.nodeList = nodeList;
	}
	public OrderTemplateNode getOrderTemplateNode() {
		return orderTemplateNode;
	}
	public void setOrderTemplateNode(OrderTemplateNode orderTemplateNode) {
		this.orderTemplateNode = orderTemplateNode;
	}
	public String getOrderTemplateId() {
		return orderTemplateId;
	}
	public void setOrderTemplateId(String orderTemplateId) {
		this.orderTemplateId = orderTemplateId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Long getNodeDepth() {
		return nodeDepth;
	}
	public void setNodeDepth(Long nodeDepth) {
		this.nodeDepth = nodeDepth;
	}
	public String getNodePath() {
		return nodePath;
	}
	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public List<Catalogue> getCatalogueList() {
		return catalogueList;
	}
	public void setCatalogueList(List<Catalogue> catalogueList) {
		this.catalogueList = catalogueList;
	}
	public Catalogue getCatalogue() {
		return catalogue;
	}
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getClass_field_name() {
		return class_field_name;
	}
	public void setClass_field_name(String class_field_name) {
		this.class_field_name = class_field_name;
	}
	public String getTable_type() {
		return table_type;
	}
	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}

}
