package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;

public class GoodsQueryResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="商品",type="String",isNecessary="Y",desc="商品信息")
	private Goods goods;
	@ZteSoftCommentAnnotationParam(name="商品评论：分页器",type="String",isNecessary="N",desc="分页器：commentPage.getResult()获取订单数据")
	private Page commentPage;
	@ZteSoftCommentAnnotationParam(name="商品咨询：分页器",type="String",isNecessary="N",desc="分页器：commentPage.getResult()获取订单数据")	
	private Page discussPage;
	
	public Page getCommentPage() {
		return commentPage;
	}

	public void setCommentPage(Page commentPage) {
		this.commentPage = commentPage;
	}

	public Page getDiscussPage() {
		return discussPage;
	}

	public void setDiscussPage(Page discussPage) {
		this.discussPage = discussPage;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
}
