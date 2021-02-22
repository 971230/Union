package params.goods.resp;

import java.util.HashMap;

import params.ZteResponse;

/**
 * @title 返回商品促销优惠信息
 * @author zou.qinghua
 *
 */
public class GoodsPromotionGetResp extends ZteResponse {

	private HashMap promotion;

	public HashMap getPromotion() {
		return promotion;
	}

	public void setPromotion(HashMap promotion) {
		this.promotion = promotion;
	}
}
