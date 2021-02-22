package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class AttrValueGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="属性字段名",type="String",isNecessary="Y",desc="属性字段名")
	private String field_name;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.orderService.attr.value.get";
	}

}
