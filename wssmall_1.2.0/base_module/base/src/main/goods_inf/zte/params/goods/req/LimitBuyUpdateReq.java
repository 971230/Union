package zte.params.goods.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.LimitBuyGoods;

public class LimitBuyUpdateReq extends ZteRequest {
	
	private LimitBuyGoods limitBuyGoods;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.limitBuyGoods.update";
	}

	public LimitBuyGoods getLimitBuyGoods() {
		return limitBuyGoods;
	}

	public void setLimitBuyGoods(LimitBuyGoods limitBuyGoods) {
		this.limitBuyGoods = limitBuyGoods;
	}
	

}
