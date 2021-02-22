package zte.net.iservice;

import zte.params.goods.req.GoodsAdjunctListReq;
import zte.params.goods.resp.GoodsAdjunctListResp;

//@ZteSoftCommentAnnotation(type="class",desc="商品配件管理",summary="商品配件管理API（解耦）")
public interface IGoodsAdjunctService {

	/**
	 * 获取商品配件
	 * @param goods_id
	 * @return
	 */
	public GoodsAdjunctListResp list(GoodsAdjunctListReq req);
}
