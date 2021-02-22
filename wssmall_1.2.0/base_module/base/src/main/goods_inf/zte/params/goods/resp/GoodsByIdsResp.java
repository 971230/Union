package zte.params.goods.resp;

import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;


/**
 * 根据商品id或产品id查询商品
 * @author hu.yi
 * @datew 2014.04.15
 */
public class GoodsByIdsResp extends ZteResponse{

	private Goods goods;

	
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	
}
