package zte.net.ecsord.params.base.req;

import params.ZteBusiRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 
 * list转zterequest对象
 * 
 */
public  class ZteBusiWraperRequest extends ZteBusiRequest  {
	String field_name;
	Object field_value;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public Object getField_value() {
		return field_value;
	}

	public void setField_value(Object field_value) {
		this.field_value = field_value;
	}

	

	

	

}