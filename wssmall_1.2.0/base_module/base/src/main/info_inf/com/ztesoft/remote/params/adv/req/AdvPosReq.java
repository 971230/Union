package com.ztesoft.remote.params.adv.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class AdvPosReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.listAllAdvPos";
	}

}
