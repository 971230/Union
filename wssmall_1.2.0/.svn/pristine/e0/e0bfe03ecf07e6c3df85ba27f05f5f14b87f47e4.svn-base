package com.ztesoft.net.cms.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;
import java.util.List;

/**
 * 数据分类
 * @author kingapex
 * 2010-7-5上午07:17:43
 */
public class DataCat implements Serializable{

 
	private static final long serialVersionUID = 3764718745312895356L;


	protected String cat_id;
	protected String name;
	protected String parent_id;
	protected String cat_path;
	protected Integer cat_order;
	protected Integer model_id;
	protected Integer if_audit;
    private String url; //栏目页地址
    private String detail_url;
    private Integer tositemap; 
    private String source_from;   

	//子类别，非数据库字段
	protected List<DataCat> children;
	private boolean hasChildren ;
	
    public Integer getIf_audit() {
		return if_audit;
	}
	public void setIf_audit(Integer if_audit) {
		this.if_audit = if_audit;
	}
 
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public Integer getCat_order() {
		return cat_order;
	}
	public void setCat_order(Integer cat_order) {
		this.cat_order = cat_order;
	}
	public String getCat_path() {
		return cat_path;
	}
	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
	}
	public Integer getModel_id() {
		return model_id;
	}
	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	@NotDbField
	public List<DataCat> getChildren() {
		return children;
	}
	public void setChildren(List<DataCat> children) {
		this.children = children;
	}

	@NotDbField
	public boolean getHasChildren() {
		hasChildren = this.children==null|| this.children.isEmpty() ?false:true;
		return hasChildren;
	}


	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDetail_url() {
		return detail_url;
	}
	public void setDetail_url(String detailUrl) {
		detail_url = detailUrl;
	}
	public Integer getTositemap() {
		return tositemap;
	}
	public void setTositemap(Integer tositemap) {
		this.tositemap = tositemap;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}	
}
