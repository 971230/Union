package com.ztesoft.net.eop.processor.facade.support.widget;

import java.util.List;
import java.util.Map;
import com.ztesoft.net.eop.processor.widget.IWidgetParamUpdater;
import com.ztesoft.net.eop.processor.widget.WidgetXmlUtil;
import com.ztesoft.net.eop.resource.IThemeManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.Theme;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;

/**
 * saas式挂件参数更新
 * @author kingapex
 * 2010-2-10上午11:26:21
 */
public class XmlWidgetParamUpdater implements IWidgetParamUpdater {
	private IThemeManager themeManager;
	
	
	@Override
	public void update(String pageId,List<Map<String,String>> params) {
		EopSite  site  =EopContext.getContext().getCurrentSite();
		Theme theme = themeManager.getTheme(site.getThemeid());
		String contextPath  = EopContext.getContext().getContextPath();
		String path =
		EopSetting.EOP_PATH	
		+contextPath
		+ EopSetting.THEMES_STORAGE_PATH_F+
		"/" + theme.getPath()  + "/widgets.xml"; 
			
		WidgetXmlUtil.save(path, pageId, params);		
	}
	
	public void setThemeManager(IThemeManager themeManager) {
		this.themeManager = themeManager;
	}
	

}
