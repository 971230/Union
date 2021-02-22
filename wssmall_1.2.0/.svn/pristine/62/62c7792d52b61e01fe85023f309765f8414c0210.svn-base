package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.OrderHandleLogsListResp;

public class OrderHandleLogsListReq extends ZteRequest<OrderHandleLogsListResp> {
	
	private String order_id;
	private String handler_type;
	private String order_is_his;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.handler.log.list";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getHandler_type() {
		return handler_type;
	}

	public void setHandler_type(String handler_type) {
		this.handler_type = handler_type;
	}

	public String getOrder_is_his() {
		return order_is_his;
	}

	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

}
