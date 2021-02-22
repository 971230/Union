package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderItemListResp;

public class OrderItemListReq extends ZteRequest<OrderItemListResp> {
	
	private String order_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.itemlist.get";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
