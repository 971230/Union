package zte.net.iservice;



import zte.params.tags.req.PackageGetReq;
import zte.params.tags.req.TagGoodsListReq;
import zte.params.tags.req.TagListReq;
import zte.params.tags.resp.PackageGetResp;
import zte.params.tags.resp.TagGoodsListResp;
import zte.params.tags.resp.TagListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="标签管理API",summary="标签管理API[查询标签列表 查询标签关联商品]")
public interface IGoodsTagsService {

	/**
	 * 标签列表
	 * @param listReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询标签列表",summary="查询标签列表")
	public TagListResp listTag(TagListReq listReq);
	
	/**
	 * 
	 * @param listReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询标签关联商品",summary="查询标签关联商品")
	public TagGoodsListResp listGoodsByTagId(TagGoodsListReq listReq);
	
	/**
	 * 获取商品包-广东联通ECS
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="根据商品ID获取商品包",summary="根据商品ID获取商品包")
	public PackageGetResp getPackage(PackageGetReq req);
}
