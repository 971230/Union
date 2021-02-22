package com.ztesoft.net.server.jsonserver.objreplacepojo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ObjectReplaceGoodsInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "prod_code", type = "String", isNecessary = "N", desc = "prod_code：商品编码")
	private String prod_code;// 商品编码 spuid
	@ZteSoftCommentAnnotationParam(name = "prod_name", type = "String", isNecessary = "N", desc = "prod_name：商品名称")
	private String prod_name;// 商品名称 用于订单展示
	@ZteSoftCommentAnnotationParam(name = "prod_offer_code", type = "String", isNecessary = "N", desc = "prod_offer_code：货品编码")
	private String prod_offer_code;// 货品编码skuid
	@ZteSoftCommentAnnotationParam(name = "prod_offer_name", type = "String", isNecessary = "N", desc = "prod_offer_name：货品名称")
	private String prod_offer_name;// 货品名称
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getProd_offer_code() {
		return prod_offer_code;
	}
	public void setProd_offer_code(String prod_offer_code) {
		this.prod_offer_code = prod_offer_code;
	}
	public String getProd_offer_name() {
		return prod_offer_name;
	}
	public void setProd_offer_name(String prod_offer_name) {
		this.prod_offer_name = prod_offer_name;
	}

	
}
