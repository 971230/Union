package zte.params.coupon.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class FreeOfferPageListReq extends ZteRequest {

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.freeOffer.pageList";
	}

}
