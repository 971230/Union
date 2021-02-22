package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.DeliveryItemsQueryResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.NotDbField;

public class DeliveryItemsQueryReq extends ZteRequest<DeliveryItemsQueryResp> {
	
	private String order_id;
	private int itemType = OrderStatus.DELIVERY_ITEM_TYPE_3;//子单类型

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ordrService.order.delivery.items.type.query";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

}
