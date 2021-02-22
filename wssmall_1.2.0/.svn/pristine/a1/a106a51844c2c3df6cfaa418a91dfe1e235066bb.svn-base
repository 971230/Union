package zte.params.goods.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.support.GoodsView;

public class GoodsIntroResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="商品信息",type="String",isNecessary="Y",desc="商品信息，取商品详情信息goods.getIntro()")
	private Goods goods;
	@ZteSoftCommentAnnotationParam(name="商品视图",type="String",isNecessary="Y",desc="商品视图，对商品图片和属性做了处理")
	private GoodsView goodsView;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public GoodsView getGoodsView() {
		return goodsView;
	}

	public void setGoodsView(GoodsView goodsView) {
		this.goodsView = goodsView;
	}
	
}
