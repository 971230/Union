package zte.net.common.params.req;

import params.ZteRequest;
import zte.net.common.params.resp.ZteVoInsertResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wu.i 
 * 实体对象查询，入参对象
 * @param <T>
 * 
 */
public class ZteVoInsertRequest extends ZteRequest<ZteVoInsertResponse> {
	
	private Object serObject;
	
	private String table_name;




	public Object getSerObject() {
		return serObject;
	}

	public void setSerObject(Object serObject) {
		this.serObject = serObject;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.vo.insert";
	}}


