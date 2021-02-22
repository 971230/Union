package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 2.3.5. 订单信息查询接口
 * 
 * @author 宋琪
 *
 * @date 2017年6月14日
 */
public class OrderPayInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "pay_method", type = "String", isNecessary = "N", desc = "pay_method：支付类型")
	private String pay_method;

	@ZteSoftCommentAnnotationParam(name = "pay_sequ", type = "String", isNecessary = "N", desc = "pay_sequ：支付流水")
	private String pay_sequ;

	@ZteSoftCommentAnnotationParam(name = "goods_amount", type = "String", isNecessary = "N", desc = "goods_amount：商品价格,单位厘")
	private String goods_amount;

	@ZteSoftCommentAnnotationParam(name = "pay_amount", type = "String", isNecessary = "N", desc = "pay_amount：实收金额")
	private String pay_amount;

	@ZteSoftCommentAnnotationParam(name = "discount_amount", type = "String", isNecessary = "N", desc = "discount_amount：优惠金额")
	private String discount_amount;

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getPay_sequ() {
		return pay_sequ;
	}

	public void setPay_sequ(String pay_sequ) {
		this.pay_sequ = pay_sequ;
	}

	public String getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(String goods_amount) {
		this.goods_amount = goods_amount;
	}

	public String getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}

}
