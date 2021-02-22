package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * goods_info节点 （商品/货品信息节点，已确认）
 * 
 * @author song.qi 2017年12月26日
 */
public class GoodsInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "prod_code", type = "String", isNecessary = "N", desc = "prod_code：商品编码")
	private String prod_code;// 商品编码 spuid
	@ZteSoftCommentAnnotationParam(name = "prod_name", type = "String", isNecessary = "N", desc = "prod_name：商品名称")
	private String prod_name;// 商品名称 用于订单展示
	@ZteSoftCommentAnnotationParam(name = "prod_offer_code", type = "String", isNecessary = "N", desc = "prod_offer_code：货品编码")
	private String prod_offer_code;// 货品编码skuid
	@ZteSoftCommentAnnotationParam(name = "prod_offer_name", type = "String", isNecessary = "N", desc = "prod_offer_name：货品名称")
	private String prod_offer_name;// 货品名称
	@ZteSoftCommentAnnotationParam(name = "goods_num", type = "String", isNecessary = "N", desc = "goods_num：货品数量")
	private int goods_num;// 货品数量
	@ZteSoftCommentAnnotationParam(name = "goods_num", type = "String", isNecessary = "N", desc = "goods_num：货品应收")
	private int offer_price;// 单位厘
	@ZteSoftCommentAnnotationParam(name = "real_offer_price", type = "String", isNecessary = "N", desc = "real_offer_price：货品实收")
	private int real_offer_price;// 单位厘
	@ZteSoftCommentAnnotationParam(name = "cat_id", type = "String", isNecessary = "N", desc = "cat_id：商品小类")
    private String cat_id;//商品小类

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

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public int getOffer_price() {
		return offer_price;
	}

	public void setOffer_price(int offer_price) {
		this.offer_price = offer_price;
	}

	public int getReal_offer_price() {
		return real_offer_price;
	}

	public void setReal_offer_price(int real_offer_price) {
		this.real_offer_price = real_offer_price;
	}

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
