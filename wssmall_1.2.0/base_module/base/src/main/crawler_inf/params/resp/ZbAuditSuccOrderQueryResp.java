package params.resp;

import java.util.List;

import params.ZteResponse;

public class ZbAuditSuccOrderQueryResp extends ZteResponse {
	private List orders;

	public List getOrders() {
		return orders;
	}

	public void setOrders(List orders) {
		this.orders = orders;
	}
}
