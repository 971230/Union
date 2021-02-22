package zte.params.goods.resp;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.LimitBuy;

public class LimitBuyResp extends ZteResponse {

	private LimitBuy  limitBuy;

	public LimitBuy getLimitBuy() {
		return limitBuy;
	}

	public void setLimitBuy(LimitBuy limitBuy) {
		this.limitBuy = limitBuy;
	}

	
}
