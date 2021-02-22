package com.ztesoft.net.app.base.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.util.List;

/**
 * 站点菜单
 * @author kingapex
 *
 */
public class SiteMenu {
	
	private String menuid;
	private String parentid;
	private String name;
	private String url;
	private String target;
	private Integer sort;
	private String pageid;
	
	//add by wui
	private Integer pageId; //cms在线编辑 
	
	
	public  SiteMenu(){
		hasChildren =false;
	}
	
	//子列表，非数据库字段
	private List<SiteMenu> children;
	
	//是否有子，非数据库字段
	private boolean hasChildren;
	
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	@NotDbField
	public List<SiteMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SiteMenu> children) {
		this.children = children;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getPageid() {
		return pageid;
	}
	public void setPageid(String pageid) {
		this.pageid = pageid;
	}
	@NotDbField
	public boolean getHasChildren() {
		hasChildren = this.children==null|| this.children.isEmpty() ?false:true;
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	
	
	
}
