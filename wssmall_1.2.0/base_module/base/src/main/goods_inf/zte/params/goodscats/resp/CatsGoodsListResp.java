package zte.params.goodscats.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;

public class CatsGoodsListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="Y",desc="商品列表")
	private List<Goods> goodsList;

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
}
