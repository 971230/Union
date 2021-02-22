package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderExcepCollect;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderExceptionResp;


public class OrderExceptionReq extends ZteRequest<OrderExceptionResp>{

	private OrderExcepCollect orderExcepCollect;

	public OrderExcepCollect getOrderExcepCollect() {
		return orderExcepCollect;
	}

	public void setOrderExcepCollect(OrderExcepCollect orderExcepCollect) {
		this.orderExcepCollect = orderExcepCollect;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(orderExcepCollect == null){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "异常对象不能为空"));
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.orderException.add";
	}
}
