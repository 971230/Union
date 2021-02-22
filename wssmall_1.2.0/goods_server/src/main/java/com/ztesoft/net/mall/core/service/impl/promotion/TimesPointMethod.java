package com.ztesoft.net.mall.core.service.impl.promotion;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.mall.core.service.promotion.ITimesPointBehavior;

import javax.servlet.http.HttpServletRequest;

/**
 * 翻倍积分优惠方式实现
 * @author kingapex
 *2010-4-25下午10:32:44
 */
public class TimesPointMethod implements IPromotionMethod, ITimesPointBehavior {

	
	@Override
	public String getInputHtml(String pmtid, String solution) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		freeMarkerPaser.putData("discount",  solution );//multiple原来是个该参数，但是request.getParameter方法取不到值。
		return freeMarkerPaser.proessPageContent();
	}

	
	@Override
	public String getName() {
		 
		return "timesPoint";
	}

	
	@Override
	public String onPromotionSave(String pmtid) {
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String multiple = request.getParameter("discount");//multiple
		return multiple==null?"":multiple;
	}

	
	@Override
	public Integer countPoint(Promotion promotion,Integer point) {
		String solution = promotion.getPmt_solution();
		Integer multiple = Integer.valueOf(solution);
		
		return point*multiple;
	}


	

}
