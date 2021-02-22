package zte.net.common.params.req;

import params.ZteBusiRequest;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.api.ApiRuleException;

public class ZteInstInsertRequest<T> extends ZteBusiRequest<ZteBusiResponse>{

	private T ZteBusiRequest;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.zteBusiRequest.insert";
	}

	public T getZteBusiRequest() {
		return ZteBusiRequest;
	}

	public void setZteBusiRequest(T zteBusiRequest) {
		ZteBusiRequest = zteBusiRequest;
	}
	

}
