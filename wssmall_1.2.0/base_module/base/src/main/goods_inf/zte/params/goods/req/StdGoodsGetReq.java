package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class StdGoodsGetReq extends ZteRequest {
	private String goodsName;
	private String titleName;
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.stdgoods.getStdGoodsByName";
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}
