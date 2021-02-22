package com.ztesoft.net.template.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.template.model.Catalogue;
import com.ztesoft.net.template.model.EsAttrDef;
import com.ztesoft.net.template.model.OrderTemplate;
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
public class TepletaParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
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
    private List<OrderTemplate> orderTemplateList;
  //------------节点树END---------------------
    private EsAttrDef esAttrDef;
	private OrderTemplate orderTemplate;
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
	public EsAttrDef getEsAttrDef() {
		return esAttrDef;
	}
	public void setEsAttrDef(EsAttrDef esAttrDef) {
		this.esAttrDef = esAttrDef;
	}
	public OrderTemplate getOrderTemplate() {
		return orderTemplate;
	}
	public void setOrderTemplate(OrderTemplate orderTemplate) {
		this.orderTemplate = orderTemplate;
	}
	public List<OrderTemplate> getOrderTemplateList() {
		return orderTemplateList;
	}
	public void setOrderTemplateList(List<OrderTemplate> orderTemplateList) {
		this.orderTemplateList = orderTemplateList;
	}
	
}
