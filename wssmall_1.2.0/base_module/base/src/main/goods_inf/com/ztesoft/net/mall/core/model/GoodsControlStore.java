package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class GoodsControlStore implements Serializable {

	private String land_id;
	private String land_name;
	private int store;
	private int type;
	public String getLand_id() {
		return land_id;
	}
	public void setLand_id(String land_id) {
		this.land_id = land_id;
	}
	public String getLand_name() {
		return land_name;
	}
	public void setLand_name(String land_name) {
		this.land_name = land_name;
	}
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
