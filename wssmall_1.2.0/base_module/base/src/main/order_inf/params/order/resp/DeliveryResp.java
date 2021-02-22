package params.order.resp;

import com.ztesoft.net.mall.core.model.Delivery;

import params.ZteResponse;

public class DeliveryResp extends ZteResponse {
	private Delivery delivery;

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
}
