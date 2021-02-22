package com.ztesoft.net.mall.core.service.impl.promotion;

import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.mall.core.service.promotion.IReduceFreightBehavior;

import javax.servlet.http.HttpServletRequest;

/**
 * 免运费方法 
 * @author kingapex
 *
 */
public class FreeFreightMethod  implements IPromotionMethod,IReduceFreightBehavior {

	
	@Override
	public String getInputHtml(String pmtid, String solution) {
		FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
		freeMarkerPaser.setClz(this.getClass());
		freeMarkerPaser.putData("free",  solution );
		return freeMarkerPaser.proessPageContent();
	}

	
	@Override
	public String getName() {
		
		return "free";
	}

	
	@Override
	public String onPromotionSave(String pmtid) {
		
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		String free = request.getParameter("free");
		return free==null?"":free;
	}


	
	@Override
	public Double reducedPrice( Double freight) {
		return 0D;
	}

	
 

}
