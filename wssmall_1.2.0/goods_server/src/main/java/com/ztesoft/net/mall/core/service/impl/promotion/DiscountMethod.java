package com.ztesoft.net.mall.core.service.impl.promotion;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.service.promotion.IDiscountBehavior;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 对指定的商品直接打拆
 * @author kingapex
 *2010-4-18上午09:14:58
 */
public class DiscountMethod implements IPromotionMethod,IDiscountBehavior {

	
	@Override
	public String getInputHtml(String pmtid, String solution) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		freeMarkerPaser.putData("discount",  solution );
		return freeMarkerPaser.proessPageContent();
	}

	
	@Override
	public String getName() {
		return "discount";
	}

	
	@Override
	public String onPromotionSave(String pmtid) {
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String discount = null;
		if(null != request){
			discount = request.getParameter("discount");
		}
		return discount==null?"":discount;
	}

	
	@Override
	public Double discount(Promotion promotion, Double  goodsPrice) {
		String solution = promotion.getPmt_solution();
		Double  discount =  Double.valueOf(solution);
		return CurrencyUtil.mul(goodsPrice, discount);
	}


}