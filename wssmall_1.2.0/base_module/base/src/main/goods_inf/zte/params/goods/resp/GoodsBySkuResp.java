package zte.params.goods.resp;

import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;


/**
 * 根据sku查询商品
 */
public class GoodsBySkuResp extends ZteResponse{

	private Goods goods;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	
}
