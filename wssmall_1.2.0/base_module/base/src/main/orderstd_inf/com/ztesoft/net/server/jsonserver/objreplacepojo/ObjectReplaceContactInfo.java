package com.ztesoft.net.server.jsonserver.objreplacepojo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ObjectReplaceContactInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "ship_name", type = "String", isNecessary = "Y", desc = "联系人姓名")
	private String ship_name;

	@ZteSoftCommentAnnotationParam(name = "ship_tel", type = "String", isNecessary = "Y", desc = "联系电话")
	private String ship_tel;

	@ZteSoftCommentAnnotationParam(name = "ship_addr", type = "String", isNecessary = "N", desc = "联系地址")
	private String ship_addr;

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
	
	
}
