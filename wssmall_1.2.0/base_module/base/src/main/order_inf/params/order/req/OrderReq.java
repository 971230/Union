package params.order.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class OrderReq extends ZteRequest {
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
