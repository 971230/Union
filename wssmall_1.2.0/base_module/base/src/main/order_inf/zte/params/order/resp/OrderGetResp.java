package zte.params.order.resp;

import com.ztesoft.net.mall.core.model.Order;

import params.ZteResponse;

public class OrderGetResp extends ZteResponse {

	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
