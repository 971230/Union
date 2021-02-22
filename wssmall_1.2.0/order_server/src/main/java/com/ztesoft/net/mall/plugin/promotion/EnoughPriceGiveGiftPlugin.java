package com.ztesoft.net.mall.plugin.promotion;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;
import com.ztesoft.net.mall.core.service.promotion.PromotionType;

/**
 * 购物车中商品总金额大于指定金额，赠送某些赠品插件
 * @author kingapex
 *2010-4-15下午05:12:00
 */
public class EnoughPriceGiveGiftPlugin extends AutoRegisterPlugin implements IPromotionPlugin {

	
	@Override
	public String[] getConditions() {
		
		return new String[]{ PromotionConditions.ORDER ,PromotionConditions.MEMBERLV};
	}

	
	@Override
	public String getMethods() {
		return "giveGift";
	}	
	
	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	
	@Override
	public String getId() {
		return "enoughPriceGiveGiftPlugin";
	}

	
	@Override
	public String getName() {
		return "满就送———购物车中商品总金额大于指定金额，赠送某个赠品";
	}

	
	@Override
	public String getType() {
		return PromotionType.PMTTYPE_ORDER ;
	}

	
	@Override
	public String getVersion() {
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {

	}

	
	@Override
	public void register() {
		
	}

}
