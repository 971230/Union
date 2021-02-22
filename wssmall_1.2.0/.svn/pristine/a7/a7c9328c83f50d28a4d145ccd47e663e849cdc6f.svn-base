package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderApplyItem implements Serializable {

	private String apply_id;
	@ZteSoftCommentAnnotationParam(name="子订单ID（service_type为4退货时必填）",type="String",isNecessary="N",desc="子订单ID（service_type为4退货时必填）")
	private String old_order_item_id;
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="产品ID（service_type为3退货时必填）",type="String",isNecessary="N",desc="产品ID（service_type为3退货时必填）")
	private String product_id;
	private String name;
	@ZteSoftCommentAnnotationParam(name="申请数量",type="String",isNecessary="Y",desc="申请数量")
	private Integer num = 1;
	private Double price;
	@ZteSoftCommentAnnotationParam(name="申请类型2、退款3、换货4、退货",type="String",isNecessary="Y",desc="申请类型2、退款3、换货4、退货")
	private String item_type;
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}
	public String getOld_order_item_id() {
		return old_order_item_id;
	}
	public void setOld_order_item_id(String old_order_item_id) {
		this.old_order_item_id = old_order_item_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
}
