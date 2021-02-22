package zte.params.order.resp;

import java.util.List;

import com.ztesoft.net.mall.core.model.DeliveryItem;

import params.ZteResponse;

public class DeliveryItemsQueryByDeIdResp extends ZteResponse {

	private List<DeliveryItem> deliveryItems;

	public List<DeliveryItem> getDeliveryItems() {
		return deliveryItems;
	}

	public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
		this.deliveryItems = deliveryItems;
	}
	
}
