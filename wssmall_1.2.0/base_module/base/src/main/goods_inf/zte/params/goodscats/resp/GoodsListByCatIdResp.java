package zte.params.goodscats.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.support.GoodsView;

public class GoodsListByCatIdResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="Y",desc="商品列表")
	private List<Goods> goodsList;
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="Y",desc="商品列表")
	private List<GoodsView> goodsViewList;

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	/**
	 * @return the goodsViewList
	 */
	public List<GoodsView> getGoodsViewList() {
		return goodsViewList;
	}

	/**
	 * @param goodsViewList the goodsViewList to set
	 */
	public void setGoodsViewList(List<GoodsView> goodsViewList) {
		this.goodsViewList = goodsViewList;
	}
}
