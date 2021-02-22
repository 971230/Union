package com.ztesoft.net.eop.processor.facade.support.widget;

import com.ztesoft.net.eop.processor.facade.support.LocalWidgetPaser;
import com.ztesoft.net.eop.processor.facade.support.WidgetEditModeWrapper;
import com.ztesoft.net.eop.processor.widget.IWidgetHtmlGetter;
import com.ztesoft.net.eop.processor.widget.IWidgetPaser;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;

import java.util.Map;

/**
 * 挂件Html获取器</br>
 * 通过 IWidgetPaser接口 解析挂件html</br>
 * </br>
 * 分为两种模式：浏览模式和编辑模式</br>
 * 浏览模式
 * @see #IWidgetPaser
 * 
 * @author kingapex
 * 2010-2-8下午10:17:58
 */
public class WidgetHtmlGetter implements IWidgetHtmlGetter {

	
	@Override
	public String process(Map<String, String> params,String page) {
		
		IWidgetPaser widgetPaser = new LocalWidgetPaser();
		widgetPaser = new BorderWrapper(widgetPaser);
		if(UserServiceFactory.getUserService().isUserLoggedIn() &&"edit".equals(params.get("mode"))){
			widgetPaser = new WidgetEditModeWrapper(widgetPaser); 
		}
		String html =widgetPaser.pase(params);
		return html;
	}



	

}
