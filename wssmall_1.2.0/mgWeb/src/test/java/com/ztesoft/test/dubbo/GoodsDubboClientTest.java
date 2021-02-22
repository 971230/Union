package com.ztesoft.test.dubbo;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;
import zte.params.brand.req.BrandGetReq;
import zte.params.brand.req.BrandGoodsListReq;
import zte.params.brand.req.BrandListByCatReq;
import zte.params.brand.req.BrandListReq;
import zte.params.brand.resp.BrandGetResp;
import zte.params.brand.resp.BrandGoodsListResp;
import zte.params.brand.resp.BrandListResp;
import zte.params.comments.req.CommentListReq;
import zte.params.comments.req.CommentsPageListReq;
import zte.params.comments.resp.CommentListResp;
import zte.params.comments.resp.CommentsPageListResp;
import zte.params.goods.req.AttrDefListReq;
import zte.params.goods.req.EcsGoodsPageListReq;
import zte.params.goods.req.GoodsAcceptPriceReq;
import zte.params.goods.req.GoodsAcceptReq;
import zte.params.goods.req.GoodsAdjunctListReq;
import zte.params.goods.req.GoodsBaseQueryReq;
import zte.params.goods.req.GoodsCatPathGetReq;
import zte.params.goods.req.GoodsCatQueryReq;
import zte.params.goods.req.GoodsColConfigReq;
import zte.params.goods.req.GoodsComplexListReq;
import zte.params.goods.req.GoodsDetailGetReq;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.req.GoodsIntroReq;
import zte.params.goods.req.GoodsOfferRelContractReq;
import zte.params.goods.req.GoodsPageByIdsReq;
import zte.params.goods.req.GoodsPageListByTypeReq;
import zte.params.goods.req.GoodsPageListReq;
import zte.params.goods.req.GoodsPlanRelReq;
import zte.params.goods.req.GoodsPromotionGetReq;
import zte.params.goods.req.GoodsQueryInfoReq;
import zte.params.goods.req.GoodsRankingReq;
import zte.params.goods.req.GoodsRelAdjunctReq;
import zte.params.goods.req.GoodsRelPageListReq;
import zte.params.goods.req.GoodsSpecCheckReq;
import zte.params.goods.req.GoodsSpecListReq;
import zte.params.goods.req.GoodsSpecificationInfoReq;
import zte.params.goods.req.GoodsStoreGetReq;
import zte.params.goods.req.GoodsTerminalRelTerminalReq;
import zte.params.goods.req.GoodsTerminalReq;
import zte.params.goods.req.GroupBuyGetReq;
import zte.params.goods.req.GroupBuyListReq;
import zte.params.goods.req.LimitBuyGoodsListReq;
import zte.params.goods.req.LimitBuyListReq;
import zte.params.goods.req.OfferChangeListReq;
import zte.params.goods.req.ProductListReq;
import zte.params.goods.req.ScoreGetReq;
import zte.params.goods.resp.AttrDefListResp;
import zte.params.goods.resp.EcsGoodsPageListResp;
import zte.params.goods.resp.GoodsAcceptPriceResp;
import zte.params.goods.resp.GoodsAcceptResq;
import zte.params.goods.resp.GoodsAdjunctListResp;
import zte.params.goods.resp.GoodsBaseQueryResp;
import zte.params.goods.resp.GoodsCatPathGetResp;
import zte.params.goods.resp.GoodsColConfigResp;
import zte.params.goods.resp.GoodsComplexListResp;
import zte.params.goods.resp.GoodsDetailGetResp;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.goods.resp.GoodsIntroResp;
import zte.params.goods.resp.GoodsListResp;
import zte.params.goods.resp.GoodsPageListResp;
import zte.params.goods.resp.GoodsPromotionGetResp;
import zte.params.goods.resp.GoodsQueryResp;
import zte.params.goods.resp.GoodsSpecCheckResp;
import zte.params.goods.resp.GoodsSpecListResp;
import zte.params.goods.resp.GoodsSpecificationInfoResp;
import zte.params.goods.resp.GoodsStoreGetResp;
import zte.params.goods.resp.GroupBuyGetResp;
import zte.params.goods.resp.GroupBuyListResp;
import zte.params.goods.resp.LimitBuyGoodsListResp;
import zte.params.goods.resp.LimitBuyListResp;
import zte.params.goods.resp.OfferChangeListResp;
import zte.params.goods.resp.ProductListResp;
import zte.params.goods.resp.ScoreGetResp;
import zte.params.goodscats.req.CatBrandListReq;
import zte.params.goodscats.req.CatGetByIdReq;
import zte.params.goodscats.req.CatGetReq;
import zte.params.goodscats.req.CatHotListReq;
import zte.params.goodscats.req.CatListByCondReq;
import zte.params.goodscats.req.CatListByGoodsIdReq;
import zte.params.goodscats.req.CatPathGetReq;
import zte.params.goodscats.req.CatsGoodsListReq;
import zte.params.goodscats.req.CatsListReq;
import zte.params.goodscats.req.ChildCatsListReq;
import zte.params.goodscats.req.ChildrenCatsListReq;
import zte.params.goodscats.req.ComplexGoodsGetReq;
import zte.params.goodscats.resp.CatBrandListResp;
import zte.params.goodscats.resp.CatGetResp;
import zte.params.goodscats.resp.CatHotListResp;
import zte.params.goodscats.resp.CatListByCondResp;
import zte.params.goodscats.resp.CatListByGoodsIdResp;
import zte.params.goodscats.resp.CatPathGetResp;
import zte.params.goodscats.resp.CatsGoodsListResp;
import zte.params.goodscats.resp.CatsListResp;
import zte.params.goodscats.resp.ChildCatsListResp;
import zte.params.goodscats.resp.ChildrenCatsListResp;
import zte.params.goodscats.resp.ComplexGoodsGetResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.req.TypeRelGoodsListReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.goodstype.resp.TypeRelGoodsListResp;
import zte.params.tags.req.TagGetReq;
import zte.params.tags.req.TagGoodsListReq;
import zte.params.tags.req.TagListReq;
import zte.params.tags.resp.TagGetResp;
import zte.params.tags.resp.TagGoodsListResp;
import zte.params.tags.resp.TagListResp;

import com.ztesoft.api.ZteClient;

import zte.net.iservice.params.user.req.UserLoginReq;
import zte.net.iservice.params.user.resp.UserLoginResp;

import com.ztesoft.test.dubbo.base.DubboClientTest;

import commons.CommonTools;

public class GoodsDubboClientTest extends DubboClientTest {
	private static Logger logger = Logger.getLogger(GoodsDubboClientTest.class);
	/* ------------------------------------成功 --------------------------------- */

	@Test(enabled = false)
	public void queryGoodsBaseInfo() {
		logger.info("查询商品基本信息");
		GoodsBaseQueryReq goodsBaseQueryReq = new GoodsBaseQueryReq();
		goodsBaseQueryReq.setGoods_id("2759");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsBaseQueryReq,
				GoodsQueryResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsBaseQueryResp goodsQueryResp = (GoodsBaseQueryResp) response;
			logger.info("cat_id is "
					+ goodsQueryResp.getGoods().getCat_id());
		}
		Assert.assertEquals(response.getError_code(), "0");
	}

	/* --------------------不用测，不对外开发------------- */
	// @Test(enabled=false)
	// public void queryGoodsPriceList(){
	// logger.info("查询商品价格");
	// GoodsPriceQueryReq goodsPriceQueryReq = new GoodsPriceQueryReq();
	// goodsPriceQueryReq.setGoods_id("2763");
	// ZteClient client = getDubboZteClient();
	// ZteResponse response = client.execute(goodsPriceQueryReq,
	// GoodsPricePageListResp.class);
	// logger.info("result:"+response.isResult());
	// if("0".equals(response.getError_code())){
	// GoodsPricePageListResp goodsQueryResp = (GoodsPricePageListResp)response;
	// logger.info("list length is: "+goodsQueryResp.getWebPage().getResult().size());
	// }
	// logger.info("result:"+response.getError_msg());
	// Assert.assertEquals(response.getError_code(), "0");
	// }

	/* -------------------------------成功---------------- */
	@Test(enabled = false)
	public void queryGoodsCatList() {
		logger.info("从缓存获取分类商品（门户展示一级分类下所有商品）");
		GoodsCatQueryReq goodsQueryReq = new GoodsCatQueryReq();
		goodsQueryReq.setTag_id("1");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsQueryReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info("list length is: "
					+ goodsListResp.getGoodsPage().getResult().size());
		}
		Assert.assertEquals(response.getError_code(), "0");
	}

	/* -------------------------------成功------------------ */
	@Test(enabled = false)
	public void queryGoodsIntro() {
		logger.info("展示商品详情页信息");
		GoodsIntroReq goodsIntroReq = new GoodsIntroReq();
		goodsIntroReq.setGoods_id("4083");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsIntroReq,
				GoodsIntroResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsIntroResp goodsDetialResp = (GoodsIntroResp) response;
			logger.info("商品ID[" + goodsIntroReq.getGoods_id()
					+ "]详情页信息:" + goodsDetialResp.getGoods().getIntro());
		}
		Assert.assertEquals(response.getError_code(), "0");
	}

	/* -------------成功------------- */
	@Test(enabled = false)
	public void queryOfferRelContract() {
		logger.info("查询套餐关联合约");
		GoodsOfferRelContractReq goodsOfferRelContractReq = new GoodsOfferRelContractReq();
		goodsOfferRelContractReq.setGoods_id("5296");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsOfferRelContractReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info(goodsListResp.getGoodsPage().getResult().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* --------------------------成功----------------- */
	@Test(enabled = false)
	public void queryTerminalRelPlan() {
		logger.info("查询终端关联计划");
		GoodsTerminalReq goodsTerminalReq = new GoodsTerminalReq();
		goodsTerminalReq.setGoods_id("5296");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsTerminalReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info(goodsListResp.getGoodsPage().getResult().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* -----------------成功------------------- */
	@Test(enabled = false)
	public void queryPlanRelOffer() {
		logger.info("查询计划关联合约");
		GoodsPlanRelReq goodsPlanRelReq = new GoodsPlanRelReq();
		goodsPlanRelReq.setA_goods_id("25931");
		goodsPlanRelReq.setZ_goods_id("2943");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsPlanRelReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info(goodsListResp.getGoodsPage().getResult().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* -------------------成功 ------------------ */
	@Test(enabled = false)
	public void queryTerminalRelTerminal() {
		logger.info("查询商品绑定商品");
		GoodsTerminalRelTerminalReq goodsTerminalRelTerminalReq = new GoodsTerminalRelTerminalReq();
		goodsTerminalRelTerminalReq.setGoods_id("201401248729001699");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsTerminalRelTerminalReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info(goodsListResp.getGoodsPage().getResult().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* -----------------------成功------------------------- */
	@Test(enabled = false)
	public void queryGoodsRelAdjunct() {
		logger.info("查询商品配件");
		GoodsRelAdjunctReq goodsRelAdjunctReq = new GoodsRelAdjunctReq();
		goodsRelAdjunctReq.setGoods_id("201308227174001001");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsRelAdjunctReq,
				GoodsListResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsListResp goodsListResp = (GoodsListResp) response;
			logger.info(goodsListResp.getGoodsPage().getResult().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* --------------------成功-------------- */
	@Test(enabled = false)
	public void queryGoodsInfo() {
		logger.info("查询商品详情（商品详情信息，评论信息，咨询信息）");
		GoodsQueryInfoReq goodsQueryInfoReq = new GoodsQueryInfoReq();
		goodsQueryInfoReq.setGoods_id("4083");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsQueryInfoReq,
				GoodsQueryResp.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsQueryResp goodsQueryResp = (GoodsQueryResp) response;
			logger.info(goodsQueryResp.getGoods().getName());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/* --------------------------成功------------------ */
	@Test(enabled = false)
	public void queryGoodsAcceptInfo() {
		logger.info("询商品受理详情（商品名称、商品价格、商品规格、商品规格属性、商品温馨提醒、商品库存、商品已售卖量）");
		GoodsAcceptReq goodsAcceptReq = new GoodsAcceptReq();
		goodsAcceptReq.setGoods_id("4083");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsAcceptReq,
				GoodsAcceptResq.class);
		logger.info("result:" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsAcceptResq goodsQueryResp = (GoodsAcceptResq) response;
			logger.info(goodsQueryResp.getGoodsAttr().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询商品库存--成功----------------
	 */
	@Test(enabled = false)
	public void getGoodsStore() {
		GoodsStoreGetReq req = new GoodsStoreGetReq();
		req.setGoods_id("4823");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, GoodsStoreGetResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询商品受理价格-------------------成功-----------
	 */
	@Test(enabled = false)
	public void getAcceptPrice() {
		GoodsAcceptPriceReq req = new GoodsAcceptPriceReq();
		req.setGoods_id("1706");
		req.setProduct_id("201209021706");
		req.setLv_id("1");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, GoodsAcceptPriceResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询商品业务属性列表（订单、支付、受理属性）--------------------成功--------------------------
	 */
	@Test(enabled = false)
	public void listAttrDef() {
		AttrDefListReq req = new AttrDefListReq();
		req.setGoods_id("201311281694001471");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, AttrDefListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询品牌-----------成功---------------------
	 */
	@Test(enabled = false)
	public void getBrand() {
		BrandGetReq req = new BrandGetReq();
		req.setBrand_id("1000200");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, BrandGetResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询类型关联商品---------------成功----------------
	 */
	@Test(enabled = false)
	public void listBrandRelGoods() {
		BrandGoodsListReq req = new BrandGoodsListReq();
		req.setBrand_id("1000216");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, BrandGoodsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询商品类型-------------------成功-------------
	 */
	@Test(enabled = false)
	public void getGoodsType() {
		GoodsTypeGetReq req = new GoodsTypeGetReq();
		req.setType_id("1002");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, GoodsTypeGetResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询类型关联商品----------------------------ok成功-------------------
	 */
	@Test(enabled = false)
	public void listGoodsByTypeId() {
		TypeRelGoodsListReq req = new TypeRelGoodsListReq();
		req.setType_id("1001");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, TypeRelGoodsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询标签列表-----------------------成功-----------------
	 */
	@Test(enabled = false)
	public void listTag() {
		TagListReq req = new TagListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, TagListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询标签关联商品------------------成功--------------------------------
	 */
	@Test(enabled = false)
	public void listGoodsByTagId() {
		TagGoodsListReq req = new TagGoodsListReq();
		req.setTag_id("1");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, TagGoodsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询分类信息，按会员类型查询不同的分类-------------------------成功---------------------
	 */
	@Test(enabled = false)
	public void listGoodsCatsByLvId() {
		CatsListReq req = new CatsListReq();
		req.setLv_id("0");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, CatsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询分类推荐商品------------------------成功------------------------
	 */
	@Test(enabled = false)
	public void listGoodsByCatId() {
		CatsGoodsListReq req = new CatsGoodsListReq();
		req.setCat_id("1000951119");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, CatsGoodsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * - 查询子分类列表（根据分类查询子分类）--------------------成功-------------------
	 */
	@Test(enabled = false)
	public void listGoodsCatsByParentId() {
		ChildCatsListReq req = new ChildCatsListReq();
		req.setParent_id("1000901021");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, ChildCatsListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询热卖分类标签---------------------------成功----------------------
	 */
	@Test(enabled = false)
	public void getHotTag() {
		TagGetReq req = new TagGetReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, TagGetResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 查询商品促销优惠--------------------成功------------------------
	 */
	@Test(enabled = false)
	public void getPromotion() {
		GoodsPromotionGetReq req = new GoodsPromotionGetReq();
		req.setPmt_id("201309174030000090");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, GoodsPromotionGetResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 商品搜索（按分类、品牌、属性、关键字、供货商、价格、属性搜索商品、排序）------------------------成功----------
	 * ---------
	 */
	@Test(enabled = false)
	public void queryGoodsForPage() {
		GoodsPageListReq req = new GoodsPageListReq();
		req.setBrand("201401214625000155");
		req.setSearch_key("苹果");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(req, GoodsPageListResp.class);
		logger.info("result:" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	@Test(enabled = false)
	public void userLogin() {
		UserLoginReq userLoginReq = new UserLoginReq();
		userLoginReq.setUserName("admin");
		userLoginReq.setUerPwd("123");
		userLoginReq.setProduct_id("201403024579001887");
		userLoginReq.setService_code("3");
		userLoginReq.setUserSessionId(CommonTools.getUserSessionId());
		ZteClient client = getDubboZteClient();
		ZteResponse response = client
				.execute(userLoginReq, UserLoginResp.class);
		logger.info("异常订单返回---" + response.getError_msg());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/**
	 * 添加商品--------------------暂缓--user_id不能为空------------------- //
	 */
	// @Test(enabled=false)
	// public void addGoods(){
	// GoodsAddReq req = new GoodsAddReq();
	// Goods goods = new Goods();
	// goods.setCat_id("1110000");
	// goods.setGoods_type("111");
	// goods.setName("dubbo测试");
	// goods.setSn("11111111");
	// goods.setMktprice(1002.00);
	// goods.setPrice(1001.00);
	// goods.setMarket_enable(1);
	// req.setGoods(goods);
	//
	// GoodsBusiness goodsBusiness = new GoodsBusiness();
	// goodsBusiness.setBiz_code("0001");
	// req.setGoodsBusiness(goodsBusiness);
	// req.setFounder("-1");
	// // AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
	// // String user_no =adminUser.getUserid();
	// req.setUser_id("1");
	// req.setParent_user_id("2");
	// ZteClient client = getDubboZteClient();
	// ZteResponse response = client.execute(req, GoodsAddResp.class);
	// logger.info("result:"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	//
	// /**
	// * 修改商品-------------------user_id不能为空------------
	// */
	// @Test(enabled=false)
	// public void editGoods(){
	// GoodsEditReq req = new GoodsEditReq();
	// Goods goods = new Goods();
	// goods.setCat_id("1");
	// goods.setGoods_type("1");
	// goods.setName("dubbo测试");
	// goods.setSn("11111");
	// goods.setMktprice(100.00);
	// goods.setPrice(100.00);
	// goods.setMarket_enable(1);
	// req.setGoods(goods);
	//
	//
	// GoodsBusiness goodsBusiness = new GoodsBusiness();
	// goodsBusiness.setBiz_code("0001");
	// req.setGoodsBusiness(goodsBusiness);
	// req.setFounder("-1");
	// ZteClient client = getDubboZteClient();
	// ZteResponse response = client.execute(req, GoodsEditResp.class);
	// logger.info("result:"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	//
	// /**
	// * 删除商品--成功
	// */
	// @Test(enabled=false)
	// public void deleteGoods(){
	// GoodsDeleteReq req = new GoodsDeleteReq();
	// req.setGoods_id("2222");
	// ZteClient client = getDubboZteClient();
	// ZteResponse response = client.execute(req, GoodsDeleteResp.class);
	// logger.info("result:"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	// ------------------------------成功-----------------------
	@Test(enabled = false)
	public void listGoodsSpec() {
		GoodsSpecListReq goodsSpecListReq = new GoodsSpecListReq();
		goodsSpecListReq.setGoods_id("1552");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsSpecListReq,
				GoodsSpecListResp.class);
		logger.info("j结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ---------------------成功-----------------------
	@Test(enabled = false)
	public void qryGoodsRanking() {
		GoodsRankingReq goodsRankingReq = new GoodsRankingReq();
		goodsRankingReq.setCount("5");
		goodsRankingReq.setType("1");
		goodsRankingReq.setVal("2");// 比较值
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsRankingReq,
				GoodsListResp.class);
		logger.info("j结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ---------------------成功--------------
	@Test(enabled = false)
	public void listGoodsComments() {
		CommentsPageListReq commentsPageListReq = new CommentsPageListReq();
		commentsPageListReq.setGoods_id("6052");
		commentsPageListReq.setComment_type("goods");// 评论类型
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(commentsPageListReq,
				CommentsPageListResp.class);
		logger.info("j结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// --------------------成功------------------
	@Test(enabled = false)
	public void getGoodsScore() {
		ScoreGetReq scoreGetReq = new ScoreGetReq();
		scoreGetReq.setGoods_id("6052");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(scoreGetReq, ScoreGetResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ----------------------成功---------------------
	@Test(enabled = false)
	public void getGoodsDetail() {
		GoodsDetailGetReq goodsDetailGetReq = new GoodsDetailGetReq();
		goodsDetailGetReq.setGoods_id("5498");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsDetailGetReq,
				GoodsDetailGetResp.class);
		logger.info("结果--" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsDetailGetResp goodsDetailGetResp = (GoodsDetailGetResp) response;
			logger.info(goodsDetailGetResp.getDetail_info());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// -------------------成功-------------
	@Test(enabled = false)
	public void checkGoodsHasSpec() {
		GoodsSpecCheckReq goodsSpecCheckReq = new GoodsSpecCheckReq();
		goodsSpecCheckReq.setGoods_id("5498");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsSpecCheckReq,
				GoodsSpecCheckResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// --------------成功---------
	@Test(enabled = false)
	public void queryGoodsByTypeId() {
		GoodsPageListByTypeReq goodsPageListByTypeReq = new GoodsPageListByTypeReq();
		goodsPageListByTypeReq.setType_id("1002");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsPageListByTypeReq,
				GoodsPageListResp.class);
		logger.info("结果--" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsPageListResp goodsPageListResp = (GoodsPageListResp) response;
			logger.info(goodsPageListResp.getGoodsPage());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/*
	 * -------------------成功-----------
	 */
	@Test(enabled = false)
	public void queryGoodsByIdsAndName() {
		GoodsPageByIdsReq goodsPageByIdsReq = new GoodsPageByIdsReq();
		goodsPageByIdsReq.setIds("3011");
		goodsPageByIdsReq.setName("海报");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsPageByIdsReq,
				GoodsPageListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ------------------------c成功--------------------------
	@Test(enabled = false)
	public void listGoodsAdjunct() {
		GoodsAdjunctListReq goodsAdjunctListReq = new GoodsAdjunctListReq();
		goodsAdjunctListReq.setGoods_id("201311281694001471");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsAdjunctListReq,
				GoodsAdjunctListResp.class);
		logger.info("结果--" + response.isResult());
		if ("0".equals(response.getError_code())) {
			GoodsAdjunctListResp goodsPageListResp = (GoodsAdjunctListResp) response;
			logger.info(goodsPageListResp.getAdjuncts().size());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------------不用测，不对外开放--------------------
	// @Test(enabled=false)
	// public void checkCouponUserTime(){
	// CouponUserTimeCheckReq couponUserTimeCheckReq=new
	// CouponUserTimeCheckReq();
	// couponUserTimeCheckReq.setMemc_code("A957201309051451202013082317270001534");
	// couponUserTimeCheckReq.setMember_id("201308231727000153");
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(couponUserTimeCheckReq,
	// CouponUserTimeCheckResp.class);
	// logger.info("结果ing--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	// --------------不用测，不对外开放------------
	// @Test(enabled=false)
	// public void editCouponUseTime(){
	// CouponUseTimesEditReq couponUseTimesEditReq=new CouponUseTimesEditReq();
	// couponUseTimesEditReq.setAddUseTimes(1);
	// couponUseTimesEditReq.setMember_id("201308221559000118");
	// couponUseTimesEditReq.setMemc_code("B123201309071556352013082215590001181");
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(couponUseTimesEditReq,
	// CouponUseTimesEditResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	// //---------------暂缓，不对外开放---------------------------------
	// @Test(enabled=false)
	// public void getFreeOffer(){
	// FreeOfferGetReq freeOfferGetReq=new FreeOfferGetReq();
	// freeOfferGetReq.setFo_id(1);
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(freeOfferGetReq,
	// FreeOfferGetResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0");
	// }
	/*
	 * 得到商品分类----------------------------------成功-----------------------------
	 */
	@Test(enabled = false)
	public void getCat() {
		CatGetReq catGetReq = new CatGetReq();
		catGetReq.setGoods_id("201311281694001471");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catGetReq, CatGetResp.class);
		logger.info("结果--" + response.isResult());
		if ("0".equals(response.getError_code())) {
			CatGetResp catGetResp = (CatGetResp) response;
			logger.info(catGetResp.getCat().getCat_id());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ------------------------成功-----------------------
	@Test(enabled = false)
	public void getCats() {
		CommentListReq commentListReq = new CommentListReq();
		commentListReq.setGoods_id("201311281694001471");
		commentListReq.setComment_type("goods");// 评论类型
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(commentListReq,
				CommentListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------------------------有问题，不对外开放---------------------
	// @Test(enabled=false)
	// public void listFreeOffer(){
	// FreeOfferPageListReq freeOfferPageListReq=new FreeOfferPageListReq();
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(freeOfferPageListReq,
	// FreeOfferPageListResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0"); }
	// //---------------------------------成功，不对外开放------------------
	// @Test(enabled=false)
	// public void getFreeOfferCategory(){
	// FreeOfferCategoryGetReq freeOfferCategoryGetReq=new
	// FreeOfferCategoryGetReq();
	// freeOfferCategoryGetReq.setFo_category_id("201308227605000425");
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(freeOfferCategoryGetReq,
	// FreeOfferCategoryGetResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0"); }
	// //--------------------------成功----------------
	@Test(enabled = false)
	public void listLimitBuyGoods() {
		LimitBuyGoodsListReq limitBuyGoodsListReq = new LimitBuyGoodsListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(limitBuyGoodsListReq,
				LimitBuyGoodsListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// -----------------有问题，不对外开放--------------------
	// @Test(enabled=false)
	// public void searchGoods(){
	// GoodsSearchPageListReq goodsSearchPageListReq=new
	// GoodsSearchPageListReq();
	// goodsSearchPageListReq.setUri("1000961092");
	// goodsSearchPageListReq.setPageNo(1);
	// goodsSearchPageListReq.setPageSize(10);
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(goodsSearchPageListReq,
	// GoodsSearchPageListResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0"); }
	// //---------------成功，不对外开放--------------------
	// @Test(enabled=false)
	// public void getSelector(){
	// SelectorGetReq selectorGetReq=new SelectorGetReq();
	// selectorGetReq.setUri("1000961092");//搜索uri
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(selectorGetReq,
	// SelectorGetResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0"); }
	// ------------------------有问题，不对外开放---------------
	// @Test(enabled=false)
	// public void putParams(){
	// ParamsPutReq paramsPutReq=new ParamsPutReq();
	// paramsPutReq.setUri("1000961092");
	// ZteClient client=getDubboZteClient();
	// ZteResponse response=client.execute(paramsPutReq, ParamsPutResp.class);
	// logger.info("结果--"+response.isResult());
	// Assert.assertEquals(response.getError_code(), "0"); }
	// ------------有问题-获取商户id，单元测试去不了--------
	@Test(enabled = false)
	public void listAllChildren() {
		ChildrenCatsListReq childrenCatsListReq = new ChildrenCatsListReq();
		childrenCatsListReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(childrenCatsListReq,
				ChildrenCatsListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// --------------------------成功-------------------
	@Test(enabled = false)
	public void listCatBrand() {
		CatBrandListReq catBrandListReq = new CatBrandListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catBrandListReq,
				CatBrandListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// ------------------------------成功--------------
	@Test(enabled = false)
	public void getCatPath() {
		CatPathGetReq catPathGetReq = new CatPathGetReq();
		catPathGetReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catPathGetReq,
				CatPathGetResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// -------------------------------有问题,要页面传值，测试不了--------------------
	@Test(enabled = false)
	public void getComplexGoods() {
		ComplexGoodsGetReq complexGoodsGetReq = new ComplexGoodsGetReq();
		complexGoodsGetReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(complexGoodsGetReq,
				ComplexGoodsGetResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");

	}

	// -------------------有问题，要页面传值，测试不了---------------
	@Test(enabled = false)
	public void listCatsByGoodsId() {
		CatListByGoodsIdReq catListByGoodsIdReq = new CatListByGoodsIdReq();
		catListByGoodsIdReq.setGood_id("2759");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catListByGoodsIdReq,
				CatListByGoodsIdResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	/*
	 * 商品查询 ------------------------成功ok------------------
	 */
	@Test(enabled = false)
	public void listBrand() {
		BrandListReq brandListReq = new BrandListReq();
		brandListReq.setGood_id("2759");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client
				.execute(brandListReq, BrandListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// ----------------------------------成功-------------
	@Test(enabled = false)
	public void listCatsByCond() {
		CatListByCondReq catListByCondReq = new CatListByCondReq();
		catListByCondReq.setCatPath("0|1000901018|1000951110|1000961861|");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catListByCondReq,
				CatListByCondResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// ----------------------成功---------------
	@Test(enabled = false)
	public void getCatById() {
		CatGetByIdReq catGetByIdReq = new CatGetByIdReq();
		catGetByIdReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catGetByIdReq, CatGetResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	/*
	 * 团购--------------------------------成功--------
	 */
	@Test(enabled = false)
	public void getGroupBuy() {
		GroupBuyGetReq groupBuyGetReq = new GroupBuyGetReq();
		groupBuyGetReq.setGroup_id("201304161511000010");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(groupBuyGetReq,
				GroupBuyGetResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// ---------------成功-------------select gb.* ,g.cat_id catid ,g.price
	// oldprice from es_group_buy gb , es_goods g where source_from = 'FJ' and
	// gb.goodsid = g.goods_id and gb.start_time<sysdate and gb.end_time>sysdate
	// order by add_time desc------------
	@Test(enabled = false)
	public void listGroupBuy() {
		GroupBuyListReq groupBuyListReq = new GroupBuyListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(groupBuyListReq,
				GroupBuyListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// ---------------成功-------------
	@Test(enabled = false)
	public void listLimitBuy() {
		LimitBuyListReq limitBuyListReq = new LimitBuyListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(limitBuyListReq,
				LimitBuyListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void getHotList() {
		CatHotListReq catHotListReq = new CatHotListReq();
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(catHotListReq,
				CatHotListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");

	}

	// -------------------------------成功---------------
	@Test(enabled = false)
	public void listBrandByCatId() {
		BrandListByCatReq brandListByCatReq = new BrandListByCatReq();
		brandListByCatReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(brandListByCatReq,
				BrandListResp.class);
		logger.info("结果--" + response.isResult());
		// Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void listGoodsPage() {
		GoodsRelPageListReq goodsRelPageListReq = new GoodsRelPageListReq();
		goodsRelPageListReq.setA_goods_id("2759");
		goodsRelPageListReq.setRel_type("TERMINAL_PLAN");// N
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsRelPageListReq,
				GoodsPageListResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void listProductIds() {
		ProductListReq productListReq = new ProductListReq();
		productListReq.setService_id("201209024769");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(productListReq,
				ProductListResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(true, true);

	}

	// -------------成功---------------------
	@Test(enabled = false)
	public void listOfferChange() {
		OfferChangeListReq offerChangeListReq = new OfferChangeListReq();
		offerChangeListReq.setGoodsType("normal");
		offerChangeListReq.setPageNo(1);
		offerChangeListReq.setPageSize(10);
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(offerChangeListReq,
				OfferChangeListResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");

	}

	// -------------------------成功，关联配数据---------------
	@Test(enabled = false)
	public void getGoods() {
		GoodsGetReq goodsGetReq = new GoodsGetReq();
		goodsGetReq.setPackage_id("1");
		goodsGetReq.setSn("mk43535");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsGetReq, GoodsGetResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void listComplexByGoodsId() {
		GoodsComplexListReq goodsComplexListReq = new GoodsComplexListReq();
		goodsComplexListReq.setGoods_id("201308082611000627");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsComplexListReq,
				GoodsComplexListResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");
	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void getGoodsSpecificationInfo() {
		GoodsSpecificationInfoReq goodsSpecificationInfoReq = new GoodsSpecificationInfoReq();
		goodsSpecificationInfoReq.setGoods_id("2759");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsSpecificationInfoReq,
				GoodsSpecificationInfoResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");

	}

	// -----------------成功----------------------
	@Test(enabled = false)
	public void getGoodsCatPath() {
		GoodsCatPathGetReq goodsCatPathGetReq = new GoodsCatPathGetReq();
		goodsCatPathGetReq.setGoods_id("2759");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsCatPathGetReq,
				GoodsCatPathGetResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");

	}

	// -----------------------------表或视图不存在----es_tab_col_config这个表在福建开发库里面没有表---------------------------

	public void getcolConfig() {
		GoodsColConfigReq goodsColConfigReq = new GoodsColConfigReq();
		goodsColConfigReq.setCat_id("1000951124");
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(goodsColConfigReq,
				GoodsColConfigResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");
	}

	@Test(enabled = true)
	public void queryGoodsForPageEcs() {
		EcsGoodsPageListReq ecsGoodsPageListReq = new EcsGoodsPageListReq();
//		ecsGoodsPageListReq.setBrand_id("1000210");
		ecsGoodsPageListReq.setType("goods");
		ecsGoodsPageListReq.setPageNo(1);
		ecsGoodsPageListReq.setPageSize(10);
		ZteClient client = getDubboZteClient();
		ZteResponse response = client.execute(ecsGoodsPageListReq,
				EcsGoodsPageListResp.class);
		logger.info("结果--" + response.isResult());
		Assert.assertEquals(response.getError_code(), "0");
	}
}
