package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class GoodsImportReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.import";
	}

}
