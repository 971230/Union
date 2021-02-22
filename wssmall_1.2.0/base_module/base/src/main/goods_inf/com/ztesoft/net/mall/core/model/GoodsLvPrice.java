package com.ztesoft.net.mall.core.model;


import java.io.Serializable;

/**
 * 商品会员价
 * @author kingapex
 *
 */
public class GoodsLvPrice implements Serializable {
	private Double price;
	private String lvid;
	private String productid;
	private String goodsid;
	private String prodname;
	private String membername;
	private String status;
	private String id;
	private Float lv_discount;
	
	public Float getLv_discount() {
		return lv_discount;
	}
	public void setLv_discount(Float lv_discount) {
		this.lv_discount = lv_discount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getLvid() {
		return lvid;
	}
	public void setLvid(String lvid) {
		this.lvid = lvid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
