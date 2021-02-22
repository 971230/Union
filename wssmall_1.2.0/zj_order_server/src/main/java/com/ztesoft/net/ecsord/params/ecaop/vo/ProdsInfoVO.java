package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;


public class ProdsInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String prod_id; 
	private String prod_name;
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	} 
}
 