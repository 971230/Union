package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 添加订单
 * @作者 wu.i
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderOuterAttrQueryReq extends ZteRequest<OrderAddResp> {

	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
