package zte.params.order.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.OrderItem;

public class OrderItemGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="子订单信息",type="String",isNecessary="Y",desc="子订单信息",hasChild=true)
	private OrderItem orderItem;

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	
}
