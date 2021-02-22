package com.ztesoft.net.mall.core.service.impl.promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.service.IFreeOfferManager;
import com.ztesoft.net.mall.core.service.IProductManager;
import com.ztesoft.net.mall.core.service.promotion.IGiveGiftBehavior;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.sqls.SF;
/**
 * 优惠方式--送赠品
 * @author kingapex
 *2010-4-15下午04:59:08
 */
public class GiveGiftMethod extends BaseSupport<FreeOffer> implements IPromotionMethod, IGiveGiftBehavior {
	
	private IFreeOfferManager freeOfferManager;
	@Resource
	private IProductManager productManager;
	
	@Override
	public String getInputHtml(String pmtid, String solution) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		if(solution!=null){
			Object[] giftIdArray= JSON.parseArray(solution, Object.class).toArray();
			if(giftIdArray!=null){
				Long[] giftIds = new Long[giftIdArray.length];
				int i=0;
				for(Object giftId: giftIdArray){
					giftIds[i] =  Long.valueOf(giftId.toString());
					i++;
				}
				List giftList  = freeOfferManager.list(giftIds);
				freeMarkerPaser.putData("giftList", giftList);
			}
				
		}

		return freeMarkerPaser.proessPageContent();
	}

	
	@Override
	public String getName() {
		
		return "giveGift";
	}

	
	@Override
	public String onPromotionSave(String pmtid) {
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String[] giveGift = request.getParameterValues("giftidArray");
		if(giveGift==null){
            throw new  IllegalArgumentException("失败，请添加赠品！");
        }
		return JSON.toJSONString(giveGift);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void giveGift(Promotion promotion,String orderId,String ...member_lv_ids) {
		List<Map> giftList  = this.getGiftList(promotion);
		
		for(Map gift:giftList){
			//String sql = SF.goodsSql("ORDER_GIFT_INSERT");
			//this.baseDaoSupport.execute(sql, 
			//					orderId,gift.get("fo_id"),gift.get("fo_name"),gift.get("score"),1,0,"present");
			
			/**
			 * 修改为把赠品添加到order_items表中=========start===========
			 */
			OrderItem  orderItem = new OrderItem();
			orderItem.setPrice(0d) ;
			orderItem.setName((String) gift.get("fo_name"));
			orderItem.setNum(1);
			String productid = gift.get("fo_id").toString();
			Product product = productManager.get(productid);
			if(product!=null)
				orderItem.setGoods_id(product.getGoods_id());
			orderItem.setShip_num(0);
			orderItem.setSpec_id(gift.get("fo_id").toString());
			orderItem.setProduct_id(product.getProduct_id());
			orderItem.setOrder_id(orderId);
			orderItem.setPrice(0d);
			orderItem.setGainedpoint(Integer.parseInt(String.valueOf(gift.get("score"))));
			//orderItem.setAddon( cartItem.getAddon() );
			//orderItem.setLan_id(order.getLan_id());
			if(member_lv_ids!=null && member_lv_ids.length>0)
				orderItem.setLv_id(member_lv_ids[0]);
			orderItem.setItem_type(2);
			orderItem.setCoupon_price(0d);
			this.baseDaoSupport.insert("order_items", orderItem);
			/**
			 * 修改为把赠品添加到order_items表中=========end===========
			 */
		}
	 
	}

	
	@Override
	public List getGiftList(Promotion promotion) {
		String solution =  promotion.getPmt_solution();
		if(StringUtils.isBlank(solution)){
            return new ArrayList();
        }
	 	Object[] giftIdArray = JSON.parseArray(solution,Object.class).toArray();
	 	String sql = SF.goodsSql("GET_GIFT_LIST") + " and fo_id in(" + StringUtil.arrayToString(giftIdArray, ",") + ")";
		return this.baseDaoSupport.queryForList(sql);
	}

	public IFreeOfferManager getFreeOfferManager() {
		return freeOfferManager;
	}

	public void setFreeOfferManager(IFreeOfferManager freeOfferManager) {
		this.freeOfferManager = freeOfferManager;
	}


	
}