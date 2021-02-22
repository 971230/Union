package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

public class EcsGoodsPageListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品货品分页器",type="Page",isNecessary="N",desc="goodsPage：商品货品分页，goodsPage.getResult()获取商品列表。")
	private Page goodsPage;

	public Page getGoodsPage() {
		return goodsPage;
	}

	public void setGoodsPage(Page goodsPage) {
		this.goodsPage = goodsPage;
	}
}
