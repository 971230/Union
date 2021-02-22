package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class CatBrandListReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.catBrand.list";
	}

}
