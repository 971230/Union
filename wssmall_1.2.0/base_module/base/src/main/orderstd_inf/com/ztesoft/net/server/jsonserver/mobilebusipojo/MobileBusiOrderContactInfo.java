package com.ztesoft.net.server.jsonserver.mobilebusipojo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * contact_info节点 （联系人信息）
 * 
 * @author song.qi 2018年03月05日
 */
public class MobileBusiOrderContactInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "ship_name", type = "String", isNecessary = "Y", desc = "联系人姓名")
	private String ship_name;

	@ZteSoftCommentAnnotationParam(name = "ship_tel", type = "String", isNecessary = "Y", desc = "联系电话")
	private String ship_tel;

	@ZteSoftCommentAnnotationParam(name = "ship_addr", type = "String", isNecessary = "N", desc = "联系地址")
	private String ship_addr;
	
	@ZteSoftCommentAnnotationParam(name = "ship_tel2", type = "String", isNecessary = "N", desc = "第二联系人")
	private String ship_tel2;

	@ZteSoftCommentAnnotationParam(name = "ship_province", type = "String", isNecessary = "N", desc = "收货省")
	private String ship_province;
	
	@ZteSoftCommentAnnotationParam(name = "ship_city", type = "String", isNecessary = "N", desc = "收货地市")
	private String ship_city;
	
	@ZteSoftCommentAnnotationParam(name = "ship_district", type = "String", isNecessary = "N", desc = "收货区县")
	private String ship_district;
	
	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getShip_tel2() {
		return ship_tel2;
	}

	public void setShip_tel2(String ship_tel2) {
		this.ship_tel2 = ship_tel2;
	}

	public String getShip_province() {
		return ship_province;
	}

	public void setShip_province(String ship_province) {
		this.ship_province = ship_province;
	}

	public String getShip_city() {
		return ship_city;
	}

	public void setShip_city(String ship_city) {
		this.ship_city = ship_city;
	}

	public String getShip_district() {
		return ship_district;
	}

	public void setShip_district(String ship_district) {
		this.ship_district = ship_district;
	}

	
}
