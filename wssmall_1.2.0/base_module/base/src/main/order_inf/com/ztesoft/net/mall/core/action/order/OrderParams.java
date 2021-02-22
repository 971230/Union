package com.ztesoft.net.mall.core.action.order;

import com.ztesoft.net.mall.core.model.Cart;

public class OrderParams {

	OrderResult orderResult;
	OrderRequst orderRequst;
	
	Cart cart;

	public OrderResult getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(OrderResult orderResult) {
		this.orderResult = orderResult;
	}

	public OrderRequst getOrderRequst() {
		return orderRequst;
	}

	public void setOrderRequst(OrderRequst orderRequst) {
		this.orderRequst = orderRequst;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	

}
