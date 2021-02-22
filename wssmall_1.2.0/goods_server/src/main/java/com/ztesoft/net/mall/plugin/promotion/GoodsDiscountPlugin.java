package com.ztesoft.net.mall.plugin.promotion;

import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;
import com.ztesoft.net.mall.core.service.promotion.PromotionType;

public class GoodsDiscountPlugin extends AutoRegisterPlugin implements IPromotionPlugin {

	
	@Override
	public String[] getConditions() {
		return new String[]{ PromotionConditions.GOODS ,PromotionConditions.MEMBERLV};
	}

	
	@Override
	public String getMethods() {
		return "discount";
	}

	
	@Override
	public String getAuthor() {
		return "kingapex";
	}

	
	@Override
	public String getId() {
		return "goodsDiscountPlugin";
	}

	
	@Override
	public String getName() {
		return "打折————商品直接打折，如全场女鞋8折。可以对商品任意折扣，适合低价清货促销";
	}

	
	@Override
	public String getType() {
		return PromotionType.PMTTYPE_GOODS;
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
