package com.ztesoft.net.mall.core.model;

/**
 * SEO实体
 * 
 * @author 李志富 lzf<br/>
 *         2010-1-19 上午10:11:48<br/>
 *         version 1.0<br/>
 * <br/>
 */
public class Seo {
	private String id;
	private String title;
	private String meta_keywords;
	private String meta_description;
	private Integer use_static;
	private Integer noindex_catalog;
	private String userid;
	private String siteid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String metaKeywords) {
		meta_keywords = metaKeywords;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String metaDescription) {
		meta_description = metaDescription;
	}

	public Integer getUse_static() {
		return use_static;
	}

	public void setUse_static(Integer useStatic) {
		use_static = useStatic;
	}

	public Integer getNoindex_catalog() {
		return noindex_catalog;
	}

	public void setNoindex_catalog(Integer noindexCatalog) {
		noindex_catalog = noindexCatalog;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

}
