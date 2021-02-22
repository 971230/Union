package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class LimitBuyGoodsListReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.limitbuy.goods.list";
	}

}
