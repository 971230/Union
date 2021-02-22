package zte.params.ecsord.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.params.ecsord.resp.ZBStartOpenAccountResp;

public class ZBStartOpenAccountReq extends ZteRequest<ZBStartOpenAccountResp>{
	@ZteSoftCommentAnnotationParam(name="订单",type="String",isNecessary="Y",desc="订单")
	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.service.order.zbStartOpenAccount";
	}

}
