package params.order.req;

import params.ZteError;
import params.ZteRequest;
import params.order.resp.OrderWorkItemQueryResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class OrderWorkItemQueryReq extends ZteRequest<OrderWorkItemQueryResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单id",type="String",isNecessary="Y",desc="订单id")
	private String order_id;
	

	
	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单id【order_id】不能为空！"));
        }
		
	}
	
	@Override
	public String getApiMethodName() {
		return "zte.service.order.orderWorkItemQuery";
	}

	
	
}