package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByInfIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetOutOrderByOrderIdReq extends ZteRequest<GetOrderByInfIdResp> {
	
	@ZteSoftCommentAnnotationParam(name="内部订单",type="String",isNecessary="Y",desc="内部订单")
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
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.getOutOrderByOrderId";
	}

}
