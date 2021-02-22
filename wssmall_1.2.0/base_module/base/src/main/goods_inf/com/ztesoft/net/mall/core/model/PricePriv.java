package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class PricePriv implements Serializable{
	private String goods_id;
	private String role_type;
	private String state;
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getRole_type() {
		return role_type;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
