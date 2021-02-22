package com.ztesoft.net.mall.core.model.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单价格信息实体
 * @author kingapex
 * 2010-5-30上午11:58:33
 */
public class OrderPrice  implements Serializable{
	@ZteSoftCommentAnnotationParam(name="原始总价",type="String",isNecessary="Y",desc="订单价格")
	private Double originalPrice; //原始总价,未经过任何优惠的
	@ZteSoftCommentAnnotationParam(name="商品价格",type="String",isNecessary="Y",desc="商品价格")
	private Double goodsPrice; //商品价格，经过优惠过的
	@ZteSoftCommentAnnotationParam(name="订单总价",type="String",isNecessary="Y",desc="订单总价")
	private Double orderPrice;//订单总价，优惠过的，包含商品价格和配置费用
	@ZteSoftCommentAnnotationParam(name="优惠的价格",type="String",isNecessary="Y",desc="优惠的价格")
	private Double discountPrice; //优惠的价格
	@ZteSoftCommentAnnotationParam(name="配送费用",type="String",isNecessary="Y",desc="配送费用")
	private Double shippingPrice; //配送费用
	@ZteSoftCommentAnnotationParam(name="保价费用",type="String",isNecessary="Y",desc="保价费用")
	private  Double protectPrice; //保价费用
	@ZteSoftCommentAnnotationParam(name="商品重量",type="String",isNecessary="Y",desc="商品重量")
	private Double weight ; //商品重量
	@ZteSoftCommentAnnotationParam(name="可获得积分",type="String",isNecessary="Y",desc="可获得积分")
	private Integer point; //可获得积分
	private Map<String,Object> discountItem; //使用优惠的项目
	private int staffCount;
	
	public OrderPrice(){
		discountItem = new HashMap<String, Object>();
	}
	public Double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Double getDiscountPrice() {
		discountPrice= discountPrice==null?0:discountPrice;
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Double getProtectPrice() {
		return protectPrice;
	}
	public void setProtectPrice(Double protectPrice) {
		this.protectPrice = protectPrice;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public Map<String, Object> getDiscountItem() {
		return discountItem;
	}
	public void setDiscountItem(Map<String, Object> discountItem) {
		this.discountItem = discountItem;
	}
	public int getStaffCount() {
		return staffCount;
	}
	public void setStaffCount(int staffCount) {
		this.staffCount = staffCount;
	}
	
	
}
