package com.ztesoft.net.server;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zte.net.iservice.IGoodsService;
import zte.params.goods.req.PromotionActAddReq;
import zte.params.order.req.PromotionAddReq;
import zte.params.order.resp.PromotionAddResp;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.DiscountPrice;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.orderstd.service.IPromotionSManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-24 16:44
 * To change this template use File | Settings | File Templates.
 */
public class PromotionSServ {
    @Resource
    private IPromotionSManager promotionSManager;
	@Resource
	private IGoodsService goodServices;

    public String add(Promotion promotion, Integer[] memberLvIdArray, String[] goodsidArray) {
        return this.promotionSManager.add(promotion,memberLvIdArray,goodsidArray);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public String add(Promotion promotion, Integer[] memberLvIdArray, int type, String[] goodsidArray, Integer[] goodsCatIdArray, Integer[] tagIdArray) {
        return this.promotionSManager.add(promotion,memberLvIdArray,type,goodsidArray,goodsCatIdArray,tagIdArray);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List<Promotion> list(String goodsid, String memberLvId,Coupons coupon, String... others) {
        return this.promotionSManager.list(goodsid,memberLvId,coupon,others);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List<Promotion> list(Double orderPrice, String memberLvId,Coupons coupon, String... others) {
        return this.promotionSManager.list(orderPrice,memberLvId,coupon,others);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public void applyGoodsPmt(List<CartItem> list, String memberLvId,Coupons coupon, String... others) {
      this.promotionSManager.applyGoodsPmt(list,memberLvId,coupon,others);
    }

    
    public DiscountPrice applyOrderPmt(Member member,Double orderPrice, Double shipFee, Integer point, String memberLvId,Coupons coupon, String... others) {
        return this.promotionSManager.applyOrderPmt(member,orderPrice, shipFee, point, memberLvId,coupon, others);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public void applyOrderPmt(String orderId, Double orderPrice, String memberLvId,Coupons coupon, String... others) {
        this.promotionSManager.applyOrderPmt(orderId,orderPrice,memberLvId,coupon,others);
    }

    
    public List listGift(List<Promotion> pmtList) {
        return this.promotionSManager.listGift(pmtList);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List listPmtPlugins() {
        return this.promotionSManager.listPmtPlugins();  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public IPromotionPlugin getPlugin(String pluginid) {
        return this.promotionSManager.getPlugin(pluginid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List listByActivityId(String activityid) {
        return this.promotionSManager.listByActivityId(activityid);  //To change body of implemented methods use File | Settings | File Templates.
    }

//    
//    public void delete(Integer[] pmtidArray) {
//        this.promotionSManager.delete(pmtidArray);//To change body of implemented methods use File | Settings | File Templates.
//    }
    
    public void delete(String pmtidStr){
    	this.promotionSManager.delete(pmtidStr);
    }
    
    public Promotion get(String pmtid) {
        return this.promotionSManager.get(pmtid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List listMemberLvId(String pmtid) {
        return this.promotionSManager.listMemberLvId(pmtid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List listGoodsId(String pmtid) {
        return this.promotionSManager.listGoodsId(pmtid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public void edit(Promotion promotion, Integer[] memberLvIdArray, String[] goodsidArray) {
        this.promotionSManager.edit(promotion,memberLvIdArray,goodsidArray); //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public Coupons useCoupon(String code, String memberId) {
        return this.promotionSManager.useCoupon(code,memberId);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public List<Map> listOrderPmt(String orderid) {
        return this.promotionSManager.listOrderPmt(orderid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    
    public boolean hasOrderPmt() {
        return this.promotionSManager.hasOrderPmt();  //To change body of implemented methods use File | Settings | File Templates.
    }

	public IPromotionSManager getpromotionSManager() {
		return promotionSManager;
	}

	public void setpromotionSManager(IPromotionSManager promotionSManager) {
		this.promotionSManager = promotionSManager;
	}
    
	public Coupons useCoupons(String coupon_code,String member_id){
		return promotionSManager.useCoupon(coupon_code, member_id);
	}

	
	public PromotionAddResp add(PromotionAddReq req) {
		PromotionActivity activity = new PromotionActivity();
		
		String userid = null;
		if(null == req.getUserId()){
			AdminUser au = ManagerUtils.getAdminUser();
			userid = au.getUserid();
		}else{
			userid = req.getUserId();
		}
		
		activity.setUserid(userid);
		activity.setName(req.getName());
		activity.setAtturl(req.getImageUrl());
		activity.setBegin_time(req.getBegin_time());
		activity.setEnd_time(req.getEnd_time());
		activity.setBrief(req.getDescribe());//规则描述
		activity.setEnable(1);//1:有效；0：无效
		activity.setPmt_code(String.valueOf(System.currentTimeMillis()));//活动编码
		PromotionActAddReq promotionActAddReq = new PromotionActAddReq();
		promotionActAddReq.setActivity(activity);
		goodServices.addPromotionActivity(promotionActAddReq);
		String id = activity.getId();
		String promotion_type = req.getPromotion_type();
		String pluginid = "goodsDiscountPlugin";
		if("001".equals(promotion_type) || "006".equals(promotion_type) || "007".equals(promotion_type)){
			pluginid = "goodsDiscountPlugin";
		}else if("002".equals(promotion_type) ){
			pluginid = "goodsTimesPointPlugin";
		}else if("003".equals(promotion_type) ){
			pluginid = "enoughPriceGiveGiftPlugin";
		}else if("004".equals(promotion_type) ){
			pluginid = "enoughPriceReducePrice";
		}else if("005".equals(promotion_type) ){
			pluginid = "enoughPriceFreeDeliveryPlugin";
		}
		
//		IPromotionPlugin plugin = this.promotionSManager.getPlugin(pluginid);
//		PromotionConditions conditions = new PromotionConditions( plugin.getConditions());
		
		Promotion promotion = new Promotion();
		
		promotion.setPmta_id(id);
		promotion.setOrder_money_from(0.0);
		promotion.setOrder_money_to(0.0);
		promotion.setPmt_time_begin(req.getBegin_time());
		promotion.setPmt_time_end(req.getEnd_time());
		promotion.setPmt_ifcoupon(Integer.parseInt(req.getIfcoupon()));
		promotion.setPmt_describe(req.getDescribe());
		promotion.setPmt_type(req.getPromotion_type()); // promotion.setPmta_id(this.activityid); //促销活动id
		promotion.setPmts_id(pluginid);
		promotion.setPromotion_type(promotion_type);
		promotion.setPmt_solution(req.getDiscount());
		promotion.setIf_limit(req.getIf_limit()); //是否限购
		promotion.setLimit_num(req.getLimit_num());//限购数量
		
		String [] lvidArrayStr = req.getLvIds().split(",");
		Integer [] lvidArray = new Integer[lvidArrayStr.length];
		for(int i=0;i<lvidArrayStr.length;i++){
			String lvid = lvidArrayStr[i];
			lvidArray[i] = Integer.parseInt(lvid);		
		}
		String [] goodsidArray = req.getGoodsIds().split(",");
		String pmtid = promotionSManager.add(promotion, lvidArray, goodsidArray);
		PromotionAddResp resp = new PromotionAddResp();
		resp.setPmtid(pmtid);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}


}
