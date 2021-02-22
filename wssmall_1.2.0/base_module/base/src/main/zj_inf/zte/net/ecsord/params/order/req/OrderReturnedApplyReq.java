package zte.net.ecsord.params.order.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.order.resp.OrderReturnedApplyResp;

public class OrderReturnedApplyReq extends ZteRequest<OrderReturnedApplyResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="退单原因",type="String",isNecessary="Y",desc="退单原因")
	private String message;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.return.apply.req";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
