package zte.net.iservice;

import zte.params.goodscats.req.CatAddReq;
import zte.params.goodscats.req.CatDeleteReq;
import zte.params.goodscats.req.CatEditReq;
import zte.params.goodscats.req.CatGetByIdReq;
import zte.params.goodscats.req.CatGetReq;
import zte.params.goodscats.req.CatHotListReq;
import zte.params.goodscats.req.CatListByCondReq;
import zte.params.goodscats.req.CatListByGoodsIdReq;
import zte.params.goodscats.req.CatListByTypeReq;
import zte.params.goodscats.req.CatListByUseridReq;
import zte.params.goodscats.req.CatPathGetReq;
import zte.params.goodscats.req.CatsGoodsListReq;
import zte.params.goodscats.req.CatsListByIdReq;
import zte.params.goodscats.req.CatsListReq;
import zte.params.goodscats.req.ChildCatsListReq;
import zte.params.goodscats.req.ChildrenCatsListReq;
import zte.params.goodscats.req.ComplexGoodsGetReq;
import zte.params.goodscats.resp.CatAddResp;
import zte.params.goodscats.resp.CatDeleteResp;
import zte.params.goodscats.resp.CatEditResp;
import zte.params.goodscats.resp.CatGetResp;
import zte.params.goodscats.resp.CatHotListResp;
import zte.params.goodscats.resp.CatListByCondResp;
import zte.params.goodscats.resp.CatListByGoodsIdResp;
import zte.params.goodscats.resp.CatListByTypeResp;
import zte.params.goodscats.resp.CatListByUseridResp;
import zte.params.goodscats.resp.CatPathGetResp;
import zte.params.goodscats.resp.CatsGoodsListResp;
import zte.params.goodscats.resp.CatsListByIdResp;
import zte.params.goodscats.resp.CatsListResp;
import zte.params.goodscats.resp.ChildCatsListResp;
import zte.params.goodscats.resp.ChildrenCatsListResp;
import zte.params.goodscats.resp.ComplexGoodsGetResp;
import zte.params.tags.req.TagGetReq;
import zte.params.tags.resp.TagGetResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="分类信息管理API",summary="分类信息管理API[查询分类信息，按会员类型查询不同的分类 查询分类推荐商品 查询子分类列表（根据分类查询子分类）查询热卖分类标签]")
public interface IGoodsCatsService {
	@ZteSoftCommentAnnotation(type="method",desc="查询分类信息，按会员类型查询不同的分类",summary="查询分类信息，按会员类型查询不同的分类")
	public CatsListResp listGoodsCatsByLvId(CatsListReq listReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询分类推荐商品",summary="查询分类推荐商品")
	public CatsGoodsListResp listComplexGoodsByCatId(CatsGoodsListReq listReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询子分类列表",summary="查询子分类列表（根据分类查询子分类）")
	public ChildCatsListResp listGoodsCatsByParentId(ChildCatsListReq listReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询热卖分类标签",summary="查询热卖分类标签")
	public TagGetResp getHotTag(TagGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取一个商品分类",summary="根据goods_id获取一个商品分类")
	public CatGetResp getCat(CatGetReq req);
	
	//获取所有子类型
	@ZteSoftCommentAnnotation(type="method",desc="获取所有子类型",summary="获取所有子类型")
	public ChildrenCatsListResp listAllChildren(ChildrenCatsListReq req);
	
	//获取分类path
	@ZteSoftCommentAnnotation(type="method",desc="获取分类的path",summary="根据分类的path")
	public CatPathGetResp getCatPath(CatPathGetReq req);
	
	//根据分类获取关联商品信息
	@ZteSoftCommentAnnotation(type="method",desc="根据分类获取关联商品信息",summary="根据分类获取关联商品信息")
	public ComplexGoodsGetResp getComplexGoods(ComplexGoodsGetReq req);
	
	//根据商品ID获取分类
	@ZteSoftCommentAnnotation(type="method",desc="根据商品id获取分类",summary="根据商品id获取分类")
	public CatListByGoodsIdResp listCatsByGoodsId(CatListByGoodsIdReq req);
	
	//获取分类列表
	@ZteSoftCommentAnnotation(type="method",desc="获取分类列表",summary="获取分类列表")
	public CatListByCondResp listCatsByCond(CatListByCondReq req);
	
	//获取单个分类
	@ZteSoftCommentAnnotation(type="method",desc="获取单个分类",summary="获取单个分类")
	public CatGetResp getCatById(CatGetByIdReq req);
	
	//获取热词
	@ZteSoftCommentAnnotation(type="method",desc="热词搜索",summary="热词搜索")
	public CatHotListResp getHotList(CatHotListReq req);
	
	//获取分类列表
	@ZteSoftCommentAnnotation(type="method",desc="获取分类列表",summary="获取分类列表")
	public CatsListByIdResp listCats(CatsListByIdReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据类型ID获取分类列表",summary="根据类型ID获取分类列表",isOpen=false)
	public CatListByTypeResp listCatByTypeId(CatListByTypeReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="添加分类",summary="添加分类")
    public CatAddResp addCat(CatAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="编辑分类",summary="编辑分类")
	public CatEditResp editCat(CatEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="删除分类",summary="编辑分类")
	public CatDeleteResp deleteCat(CatDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取分销商分类列表",summary="获取分销商分类列表")
	public CatListByUseridResp listCatsByUserid(CatListByUseridReq req);
}
