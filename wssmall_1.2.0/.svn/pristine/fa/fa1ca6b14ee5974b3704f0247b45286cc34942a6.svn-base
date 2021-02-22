package zte.net.iservice;



import zte.net.ecsord.params.spec.req.GoodsTypeSpecGetReq;
import zte.net.ecsord.params.spec.resp.GoodsTypeSpecGetResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.req.GoodsTypeListReq;
import zte.params.goodstype.req.TypeListReq;
import zte.params.goodstype.req.TypeRelGoodsListReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.goodstype.resp.GoodsTypeListResp;
import zte.params.goodstype.resp.TypeListResp;
import zte.params.goodstype.resp.TypeRelGoodsListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="商品类型管理API",summary="商品类型管理API[查询商品类型 查询类型关联商品]")
public interface IGoodsTypeService {
	/**
	 * 查询商品类型
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品类型",summary="查询商品类型")
	public GoodsTypeGetResp getGoodsType(GoodsTypeGetReq req);
	
	/**
	 * 类型关联商品
	 * @param pageReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询类型关联商品",summary="查询类型关联商品")
	public TypeRelGoodsListResp listGoodsByTypeId(TypeRelGoodsListReq pageReq);
	
	/**
	 * 获取类型
	 * @param req.goods_id，如果为空，则返回全部类型
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="获取商品类型",summary="获取商品类型")
	public TypeListResp listTypes(TypeListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品货品类型信息",summary="获取商品货品类型信息",isOpen=false)
    public GoodsTypeSpecGetResp getGoodsType(GoodsTypeSpecGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品货品类型列表",summary="获取商品货品类型列表",isOpen=false)
	public GoodsTypeListResp getTypeList(GoodsTypeListReq req);
}
