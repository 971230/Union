package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.HandlerOrderRoleResp;

/**
 * 处理订单角色权限
 * @作者 MoChunrun
 * @创建日期 2014-11-21 
 * @版本 V 1.0
 */
public class HandlerOrderRoleReq extends ZteRequest<HandlerOrderRoleResp> {
	
	private String order_id;
	private String order_city_code;
	private String flow_trace_id;
	private String order_model;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.data.role.handler";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getFlow_trace_id() {
		return flow_trace_id;
	}

	public void setFlow_trace_id(String flow_trace_id) {
		this.flow_trace_id = flow_trace_id;
	}

	public String getOrder_model() {
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

}
