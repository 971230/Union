package com.ztesoft.net.mall.plugin.promotion;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;
import com.ztesoft.net.mall.core.service.promotion.PromotionType;

/**
 * 某商品翻倍积分
 * @author kingapex
 *2010-4-25下午10:39:13
 */
public class GoodsTimesPointPlugin extends AutoRegisterPlugin implements
		IPromotionPlugin {

	
	@Override
	public void register() {

	}

	
	@Override
	public String[] getConditions() {
		 
		return new String[]{ PromotionConditions.GOODS ,PromotionConditions.MEMBERLV};
	}

	
	@Override
	public String getMethods() {
		return "timesPoint";
	}

	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	
	@Override
	public String getId() {
		return "goodsTimesPointPlugin";
	}

	
	@Override
	public String getName() {
		return "积分翻倍——顾客购买指定的商品，可获得翻倍积分或者x倍积分";
	}

	
	@Override
	public String getType() {
		return  PromotionType.PMTTYPE_GOODS;
	}

	
	@Override
	public String getVersion() {
		return "1.0";
	}

	
	@Override
	public void perform(Object... params) {

	}

}
