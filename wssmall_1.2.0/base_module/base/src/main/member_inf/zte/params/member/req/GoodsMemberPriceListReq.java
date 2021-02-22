package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class GoodsMemberPriceListReq extends ZteRequest {

	private String goods_id;
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "zte.memberService.goods.price.list";
	}

}