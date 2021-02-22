package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

import zte.params.goods.req.GoodsSearchPageListReq;
import zte.params.goods.req.ParamsPutReq;
import zte.params.goods.req.SelectorGetReq;
import zte.params.goods.resp.GoodsSearchPageListResp;
import zte.params.goods.resp.ParamsPutResp;
import zte.params.goods.resp.SelectorGetResp;

//@ZteSoftCommentAnnotation(type="class",desc="商品搜索",summary="商品搜索（解耦用）")
public interface IGoodSearchService {

	//搜索商品
	@ZteSoftCommentAnnotation(type="method",desc="搜索商品",summary="搜索商品（解耦）")	
	public GoodsSearchPageListResp searchGoods(GoodsSearchPageListReq req);
	
	//获取选择器
	@ZteSoftCommentAnnotation(type="method",desc="获取选择器",summary="获取选择器（解耦）")	
	public SelectorGetResp getSelector(SelectorGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="设置参数",summary="设置参数（解耦）")	
	public ParamsPutResp putParams(ParamsPutReq req);
}
