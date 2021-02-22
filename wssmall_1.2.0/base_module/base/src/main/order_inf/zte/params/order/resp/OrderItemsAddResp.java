package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.OrderItem;

public class OrderItemsAddResp extends ZteResponse {

	private List<OrderItem> orderItemList;

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
}
