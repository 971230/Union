package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.OrderHandleLogsListResp;

public class OrderExceptionLogsListReq extends ZteRequest<OrderHandleLogsListResp> {
	
	private String order_id;
	private String flow_trace_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.exception.log.list";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getFlow_trace_id() {
		return flow_trace_id;
	}

	public void setFlow_trace_id(String flow_trace_id) {
		this.flow_trace_id = flow_trace_id;
	}
	
}
