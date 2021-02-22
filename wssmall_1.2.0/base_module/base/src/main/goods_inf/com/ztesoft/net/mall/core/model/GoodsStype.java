package com.ztesoft.net.mall.core.model;

/**
 * 服务类型
 * @author hu.yi
 *
 */
public class GoodsStype  implements java.io.Serializable {

	
	private String stype_id;
	private String name;
	private String stype_code;
	private int disabled;
	private String create_time;
	
	
	public String getStype_id() {
		return stype_id;
	}
	public void setStype_id(String stype_id) {
		this.stype_id = stype_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStype_code() {
		return stype_code;
	}
	public void setStype_code(String stype_code) {
		this.stype_code = stype_code;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
	
}
