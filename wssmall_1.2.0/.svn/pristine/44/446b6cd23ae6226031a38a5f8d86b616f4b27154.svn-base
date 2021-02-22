package zte.params.ecsord.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.ecsord.resp.AttrTableCacheGetResp;

public class AttrTableCacheGetReq extends ZteRequest<AttrTableCacheGetResp> {
	
	private String field_name; 
	private String type = "ext";//扩展表 ATTR_TABLE_KEY_SOURCE 属性表

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.attrTable.cache.get";
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
