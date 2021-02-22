package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.params.req.ZteInstQueryRequest;

public class AttrInstReq extends ZteRequest<ZteResponse> implements
		IZteBusiRequestHander {

	@Override
	public <T> T store() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T load(ZteInstQueryRequest instParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
