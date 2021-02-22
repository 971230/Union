package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.AttrDefGetResp;

public class AttrDefGetReq extends ZteRequest<AttrDefGetResp> {
	
	private String field_attr_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.attr.def.get";
	}

	public String getField_attr_id() {
		return field_attr_id;
	}

	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}

}
