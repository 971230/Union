package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/*
 * 货位
 */
public class WarehouseSeat implements Serializable {

	private String seat_id;
	private String seat_name;
	private String house_id;
	private String disabled;
	public String getSeat_id() {
		return seat_id;
	}
	public void setSeat_id(String seat_id) {
		this.seat_id = seat_id;
	}
	public String getSeat_name() {
		return seat_name;
	}
	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	
	
}
