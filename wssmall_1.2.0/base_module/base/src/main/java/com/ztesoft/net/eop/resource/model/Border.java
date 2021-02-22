package com.ztesoft.net.eop.resource.model;

/**
 * 边框实体 
 * @author kingapex
 * 2010-1-28下午05:02:12
 */
public class Border extends Resource {
	private String id;
	private String borderid;
	private String bordername;
	private String themepath;
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getBorderid() {
		return borderid;
	}
	public void setBorderid(String borderid) {
		this.borderid = borderid;
	}
	public String getBordername() {
		return bordername;
	}
	public void setBordername(String bordername) {
		this.bordername = bordername;
	}
	public String getThemepath() {
		return themepath;
	}
	public void setThemepath(String themepath) {
		this.themepath = themepath;
	}
	
}
