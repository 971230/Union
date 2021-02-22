package com.ztesoft.net.mall.core.model;

import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;





/**
 * 文章分类
 * @author apexking
 *
 */
public class ArticleCat  {


     protected String cat_id;
     protected String name;
     protected String parent_id;
     protected String cat_path;
     protected int cat_order;
     
     /************以下为非数据库字段*************/
     private boolean hasChildren ;
     private List<ArticleCat> children;  //子类别
     
    public ArticleCat() {
    }


	public String getCat_id() {
		return cat_id;
	}


	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}


	public int getCat_order() {
		return cat_order;
	}


	public void setCat_order(int cat_order) {
		this.cat_order = cat_order;
	}


	public String getCat_path() {
		return cat_path;
	}


	public void setCat_path(String cat_path) {
		this.cat_path = cat_path;
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
	public boolean getHasChildren() {
		hasChildren = this.children==null|| this.children.isEmpty() ?false:true;
		return hasChildren;
	}


	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@NotDbField
	public List<ArticleCat> getChildren() {
		
		return children;
	}


	public void setChildren(List<ArticleCat> children) {
		this.children = children;
	}

	
}