package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.model.OrderRole;

public class HandlerOrderRoleResp extends ZteResponse {

	private List<OrderRole> orderRoleList;

	public List<OrderRole> getOrderRoleList() {
		return orderRoleList;
	}

	public void setOrderRoleList(List<OrderRole> orderRoleList) {
		this.orderRoleList = orderRoleList;
	}
	
}
