package com.ztesoft.net.eop.resource.model;

import java.io.Serializable;

public class Access implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4339848792738875940L;
	private String id;
	private String ip;
	private String url; // 具体的url
	private String page; //访问的页面名
	private String area; //访问者地区
	private String access_time; //访问时间
	private int stay_time; //停留时间
	private int point ;//消耗积分
	private String membername; //会员名称，如果为空则为访客
	private String source_from;
	private String objname;//会员名称、系统username
	private String obj_type;//member、adminuser
	private String sessionid;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAccess_time() {
		return access_time;
	}
	public void setAccess_time(String accessTime) {
		 
		access_time = accessTime;
	}
	public int getStay_time() {
		return stay_time;
	}
	public void setStay_time(int stayTime) {
		stay_time = stayTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	public String getObjname() {
		return objname;
	}
	
	public void setObjname(String objname) {
		this.objname = objname;
	}
	
	public String getObj_type() {
		return obj_type;
	}
	
	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}
	
	public String getSessionid() {
		return sessionid;
	}
	
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}	
	
	
}
