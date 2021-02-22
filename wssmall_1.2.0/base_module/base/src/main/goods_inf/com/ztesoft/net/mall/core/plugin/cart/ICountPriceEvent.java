package com.ztesoft.net.mall.core.plugin.cart;

import com.ztesoft.net.mall.core.model.support.OrderPrice;

/**
 * 计算价格事件
 * @author kingapex
 *
 */
public interface ICountPriceEvent {
	
	
	public OrderPrice countPrice(OrderPrice orderprice);
	
}
