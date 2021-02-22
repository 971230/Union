package com.ztesoft.net.mall.plugin.promotion;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;
import com.ztesoft.net.mall.core.service.promotion.PromotionType;



/**
 * 购物车金额满多少就免运费
 * @author kingapex
 *
 */
public class EnoughPriceFreeDeliveryPlugin  extends AutoRegisterPlugin implements
IPromotionPlugin {

	
	@Override
	public void register() {
		 
		
	}

	
	@Override
	public String[] getConditions() {
		 
		return new String[]{ PromotionConditions.ORDER ,PromotionConditions.MEMBERLV};
	}

	
	@Override
	public String getMethods() {
	 
		return "freeFreight";
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "enoughPriceFreeDeliveryPlugin";
	}

	
	@Override
	public String getName() {
		
		return "免运费———购物车中商品总金额大于指定金额,免运费。";
	}

	
	@Override
	public String getType() {
		
		return PromotionType.PMTTYPE_ORDER;
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {
		
		
	}
 

}
