package zte.params.ecsord.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GetOrderByLogiNoResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="内部订单号",type="List",isNecessary="Y",desc="内部订单号")
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	
}
