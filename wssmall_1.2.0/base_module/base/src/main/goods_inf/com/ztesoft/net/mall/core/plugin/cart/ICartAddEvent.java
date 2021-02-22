package com.ztesoft.net.mall.core.plugin.cart;

import com.ztesoft.net.mall.core.model.Cart;

/**
 * 购物车添加事件
 * @author kingapex
 *
 */
public interface ICartAddEvent {
	public void add(Cart cart);
}
