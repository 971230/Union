package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.params.order.resp.DeliveryItemAddResp;

public class DeliveryItemAddReq extends ZteRequest<DeliveryItemAddResp> {

	private OrderDeliveryItemBusiRequest deliveryItem;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.delivery.item.add";
	}

	public OrderDeliveryItemBusiRequest getDeliveryItem() {
		return deliveryItem;
	}

	public void setDeliveryItem(OrderDeliveryItemBusiRequest deliveryItem) {
		this.deliveryItem = deliveryItem;
	}
	
}
