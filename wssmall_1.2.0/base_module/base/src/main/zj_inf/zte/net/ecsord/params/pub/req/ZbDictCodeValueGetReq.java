package zte.net.ecsord.params.pub.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class ZbDictCodeValueGetReq extends ZteRequest {

	private String other_system;
	
	private String field_name;
	
	private String field_attr_id;
	
	private String field_value;
	
	public String getOther_system() {
		return other_system;
	}

	public void setOther_system(String other_system) {
		this.other_system = other_system;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_attr_id() {
		return field_attr_id;
	}

	public void setField_attr_id(String field_attr_id) {
		this.field_attr_id = field_attr_id;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.ZbDictCodeValue.get";
	}

}
