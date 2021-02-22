package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class WareHouseAttrCfg implements Serializable{
       private String ename;
       private String cname;
       private String value;
       private String remark;
       private String api_path;
       private String source_from;
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getApi_path() {
		return api_path;
	}
	public void setApi_path(String api_path) {
		this.api_path = api_path;
	}
       
}
