package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.DeliveryFlowListResp;

public class DeliveryFlowListReq extends ZteRequest<DeliveryFlowListResp> {
	@ZteSoftCommentAnnotationParam(name="物流ID",type="String",isNecessary="Y",desc="物流ID")
	private String delivery_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.delivery.flow.list";
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

}
