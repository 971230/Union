package zte.params.order.req;

import java.util.List;

import params.ZteRequest;
import zte.params.order.resp.OrderItemsAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderItem;

public class OrderItemsAddReq extends ZteRequest<OrderItemsAddResp> {
	
	private List<OrderItem> orderItemList;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.items.add";
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
