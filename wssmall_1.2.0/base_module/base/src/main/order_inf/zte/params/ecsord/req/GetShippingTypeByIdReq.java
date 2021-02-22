package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByInfIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetShippingTypeByIdReq extends ZteRequest<GetOrderByInfIdResp> {
	
	@ZteSoftCommentAnnotationParam(name="外部订单",type="String",isNecessary="Y",desc="外部订单")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="总部订单号",type="String",isNecessary="Y",desc="总部订单号")
	private String inf_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.getShippingTypeById";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getInf_id() {
		return inf_id;
	}

	public void setInf_id(String inf_id) {
		this.inf_id = inf_id;
	}
}
