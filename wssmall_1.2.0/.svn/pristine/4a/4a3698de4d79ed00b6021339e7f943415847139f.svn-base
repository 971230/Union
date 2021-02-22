package zte.net.iservice.impl;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.params.spec.req.GoodsSpecReq;
import zte.net.ecsord.params.spec.req.GoodsTypeIdGetReq;
import zte.net.ecsord.params.spec.req.GoodsTypeSpecGetReq;
import zte.net.ecsord.params.spec.req.PCodeGetReq;
import zte.net.ecsord.params.spec.req.ProductSpecReq;
import zte.net.ecsord.params.spec.resp.GoodsSpecResp;
import zte.net.ecsord.params.spec.resp.GoodsTypeIdGetResp;
import zte.net.ecsord.params.spec.resp.GoodsTypeSpecGetResp;
import zte.net.ecsord.params.spec.resp.PCodeGetResp;
import zte.net.ecsord.params.spec.resp.ProductSpecResp;
import zte.net.ecsord.params.wyg.req.RefreshGoodsCacheReq;
import zte.net.ecsord.params.wyg.resp.RefreshGoodsCacheResp;
import zte.net.iservice.ICommentsService;
import zte.net.iservice.ICouponService;
import zte.net.iservice.IFreeOfferService;
import zte.net.iservice.IGoodSearchService;
import zte.net.iservice.IGoodsAdjunctService;
import zte.net.iservice.IGoodsBrandService;
import zte.net.iservice.IGoodsCatsService;
import zte.net.iservice.IGoodsPromotionService;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IGoodsTagsService;
import zte.net.iservice.IGoodsTypeService;
import zte.params.brand.req.BrandGetReq;
import zte.params.brand.req.BrandGoodsListReq;
import zte.params.brand.req.BrandListAllReq;
import zte.params.brand.req.BrandListByCatReq;
import zte.params.brand.req.BrandListByTypeReq;
import zte.params.brand.req.BrandListReq;
import zte.params.brand.req.BrandModelListAllReq;
import zte.params.brand.req.BrandModelListByBrandReq;
import zte.params.brand.req.BrandModelListByModelCodeReq;
import zte.params.brand.resp.BrandGetResp;
import zte.params.brand.resp.BrandGoodsListResp;
import zte.params.brand.resp.BrandListByTypeResp;
import zte.params.brand.resp.BrandListResp;
import zte.params.brand.resp.BrandModelListByBrandResp;
import zte.params.brand.resp.BrandModelListByModelCodeResp;
import zte.params.brand.resp.BrandModelListResp;
import zte.params.comments.req.CommentListReq;
import zte.params.comments.req.CommentsPageListReq;
import zte.params.comments.resp.CommentListResp;
import zte.params.comments.resp.CommentsPageListResp;
import zte.params.coupon.req.CouponUseTimesEditReq;
import zte.params.coupon.req.CouponUserTimeCheckReq;
import zte.params.coupon.req.FreeOfferCategoryGetReq;
import zte.params.coupon.req.FreeOfferGetReq;
import zte.params.coupon.req.FreeOfferPageListReq;
import zte.params.coupon.resp.CouponUseTimesEditResp;
import zte.params.coupon.resp.CouponUserTimeCheckResp;
import zte.params.coupon.resp.FreeOfferCategoryGetResp;
import zte.params.coupon.resp.FreeOfferGetResp;
import zte.params.coupon.resp.FreeOfferPageListResp;
import zte.params.goods.req.ActivityImportReq;
import zte.params.goods.req.ActivityListGetReq;
import zte.params.goods.req.AttrDefListReq;
import zte.params.goods.req.BroadbandGoodsQryReq;
import zte.params.goods.req.CacheGoodsDataReq;
import zte.params.goods.req.EcsGoodsPageListReq;
import zte.params.goods.req.GoodsAcceptPriceReq;
import zte.params.goods.req.GoodsAcceptReq;
import zte.params.goods.req.GoodsAddReq;
import zte.params.goods.req.GoodsAdjunctListReq;
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
import zte.params.goods.req.GoodsSearchPageListReq;
import zte.params.goods.req.GoodsServReq;
import zte.params.goods.req.GoodsSpecCheckReq;
import zte.params.goods.req.GoodsSpecListReq;
import zte.params.goods.req.GoodsSpecificationInfoReq;
import zte.params.goods.req.GoodsStoreGetReq;
import zte.params.goods.req.GoodsTerminalRelTerminalReq;
import zte.params.goods.req.GoodsTerminalReq;
import zte.params.goods.req.GroupBuyGetReq;
import zte.params.goods.req.GroupBuyListReq;
import zte.params.goods.req.IncreaseInventoryNumReq;
import zte.params.goods.req.LimitBuyGoodsListReq;
import zte.params.goods.req.LimitBuyListReq;
import zte.params.goods.req.LimitBuyReq;
import zte.params.goods.req.OfferChangeListReq;
import zte.params.goods.req.ParamsPutReq;
import zte.params.goods.req.ProductInfoByNameReq;
import zte.params.goods.req.ProductInfoGetReq;
import zte.params.goods.req.ProductListReq;
import zte.params.goods.req.ProductPageQueryReq;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.req.ProxyGoodsAddReq;
import zte.params.goods.req.ProxyGoodsDeleteReq;
import zte.params.goods.req.ProxyGoodsPageQueryReq;
import zte.params.goods.req.ScoreGetReq;
import zte.params.goods.req.SelectorGetReq;
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
import zte.params.goods.resp.EcsGoodsPageListResp;
import zte.params.goods.resp.GoodsAcceptPriceResp;
import zte.params.goods.resp.GoodsAcceptResq;
import zte.params.goods.resp.GoodsAddResp;
import zte.params.goods.resp.GoodsAdjunctListResp;
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
import zte.params.goods.resp.GoodsSearchPageListResp;
import zte.params.goods.resp.GoodsSpecCheckResp;
import zte.params.goods.resp.GoodsSpecListResp;
import zte.params.goods.resp.GoodsSpecificationInfoResp;
import zte.params.goods.resp.GoodsStoreGetResp;
import zte.params.goods.resp.GroupBuyGetResp;
import zte.params.goods.resp.GroupBuyListResp;
import zte.params.goods.resp.IncreaseInventoryNumResp;
import zte.params.goods.resp.LimitBuyGoodsListResp;
import zte.params.goods.resp.LimitBuyListResp;
import zte.params.goods.resp.LimitBuyResp;
import zte.params.goods.resp.OfferChangeListResp;
import zte.params.goods.resp.ParamsPutResp;
import zte.params.goods.resp.ProductInfoByNameResp;
import zte.params.goods.resp.ProductInfoGetResp;
import zte.params.goods.resp.ProductListResp;
import zte.params.goods.resp.ProductPageQueryResp;
import zte.params.goods.resp.ProductsListResp;
import zte.params.goods.resp.ProxyGoodsAddResp;
import zte.params.goods.resp.ProxyGoodsDeleteResp;
import zte.params.goods.resp.ProxyGoodsPageQueryResp;
import zte.params.goods.resp.ScoreGetResp;
import zte.params.goods.resp.SelectorGetResp;
import zte.params.goods.resp.SkuQueryResp;
import zte.params.goods.resp.StdGoodsGetResp;
import zte.params.goods.resp.SubtractInventoryNumResp;
import zte.params.goods.resp.TerminalImportResp;
import zte.params.goods.resp.TerminalNumResp;
import zte.params.goodscats.req.CatAddReq;
import zte.params.goodscats.req.CatBrandListReq;
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
import zte.params.goodscats.req.GoodsListByCatIdReq;
import zte.params.goodscats.req.GoodsViewListByCatIdReq;
import zte.params.goodscats.resp.CatAddResp;
import zte.params.goodscats.resp.CatBrandListResp;
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
import zte.params.goodscats.resp.GoodsListByCatIdResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.req.GoodsTypeListReq;
import zte.params.goodstype.req.TypeListReq;
import zte.params.goodstype.req.TypeRelGoodsListReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.goodstype.resp.GoodsTypeListResp;
import zte.params.goodstype.resp.TypeListResp;
import zte.params.goodstype.resp.TypeRelGoodsListResp;
import zte.params.order.req.CreateRedPkgReq;
import zte.params.order.req.GrabRedPkgReq;
import zte.params.order.req.RedPkgEditReq;
import zte.params.order.req.RedPkgListReq;
import zte.params.order.resp.CreateRedPkgResp;
import zte.params.order.resp.GrabRedPkgResp;
import zte.params.order.resp.RedPkgEditResp;
import zte.params.order.resp.RedPkgListResp;
import zte.params.tags.req.PackageGetReq;
import zte.params.tags.req.TagGetReq;
import zte.params.tags.req.TagGoodsListReq;
import zte.params.tags.req.TagListReq;
import zte.params.tags.resp.PackageGetResp;
import zte.params.tags.resp.TagGetResp;
import zte.params.tags.resp.TagGoodsListResp;
import zte.params.tags.resp.TagListResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteGoodsOpenService {
//	@Resource
	private IGoodsService goodServices;
	
//	@Resource
	private IGoodsBrandService goodsBrandService;
	
//	@Resource
	private IGoodsCatsService goodsCatsService;
	
//	@Resource
	private IGoodsPromotionService goodsPromotionService;
	
//	@Resource
	private IGoodsTagsService goodsTagsService;
	
//	@Resource
	private IGoodsTypeService goodsTypeService;
	
//	@Resource
	private ICommentsService commentsService;
	
//	@Resource
	private IGoodsAdjunctService adjunctService;//不开放
	
//	@Resource
	private ICouponService couponService;//不开放
	
//	@Resource 
	private IFreeOfferService freeOfferService;//不开放
	
//	@Resource
	private IGoodSearchService goodSearchService;//不开放
	
	private void init(ZteRequest request){
		this.goodServices = ApiContextHolder.getBean("goodServices");
		this.goodsBrandService = ApiContextHolder.getBean("goodsBrandService");
		this.goodsCatsService = ApiContextHolder.getBean("goodsCatsService");
		this.goodsPromotionService = ApiContextHolder.getBean("goodsPromotionService");
		this.goodsTagsService = ApiContextHolder.getBean("goodsTagsService");
		this.goodsTypeService = ApiContextHolder.getBean("goodsTypeService");
		this.commentsService = ApiContextHolder.getBean("commentsService");
		this.adjunctService = ApiContextHolder.getBean("adjunctService");
		this.couponService = ApiContextHolder.getBean("couponService");
		this.freeOfferService = ApiContextHolder.getBean("freeOfferService");
		this.goodSearchService = ApiContextHolder.getBean("goodSearchService");
	}
	
	@ServiceMethod(method="zte.goodsService.brand.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandGetResp getBrand(BrandGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.getBrand(req);
	}

	
	@ServiceMethod(method="zte.goodsService.brandGoods.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandGoodsListResp listBrandRelGoods(BrandGoodsListReq pageReq) {
		this.init(pageReq);
		pageReq.setRopRequestContext(null);
		return goodsBrandService.listBrandRelGoods(pageReq);
	}

	
	@ServiceMethod(method="com.goodsService.goodsType.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TypeRelGoodsListResp listGoodsByTypeId(TypeRelGoodsListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsTypeService.listGoodsByTypeId(listReq);
	}

	
	@ServiceMethod(method="zte.goodsService.goodsType.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsTypeGetResp getGoodsType(GoodsTypeGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsTypeService.getGoodsType(req);
	}

	
	@ServiceMethod(method="com.goodsService.tag.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TagListResp listTag(TagListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsTagsService.listTag(listReq);
	}
	
	
	@ServiceMethod(method="com.goodsService.tag.listGoods",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TagGoodsListResp listGoodsByTagId(TagGoodsListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsTagsService.listGoodsByTagId(listReq);
	}

	
	@ServiceMethod(method="com.goodsService.goodscats.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatsListResp listGoodsCatsByLvId(CatsListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsCatsService.listGoodsCatsByLvId(listReq);
	}
	
	
	@ServiceMethod(method="com.goodsService.goodscats.listGoods",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatsGoodsListResp listGoodsByCatId(CatsGoodsListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsCatsService.listComplexGoodsByCatId(listReq);
	}
	
	@ServiceMethod(method="com.goodsService.goods.listByCatId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListByCatIdResp listCatGoods(GoodsListByCatIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listCatGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.listViewByCatId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListByCatIdResp listCatGoodsView(GoodsViewListByCatIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listCatGoodsView(req);
	}
	
	
	@ServiceMethod(method="com.goodsService.goodscats.childCatslist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ChildCatsListResp listGoodsCatsByParentId(ChildCatsListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodsCatsService.listGoodsCatsByParentId(listReq);
	}

	
	@ServiceMethod(method="com.goodsService.tag.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TagGetResp getHotTag(TagGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getHotTag(req);
	}

	@ServiceMethod(method="com.goodsService.promotion.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPromotionGetResp getPromotion(GoodsPromotionGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		GoodsPromotionGetResp resp = null;
		if(req.getPmt_id()!=null){
			resp = goodsPromotionService.getPromotion(req);
		}
		else if(req.getGoods_id()!=null){
			resp = goodServices.getPromotionByGoodsId(req);
		}
		return resp;
	}

	
	@ServiceMethod(method="com.goodsService.goods.base.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsBaseQueryResp queryGoodsBaseInfo(GoodsBaseQueryReq goodsBaseQueryReq) throws Exception {
		this.init(goodsBaseQueryReq);
		goodsBaseQueryReq.setRopRequestContext(null);
		return goodServices.queryGoodsBaseInfo(goodsBaseQueryReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.price.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPricePageListResp queryGoodsPriceList(GoodsPriceQueryReq goodsPriceQueryReq) throws Exception {
		this.init(goodsPriceQueryReq);
		goodsPriceQueryReq.setRopRequestContext(null);
		return goodServices.queryGoodsPriceList(goodsPriceQueryReq);
	}

	@ServiceMethod(method="com.goodsService.goods.serv.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryGoodsByServ(GoodsServReq goodsServReq)
			throws Exception {
		this.init(goodsServReq);
		goodsServReq.setRopRequestContext(null);
		return goodServices.queryGoodsByServ(goodsServReq);
	}
	
	@ServiceMethod(method="com.goodsService.goods.cat.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryGoodsCatList(GoodsCatQueryReq goodsCatQueryReq)
			throws Exception {
		this.init(goodsCatQueryReq);
		goodsCatQueryReq.setRopRequestContext(null);
		return goodServices.queryGoodsCatList(goodsCatQueryReq);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品库存", summary = "查询商品库存")
	@ServiceMethod(method="com.goodsService.store.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsStoreGetResp getGoodsStore(GoodsStoreGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsStore(req);
	}
	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "删除商品", summary = "删除商品")
	@ServiceMethod(method="com.goodsService.goods.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsDeleteResp deleteGoods(GoodsDeleteReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.deleteGoods(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "修改商品", summary = "修改商品")
	@ServiceMethod(method="com.goodsService.goods.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsEditResp editGoods(GoodsEditReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.editGoods(req);
	}

	
	@ZteSoftCommentAnnotation(type = "method", desc = "新增商品", summary = "新增商品")
	@ServiceMethod(method="com.goodsService.goods.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsAddResp addGoods(GoodsAddReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.addGoods(req);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "复制商品", summary = "复制商品")
	@ServiceMethod(method="com.goodsService.goods.copy",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsCopyResp copyGoods(GoodsCopyReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.copyGoods(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品业务属性列表（订单、支付、受理属性）", summary = "查询商品业务属性列表（订单、支付、受理属性）")
	@ServiceMethod(method="com.goodsService.attrdef.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrDefListResp listAttrDef(AttrDefListReq listReq) {
		this.init(listReq);
		listReq.setRopRequestContext(null);
		return goodServices.listAttrDef(listReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.detail.page.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsIntroResp queryGoodsIntro(GoodsIntroReq goodsIntroReq) throws Exception {
		this.init(goodsIntroReq);
		goodsIntroReq.setRopRequestContext(null);
		return goodServices.queryGoodsIntro(goodsIntroReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.page",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPageListResp queryGoodsForPage(GoodsPageListReq pageQeq) {
		this.init(pageQeq);
		pageQeq.setRopRequestContext(null);
		return goodServices.queryGoodsForPage(pageQeq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.terminal.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryTerminalRelPlan(GoodsTerminalReq goodsTerminalReq)
			throws Exception {
		this.init(goodsTerminalReq);
		goodsTerminalReq.setRopRequestContext(null);
		return goodServices.queryTerminalRelPlan(goodsTerminalReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.plan.rel.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryPlanRelOffer(GoodsPlanRelReq goodsPlanRelReq)throws Exception {
		this.init(goodsPlanRelReq);
		goodsPlanRelReq.setRopRequestContext(null);
		return goodServices.queryPlanRelOffer(goodsPlanRelReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.offer.rel.contract.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryOfferRelContract(GoodsOfferRelContractReq goodsOfferRelContractReq)
			throws Exception {
		this.init(goodsOfferRelContractReq);
		goodsOfferRelContractReq.setRopRequestContext(null);
		return goodServices.queryOfferRelContract(goodsOfferRelContractReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.terminal.to.terminal.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryTerminalRelTerminal(GoodsTerminalRelTerminalReq goodsTerminalRelTerminalReq)
			throws Exception {
		this.init(goodsTerminalRelTerminalReq);
		goodsTerminalRelTerminalReq.setRopRequestContext(null);
		return goodServices.queryTerminalRelTerminal(goodsTerminalRelTerminalReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.adjunct.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp queryGoodsRelAdjunct(GoodsRelAdjunctReq goodsRelAdjunctReq)
			throws Exception {
		this.init(goodsRelAdjunctReq);
		goodsRelAdjunctReq.setRopRequestContext(null);
		return goodServices.queryGoodsRelAdjunct(goodsRelAdjunctReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.info.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsQueryResp queryGoodsInfo(GoodsQueryInfoReq goodsQueryInfoReq)throws Exception {
		this.init(goodsQueryInfoReq);
		goodsQueryInfoReq.setRopRequestContext(null);
		return goodServices.queryGoodsInfo(goodsQueryInfoReq);
	}

	
	@ServiceMethod(method="com.goodsService.goods.accept.req",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsAcceptResq queryGoodsAcceptInfo(GoodsAcceptReq goodsAcceptReq)
			throws Exception {
		this.init(goodsAcceptReq);
		goodsAcceptReq.setRopRequestContext(null);
		return goodServices.queryGoodsAcceptInfo(goodsAcceptReq);
	}
	
	
	@ServiceMethod(method="com.goodsService.goods.acceptPrice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsAcceptPriceResp getAcceptPrice(GoodsAcceptPriceReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getAcceptPrice(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.ranking",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsListResp qryGoodsRanking(GoodsRankingReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.qryGoodsRanking(req);
	}
	
	@ServiceMethod(method="com.goodsService.goodsSpec.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsSpecListResp listGoodsSpec(GoodsSpecListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listGoodsSpec(req);
	}
	
	@ServiceMethod(method="com.commentsService.comments.pageList",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CommentsPageListResp listGoodsComments(CommentsPageListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return commentsService.listGoodsComments(req);
	}
	
	@ServiceMethod(method="com.goodsService.score.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ScoreGetResp getGoodsScore(ScoreGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsScore(req);
	}
	
	@ServiceMethod(method="com.goodsService.detail.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsDetailGetResp getGoodsDetail(GoodsDetailGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsDetail(req);
	}
	
	@ServiceMethod(method="com.goodsService.goodsSpec.check",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsSpecCheckResp checkGoodsHasSpec(GoodsSpecCheckReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.checkGoodsHasSpec(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.queryByType",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPageListResp queryGoodsByTypeId(GoodsPageListByTypeReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryGoodsByTypeId(req);
		
	}
	
	@ServiceMethod(method="com.goodsService.goods.pagebyids",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPageListResp queryGoodsByIdsAndName(GoodsPageByIdsReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryGoodsByIdsAndName(req);
		
	}
	
	@ServiceMethod(method="com.goodsService.adjuncts.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsAdjunctListResp listGoodsAdjunct(GoodsAdjunctListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return adjunctService.list(req);
	}
	
	@ServiceMethod(method="com.goodsService.coupon.usertimes.check",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CouponUserTimeCheckResp checkCouponUserTime(CouponUserTimeCheckReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return couponService.checkCouponUserTime(req);
	}
	
	@ServiceMethod(method="com.goodsService.coupon.usetimes.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CouponUseTimesEditResp editCouponUseTime(CouponUseTimesEditReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return couponService.editCouponUseTime(req);
	}
	
	@ServiceMethod(method="com.goodsService.fo.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public FreeOfferGetResp getFreeOffer(FreeOfferGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return freeOfferService.getFreeOffer(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatGetResp getCat(CatGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getCat(req);
	}
	
	@ServiceMethod(method="com.commentsService.comments.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CommentListResp getCat(CommentListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return commentsService.listComments(req);
	}
	
	@ServiceMethod(method="com.goodsService.freeOffer.pageList",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public FreeOfferPageListResp listFreeOffer(FreeOfferPageListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return freeOfferService.listFreeOffer(req);
	}
	
	@ServiceMethod(method="com.goodsService.fo.category.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public FreeOfferCategoryGetResp getFreeOfferCategory(FreeOfferCategoryGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return freeOfferService.getFreeOfferCategory(req);
	}
	
	@ServiceMethod(method="com.goodsService.limitbuy.goods.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LimitBuyGoodsListResp listLimitBuyGoods(LimitBuyGoodsListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listLimitBuyGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.searchByUrl",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsSearchPageListResp searchGoods(GoodsSearchPageListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodSearchService.searchGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.selector.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SelectorGetResp getSelector(SelectorGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodSearchService.getSelector(req);
	}
	
	@ServiceMethod(method="com.goodsService.params.put",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ParamsPutResp putParams(ParamsPutReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodSearchService.putParams(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.children.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ChildrenCatsListResp listAllChildren(ChildrenCatsListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listAllChildren(req);
	}
	
	@ServiceMethod(method="com.goodsService.catBrand.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatBrandListResp listCatBrand(CatBrandListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listCatBrand(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.path.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatPathGetResp getCatPath(CatPathGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getCatPath(req);
	}
	
	@ServiceMethod(method="com.goodsService.complex.goods.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ComplexGoodsGetResp getComplexGoods(ComplexGoodsGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getComplexGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatListByGoodsIdResp listCatsByGoodsId(CatListByGoodsIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listCatsByGoodsId(req);
	}
	
	@ServiceMethod(method="com.goodsService.brand.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandListResp listBrand(BrandListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listBrand(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.listByCond",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatListByCondResp listCatsByCond(CatListByCondReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listCatsByCond(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.listByUserid",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatListByUseridResp listCatsByUserid(CatListByUseridReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listCatsByUserid(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.getById",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatGetResp getCatById(CatGetByIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getCatById(req);
	}
	
	@ServiceMethod(method="com.goodsService.groupbuy.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GroupBuyGetResp getGroupBuy(GroupBuyGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGroupBuy(req);
	}
	
	@ServiceMethod(method="com.goodsService.groupBuy.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GroupBuyListResp listGroupBuy(GroupBuyListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listGroupBuy(req);
	}
	
	@ServiceMethod(method="com.goodsService.limitBuy.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LimitBuyListResp listLimitBuy(LimitBuyListReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listLimitBuy(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.hot.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatHotListResp getHotList(CatHotListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.getHotList(req);
	}
	
	@ServiceMethod(method="com.goodsService.brand.listByCat",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandListResp listBrandByCatId(BrandListByCatReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listBrandByCatId(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.relGoods.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsPageListResp listGoodsPage(GoodsRelPageListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listGoodsPage(req);
	}
	
	@ServiceMethod(method="com.goodsService.product.id.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductListResp listProductIds(ProductListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listProductIds(req);
	}
	
	@ServiceMethod(method="com.goodsService.offer.change.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OfferChangeListResp listOfferChange(OfferChangeListReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listOfferChange(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.orderinfo.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsGetResp getGoods(GoodsGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoods(req);
	}

	@ServiceMethod(method="com.goodsService.complex.goods.listByGoodsId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsComplexListResp listComplexByGoodsId(GoodsComplexListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listComplexByGoods_id(req);
	}
	
	@ServiceMethod(method="com.goodsService.specificationInfo.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsSpecificationInfoResp getGoodsSpecificationInfo(GoodsSpecificationInfoReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsSpecificationInfo(req);
	}
	@ServiceMethod(method="com.goodsService.catPath.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsCatPathGetResp getGoodsCatPath(GoodsCatPathGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsCatPath(req);
	}
	@ServiceMethod(method="com.goodsService.colConfig.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsColConfigResp getcolConfig(GoodsColConfigReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getcolConfig(req);
	}
		
		@ServiceMethod(method="com.goodsService.limitBuy.inst",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public LimitBuyResp queryLimitBuy(LimitBuyReq req){
			this.init(req);
			req.setRopRequestContext(null);
			return goodServices.queryLimitBuy(req);
		}
		
	@ServiceMethod(method="com.goodsService.ecs.goods.pageList",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public EcsGoodsPageListResp queryGoodsForPageEcs(EcsGoodsPageListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryGoodsForPageEcs(req);
	}
	
	
	@ServiceMethod(method="com.goodsService.goods.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsInfoGetResp getGoodsInfo(GoodsInfoGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsInfo(req);
	}
	
	@ServiceMethod(method="com.goodsService.product.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductInfoGetResp getProductInfo(ProductInfoGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getProductInfo(req);
	}
	
	@ServiceMethod(method="com.goodsService.products.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductsListResp listProducts(ProductsListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.listProducts(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.qryByIds",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsByIdsResp qryGoodsByIds(GoodsByIdsReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.qryGoodsByIds(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.qryGoodsByCrmOfferId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsByCrmOfferIdResp qryGoodsByCrmOfferId(GoodsByCrmOfferIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.qryGoodsByCrmOfferId(req);
	}
	

	@ServiceMethod(method="com.goodsService.types.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TypeListResp listTypes(TypeListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsTypeService.listTypes(req);
	}
	@ServiceMethod(method="com.goodsService.brand.list.all",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandListResp brandListAll(BrandListAllReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.brandListAll(req);
	}



	@ServiceMethod(method="com.goodsService.cats.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatsListByIdResp listCats(CatsListByIdReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listCats(req);
	}


	@ServiceMethod(method="com.goodsService.brandModel.list.all",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandModelListResp brandModelListAll(BrandModelListAllReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.brandModelListAll(req);
	}
	
	@ServiceMethod(method="com.goodsService.package.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PackageGetResp getPackage(PackageGetReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsTagsService.getPackage(req);
	}
	
	@ServiceMethod(method="com.goodsService.goods.import",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsImportResp importGoods(GoodsImportReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.importGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.activity.import",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ActivityImportResp importActivity(ActivityImportReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.importActivity(req);
	}
	
	@ServiceMethod(method="com.goodsService.terminal.import",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TerminalImportResp importTerminal(TerminalImportReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.importTerminal(req);
	}
	
	@ServiceMethod(method="com.goodsService.spec.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsSpecResp getGoodsSpec(GoodsSpecReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodSpec(req);
	}
	
	@ServiceMethod(method="com.goodsService.productSpec.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductSpecResp getGoodsSpec(ProductSpecReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getProductSpec(req);
	}
	
	@ServiceMethod(method="com.goodsService.isGift.check",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsTypeIdGetResp getGoodsTypeId(GoodsTypeIdGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGoodsTypeId(req);
	}
	
	@ServiceMethod(method="com.goodsService.pcode.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PCodeGetResp getPCode(PCodeGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getPCode(req);
	}
	
	@ServiceMethod(method="com.goodsService.goodsType.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsTypeSpecGetResp getGoodsType(GoodsTypeSpecGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsTypeService.getGoodsType(req);
	}

	
	@ServiceMethod(method="com.goodsService.goods.qryGoodsBySku",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsBySkuResp qryGoodsBySku(GoodsBySkuReq req)throws Exception{
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryGoodsBySku(req);
	}

	
	@ServiceMethod(method="com.goodsService.goodsType.typelist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsTypeListResp getTypeList(GoodsTypeListReq req) {
		this.init(req);
		req.setRopRequestContext(null);
		return goodsTypeService.getTypeList(req);
	}
	
	@ServiceMethod(method="com.goodsService.brand.listbytype",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandListByTypeResp listBrandByTypeId(BrandListByTypeReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listBrandByTypeId(req);
	}
	
	@ServiceMethod(method="com.goodsService.brandModel.listbybrand",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandModelListByBrandResp listBrandModelByBrandId(BrandModelListByBrandReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listBrandModelByBrandId(req);
	}
	
	@ServiceMethod(method="com.goodsService.brandModel.listbymodel",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BrandModelListByModelCodeResp listBrandModelByModel(BrandModelListByModelCodeReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsBrandService.listBrandModelByModel(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.listbytype",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatListByTypeResp listCatByTypeId(CatListByTypeReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodsCatsService.listCatByTypeId(req);
	}
	
	@ServiceMethod(method="com.goodsService.product.getByName",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductInfoByNameResp qryProductInfoByName(ProductInfoByNameReq req)throws Exception{
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryProductInfoByName(req);
	}

	@ServiceMethod(method="com.goodsService.goods.cache",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CacheGoodsDataResp cacheGoodsData(CacheGoodsDataReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.cacheGoodsData(req);
	}
	
	@ServiceMethod(method="com.goodsService.terminalNum.cache",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public TerminalNumResp getTerminalNumBySN(TerminalNumReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getTerminalNumBySN(req);
	}
	
	@ServiceMethod(method="com.goodsService.sku.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SkuQueryResp getGiftByActivityCode(SkuQueryReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.getGiftByActivityCode(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatAddResp addCat(CatAddReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return this.goodsCatsService.addCat(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatEditResp editCat(CatEditReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return this.goodsCatsService.editCat(req);
	}
	
	@ServiceMethod(method="com.goodsService.cat.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CatDeleteResp editCat(CatDeleteReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return this.goodsCatsService.deleteCat(req);
	}
	
	@ServiceMethod(method="com.goodsService.proxy.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProxyGoodsAddResp addProxyGoods(ProxyGoodsAddReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return this.goodServices.addProxyGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.proxy.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProxyGoodsDeleteResp deleteProxyGoods(ProxyGoodsDeleteReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return this.goodServices.deleteProxyGoods(req);
	}
	
	@ServiceMethod(method="com.goodsService.proxy.goods.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProxyGoodsPageQueryResp queryProxyGoodsForPage(ProxyGoodsPageQueryReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return goodServices.queryProxyGoodsForPage(req);
	}
	

	@ServiceMethod(method = "zte.orderService.promotionredpkg.create",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CreateRedPkgResp createRedPkg(CreateRedPkgReq req) {
		this.init(req);
		CreateRedPkgResp createRedPkgResp = goodServices.createRedPkg(req);
		return createRedPkgResp;
	}

	
//	@ServiceMethod(method = "zte.orderService.promotionredpkg.create.cpns",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
//	public CreateRedPkgCpnsResp createRedPkgCpns(CreateRedPkgCpnsReq req) {
//		this.init(req);
//		CreateRedPkgCpnsResp resp = goodServices.createRedPkgCpns(req);
//		return resp;
//	}
	
	@ServiceMethod(method = "zte.orderService.promotionredpkg.grab",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GrabRedPkgResp grabRedPkg(GrabRedPkgReq req) {
		this.init(req);
		GrabRedPkgResp resp = goodServices.grabRedPkg(req);
		return resp;
	}

	
	@ServiceMethod(method = "zte.orderService.promotionredpkg.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RedPkgListResp redPkgList(RedPkgListReq req) {
		this.init(req);
		RedPkgListResp resp = goodServices.redPkgList(req);
		return resp;
	}

	
	@ServiceMethod(method = "zte.orderService.promotionredpkg.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RedPkgEditResp redPkgEdit(RedPkgEditReq req) {
		this.init(req);
		RedPkgEditResp resp = goodServices.redPkgEdit(req);
		return resp;
	}
	@ServiceMethod(method = "com.goodsService.product.page.quer",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ProductPageQueryResp getProductPageQuery(ProductPageQueryReq req){
		this.init(req);
		return this.goodServices.getProductPageQuery(req);
	}
	
	@ServiceMethod(method = "com.goodsService.goods.refreshCache",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RefreshGoodsCacheResp refreshGoodsCache(RefreshGoodsCacheReq req){
		this.init(req);
		return this.goodServices.refreshGoodsCache(req);
	}
	
	@ServiceMethod(method = "zte.params.goods.req.subtractinventorynumreq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SubtractInventoryNumResp subtractInventoryNum(SubtractInventoryNumReq req) {
		this.init(req);
		return goodServices.subtractInventoryNum(req);
	}
	@ServiceMethod(method = "zte.params.goods.req.increaseinventorynumreq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public IncreaseInventoryNumResp increaseInventoryNum(IncreaseInventoryNumReq req) {
		this.init(req);
		return goodServices.increaseInventoryNum(req);
	}
	
	@ServiceMethod(method = "com.goodsService.activity.getGoodsJoinActivities",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ActivityListGetResp getGoodsJoinActivities(ActivityListGetReq req){
		this.init(req);
		return goodServices.getGoodsJoinActivities(req);
	}
	
	@ServiceMethod(method = "com.goodsService.stdgoods.getStdGoodsByName",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StdGoodsGetResp getStdGoodsByName(StdGoodsGetReq req){
		this.init(req);
		return goodServices.getStdGoodsByName(req);
	}
	
	@ServiceMethod(method = "com.goodsService.goods.qryAdmissibleBroadbandGoods",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoods(BroadbandGoodsQryReq req){
		this.init(req);
		return goodServices.qryAdmissibleBroadbandGoods(req);
	}
	
	@ServiceMethod(method = "com.goodsService.goods.qryAdmissibleBroadbandGoodsByCityOrCounty",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoodsByCityOrCounty(BroadbandGoodsQryReq req){
		this.init(req);
		return goodServices.qryAdmissibleBroadbandGoodsByCityOrCounty(req);
	}
	@ServiceMethod(method = "com.goodsService.goods.goodsBroadbandAdd",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse goodsBroadbandAdd(GoodsBroadbandAddReq req){
		this.init(req);
		return goodServices.goodsBroadbandAdd(req);
	}
}