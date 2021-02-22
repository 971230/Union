package com.ztesoft.net.server.jsonserver.depositpojo;

public class DepositGoodsInfo {
	
	private String prod_offer_code;//商品编码-必填
	
	private String prod_offer_name;//商品名称-必填
	

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
