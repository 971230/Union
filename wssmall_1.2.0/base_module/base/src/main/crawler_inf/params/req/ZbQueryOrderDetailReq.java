package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class ZbQueryOrderDetailReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.queryOrderDetail";
	}

	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
