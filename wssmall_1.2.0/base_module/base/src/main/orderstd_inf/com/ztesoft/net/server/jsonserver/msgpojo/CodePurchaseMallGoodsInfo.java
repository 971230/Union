package com.ztesoft.net.server.jsonserver.msgpojo;

import java.util.List;

public class CodePurchaseMallGoodsInfo {

	private String prod_offer_code;//商品编码 -必填
	
	private String prod_offer_name;//商品名称 -必填
	
	private int goods_num;//商品数量 -必填
	
	private String offer_price;//商品应收 -必填
	
	private String real_offer_price;//商品实收 -必填
	
	private String realname_type;//实名认证类型 -必填
	
	private String act_flag;//激活类型 -必填
	
	private String develop_code;//发展人编码 -非必填

	private String develop_name;//发展人姓名 -非必填
	
	private String referee_name;//推荐人名称 -非必填
	
	private String referee_num;//推荐人号码 -非必填
	
	private String ser_type;//付费类型 -必填
	
	private CodePurchaseMallBroadInfo broad_info;//宽带信息节点 -必填
	
	private List<CodePurchaseMallDiscountInfo> discount_info;//优惠信息 -必填

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

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public String getOffer_price() {
		return offer_price;
	}

	public void setOffer_price(String offer_price) {
		this.offer_price = offer_price;
	}

	public String getReal_offer_price() {
		return real_offer_price;
	}

	public void setReal_offer_price(String real_offer_price) {
		this.real_offer_price = real_offer_price;
	}

	public String getRealname_type() {
		return realname_type;
	}

	public void setRealname_type(String realname_type) {
		this.realname_type = realname_type;
	}

	public String getAct_flag() {
		return act_flag;
	}

	public void setAct_flag(String act_flag) {
		this.act_flag = act_flag;
	}

	public String getDevelop_code() {
		return develop_code;
	}

	public void setDevelop_code(String develop_code) {
		this.develop_code = develop_code;
	}

	public String getDevelop_name() {
		return develop_name;
	}

	public void setDevelop_name(String develop_name) {
		this.develop_name = develop_name;
	}

	public String getReferee_name() {
		return referee_name;
	}

	public void setReferee_name(String referee_name) {
		this.referee_name = referee_name;
	}

	public String getReferee_num() {
		return referee_num;
	}

	public void setReferee_num(String referee_num) {
		this.referee_num = referee_num;
	}

	public String getSer_type() {
		return ser_type;
	}

	public void setSer_type(String ser_type) {
		this.ser_type = ser_type;
	}

	public CodePurchaseMallBroadInfo getBroad_info() {
		return broad_info;
	}

	public void setBroad_info(CodePurchaseMallBroadInfo broad_info) {
		this.broad_info = broad_info;
	}

	public List<CodePurchaseMallDiscountInfo> getDiscount_info() {
		return discount_info;
	}

	public void setDiscount_info(List<CodePurchaseMallDiscountInfo> discount_info) {
		this.discount_info = discount_info;
	}

	
	
}
