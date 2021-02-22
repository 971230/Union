package rule.params.coqueue.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.ZteResponse;

public class FailQueueScanReq extends ZteRequest<ZteResponse> {

	private String order_id; //订单ID
	
	private String co_id; //队列ID
	
	private String action_code;//动作编码
	
	private String service_code;//服务编码
	
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "com.orderService.failQueue.deal";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

}
