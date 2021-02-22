package com.ztesoft.net.service;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import params.ZteResponse;
import params.req.PartnerByIdReq;
import params.resp.PartnerByIdResp;
import params.tags.req.TagCat_typeReq;
import params.tags.resp.TagResp;
import services.PartnerInf;
import services.ServiceBase;
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
import zte.net.iservice.IGoodsService;
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

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.eop.sdk.utils.UploadUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsBusiness;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsRelAttConvert;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.GoodsSpecificationModel;
import com.ztesoft.net.mall.core.model.GroupBuy;
import com.ztesoft.net.mall.core.model.LimitBuy;
import com.ztesoft.net.mall.core.model.LimitBuyGoods;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.model.PromotionRedPkg;
import com.ztesoft.net.mall.core.model.PromotionRedPkgDetail;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.service.ICommentsManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsComplexManager;
import com.ztesoft.net.mall.core.service.IGoodsContractManager;
import com.ztesoft.net.mall.core.service.IGoodsImportHandler;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;
import com.ztesoft.net.mall.core.service.IGoodsSearchManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.IGroupBuyManager;
import com.ztesoft.net.mall.core.service.ILimitBuyManager;
import com.ztesoft.net.mall.core.service.IPromotionActivityManager;
import com.ztesoft.net.mall.core.service.IPromotionRedManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.service.ITerminalImportHandler;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.widget.goods.list.GoodsDataProvider;
import com.ztesoft.net.mall.widget.goods.list.Term;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import consts.ConstsCore;


@ServiceMethodBean(version = "1.0")
public class GoodsService extends ServiceBase implements  IGoodsService{

	private PartnerInf partnerServ;
	private IGoodsManager goodsManager;
	private IGoodsManagerN goodsManagerN;
	private IGoodsTypeManager goodsTypeManager;
	private GoodsTypeDTO goodsType;
	private GoodsControl goodsControl;
	private IGoodsCatManager goodsCatManager;
	private IGoodsSearchManager goodsSearchManager;
	private GoodsNetCacheRead read = new GoodsNetCacheRead();
	private GoodsNetCacheWrite write = new GoodsNetCacheWrite();
	private IGoodsContractManager goodsConManager;
	private GoodsDataProvider goodsDataProvider;
	private IGoodsComplexManager goodsComplexManager;
	private ILimitBuyManager limitBuyManager;
	private IGroupBuyManager groupBuyManager;
	private IPromotionActivityManager promotionActivityManager;
	private ITagManager tagManager ;
	private IWarehouseManager warehouseManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	private IPromotionRedManager promotionRedManager;
	
	public void init(){
		if(null == partnerServ) partnerServ = ApiContextHolder.getBean("partnerServ");
	}
	
	public IPromotionActivityManager getPromotionActivityManager() {
		return promotionActivityManager;
	}

	public void setPromotionActivityManager(
			IPromotionActivityManager promotionActivityManager) {
		this.promotionActivityManager = promotionActivityManager;
	}

	public IGoodsManagerN getGoodsManagerN() {
		return goodsManagerN;
	}

	public void setGoodsManagerN(IGoodsManagerN goodsManagerN) {
		this.goodsManagerN = goodsManagerN;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

	public IGroupBuyManager getGroupBuyManager() {
		return groupBuyManager;
	}

	public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
		this.groupBuyManager = groupBuyManager;
	}

	public ILimitBuyManager getLimitBuyManager() {
		return limitBuyManager;
	}

	public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
		this.limitBuyManager = limitBuyManager;
	}

	public IGoodsComplexManager getGoodsComplexManager() {
		return goodsComplexManager;
	}

	public void setGoodsComplexManager(IGoodsComplexManager goodsComplexManager) {
		this.goodsComplexManager = goodsComplexManager;
	}

	public GoodsDataProvider getGoodsDataProvider() {
		return goodsDataProvider;
	}

	public void setGoodsDataProvider(GoodsDataProvider goodsDataProvider) {
		this.goodsDataProvider = goodsDataProvider;
	}
	public IGoodsContractManager getGoodsConManager() {
		return goodsConManager;
	}

	public void setGoodsConManager(IGoodsContractManager goodsConManager) {
		this.goodsConManager = goodsConManager;
	}

	private ICommentsManager commentsManager;
	
	public ICommentsManager getCommentsManager() {
		return commentsManager;
	}

	public void setCommentsManager(ICommentsManager commentsManager) {
		this.commentsManager = commentsManager;
	}
		
	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}

	public GoodsTypeDTO getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsTypeDTO goodsType) {
		this.goodsType = goodsType;
	}

	public GoodsControl getGoodsControl() {
		return goodsControl;
	}

	public void setGoodsControl(GoodsControl goodsControl) {
		this.goodsControl = goodsControl;
	}
	
	public IGoodsCatManager getGoodsCatManager() {
		return goodsCatManager;
	}

	public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
		this.goodsCatManager = goodsCatManager;
	}
	public IGoodsSearchManager getGoodsSearchManager() {
		return goodsSearchManager;
	}

	public void setGoodsSearchManager(IGoodsSearchManager goodsSearchManager) {
		this.goodsSearchManager = goodsSearchManager;
	}
	public IWarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(IWarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	@Override
	public TagResp tagListByCatType(TagCat_typeReq req){
		Tag tag = new Tag();
		TagResp resp = new TagResp();
		String catType = req.getCat_type(); 
		List tagList = new ArrayList();
		tagList  =  tagManager.ListByCatType(catType);
		resp.setTags(tagList);
		return resp ;
	}
	@Override
	public GoodsBaseQueryResp queryGoodsBaseInfo(GoodsBaseQueryReq goodsBaseQueryReq)
			throws Exception {
		Goods goods = read.getCachesGoods(goodsBaseQueryReq.getGoods_id());
		HashMap map = goodsManager.getGoodsScore(goodsBaseQueryReq.getGoods_id());
		goods.setComm_num(Integer.valueOf(map.get("comm_num").toString()));
		goods.setScore(Double.valueOf(map.get("score").toString()));
		GoodsBaseQueryResp resp = new GoodsBaseQueryResp();
		resp.setGoods(goods);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(goodsBaseQueryReq,resp);
		return resp;
	}
	
	@Override
	public GoodsBySkuResp queryGoodsBySku(GoodsBySkuReq goodsBySkuReq)
			throws Exception {
		Goods goods = read.getCachesGoodsBySku(goodsBySkuReq.getSku());
		
		GoodsBySkuResp resp = new GoodsBySkuResp();
		resp.setGoods(goods);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(goodsBySkuReq,resp);
		return resp;
	}

	@Override
	public GoodsPricePageListResp queryGoodsPriceList(GoodsPriceQueryReq goodsPriceQueryReq) throws Exception {
		GoodsPricePageListResp resp = new GoodsPricePageListResp();
		Page pg = new Page();
		pg.setData(goodsManager.getPriceList(goodsPriceQueryReq.getGoods_id()));
		resp.setWebPage(pg);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(goodsPriceQueryReq,resp);
		return resp;
	}

	@Override
	public GoodsListResp queryGoodsByServ(GoodsServReq goodsServReq) throws Exception{
		GoodsListResp resp = new GoodsListResp();
		List list = read.getAllGoodsByServ(goodsServReq.getStyle_id());
		int totalcount = list.size();
		int index = Integer.parseInt(goodsServReq.getPage_index());
		int pagesize = Integer.parseInt(goodsServReq.getPage_size());
		List data = new ArrayList();
		for(int i=(index-1)*pagesize,j=0; i<totalcount&&j<pagesize; i++,j++){
			data.add(list.get(i));
		}
		Page pg = new Page();
		pg.setTotalCount(totalcount);
		pg.setPageSize(Integer.parseInt(goodsServReq.getPage_size()));
		pg.setStart(Long.parseLong(goodsServReq.getPage_index()));
		pg.setData(data);
		resp.setGoodsPage(pg);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
	@Override
	public GoodsListResp queryGoodsCatList(GoodsCatQueryReq goodsCatQueryReq)
			throws Exception {
		GoodsListResp resp = new GoodsListResp();
		Page pg = new Page();
		pg.setData(read.getGoodsByCatLvI(goodsCatQueryReq.getTag_id()));
		resp.setGoodsPage(pg);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新增商品", summary = "新增商品")
	public GoodsAddResp addGoods(GoodsAddReq req) {
		Goods goods = new Goods();
		GoodsExtData goodsExtData = new GoodsExtData();
		GoodsRules goodsRules = req.getGoodsRules();
		GoodsAddResp resp = new GoodsAddResp();
		
		int founder = -1;
		String userId = null;
		String parent_user_id = null;
		String user_id = req.getUser_id();
//		AdminUser adminUser = ManagerUtils.getAdminUser();
		AdminUser adminUser = goodsManagerN.getUserInfo(user_id);
		if(adminUser!=null){
			founder = adminUser.getFounder()==null?-1:adminUser.getFounder();
			parent_user_id = adminUser.getParuserid();
			if (Consts.CURR_FOUNDER0 == founder || Consts.CURR_FOUNDER1 == founder) {
				userId = user_id ;
				goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
			}else if(Consts.CURR_FOUNDER3 == founder){
				userId = adminUser.getUserid();
				goods.setAudit_state(Consts.GOODS_AUDIT_SUC);
			}else{
				if(Consts.CURR_FOUNDER4==founder) //供货商
					userId = user_id;
				else if(Consts.CURR_FOUNDER5 == founder) //供货商员工
					userId = parent_user_id;
				goods.setAudit_state(Consts.GOODS_AUDIT_NOT) ; //待审核商品
			}
			if(goods.getSearch_key()!=null){
				goods.setSearch_key("_"+req.getName()+goods.getSearch_key());
			}else{
				goods.setSearch_key("_"+req.getName()+"_");
			}
			goods.setStaff_no(userId);
			goods.setCreator_user(user_id);
			try {
				BeanUtils.copyProperties(goods, req);
				BeanUtils.copyProperties(goodsExtData, req);
				this.goodsManagerN.addGoods(goods, goodsRules, goodsExtData);
				resp.setResult(true);
				resp.setError_code("0");
				resp.setError_msg("成功");
			} catch (Exception e) {
				e.printStackTrace();
				resp.setResult(false);
				resp.setError_code("-1");
				resp.setError_msg("商品添加失败："+e.getMessage());
			}
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("商品添加失败：根据分销商ID获取登陆信息失败！");
		}
		addReturn(req,resp);
		return resp;
	}

	/**
	 * 开发活动修改方法
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "修改活动", summary = "修改活动")
	public PromotionActEditResp editPromotionActivity(PromotionActEditReq req) {
		PromotionActivity activity = req.getActivity();
		String[] tagids = req.getTagids();
		promotionActivityManager.edit(activity, tagids);
		PromotionActEditResp resp = new PromotionActEditResp();
		return resp ;
	}
	/**
	 * 开发活动增加方法
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新增活动", summary = "新增活动")
	public PromotionActAddResp addPromotionActivity(PromotionActAddReq req) {
		PromotionActivity activity = req.getActivity();
		String[] tagids = req.getTagids();
		promotionActivityManager.add(activity, tagids);
		PromotionActAddResp resp = new PromotionActAddResp();
		return resp ;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "修改商品", summary = "修改商品")
	public GoodsEditResp editGoods(GoodsEditReq req) {
		GoodsEditResp resp = new GoodsEditResp();
//		AdminUser adminUser = ManagerUtils.getAdminUser(); // 获取登录用户
//		if(adminUser == null){
//			resp.setResult(false);
//			resp.setError_code("-1");
//			resp.setError_msg("商品编辑失败：获取登陆用户失败！");
//			addReturn(req,resp);
//			return resp;
//		}
		Goods goods = new Goods();
		GoodsExtData goodsExtData = new GoodsExtData();
		GoodsRules goodsRules = req.getGoodsRules();
		if(goods.getSearch_key()!=null){
			goods.setSearch_key("_"+goods.getName()+"_"+goods.getSearch_key());
		}else{
			goods.setSearch_key("_"+goods.getName()+"_");
		}
		try {
			BeanUtils.copyProperties(goods, req);
			BeanUtils.copyProperties(goodsExtData, req);
			this.goodsManagerN.editGoods(goods, goodsRules, goodsExtData);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("商品编辑失败："+e.getMessage());
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "删除商品", summary = "根据商品ID删除商品")
	public GoodsDeleteResp deleteGoods(GoodsDeleteReq req) {
		String goods_id = req.getGoods_id();
		String[] ids = new String[]{goods_id};
		goodsManager.delete(ids);
		
		GoodsDeleteResp resp = new GoodsDeleteResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品仓库库存", summary = "查询商品仓库库存")
	public GoodsStoreGetResp getGoodsStore(GoodsStoreGetReq req) {
		int stores = goodsManager.getGoodsStore(req.getGoods_id());
		GoodsStoreGetResp resp = new GoodsStoreGetResp();
		resp.setStores(stores);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	public void saveGoodsBus(GoodsBusiness goodsBusiness){
		//设置外系统扩展参数
		//HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		//request.getSession().setAttribute("goodsBusiness", goodsBusiness);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品业务属性列表（订单、支付、受理属性）", summary = "查询商品业务属性列表（订单、支付、受理属性）")
	public AttrDefListResp listAttrDef(AttrDefListReq listReq) {
		String goods_id = listReq.getGoods_id();
		List attrList = goodsManager.listGoodsAttrDef(goods_id);
		
		AttrDefListResp resp = new AttrDefListResp();
		if(attrList!=null || attrList.size()>0){
			resp.setAttrList(attrList);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(listReq,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品详情信息", summary = "展示商品详情页信息")
	public GoodsIntroResp queryGoodsIntro(GoodsIntroReq goodsIntroReq)
			throws Exception {
		String goods_id = goodsIntroReq.getGoods_id();
		Goods goods = goodsManagerN.getGoodsIntros(goods_id);
		GoodsIntroResp resp = new GoodsIntroResp();
		if(null != goods){
			GoodsView goodsView = new GoodsView();
			BeanUtils.copyProperties(goodsView, goods);
			String intro = goods.getIntro();
			intro = UploadUtil.replacePath(intro);
			goods.setIntro(intro);
			goodsView.setIntro(intro);
			resp.setGoods(goods);
			resp.setGoodsView(goodsView);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsIntroReq,resp);			
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");			
		}
		return resp;
	}
	
	@Override
	public GoodsPageListResp queryGoodsForPage(GoodsPageListReq pageQeq) {
		//初始化beans
		init();
		
		GoodsPageListResp resp = new GoodsPageListResp();
		HashMap params = new HashMap();
		params.put("cat_path", pageQeq.getCat_path());  //商品分类1|1111111111|2222222222
		params.put("order", pageQeq.getOrder_by()); //排序
		params.put("brandStr", pageQeq.getBrand()); //品牌ID串,1_2_3
		params.put("propStr", pageQeq.getProp());  //商品属性
		//params.put("keyword", pageQeq.getKeyword()); //关键字，不用
		params.put("minPrice", pageQeq.getMinPrice()); //最小价格
		params.put("maxPrice", pageQeq.getMaxPrice()); //最大价格
		params.put("tagids", pageQeq.getTagids()); //商品标签,格式1,2,3,4
		params.put("agn", pageQeq.getAgn()); //商家
		params.put("attrStr", pageQeq.getAttrStr()); //商品自定义属性
		params.put("typeid", pageQeq.getType_id());//商品类型
		params.put("search_key", pageQeq.getSearch_key());  //搜索关键字
		params.put("has_stock", pageQeq.getHas_stock());
		params.put("user_id", pageQeq.getUser_id());
		params.put("is_sale", pageQeq.getIs_sale());
		
		int page = pageQeq.getPageNo();
		int pageSize = pageQeq.getPageSize();
		Page goodsPage = goodsSearchManager.search(page, pageSize, params);
		
		List<Cat> cats = null;
		if(goodsPage!=null && goodsPage.getResult().size()>0){//获取第一个商品的分类ID
			List goods = goodsPage.getResult();
			for(int i = 0;i<goods.size();i++){
				Goods g = (Goods)goods.get(i);
				PartnerByIdReq req = new PartnerByIdReq();
				req.setPartnerId(g.getCreator_user());
				PartnerByIdResp partnerByIdResp = this.partnerServ.getPartnerById(req);
				if(null != partnerByIdResp.getPartner())
					g.setCreator_user_name(partnerByIdResp.getPartner().getPartner_name());
				
				if(StringUtils.isEmpty(g.getImage_default())){
					g.setImage_default(UploadUtils.replacePath(g.getImage_default()));
		        }
		        if(StringUtils.isEmpty(g.getImage_file())){
		        	g.setImage_file(UploadUtils.replacePath(g.getImage_file()));
		        }
		        if(StringUtils.isEmpty(g.getPc_image_default())){
					g.setPc_image_default(UploadUtils.replacePath(g.getPc_image_default()));
		        }
		        if(StringUtils.isEmpty(g.getPc_image_file())){
		        	g.setPc_image_file(UploadUtils.replacePath(g.getPc_image_file()));
		        }
		        if(StringUtils.isEmpty(g.getMbl_image_default())){
					g.setMbl_image_default(UploadUtils.replacePath(g.getMbl_image_default()));
		        }
		        if(StringUtils.isEmpty(g.getMbl_image_file())){
		        	g.setMbl_image_file(UploadUtils.replacePath(g.getMbl_image_file()));
		        }
		        if(StringUtils.isEmpty(g.getWx_image_default())){
					g.setWx_image_default(UploadUtils.replacePath(g.getWx_image_default()));
		        }
		        if(StringUtils.isEmpty(g.getWx_image_file())){
		        	g.setWx_image_file(UploadUtils.replacePath(g.getWx_image_file()));
		        }
		        if(StringUtils.isEmpty(g.getOther_image_default())){
					g.setOther_image_default(UploadUtils.replacePath(g.getOther_image_default()));
		        }
		        if(StringUtils.isEmpty(g.getOther_image_file())){
		        	g.setOther_image_file(UploadUtils.replacePath(g.getOther_image_file()));
		        }
			}
			Goods good = (Goods) goods.get(0);
			String cat_id = good.getCat_id();
			String parent_cat_id = goodsCatManager.getParentIdById(cat_id);
			//cats = goodsCatManager.listCatGoodsCount(pageQeq.getAgn(),parent_cat_id);
			String agn = pageQeq.getAgn()==null?good.getStaff_no():pageQeq.getAgn();//agn不是必填，当入参没有传agn时，去第一个商品的staff_no
			cats = goodsCatManager.listCatGoodsCount(agn,parent_cat_id);
			
			for(Cat cat : cats){
				// 获取二级节点
				Term term = new Term();
				term.setCatid(cat.getCat_id());
				term.setKeyword(pageQeq.getSearch_key());
				List goodsList = goodsDataProvider.list(term, 1000);
				cat.setCatGoodsList(goodsList);
			}
		}
		resp.setCats(cats);
		resp.setGoodsPage(goodsPage);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(pageQeq, resp);
		return resp;
	}
	
	
	

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询终端关联计划", summary = "查询终端关联计划")
	public GoodsListResp queryTerminalRelPlan(GoodsTerminalReq goodsTerminalReq)throws Exception {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		listResp.addAll(goodsManager.GoodsRelGoods(goodsTerminalReq.getGoods_id(),"TERMINAL_PLAN"));
		if(null != listResp && listResp.size()>0){
			goodsPage.setData(listResp);
			resp.setGoodsPage(goodsPage);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
					
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");			
		}
		addReturn(goodsTerminalReq, resp);	
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询计划关联套餐", summary = "查询计划关联套餐")
	public GoodsListResp queryPlanRelOffer(GoodsPlanRelReq goodsPlanRelReq)throws Exception {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		List<GoodsRelAttConvert> list= this.goodsConManager.getSetContract(goodsPlanRelReq.getA_goods_id(),goodsPlanRelReq.getZ_goods_id());
		if(list!=null && list.size()>0){
			for(GoodsRelAttConvert grac : list){
				Goods g = new Goods();
				g.setGoods_id(grac.getGoods_id());
				g.setName(grac.getGoods_name());
				listResp.add(g);
			}
		}
		if(null != listResp && listResp.size()>0){
			goodsPage.setData(listResp);
			resp.setGoodsPage(goodsPage);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsPlanRelReq, resp);			
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");			
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询套餐关联合约", summary = "查询套餐关联合约")
	public GoodsListResp queryOfferRelContract(GoodsOfferRelContractReq goodsOfferRelContractReq)
			throws Exception {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		goodsPage.setData(listResp);
		listResp.addAll(goodsManager.GoodsRelGoods(goodsOfferRelContractReq.getGoods_id(),"CONTRACT_OFFER"));
		if(null!=listResp && listResp.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsOfferRelContractReq, resp);
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品配件", summary = "查询商品配件")
	public GoodsListResp queryGoodsRelAdjunct(GoodsRelAdjunctReq goodsRelAdjunctReq)
			throws Exception {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		goodsPage.setData(listResp);
		List list = read.getCachesGoodsAdjunct(goodsRelAdjunctReq.getGoods_id());
		if(null!=list && list.size()>0){
			listResp.addAll(list);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsRelAdjunctReq, resp);
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}		
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品详情", summary = "查询商品详情（商品详情信息，评论信息，咨询信息）")
	public GoodsQueryResp queryGoodsInfo(GoodsQueryInfoReq goodsQueryInfoReq)
			throws Exception {
		GoodsNetCacheRead read = new GoodsNetCacheRead();
		Goods goods = read.getCachesGoods(goodsQueryInfoReq.getGoods_id());
		GoodsQueryResp resp = new GoodsQueryResp();
		resp.setGoods(goods);
		//评论信息
		Page commentsPage  = commentsManager.pageComments_Display(Integer.valueOf(goodsQueryInfoReq.getCommentPageIndex()), Integer.valueOf(goodsQueryInfoReq.getCommentPageSize()), Long.valueOf(goodsQueryInfoReq.getGoods_id()), "shop", "leavewords");
		resp.setCommentPage(commentsPage);
		//咨询信息
		Page discussPage  = commentsManager.pageComments_Display(Integer.valueOf(goodsQueryInfoReq.getDiscussPageIndex()), Integer.valueOf(goodsQueryInfoReq.getDiscussPageSize()), Long.valueOf(goodsQueryInfoReq.getGoods_id()), "wssdetails", "discuss");
		resp.setDiscussPage(discussPage);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(goodsQueryInfoReq,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品受理详情", summary = "查询商品受理详情（商品名称、商品价格、商品规格、商品规格属性、商品温馨提醒、商品库存、商品已售卖量）")
	public GoodsAcceptResq queryGoodsAcceptInfo(GoodsAcceptReq goodsAcceptReq)
			throws Exception {
		GoodsAcceptResq resp = new GoodsAcceptResq();
		Map goodsAttr = this.goodsManager.getGoodsMap(goodsAcceptReq.getGoods_id());
		Map goodsBusiness = this.goodsManager.getGoodsBusinessMap(goodsAcceptReq.getGoods_id());
		if(null != goodsAttr){
			resp.setName((String)goodsAttr.get("name"));
			resp.setPrice(goodsAttr.get("price").toString());
			resp.setStore(goodsAttr.get("store").toString());
			resp.setView_count(goodsAttr.get("view_count").toString());
			if(null != goodsBusiness){
				resp.setReminder(goodsBusiness.get("service_tip").toString());
			}
			List<Attribute> attrList =goodsTypeManager.getAttrListByTypeId(goodsAttr.get("type_id").toString());
			resp.setGoodsAttr(goodsAttr);
			int i=1;
			for(Attribute attr: attrList){
				String value =""+goodsAttr.get("p"+i);
				attr.setValue(value);
				goodsAttr.put("p"+i+"_text", attr.getValStr()); //为商品取出属性的字符值
				i++;
			}
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsAcceptReq,resp);			
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败:返回为空");
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品受理价格", summary = "查询商品受理价格（根据规格id联动展示商品价格）")
	public GoodsAcceptPriceResp getAcceptPrice(GoodsAcceptPriceReq req) {
		String goods_id = req.getGoods_id();
		String product_id = req.getProduct_id();
		String lv_id = req.getLv_id();
		String price = goodsManager.getAcceptPrice(goods_id, product_id, lv_id);
		
		GoodsAcceptPriceResp resp = new GoodsAcceptPriceResp();
		resp.setPrice(price);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询商品绑定商品", summary = "查询商品绑定商品")
	public GoodsListResp queryTerminalRelTerminal(
			GoodsTerminalRelTerminalReq goodsTerminalRelTerminalReq)
			throws Exception {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		goodsPage.setData(listResp);
		List<Goods> list = goodsManager.listGoodsByTerminalRelTerminal(goodsTerminalRelTerminalReq.getGoods_id());
		if(list != null && list.size()>0){
			listResp.addAll(list);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(goodsTerminalRelTerminalReq, resp);			
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败:返回为空");			
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品排行榜", summary = "按【同价位】【通品牌】【同类别】搜索商品列表")
	public GoodsListResp qryGoodsRanking(GoodsRankingReq req) {
		List<Goods> listResp= new ArrayList<Goods>();
		GoodsListResp resp = new GoodsListResp();
		Page goodsPage = new Page();
		resp.setGoodsPage(goodsPage);
		goodsPage.setData(listResp);
		List<Goods> list = goodsManager.qryGoodsRanking(req.getType(),req.getVal(),req.getCount());
		if(list != null && list.size()>0){
			listResp.addAll(list);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			addReturn(req, resp);			
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败:返回为空");			
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品规格列表", summary = "获取商品规格列表，包括颜色，尺寸，容量")
	public GoodsSpecListResp listGoodsSpec(GoodsSpecListReq req) {
		String goods_id = req.getGoods_id();
		List specs = goodsManager.listGoodsSpecs(goods_id);
		
		GoodsSpecListResp resp = new GoodsSpecListResp();
		resp.setSpecs(specs);
		if(specs!=null && specs.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败:返回为空");		
		}
		addReturn(req, resp);	
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品促销优惠", summary = "商品促销优惠")
	public GoodsPromotionGetResp getPromotionByGoodsId(GoodsPromotionGetReq req) {
		String goods_id = req.getGoods_id();
		List<HashMap> pmts = goodsManager.getPromotionByGoodsId(goods_id);
		
		GoodsPromotionGetResp resp = new GoodsPromotionGetResp();
		resp.setPromotions(pmts);
		if(pmts!=null && pmts.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败:返回为空");		
		}
		addReturn(req, resp);	
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品评分", summary = "获取商品评分")
	public ScoreGetResp getGoodsScore(ScoreGetReq req) {
		String goods_id = req.getGoods_id();
		HashMap map = goodsManager.getGoodsScore(goods_id);
		
		ScoreGetResp resp = new ScoreGetResp();
		resp.setComm_num(Integer.valueOf(map.get("comm_num").toString()));
		resp.setScore(Double.valueOf(map.get("score").toString()));
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);	
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品详情", summary = "获取商品详情")
	public GoodsDetailGetResp getGoodsDetail(GoodsDetailGetReq req) {
		GoodsDetailGetResp resp = new GoodsDetailGetResp();
		String goods_id = req.getGoods_id();
		HashMap goods = goodsManager.getGoodsDetail(goods_id);
		resp.setDetail_info(goods.get("intro").toString());
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "判断商品是否开启规格", summary = "判断商品是否开启规格")
	public GoodsSpecCheckResp checkGoodsHasSpec(GoodsSpecCheckReq req) {
		GoodsSpecCheckResp resp = new GoodsSpecCheckResp();
		String goods_id = req.getGoods_id();
		boolean hasSpec = goodsManager.checkGoodsHasSpec(goods_id);
		resp.setHasSpec(hasSpec);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

    @Override
    public ProductDetailsResp getProductByGoodsId(ProductDetailsReq req) {
        ProductDetailsResp resp=new ProductDetailsResp();
        resp.setList(goodsManager.getProductByGoodsId(req.getGoods_id()));
        resp.setResult(true);
        resp.setError_code("0");
        resp.setError_msg("成功");
        addReturn(req,resp);
        return resp;
    }

	@Override
	public GoodsPageListResp queryGoodsByTypeId(GoodsPageListByTypeReq req) {
		String type_id = req.getType_id();
		String price = req.getPrice();
		String sub_stype_id=req.getSub_stype_id();
		String source_from = req.getSource_from() == null ? ManagerUtils.getSourceFrom() : req.getSource_from();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
		Page goods = goodsManager.searchGoodsByType(type_id, price,sub_stype_id, source_from, pageNo, pageSize);
		
		GoodsPageListResp resp = new GoodsPageListResp();
		if(goods==null || goods.getTotalCount()==0){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		resp.setGoodsPage(goods);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GroupBuyGetResp getGroupBuy(GroupBuyGetReq req) {
		String group_id = req.getGroup_id();
		GroupBuy groupBuy = groupBuyManager.get(group_id);
		
		GroupBuyGetResp resp = new GroupBuyGetResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setGroupBuy(groupBuy);
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public GroupBuyEditResp updateGroupBuyDisabled(GroupBuyEditReq req){
		GroupBuyEditResp resp = new GroupBuyEditResp();
		groupBuyManager.editGroupState(req.getGroupBuy().getGroupid(),req.getGroupBuy().getDisabled());
		return resp;
	}

	@Override
	public GoodsComplexListResp listComplex(GoodsComplexListReq req) {
		String goods_id = req.getGoods_id();
		List complex = goodsComplexManager.listAllComplex(goods_id);
		GoodsComplexListResp resp = new GoodsComplexListResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setResult(true);
		resp.setComplex(complex);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GroupBuyEditResp updateGroupByCount(GroupBuyEditReq req){
		GroupBuyEditResp resp = new GroupBuyEditResp();
		groupBuyManager.updateBuyedCount(req.getGroupBuy());
		return resp;
	}
	
	@Override
	public LimitBuyGoodsListResp listLimitBuyGoods(LimitBuyGoodsListReq req) {
		List<Map> goodsList  = limitBuyManager.listEnableGoods();
		LimitBuyGoodsListResp resp = new LimitBuyGoodsListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setGoodsList(goodsList);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GroupBuyResp queryGroupBuy(GroupBuyReq req) {
		GroupBuy groupBuy = groupBuyManager.get(req.getGroupid());
		GroupBuyResp resp = new GroupBuyResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setGroupBuy(groupBuy);
		return resp;
	}
	
	@Override
	public GroupBuyListResp listGroupBuy(GroupBuyListReq req) {
		List list = groupBuyManager.listEnable();
		GroupBuyListResp resp = new GroupBuyListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setList(list);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public LimitBuyListResp listLimitBuy(LimitBuyListReq req) {
		List limitBuyList  = limitBuyManager.listEnable();
		LimitBuyListResp resp = new LimitBuyListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setDatas(limitBuyList);
		addReturn(req,resp);
		return resp;
	}


	@Override
	public LimitBuyResp queryLimitBuy(LimitBuyReq req) {
		LimitBuy limitBuy  = limitBuyManager.get(req.getLimitbuyid());
		LimitBuyResp resp = new LimitBuyResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setLimitBuy(limitBuy);
		return resp;
	}
	
	@Override
	public LimitBuyUpdateResp modLimitBuy(LimitBuyUpdateReq req){
		LimitBuyGoods limitBuyGoods  = req.getLimitBuyGoods();
		limitBuyManager.updateBuyGoods(limitBuyGoods);
		LimitBuyUpdateResp resp = new LimitBuyUpdateResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
	@Override
	public LimitBuyResp updateLimitBuyDisabled(LimitBuyReq req){
		limitBuyManager.editLimitState(req.getLimitbuyid(), req.getDisabled());
		LimitBuyResp resp = new LimitBuyResp();
		return resp;
	}
	
	@Override
	public GoodsPageListResp listGoodsPage(GoodsRelPageListReq req) {
		String rel_type = req.getRel_type();
		String a_good_id = req.getA_goods_id();
		String source_from = req.getSourceFrom() == null ? ManagerUtils.getSourceFrom(): req.getSourceFrom();
        String sub_stype_id=req.getSub_stype_id();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
        Page page=null;
        if(StringUtils.isNotEmpty(sub_stype_id)){
        	if(StringUtils.isNotEmpty(source_from)){
        		page= goodsManager.GoodsRelGoodsPage(pageNo, pageSize, rel_type, a_good_id, source_from, sub_stype_id, source_from);
        	}else{
        		page= goodsManager.GoodsRelGoodsPage(pageNo, pageSize, rel_type, a_good_id, source_from, sub_stype_id,ManagerUtils.getSourceFrom());
        	}
        }else {
        	if(StringUtils.isNotEmpty(source_from)){
        		page= goodsManager.GoodsRelGoodsPage(pageNo, pageSize, a_good_id,rel_type, source_from);
        	}else{
        		page= goodsManager.GoodsRelGoodsPage(pageNo, pageSize, a_good_id,rel_type, ManagerUtils.getSourceFrom());
        	}
        }

		GoodsPageListResp resp = new GoodsPageListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setGoodsPage(page);
		addReturn(req,resp);
		return resp;
	}
    
	
	@Override
	public GoodsPageListResp queryGoodsByIdsAndName(GoodsPageByIdsReq goodsPageByIdsReq){
		String ids = goodsPageByIdsReq.getIds();
		String name = goodsPageByIdsReq.getName();
		int page = goodsPageByIdsReq.getPageNo();
		int pageSize = goodsPageByIdsReq.getPageSize();
		Page webPage = this.goodsManager.queryGoodsByIdsAndName(ids,name,page,pageSize);
		GoodsPageListResp resp = new GoodsPageListResp();
		if(webPage.getResult().size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");		
			resp.setGoodsPage(webPage);
		}else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");			
		}
		addReturn(goodsPageByIdsReq, resp);
		return resp;
	}

	@Override
	public ProductListResp listProductIds(ProductListReq req) {
		String service_id = req.getService_id();
		List datas = this.goodsManager.listProducts(service_id);
		
		ProductListResp resp = new ProductListResp();
		if(datas!=null && datas.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setProducts(datas);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public OfferChangeListResp listOfferChange(OfferChangeListReq req) {
		String goodsType = req.getGoodsType();
		//mod by tong.xin 20140828 查询套餐时增加套餐子类
		String sub_stype_id = req.getSub_stype_id();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
		//Page page = this.goodsManager.listOfferChange(goodsType, pageNo, pageSize);
		//mod by tong.xin 20140828 查询套餐时增加套餐子类
		Page page = this.goodsManager.listOfferChange(goodsType, pageNo, pageSize,sub_stype_id);
		OfferChangeListResp resp = new OfferChangeListResp();
		if(page!=null && page.getTotalCount()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setOfferChanges(page);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public CoModifyStatusResp modifyStatus(CoModifyStatusReq req) {
		
		CoModifyStatusResp resp =  new CoModifyStatusResp();
		this.goodsManager.modifyStatus(req.getId(), req.getService_code(), req.getStatus_new());
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public GoodsComplexListResp listComplexByGoods_id(GoodsComplexListReq req) {
		// TODO Auto-generated method stub
		GoodsComplexListResp resp =  new GoodsComplexListResp();
		List list = this.goodsManager.listAllComplex(req.getGoods_id());
		resp.setComplex(list);
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	public GoodsGetResp getGoods(GoodsGetReq req) {
		String goods_id = req.getGoods_id();
		String package_id = req.getPackage_id();
		String sn = req.getSn();
		Map<String,String> data = this.goodsManager.getGoodsInfo(goods_id,sn,package_id);
		
		GoodsGetResp resp = new GoodsGetResp();
		if(!data.isEmpty()){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setData(data);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品参数信息", summary = "获取商品参数信息")
	public GoodsSpecificationInfoResp getGoodsSpecificationInfo(GoodsSpecificationInfoReq req) {
		GoodsSpecificationInfoResp resp = new GoodsSpecificationInfoResp();
		String goods_id = req.getGoods_id();
		HashMap goods = goodsManager.getGoodsSpecInfo(goods_id);
		resp.setParam(goods.get("params").toString());
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品类别id", summary = "获取商品类别id")
	public GoodsCatPathGetResp getGoodsCatPath(GoodsCatPathGetReq req) {
		GoodsCatPathGetResp resp = new GoodsCatPathGetResp();
		String goods_id = req.getGoods_id();
		HashMap goods = goodsManager.getGoodsCatPath(goods_id);
		resp.setCatPath(goods.get("cat_path").toString());
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品类别id", summary = "获取商品类别id")
	public GoodsColConfigResp getcolConfig(GoodsColConfigReq req){
		GoodsColConfigResp resp = new GoodsColConfigResp();
		String cat_id = req.getCat_id();
		List<GoodsSpecificationModel> colConfig = goodsManager.getcolConfig(cat_id);
		resp.setConfigData(colConfig);
//		resp.setColumn_name(colConfig.get("column_name").toString());
//		resp.setColumn_desc(colConfig.get("column_desc").toString());
//		resp.setRank(colConfig.get("rank").toString());
//		resp.setType(colConfig.get("type").toString());
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
		//select t.column_name,t.column_desc,t.rank,t.type from es_tab_col_config t where t.goods_cat = ? order by rank  
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "广东联通：查询商品货品", summary = "广东联通：查询商品货品")
	public EcsGoodsPageListResp queryGoodsForPageEcs(EcsGoodsPageListReq req) {
		String name = req.getName();
		String cat_id = req.getCat_id();
		String brand_id = req.getBrand_id();
		String type = req.getType();
		String org_ids = req.getOrg_ids();
		int market_enable = req.getMarket_enable();
		int pageNo = req.getPageNo();
		int pageSize = req.getPageSize();
		
		Map params = new HashMap();
		params.put("name", name);
		params.put("cat_id", cat_id);
		params.put("brand_id", brand_id);
		params.put("type", type);
		params.put("market_enable", market_enable);
		params.put("org_ids", org_ids);
		
		Page page = null;
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(type)){
			page = goodsManager.searchGoodsECS(params, pageNo,pageSize);
		}
		else if(Consts.ECS_QUERY_TYPE_PRODUCT.equals(type)){
			
			page = goodsManager.searchProductsECS(params, pageNo,pageSize);
		}
		
		EcsGoodsPageListResp resp = new EcsGoodsPageListResp();
		if(page!=null && page.getTotalCount()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setGoodsPage(page);
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GoodsInfoGetResp getGoodsInfo(GoodsInfoGetReq req) {
		String goods_id = req.getGoods_id();
		Goods goods = read.getCachesGoods(goods_id);
		
		
				
		GoodsInfoGetResp resp = new GoodsInfoGetResp();
		if(goods==null){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setGoods(goods);
			//add bywui
			List<Attribute> attrs = goodsSearchManager.getpPropInstsByGoods(goods);
			resp.setAttrs(attrs);
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ProductInfoGetResp getProductInfo(ProductInfoGetReq req) {
		String product_id = req.getProduct_id();
		Goods product = read.getCachesProducts(product_id);
		ProductInfoGetResp resp = new ProductInfoGetResp();
		if(product==null){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setProduct(product);
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ProductsListResp listProducts(ProductsListReq req) {
		String goods_id = req.getGoods_id();
//		List<Goods> products = read.getGoodsRelProducts(goods_id);
//		if(products==null || products.size()==0){
//			products = goodsManager.listGoodsRelProducts(goods_id);
//			write.write("CACHE_A_GOODS_ID_", goods_id, products);
//		}
		List<Goods> products = goodsManager.listGoodsRelProducts(goods_id);
		ProductsListResp resp = new ProductsListResp();
		if(products!=null && products.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setProducts(products);
		}
		else{
			products = goodsManager.listGoodsRelProducts(goods_id);
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(req,resp);
		return resp;
	}
	
	
	@Override
	public GoodsByIdsResp qryGoodsByIds(GoodsByIdsReq goodsByIdsReq){
		
		Goods goods = goodsManager.qryGoodsByIds(goodsByIdsReq.getGoods_id(),goodsByIdsReq.getProduct_id()
				,goodsByIdsReq.getSource_from());
		
		GoodsByIdsResp goodsByIdsResp = new GoodsByIdsResp();
		if(goods!=null){
			goodsByIdsResp.setResult(true);
			goodsByIdsResp.setError_code("0");
			goodsByIdsResp.setError_msg("成功");
			goodsByIdsResp.setGoods(goods);
		}
		else{
			goodsByIdsResp.setResult(false);
			goodsByIdsResp.setError_code("-1");
			goodsByIdsResp.setError_msg("失败：返回为空");
		}
		addReturn(goodsByIdsReq, goodsByIdsResp);
		return goodsByIdsResp;
	}

	@Override
	public GoodsImportResp importGoods(GoodsImportReq req){
		IGoodsImportHandler goodsImportHandler = SpringContextHolder.getBean("goodsImportHandler");
		boolean result = goodsImportHandler.importGoods();
		
		GoodsImportResp resp = new GoodsImportResp();
		if(result){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public ActivityImportResp importActivity(ActivityImportReq req){
		boolean result = promotionActivityManager.importActivity();
		
		ActivityImportResp resp = new ActivityImportResp();
		if(result){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public TerminalImportResp importTerminal(TerminalImportReq req) {
		ITerminalImportHandler terminalImportHandler = SpringContextHolder.getBean("terminalImportHandler");
		boolean result = false;
		try{
			result = terminalImportHandler.importTerminals();
		}catch(Exception e){
			e.printStackTrace();
			result = false;
		}
		TerminalImportResp resp = new TerminalImportResp();
		if(result){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		addReturn(req,resp);
		return resp;
	}
	
	@Override
	public GoodsByCrmOfferIdResp qryGoodsByCrmOfferId(GoodsByCrmOfferIdReq goodsByCrmOfferIdReq){
		
		Goods goods = goodsManager.qryGoodsByCrmOfferId(goodsByCrmOfferIdReq.getCrm_offer_id(),goodsByCrmOfferIdReq.getSource_from());
		
		GoodsByCrmOfferIdResp goodsByCrmOfferIdResp = new GoodsByCrmOfferIdResp();
		if(goods!=null){
			goodsByCrmOfferIdResp.setResult(true);
			goodsByCrmOfferIdResp.setError_code("0");
			goodsByCrmOfferIdResp.setError_msg("成功");
			goodsByCrmOfferIdResp.setGoods(goods);
		}
		else{
			goodsByCrmOfferIdResp.setResult(false);
			goodsByCrmOfferIdResp.setError_code("-1");
			goodsByCrmOfferIdResp.setError_msg("失败：返回为空");
		}
		addReturn(goodsByCrmOfferIdReq, goodsByCrmOfferIdResp);
		return goodsByCrmOfferIdResp;
	}

	@Override
	public GoodsSpecResp getGoodSpec(GoodsSpecReq req) {
		String goods_id = req.getGoods_id();
		Map specs = goodsManager.getGoodSpec(goods_id);
		
		GoodsSpecResp resp = new GoodsSpecResp();
		if(specs ==null || specs.isEmpty()){
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setSpecs(specs);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req, resp);
		return resp;
	}
	
	@Override
	public ProductSpecResp getProductSpec(ProductSpecReq req) {
		Goods product = req.getProduct();
		Map productSpec = goodsManager.getProductSpec(product);
		ProductSpecResp resp = new ProductSpecResp();
		if(productSpec.isEmpty()){
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setProductSpecMap(productSpec);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		return resp;
	}

	@Override
	public GoodsTypeIdGetResp getGoodsTypeId(GoodsTypeIdGetReq req) {
		String goods_id = req.getGoods_id();
		String type_id = goodsManager.getGoodsTypeId(goods_id);
		GoodsTypeIdGetResp resp = new GoodsTypeIdGetResp();
		resp.setType_id(type_id);
		if(StringUtils.isEmpty(type_id)){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public PCodeGetResp getPCode(PCodeGetReq req) {
		String goods_id = req.getGoods_id();
		String p_code = goodsManager.getPCode(goods_id);
		
		PCodeGetResp resp = new PCodeGetResp();
		resp.setP_code(p_code);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	@Override
	public ProductInfoByNameResp queryProductInfoByName(ProductInfoByNameReq req) {
		String product_name = req.getProduct_name();
		Map map = read.getProducts(product_name);
		if(map==null || map.isEmpty()){
			map = goodsManager.getProductInfoByName(product_name);
		}
		ProductInfoByNameResp resp = new ProductInfoByNameResp();
		if(map ==null || map.isEmpty()){
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			resp.setData(map);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	public CacheGoodsDataResp cacheGoodsData(CacheGoodsDataReq req) {
		String goods_id = req.getGoods_id();
		Goods goods = goodsManager.getGoodsForCache(goods_id);
		write.loadGood(goods);
		List<Goods> products = goodsManager.listGoodsRelProducts(goods_id);
		write.write("CACHE_A_GOODS_ID_", goods_id, products);
		CacheGoodsDataResp resp = new CacheGoodsDataResp();
		return resp;
	}
	
	@Override
	public TerminalNumResp getTerminalNumBySN(TerminalNumReq req) {
		TerminalNumResp resp = new TerminalNumResp();
		String sn = req.getSn();
		String terminalNum = goodsManager.getTerminalNumBySN(sn);
		resp.setTerminal_no(terminalNum);
		return resp;
	}
	
	@Override
	public SkuQueryResp getGiftByActivityCode(SkuQueryReq req){
		String pmtCode = req.getActivityCode();
		SkuQueryResp resp = new SkuQueryResp();
		List<Map<String,String>> goodsList = goodsManager.listSkuByActivityCode(pmtCode);
		StringBuffer result = new StringBuffer();
		result.append("[");
		if(goodsList.size()==0){
			result.append("]");
			resp.setBody(result.toString());
		}else{
			for(Map<String,String> goods: goodsList){
				result.append("{\"gift_id\":\"");
				result.append(goods.get("sku"));
				result.append("\",\"gift_num\":\"1\",\"is_process\":\"0\",\"process_type\":\"\"},");
			}
			String reGiftStr = result.substring(0, result.length()-1)+"]";
			
			logger.info(reGiftStr);
			resp.setBody(reGiftStr);
		}
		
		return resp;
	}

	@Override
	public ProxyGoodsAddResp addProxyGoods(ProxyGoodsAddReq req) {
		Map<String, String> proxy = new HashMap<String, String>();
		ProxyGoodsAddResp resp = new ProxyGoodsAddResp();
		try {
			proxy.put("userid", req.getUserid());
			proxy.put("p_goods_id", req.getP_goods_id());
			proxy.put("state", "00A");
			goodsManagerN.addProxyGoods(proxy);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(true);
			resp.setError_code("-1");
			resp.setError_msg("添加代理商品失败："+e.getMessage());
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ProxyGoodsDeleteResp deleteProxyGoods(ProxyGoodsDeleteReq req) {
		ProxyGoodsDeleteResp resp = new ProxyGoodsDeleteResp();
		Map<String, String> proxy = new HashMap<String, String>();
		try {
			proxy.put("userid", req.getUserid());
			proxy.put("p_goods_id", req.getP_goods_id());
			goodsManagerN.deleteProxyGoods(proxy);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(true);
			resp.setError_code("-1");
			resp.setError_msg("添加代理商品失败："+e.getMessage());
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GoodsCopyResp copyGoods(GoodsCopyReq req) {
		String goods_id = req.getGoods_id();
		String user_id = req.getUser_id();
		AdminUser adminUser = goodsManagerN.getUserInfo(user_id);
		String founder = adminUser.getFounder()==null?"":adminUser.getFounder().toString();
		String parent_user_id = adminUser.getParuserid()==null?null : adminUser.getParuserid().toString();
		goodsManagerN.copyGoods(goods_id, user_id, founder, parent_user_id);
		
		GoodsCopyResp resp = new GoodsCopyResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	public GoodsListByCatIdResp listCatGoods(GoodsListByCatIdReq req) {
		String cat_id = req.getCat_id();
		List<Goods> goodsList = read.getCacheCatGoods(cat_id);
		if(goodsList.size()==0){
			goodsList = goodsManagerN.listGoodsByCatId(cat_id);
			write.write("CAT_GOODS_LIST_", cat_id, goodsList);
		}
		GoodsListByCatIdResp resp = new GoodsListByCatIdResp();
		if(goodsList==null || goodsList.size()==0){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
//			List<Goods>goodsViews = new ArrayList<Goods>();
//			for (Goods goods : goodsList) {
//				GoodsView goodsView = new GoodsView();
//				try {
//					BeanUtils.copyProperties(goodsView, goods);
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//				goodsViews.add(goodsView);
//			}
			
			resp.setGoodsList(goodsList);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询分类的所有商品", summary = "查询分类的所有商品")
	public GoodsListByCatIdResp listCatGoodsView(GoodsViewListByCatIdReq req) {
		String cat_id = req.getCat_id();
		String user_id = req.getUser_id();
		List<Goods> goodsList = new ArrayList<Goods>();
		if(goodsList.size()==0){
			goodsList = goodsManagerN.listGoodsByCatId(cat_id);
			write.write("CAT_GOODS_LIST_", cat_id, goodsList);
		}
		GoodsListByCatIdResp resp = new GoodsListByCatIdResp();
		if(goodsList==null || goodsList.size()==0){
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		else{
			List<GoodsView>goodsViews = new ArrayList<GoodsView>();
			for (Goods goods : goodsList) {
				if(user_id.equals(goods.getCreator_user())){
					GoodsView goodsView = new GoodsView();
					try {
						BeanUtils.copyProperties(goodsView, goods);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					goodsViews.add(goodsView);
				}
			}
			resp.setGoodsViewList(goodsViews);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		addReturn(req,resp);
		return resp;
	}

	@Override
	public ProxyGoodsPageQueryResp queryProxyGoodsForPage(
			ProxyGoodsPageQueryReq req) {
		ProxyGoodsPageQueryResp resp = new ProxyGoodsPageQueryResp();
		HashMap params = new HashMap();
		params.put("search_key", req.getSearch_key());  //搜索关键字
		params.put("user_id", req.getUserid());
		int page = req.getPageNo();
		int pageSize = req.getPageSize();
		Page goodsPage = goodsSearchManager.searchProxyGoods(page, pageSize, params);
		
		if(goodsPage!=null && goodsPage.getResult().size()>0){//获取第一个商品的分类ID
			List goodsList = goodsPage.getResult();
			for(int i=0;i<goodsList.size();i++){
				Goods good = (Goods) goodsList.get(i);
				if(StringUtils.isEmpty(good.getImage_default())){
					good.setImage_default(UploadUtils.replacePath(good.getImage_default()));
		        }
		        if(StringUtils.isEmpty(good.getImage_file())){
		        	good.setImage_file(UploadUtils.replacePath(good.getImage_file()));
		        }
		        if(StringUtils.isEmpty(good.getPc_image_default())){
					good.setPc_image_default(UploadUtils.replacePath(good.getPc_image_default()));
		        }
		        if(StringUtils.isEmpty(good.getPc_image_file())){
		        	good.setPc_image_file(UploadUtils.replacePath(good.getPc_image_file()));
		        }
		        if(StringUtils.isEmpty(good.getMbl_image_default())){
					good.setMbl_image_default(UploadUtils.replacePath(good.getMbl_image_default()));
		        }
		        if(StringUtils.isEmpty(good.getMbl_image_file())){
		        	good.setMbl_image_file(UploadUtils.replacePath(good.getMbl_image_file()));
		        }
		        if(StringUtils.isEmpty(good.getWx_image_default())){
					good.setWx_image_default(UploadUtils.replacePath(good.getWx_image_default()));
		        }
		        if(StringUtils.isEmpty(good.getWx_image_file())){
		        	good.setWx_image_file(UploadUtils.replacePath(good.getWx_image_file()));
		        }
		        if(StringUtils.isEmpty(good.getOther_image_default())){
					good.setOther_image_default(UploadUtils.replacePath(good.getOther_image_default()));
		        }
		        if(StringUtils.isEmpty(good.getOther_image_file())){
		        	good.setOther_image_file(UploadUtils.replacePath(good.getOther_image_file()));
		        }
			}
		}
		resp.setGoodsPage(goodsPage);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "录入红包", summary = "录入红包", isOpen = false)
	public CreateRedPkgResp createRedPkg(CreateRedPkgReq req) {
		CreateRedPkgResp createRedPkgResp = new CreateRedPkgResp();
		PromotionRedPkg promotionRedPkg = new PromotionRedPkg();
		promotionRedPkg.setName(req.getName());
		promotionRedPkg.setType(req.getType());
		promotionRedPkg.setNum(req.getNum());
		promotionRedPkg.setAmount(req.getAmount());
		promotionRedPkg.setMemo(req.getMemo());
		String red_id = promotionRedManager.addred(promotionRedPkg);
		createRedPkgResp.setRed_id(red_id);
		createRedPkgResp.setError_code("0");
		createRedPkgResp.setError_msg("成功");
		return createRedPkgResp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "抢红包", summary = "抢红包", isOpen = false)
	public GrabRedPkgResp grabRedPkg(GrabRedPkgReq req) {
		GrabRedPkgResp grabRedPkgResp = new GrabRedPkgResp();
		promotionRedManager.grabredpkg(req.getRed_id(), req.getMember_id(), grabRedPkgResp);
		return grabRedPkgResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "可使用优惠券红包列表", summary = "可使用优惠券红包列表", isOpen = false)
	public RedPkgListResp redPkgList(RedPkgListReq req) {
		RedPkgListResp resp = new RedPkgListResp();
		List<PromotionRedPkgDetail> redCpnsList = promotionRedManager.redCpnsList(req.getRed_id());
		resp.setRedCpnsList(redCpnsList);
		resp.setError_msg("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "编辑红包", summary = "编辑红包", isOpen = false)
	public RedPkgEditResp redPkgEdit(RedPkgEditReq req) {
		RedPkgEditResp resp = new RedPkgEditResp();
		PromotionRedPkg promotionRedPkg = promotionRedManager.rededit(req.getRed_id(), req.getName(), req.getMemo());
		resp.setId(promotionRedPkg.getId());
		resp.setName(promotionRedPkg.getName());
		resp.setType(promotionRedPkg.getType());
		resp.setNum(promotionRedPkg.getNum());
		resp.setMemo(promotionRedPkg.getMemo());
		resp.setUsednum(promotionRedPkg.getUsednum());
		resp.setCreate_time(promotionRedPkg.getCreate_time());
		resp.setSource_from(promotionRedPkg.getSource_from());
		resp.setAmount(promotionRedPkg.getAmount());
		resp.setError_msg("0");
		resp.setError_msg("成功");
		return resp;
	}
	@Override
	public ProductPageQueryResp getProductPageQuery(ProductPageQueryReq req) {
		// TODO Auto-generated method stub
		ProductPageQueryResp resp = new ProductPageQueryResp();
		Page pg = goodsManager.getProductPageQuery(req.getProduct_id(), req.getGoods_id(), req.getName(), req.getPageNo(), req.getPageSize());
		resp.setProductPage(pg);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	public IPromotionRedManager getPromotionRedManager() {
		return promotionRedManager;
	}

	public void setPromotionRedManager(IPromotionRedManager promotionRedManager) {
		this.promotionRedManager = promotionRedManager;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新商品库存", summary = "刷新商品库存")
	public RefreshGoodsCacheResp refreshGoodsCache(RefreshGoodsCacheReq req) {
		GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
		goodsRefreshDTO.setGoods_ids(req.getGoodsIds());
		goodsManager.initGoodsCacheByCondition(goodsRefreshDTO);
		RefreshGoodsCacheResp resp = new RefreshGoodsCacheResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setResp_code("0");
		resp.setResp_msg(req.getGoodsIds()+"商品刷新成功!");
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存")
	public SubtractInventoryNumResp subtractInventoryNum(SubtractInventoryNumReq req) {
		SubtractInventoryNumResp resp = warehouseManager.subtractInventoryNum(req);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存")
	public IncreaseInventoryNumResp increaseInventoryNum(IncreaseInventoryNumReq req) {
		IncreaseInventoryNumResp resp = warehouseManager.increaseInventoryNum(req);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取商品参与的活动", summary = "获取商品参与的活动")
	public ActivityListGetResp getGoodsJoinActivities(ActivityListGetReq req){
		List<Map> actList = this.goodsManager.getGoodsJoinActivities(req.getGoodsId(), req.getOrderCity(), req.getOrderTime(), req.getUserType());
		ActivityListGetResp resp = new ActivityListGetResp();
		resp.setActList(actList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据商品名称获取商品编码", summary = "根据商品名称获取商品编码")
	public StdGoodsGetResp getStdGoodsByName(StdGoodsGetReq req) {
		Map goods = this.goodsManager.getStdGoodsByName(req.getGoodsName(),req.getTitleName());
		StdGoodsGetResp resp = new StdGoodsGetResp();
		if(goods!=null && goods.size()>0){
			resp.setGoodsName(Const.getStrValue(goods, "goods_name"));
			resp.setGoodsId(Const.getStrValue(goods, "goods_id"));
			resp.setpCode(Const.getStrValue(goods, "p_code"));
			resp.setSn(Const.getStrValue(goods, "sn"));
			resp.setGoodsType(Const.getStrValue(goods, "goods_type"));
			resp.setLaterActFlag(Const.getStrValue(goods, "later_act_flag"));
			resp.setNumType(Const.getStrValue(goods, "num_ytpe"));
			resp.setProductBrand(Const.getStrValue(goods, "product_brand"));
			resp.setReliefPresFlag(Const.getStrValue(goods, "relief_pres_flag"));
			resp.setSerType(Const.getStrValue(goods, "ser_type"));
			resp.setContractMonth(Const.getStrValue(goods, "contract_month"));
			resp.setActivityType(Const.getStrValue(goods, "activity_type"));
			resp.setBrandCode(Const.getStrValue(goods, "brand_code"));
			resp.setModelCode(Const.getStrValue(goods, "model_code"));
			resp.setColorCode(Const.getStrValue(goods, "color_code"));
			
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		return resp;
	}

	@Override
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoods(BroadbandGoodsQryReq req) {
		List<Map> goodsList = this.goodsManager.qryAdmissibleBroadbandGoods(req.getCommunityCode(),req.getType());
		BroadbandGoodsQryResp resp = new BroadbandGoodsQryResp();
		resp.setProduct_list(goodsList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
	@Override
	public BroadbandGoodsQryResp qryAdmissibleBroadbandGoodsByCityOrCounty(BroadbandGoodsQryReq req) {
		List<Map> goodsList = this.goodsManager.qryAdmissibleBroadbandGoodsByCityOrCounty(req.getCommunityCode(),req.getType());
		BroadbandGoodsQryResp resp = new BroadbandGoodsQryResp();
		resp.setProduct_list(goodsList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	@Override
	public ZteResponse goodsBroadbandAdd(GoodsBroadbandAddReq req){
		ZteResponse resp = new ZteResponse();
		Goods goods = new Goods();
		goods.setType_id(req.getType_id());
		goods.setCat_id(req.getCat_id());
		goods.setType(req.getType());
		goods.setBrand_id(req.getBrand_id());
		goods.setModel_code(req.getModel_code());
		goods.setName(req.getGoods_name());
		goods.setParams(req.getGoods_params());		
		goods.setSn(goodsManager.createSN("", "", ""));
		this.goodsManager.saveBraoadband(goods, null, null,null);
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("成功");
		return resp;
	}
}
