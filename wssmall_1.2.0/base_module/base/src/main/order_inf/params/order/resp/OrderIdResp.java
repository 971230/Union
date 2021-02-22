package params.order.resp;

import params.ZteResponse;

/**
 * 
 * @author zengxianlian
 *
 */
public class OrderIdResp extends ZteResponse {
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}
