package zte.net.ecsord.params.pub.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class RegionListReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.region.list";
	}

}
