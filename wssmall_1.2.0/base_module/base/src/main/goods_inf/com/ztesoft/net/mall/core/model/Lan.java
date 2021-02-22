package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * @author lqy
 *
 */
public class Lan implements Serializable {
	private String prov_id;
	private String lan_id;
	private String lan_name;
	private String lan_code;
	
	public Lan() {}
	
	public Lan(String lan_id, String lan_name) {
		this.lan_id = lan_id;
		this.lan_name = lan_name;
	}
	
	public String getProv_id() {
		return prov_id;
	}
	public void setProv_id(String prov_id) {
		this.prov_id = prov_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getLan_name() {
		return lan_name;
	}
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	
}
