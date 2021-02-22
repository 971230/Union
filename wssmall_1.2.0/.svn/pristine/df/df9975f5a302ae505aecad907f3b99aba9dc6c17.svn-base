package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderQueryParam;

import params.ZteRequest;
import zte.params.order.resp.OrderCountGetResp;

public class OrderCountGetReq extends ZteRequest<OrderCountGetResp> {

	@ZteSoftCommentAnnotationParam(name="查询参数",type="orderQueryParam",isNecessary="N",desc="查询参数",hasChild=true)
	private OrderQueryParam orderQueryParam;
	@ZteSoftCommentAnnotationParam(name="是否按权限查询",type="String",isNecessary="Y",desc="是否按权限查询[默认为 false]")
	private boolean security = false;
	private int event=0;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.count";
	}

	public OrderQueryParam getOrderQueryParam() {
		return orderQueryParam;
	}

	public void setOrderQueryParam(OrderQueryParam orderQueryParam) {
		this.orderQueryParam = orderQueryParam;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

}
