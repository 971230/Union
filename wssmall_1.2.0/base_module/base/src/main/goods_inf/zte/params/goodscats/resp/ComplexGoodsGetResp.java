package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.support.GoodsView;

import params.ZteResponse;

public class ComplexGoodsGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="N",desc="goods：商品列表。")
	private List<GoodsView> goods;

	public List<GoodsView> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsView> goods) {
		this.goods = goods;
	}
}
