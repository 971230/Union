package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.OrderAuthorityByUserResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 处理订单角色权限
 * @作者 MoChunrun
 * @创建日期 2014-11-21 
 * @版本 V 1.0
 */
public class OrderAuthorityByUserReq extends ZteRequest<OrderAuthorityByUserResp> {
	
	private String order_id;
	private String user_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.authority.by.user";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

}
