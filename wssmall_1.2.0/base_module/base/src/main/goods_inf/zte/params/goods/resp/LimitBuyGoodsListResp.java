package zte.params.goods.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class LimitBuyGoodsListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="限购商品列表",type="List",isNecessary="N",desc="goodsList： 限购商品列表。")
	private List<Map> goodsList;

	public List<Map> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Map> goodsList) {
		this.goodsList = goodsList;
	}
}
