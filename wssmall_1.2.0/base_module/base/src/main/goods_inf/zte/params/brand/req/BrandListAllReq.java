package zte.params.brand.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

public class BrandListAllReq extends ZteRequest {
	

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.brand.list.all";
	}

}
