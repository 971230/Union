package zte.params.ecsord.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author ZX
 * InsertOrderHandLogResp.java
 * 2014-12-25
 */
public class InsertOrderHandLogResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="订单编号",type="List",isNecessary="Y",desc="订单编号")
	private String order_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}
