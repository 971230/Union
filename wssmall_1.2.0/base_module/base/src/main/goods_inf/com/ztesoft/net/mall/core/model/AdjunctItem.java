package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * 配件列表实体
 * 
 * @author lzf<br/>
 *         2010-3-31 上午09:49:07<br/>
 *         version 1.0<br/>
 */
public class AdjunctItem implements Serializable {
	
	private String productid;
	private String goodsid;
	private String name;
	private String specs;
	private Double price;
	private Double coupPrice; //优惠后的价格
	
	private String adjunct_id;
	
	private String imgPath;
	@NotDbField
    public String getAdjunct_id() {
		return adjunct_id;
	}
	public void setAdjunct_id(String adjunct_id) {
		this.adjunct_id = adjunct_id;
	}
	@NotDbField
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	private int num;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
 
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCoupPrice() {
		return coupPrice;
	}
	public void setCoupPrice(Double coupPrice) {
		this.coupPrice = coupPrice;
	}
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
 
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	 
}
