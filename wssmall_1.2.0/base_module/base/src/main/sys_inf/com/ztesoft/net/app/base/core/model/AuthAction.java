package com.ztesoft.net.app.base.core.model;

/**
 * 权限点实体
 * @author kingapex
 * 2010-10-24下午12:38:51
 */
public class AuthAction{
	private String actid;
	private String name;
	private String type;
	private String objvalue;
	private String userid;
	private String createtime;  
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObjvalue() {
		return objvalue;
	}
	public void setObjvalue(String objvalue) {
		this.objvalue = objvalue;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
