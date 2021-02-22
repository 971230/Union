package zte.net.iservice;

import params.ZteResponse;
import params.tags.req.TagCat_typeReq;
import params.tags.resp.TagResp;
import zte.net.ecsord.params.spec.req.GoodsSpecReq;
import zte.net.ecsord.params.spec.req.GoodsTypeIdGetReq;
import zte.net.ecsord.params.spec.req.PCodeGetReq;
import zte.net.ecsord.params.spec.req.ProductSpecReq;
import zte.net.ecsord.params.spec.resp.GoodsSpecResp;
import zte.net.ecsord.params.spec.resp.GoodsTypeIdGetResp;
import zte.net.ecsord.params.spec.resp.PCodeGetResp;
import zte.net.ecsord.params.spec.resp.ProductSpecResp;
import zte.net.ecsord.params.wyg.req.RefreshGoodsCacheReq;
import zte.net.ecsord.params.wyg.resp.RefreshGoodsCacheResp;
import zte.params.goods.req.ActivityImportReq;
import zte.params.goods.req.ActivityListGetReq;
import zte.params.goods.req.AttrDefListReq;
import zte.params.goods.req.BroadbandGoodsQryReq;
import zte.params.goods.req.CacheGoodsDataReq;
import zte.params.goods.req.CoModifyStatusReq;
import zte.params.goods.req.EcsGoodsPageListReq;
import zte.params.goods.req.GoodsAcceptPriceReq;
import zte.params.goods.req.GoodsAcceptReq;
import zte.params.goods.req.GoodsAddReq;
import zte.params.goods.req.GoodsBaseQueryReq;
import zte.params.goods.req.GoodsBroadbandAddReq;
import zte.params.goods.req.GoodsByCrmOfferIdReq;
import zte.params.goods.req.GoodsByIdsReq;
import zte.params.goods.req.GoodsBySkuReq;
import zte.params.goods.req.GoodsCatPathGetReq;
import zte.params.goods.req.GoodsCatQueryReq;
import zte.params.goods.req.GoodsColConfigReq;
import zte.params.goods.req.GoodsComplexListReq;
import zte.params.goods.req.GoodsCopyReq;
import zte.params.goods.req.GoodsDeleteReq;
import zte.params.goods.req.GoodsDetailGetReq;
import zte.params.goods.req.GoodsEditReq;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.req.GoodsImportReq;
import zte.params.goods.req.GoodsInfoGetReq;
import zte.params.goods.req.GoodsIntroReq;
import zte.params.goods.req.GoodsOfferRelContractReq;
import zte.params.goods.req.GoodsPageByIdsReq;
import zte.params.goods.req.GoodsPageListByTypeReq;
import zte.params.goods.req.GoodsPageListReq;
import zte.params.goods.req.GoodsPlanRelReq;
import zte.params.goods.req.GoodsPriceQueryReq;
import zte.params.goods.req.GoodsPromotionGetReq;
import zte.params.goods.req.GoodsQueryInfoReq;
import zte.params.goods.req.GoodsRankingReq;
import zte.params.goods.req.GoodsRelAdjunctReq;
import zte.params.goods.req.GoodsRelPageListReq;
import zte.params.goods.req.GoodsServReq;
import zte.params.goods.req.GoodsSpecCheckReq;
import zte.params.goods.req.GoodsSpecListReq;
import zte.params.goods.req.GoodsSpecificationInfoReq;
import zte.params.goods.req.GoodsStoreGetReq;
import zte.params.goods.req.GoodsTerminalRelTerminalReq;
import zte.params.goods.req.GoodsTerminalReq;
import zte.params.goods.req.GroupBuyEditReq;
import zte.params.goods.req.GroupBuyGetReq;
import zte.params.goods.req.GroupBuyListReq;
import zte.params.goods.req.GroupBuyReq;
import zte.params.goods.req.IncreaseInventoryNumReq;
import zte.params.goods.req.LimitBuyGoodsListReq;
import zte.params.goods.req.LimitBuyListReq;
import zte.params.goods.req.LimitBuyReq;
import zte.params.goods.req.LimitBuyUpdateReq;
import zte.params.goods.req.OfferChangeListReq;
import zte.params.goods.req.ProductDetailsReq;
import zte.params.goods.req.ProductInfoByNameReq;
import zte.params.goods.req.ProductInfoGetReq;
import zte.params.goods.req.ProductListReq;
import zte.params.goods.req.ProductPageQueryReq;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.req.PromotionActAddReq;
import zte.params.goods.req.PromotionActEditReq;
import zte.params.goods.req.ProxyGoodsAddReq;
import zte.params.goods.req.ProxyGoodsDeleteReq;
import zte.params.goods.req.ProxyGoodsPageQueryReq;
import zte.params.goods.req.ScoreGetReq;
import zte.params.goods.req.SkuQueryReq;
import zte.params.goods.req.StdGoodsGetReq;
import zte.params.goods.req.SubtractInventoryNumReq;
import zte.params.goods.req.TerminalImportReq;
import zte.params.goods.req.TerminalNumReq;
import zte.params.goods.resp.ActivityImportResp;
import zte.params.goods.resp.ActivityListGetResp;
import zte.params.goods.resp.AttrDefListResp;
import zte.params.goods.resp.BroadbandGoodsQryResp;
import zte.params.goods.resp.CacheGoodsDataResp;
import zte.params.goods.resp.CoModifyStatusResp;
import zte.params.goods.resp.EcsGoodsPageListResp;
import zte.params.goods.resp.GoodsAcceptPriceResp;
import zte.params.goods.resp.GoodsAcceptResq;
import zte.params.goods.resp.GoodsAddResp;
import zte.params.goods.resp.GoodsBaseQueryResp;
import zte.params.goods.resp.GoodsByCrmOfferIdResp;
import zte.params.goods.resp.GoodsByIdsResp;
import zte.params.goods.resp.GoodsBySkuResp;
import zte.params.goods.resp.GoodsCatPathGetResp;
import zte.params.goods.resp.GoodsColConfigResp;
import zte.params.goods.resp.GoodsComplexListResp;
import zte.params.goods.resp.GoodsCopyResp;
import zte.params.goods.resp.GoodsDeleteResp;
import zte.params.goods.resp.GoodsDetailGetResp;
import zte.params.goods.resp.GoodsEditResp;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.goods.resp.GoodsImportResp;
import zte.params.goods.resp.GoodsInfoGetResp;
import zte.params.goods.resp.GoodsIntroResp;
import zte.params.goods.resp.GoodsListResp;
import zte.params.goods.resp.GoodsPageListResp;
import zte.params.goods.resp.GoodsPricePageListResp;
import zte.params.goods.resp.GoodsPromotionGetResp;
import zte.params.goods.resp.GoodsQueryResp;
import zte.params.goods.resp.GoodsSpecCheckResp;
import zte.params.goods.resp.GoodsSpecListResp;
import zte.params.goods.resp.GoodsSpecificationInfoResp;
import zte.params.goods.resp.GoodsStoreGetResp;
import zte.params.goods.resp.GroupBuyEditResp;
import zte.params.goods.resp.GroupBuyGetResp;
import zte.params.goods.resp.GroupBuyListResp;
import zte.params.goods.resp.GroupBuyResp;
import zte.params.goods.resp.IncreaseInventoryNumResp;
import zte.params.goods.resp.LimitBuyGoodsListResp;
import zte.params.goods.resp.LimitBuyListResp;
import zte.params.goods.resp.LimitBuyResp;
import zte.params.goods.resp.LimitBuyUpdateResp;
import zte.params.goods.resp.OfferChangeListResp;
import zte.params.goods.resp.ProductDetailsResp;
import zte.params.goods.resp.ProductInfoByNameResp;
import zte.params.goods.resp.ProductInfoGetResp;
import zte.params.goods.resp.ProductListResp;
import zte.params.goods.resp.ProductPageQueryResp;
import zte.params.goods.resp.ProductsListResp;
import zte.params.goods.resp.PromotionActAddResp;
import zte.params.goods.resp.PromotionActEditResp;
import zte.params.goods.resp.ProxyGoodsAddResp;
import zte.params.goods.resp.ProxyGoodsDeleteResp;
import zte.params.goods.resp.ProxyGoodsPageQueryResp;
import zte.params.goods.resp.ScoreGetResp;
import zte.params.goods.resp.SkuQueryResp;
import zte.params.goods.resp.StdGoodsGetResp;
import zte.params.goods.resp.SubtractInventoryNumResp;
import zte.params.goods.resp.TerminalImportResp;
import zte.params.goods.resp.TerminalNumResp;
import zte.params.goodscats.req.GoodsListByCatIdReq;
import zte.params.goodscats.req.GoodsViewListByCatIdReq;
import zte.params.goodscats.resp.GoodsListByCatIdResp;
import zte.params.order.req.CreateRedPkgReq;
import zte.params.order.req.GrabRedPkgReq;
import zte.params.order.req.RedPkgEditReq;
import zte.params.order.req.RedPkgListReq;
import zte.params.order.resp.CreateRedPkgResp;
import zte.params.order.resp.GrabRedPkgResp;
import zte.params.order.resp.RedPkgEditResp;
import zte.params.order.resp.RedPkgListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="商品管理API",summary="商品管理API[查询商品基本信息 查询商品价格 查询品牌商品（门户展示）查询分类商品（门户展示一级分类下所有商品）查询商品详情信息]")
public interface IGoodsService {

	/**
	 * 查询商品基本信息
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品基本信息",summary="商品基本信息：商品基本信息：商品名称，商品编号，市场价，销售价，商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、产品简述，重量，浏览次数，购买数量，库存，积分，是否团购，是否限够，图片，类型编码，状态，供应商ID，审核状态，简称，规格信息，供应商userid，供应商名称，不包括详情页属性信息，详情信息")
	public GoodsBaseQueryResp queryGoodsBaseInfo(GoodsBaseQueryReq goodsBaseQueryReq) throws Exception;
	
	
	/**
	 * 根据sku查询商品基本信息
	 * @param goodsBySkuReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="根据sku查询商品基本信息",summary="商品基本信息：商品基本信息：商品名称，商品编号，市场价，销售价，商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、产品简述，重量，浏览次数，购买数量，库存，积分，是否团购，是否限够，图片，类型编码，状态，供应商ID，审核状态，简称，规格信息，供应商userid，供应商名称，不包括详情页属性信息，详情信息")
	public GoodsBySkuResp queryGoodsBySku(GoodsBySkuReq goodsBySkuReq) throws Exception;
	
	/**
	 * 根据name查询货品基本信息，对应视图v_product
	 * @param productInfoByNameReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="根据name查询货品基本信息",summary="货品基本信息：对应视图v_product")
	public ProductInfoByNameResp queryProductInfoByName(ProductInfoByNameReq productInfoByNameReq) throws Exception;
	
	/**
	 * 查询商品价格
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品价格",summary="商品会员价格列表：会员等级，会员价格")	
	public GoodsPricePageListResp queryGoodsPriceList(GoodsPriceQueryReq goodsPriceQueryReq) throws Exception;

	/**
	 * 查询分类商品
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询分类商品",summary="查询分类商品（门户展示一级分类下所有商品）")	
	public GoodsListResp queryGoodsCatList(GoodsCatQueryReq goodsCatQueryReq) throws Exception;
	
	/**
	 * 查询商品详情信息
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品详情信息",summary="展示商品详情页信息")	
	public GoodsIntroResp queryGoodsIntro(GoodsIntroReq goodsIntroReq) throws Exception;

	@ZteSoftCommentAnnotation(type="method",desc="获取团购信息",summary="获取团购信息")	
	public GroupBuyResp queryGroupBuy(GroupBuyReq req);
	/**
	 * 查询商品详情信息
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品详情",summary="查询商品详情（商品详情信息，评论信息，咨询信息）")	
	public GoodsQueryResp queryGoodsInfo(GoodsQueryInfoReq goodsQueryInfoReq) throws Exception;
	
	/**
	 * 查询商品受理详情
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品受理详情",summary="查询商品受理详情（商品名称、商品价格、商品规格、商品规格属性、商品温馨提醒、商品库存、商品已售卖量）")
	public GoodsAcceptResq queryGoodsAcceptInfo(GoodsAcceptReq goodsAcceptReq) throws Exception;
	
    /**
     * 查询服务商品
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询服务商品",summary="查询服务商品:按服务类型查询商品列表")    
    public GoodsListResp queryGoodsByServ(GoodsServReq goodsServReq) throws Exception;    

    
    /**
     * 查询终端关联计划
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询终端关联计划",summary="查询终端关联计划")    
    public GoodsListResp queryTerminalRelPlan(GoodsTerminalReq goodsTerminalReq) throws Exception;    
  
    /**
     * 查询计划关联套餐
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询计划关联套餐",summary="查询计划关联套餐")    
    public GoodsListResp queryPlanRelOffer(GoodsPlanRelReq goodsPlanRelReq) throws Exception;    
 
    /**
     * 查询套餐关联合约
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询套餐关联合约",summary="查询套餐关联合约")    
    public GoodsListResp queryOfferRelContract(GoodsOfferRelContractReq goodsOfferRelContractReq) throws Exception;    
   
    /**
     * 查询商品绑定商品
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询商品绑定商品",summary="查询商品绑定商品")    
    public GoodsListResp queryTerminalRelTerminal(GoodsTerminalRelTerminalReq goodsTerminalRelTerminalReq) throws Exception;    
    
    /**
     * 查询商品配件
     * @param goodsQueryReq
     * @return
     */
    @ZteSoftCommentAnnotation(type="method",desc="查询商品配件",summary="查询商品配件")    
    public GoodsListResp queryGoodsRelAdjunct(GoodsRelAdjunctReq goodsRelAdjunctReq) throws Exception;    

	/**
	 * 查询商品列表
	 * @param pageQeq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="商品搜索",summary="商品搜索（按分类、品牌、属性、关键字、供货商、价格、属性搜索商品、排序），<font color='red'>注：条件为空则返回默认返回所有数据</font>")	
	public GoodsPageListResp queryGoodsForPage(GoodsPageListReq pageQeq);
	
	
	/**
	 * 根据商品id及名称查询商品列表
	 * @param goodsPageByIdsReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="商品搜索",summary="商品搜索,根据批量的商品id和名称查询")
	public GoodsPageListResp queryGoodsByIdsAndName(GoodsPageByIdsReq goodsPageByIdsReq);
	
	/**
	 * 查询商品受理价格
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品受理价格",summary="查询商品受理价格（根据规格id联动展示商品价格）")	
	public GoodsAcceptPriceResp getAcceptPrice(GoodsAcceptPriceReq req);
	
	/**
	 * 查询商品业务属性
	 * @param listReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品业务属性",summary="查询商品业务属性列表（订单、支付、受理属性）")	
	public AttrDefListResp listAttrDef(AttrDefListReq listReq);
	
	/**
	 * 查询商品库存
	 * @param goodsQueryReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商品库存",summary="查询商品库存")	
	public GoodsStoreGetResp getGoodsStore(GoodsStoreGetReq req);
	
	/**
	 * 删除商品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="删除商品",summary="删除商品")	
	public GoodsDeleteResp deleteGoods(GoodsDeleteReq req);
	
	/**
	 * 修改商品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="修改商品",summary="修改商品")	
	public GoodsEditResp editGoods(GoodsEditReq req);
	
	/**
	 * 新增商品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="新增商品",summary="新增商品")	
	public GoodsAddResp addGoods(GoodsAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="复制商品",summary="复制商品")	
	public GoodsCopyResp copyGoods(GoodsCopyReq req);
	
	/**
	 * 商品排行榜 按【同价位】【通品牌】【同类别】
	 */
	@ZteSoftCommentAnnotation(type="method",desc="商品排行榜",summary="按【同价位】【通品牌】【同类别】搜索商品列表 按销售量排行")
	public GoodsListResp qryGoodsRanking(GoodsRankingReq req);
	
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品规格列表",summary="获取商品规格列表，包括颜色，尺寸，容量")	
	public GoodsSpecListResp listGoodsSpec(GoodsSpecListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品优惠促销",summary="根据商品ID获取该商品优惠促销")	
	public GoodsPromotionGetResp getPromotionByGoodsId(GoodsPromotionGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品评分和评论数",summary="获取商品评分和评论数")	
	public ScoreGetResp getGoodsScore(ScoreGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商品详情",summary="获取商品详情")	
	public GoodsDetailGetResp getGoodsDetail(GoodsDetailGetReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="判断商品是否开启规格",summary="根据商品ID判断是否开启规格，已开启返回true，未开启返回false。")	
	public GoodsSpecCheckResp checkGoodsHasSpec(GoodsSpecCheckReq req);

	@ZteSoftCommentAnnotation(type="method",desc="根据关联商品ID和关联类型（如终端关联计划TERMINAL_PLAN）获取商品分页",summary="根据关联商品ID和关联类型（如终端关联计划TERMINAL_PLAN）获取商品分页。")	
	public GoodsPageListResp listGoodsPage(GoodsRelPageListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取变更信息",summary="获取变更信息。")	
	public OfferChangeListResp listOfferChange(OfferChangeListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取秒杀活动",summary="获取秒杀活动。")
	public LimitBuyResp queryLimitBuy(LimitBuyReq req);

	@ZteSoftCommentAnnotation(type="method",desc="更新秒杀信息",summary="更新秒杀信息")	
	public LimitBuyUpdateResp modLimitBuy(LimitBuyUpdateReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据服务ID获取产品ID列表",summary="根据服务ID获取产品ID列表")	
	public ProductListResp listProductIds(ProductListReq req);
	
	
	@ZteSoftCommentAnnotation(type="method",desc="获取秒杀活动",summary="获取秒杀活动。")
	public GroupBuyEditResp updateGroupByCount(GroupBuyEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="修改团购活动状态",summary="修改团购活动状态")
	public GroupBuyEditResp updateGroupBuyDisabled(GroupBuyEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="修改秒杀活动状态",summary="修改秒杀活动状态")
	public LimitBuyResp updateLimitBuyDisabled(LimitBuyReq req);
	
	/**
	 * 根据product_id获取货品/商品信息，包括货品/商品基本信息，参数信息，规格信息等（广东联通ECS）
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="根据product_id获取货品",summary="根据product_id获取货品商品信息，包括货品/商品基本信息，参数信息，规格信息等（广东联通ECS）。")
	public GoodsGetResp getGoods(GoodsGetReq req);
	
	/**
     * 发布状态更新,包括：商品、货品、号码
     * @param req
     * @return
     */
	@ZteSoftCommentAnnotation(type="method",desc="发布状态更新",summary="发布状态更新,包括：商品、货品、号码。")
	public CoModifyStatusResp modifyStatus(CoModifyStatusReq req);
	
	
    //获取产品
	@ZteSoftCommentAnnotation(type="method",desc="根据商品id获取产品",summary="根据商品id获取产品。")
    public ProductDetailsResp getProductByGoodsId(ProductDetailsReq req);
    
	@ZteSoftCommentAnnotation(type="method",desc="根据类型Id查商品",summary="据类型Id查商品。")
    public GoodsPageListResp queryGoodsByTypeId(GoodsPageListByTypeReq req);
    
    //获取团购信息
	@ZteSoftCommentAnnotation(type="method",desc="获取团购信息",summary="获取团购信息。")
    public GroupBuyGetResp getGroupBuy(GroupBuyGetReq req);
    
    //获取相关商品
	@ZteSoftCommentAnnotation(type="method",desc="根据商品Id获取相关商品",summary="根据商品Id获取相关商品。")
    public GoodsComplexListResp listComplex(GoodsComplexListReq req);
    
    //获取限制抢购商品及其价格
	@ZteSoftCommentAnnotation(type="method",desc="获取限制抢购商品及价格",summary="获取限制抢购商品及价格。")
    public LimitBuyGoodsListResp listLimitBuyGoods(LimitBuyGoodsListReq req);
    
    //获取所有未到截止时间的团购信息
	@ZteSoftCommentAnnotation(type="method",desc="获取所有未到截止时间的团购信息",summary="获取所有未到截止时间的团购信息。")
    public GroupBuyListResp listGroupBuy(GroupBuyListReq req);
    
    //可用的限时抢购列表及其商品
	@ZteSoftCommentAnnotation(type="method",desc="可用的限时抢购列表及其商品",summary="可用的限时抢购列表及其商品。")
    public LimitBuyListResp listLimitBuy(LimitBuyListReq req);
    
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品的相关商品", summary = "获取商品的相关商品")
    public GoodsComplexListResp listComplexByGoods_id(GoodsComplexListReq req);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "获取商品规则信息", summary = "获取商品规则信息")
   	public GoodsSpecificationInfoResp getGoodsSpecificationInfo(GoodsSpecificationInfoReq req);
    //获得catId goods表
    @ZteSoftCommentAnnotation(type = "method", desc = "获取商品类别id", summary = "获取商品类别id")
    public GoodsCatPathGetResp getGoodsCatPath(GoodsCatPathGetReq req);
    //获得规格配置信息
    @ZteSoftCommentAnnotation(type = "method", desc = "获得规格配置信息", summary = "获得规格配置信息")
    public GoodsColConfigResp getcolConfig(GoodsColConfigReq req);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "广东联通：查询商品", summary = "广东联通：查询商品")
    public EcsGoodsPageListResp queryGoodsForPageEcs(EcsGoodsPageListReq req);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "根据商品ID获取商品信息，用于数据同步", summary = "根据商品ID获取商品信息，用于数据同步")
    public GoodsInfoGetResp getGoodsInfo(GoodsInfoGetReq req);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "根据货品ID获取货品信息，用于数据同步", summary = "根据货品ID获取货品信息，用于数据同步，不包括属性信息")
    public ProductInfoGetResp getProductInfo(ProductInfoGetReq req);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "获取商品下的货品列表", summary = "获取商品下的货品列表")
    public ProductsListResp listProducts(ProductsListReq req);
	/**
	 * 修改活动
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="修改活动",summary="修改活动",isOpen=false)	
    public PromotionActEditResp editPromotionActivity(PromotionActEditReq req) ;
	/**
	 * 新增活动
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="新增活动",summary="新增活动",isOpen=false)	
	public PromotionActAddResp addPromotionActivity(PromotionActAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="标签tag",summary="标签tag",isOpen=false)	
	public TagResp tagListByCatType(TagCat_typeReq req);
	
	
	@ZteSoftCommentAnnotation(type="method",desc="根据商品或产品id查询商品",summary="根据商品或产品id查询商品")	
	public GoodsByIdsResp qryGoodsByIds(GoodsByIdsReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="商品批量导入",summary="商品批量导入",isOpen=false)
	public GoodsImportResp importGoods(GoodsImportReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="活动批量导入",summary="活动批量导入",isOpen=false)
	public ActivityImportResp importActivity(ActivityImportReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="手机货品批量导入",summary="手机货品批量导入",isOpen=false)
	public TerminalImportResp importTerminal(TerminalImportReq req);
	
	/**
	 * 根据关联CRM id查询商品
	 * @param crm_offer_id
	 * @param source_from
	 * @return
	 */
    @ZteSoftCommentAnnotation(type = "method", desc = "根据关联crm_offer_id查询商品", summary = "根据关联crm_offer_id查询商品")
    public GoodsByCrmOfferIdResp qryGoodsByCrmOfferId(GoodsByCrmOfferIdReq goodsByCrmOfferIdReq);
    
    
    @ZteSoftCommentAnnotation(type="method",desc="获取商品规格信息",summary="获取商品规格信息",isOpen=false)
    public GoodsSpecResp getGoodSpec(GoodsSpecReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="获取货品规格信息",summary="获取货品规格信息",isOpen=false)
    public ProductSpecResp getProductSpec(ProductSpecReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="判断货品是否赠品",summary="判断货品是否赠品",isOpen=false)
    public GoodsTypeIdGetResp getGoodsTypeId(GoodsTypeIdGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="判断货品是否赠品",summary="判断货品是否赠品",isOpen=false)
    public PCodeGetResp getPCode(PCodeGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="刷新一个商品的缓存",summary="刷新一个商品的缓存",isOpen=false)
    public CacheGoodsDataResp cacheGoodsData(CacheGoodsDataReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="获取终端串号",summary="获取终端串号",isOpen=false)
    public TerminalNumResp getTerminalNumBySN(TerminalNumReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="根据活动编码获取赠品信息",summary="根据活动编码获取赠品信息")
    public SkuQueryResp getGiftByActivityCode(SkuQueryReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="分销商添加代理商品",summary="分销商添加代理商品")
    public ProxyGoodsAddResp addProxyGoods(ProxyGoodsAddReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="分销商删除代理商品",summary="分销商删除代理商品")
    public ProxyGoodsDeleteResp deleteProxyGoods(ProxyGoodsDeleteReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="查询分类的所有商品",summary="查询分类的所有商品")
    public GoodsListByCatIdResp listCatGoods(GoodsListByCatIdReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="查询分类的所有商品",summary="查询分类的所有商品")
    public GoodsListByCatIdResp listCatGoodsView(GoodsViewListByCatIdReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="查询一件代发商品",summary="查询一件代发商品")
    public ProxyGoodsPageQueryResp queryProxyGoodsForPage(ProxyGoodsPageQueryReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "红包信息录入", summary = "红包信息录入")
	public CreateRedPkgResp createRedPkg(CreateRedPkgReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "抢红包", summary = "抢红包")
	public GrabRedPkgResp grabRedPkg(GrabRedPkgReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "可使用优惠券红包列表", summary = "可使用优惠券红包列表")
	public RedPkgListResp redPkgList(RedPkgListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "编辑红包", summary = "编辑红包")
	public RedPkgEditResp redPkgEdit(RedPkgEditReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "分页查询货品信息", summary="分页查询货品信息")
	public ProductPageQueryResp getProductPageQuery(ProductPageQueryReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新商品库存", summary="刷新商品库存")
	public RefreshGoodsCacheResp refreshGoodsCache(RefreshGoodsCacheReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", summary="根据订单来源商城、固定的物理仓库编码、货品ID扣减库存")
	public SubtractInventoryNumResp subtractInventoryNum(SubtractInventoryNumReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存", summary="根据订单来源商城、固定的物理仓库编码、货品ID增加库存")
	public IncreaseInventoryNumResp increaseInventoryNum(IncreaseInventoryNumReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品参与的活动", summary = "获取商品参与的活动")
	public ActivityListGetResp getGoodsJoinActivities(ActivityListGetReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据商品名称获取商品编码", summary = "根据商品名称获取商品编码")
	public StdGoodsGetResp getStdGoodsByName(StdGoodsGetReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询小区可受理宽带商品", summary = "查询小区可受理宽带商品")
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoods(BroadbandGoodsQryReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "通过地市县分查询小区可受理宽带商品", summary = "通过地址县分查询小区可受理宽带商品")
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoodsByCityOrCounty(BroadbandGoodsQryReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带新增商品", summary = "宽带新增商品")
	public ZteResponse goodsBroadbandAdd(GoodsBroadbandAddReq req);
}
