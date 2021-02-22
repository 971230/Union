package com.ztesoft.net.mall.core.model.support;


import java.io.Serializable;

/**
 * 公告视图
 * 
 * @author wui 2010-7-16下午02:05:11
 */
public class DataView  implements Serializable {

	private String cat_name;
	private String sys_lock;
	private String hit;
	private String lastmodified;
	private String add_time;
	private String cat_id;
	private String id;
	private String sort;
	
	private String content;
	private String article_img_url;
	private String page_title;
	private String title;
	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getSys_lock() {
		return sys_lock;
	}

	public void setSys_lock(String sys_lock) {
		this.sys_lock = sys_lock;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticle_img_url() {
		return article_img_url;
	}

	public void setArticle_img_url(String article_img_url) {
		this.article_img_url = article_img_url;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String page_title) {
		this.page_title = page_title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
