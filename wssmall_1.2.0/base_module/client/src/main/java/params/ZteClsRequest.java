package params;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


public class ZteClsRequest extends ZteRequest {
	private ZteBusiRequest  zteBusiRequest;

	public ZteBusiRequest getZteBusiRequest() {
		return zteBusiRequest;
	}

	public void setZteBusiRequest(ZteBusiRequest zteBusiRequest) {
		this.zteBusiRequest = zteBusiRequest;
	}

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
	
	
}
