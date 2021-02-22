package com.ztesoft.net.eop.processor;

import com.ztesoft.net.eop.processor.backend.BackgroundProcessor;
import com.ztesoft.net.eop.processor.facade.*;
import com.ztesoft.net.eop.resource.IAppManager;
import com.ztesoft.net.eop.resource.model.EopApp;
import com.ztesoft.net.eop.sdk.context.ConnectType;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author kingapex
 * @version 1.0
 * @created 13-十月-2009 11:36:29
 */
public abstract class ProcessorFactory {

	/**
	 * 
	 * @param uri
	 */
	public static Processor newProcessorInstance(String uri,
			HttpServletRequest httpRequest) {
		Processor processor = null;

		if (uri.startsWith("/statics") || uri.startsWith("/publics"))
			return null;

		// sitemap生成
		if (uri.toLowerCase().equals("/sitemap.xml")) {
			return new SiteMapProcessor();
		}

		if (uri.toLowerCase().equals("/robots.txt")) {
			return null;
		}

		IAppManager appManager = SpringContextHolder.getBean("appManager");
		List<EopApp> appList = appManager.list();

		String path = uri;

		for (EopApp app : appList) {
			if (app.getDeployment() == ConnectType.remote)
				continue;

			if (path.startsWith(app.getPath() + "/admin")) {// path.startsWith(app.getPath()
															// add by wui
				if (isExinclude(path)) {
					return null;
				}

				processor = new BackgroundProcessor();
				return processor;
			}
			if (path.startsWith(app.getPath())) {
				return null;
			}
		}

		if (uri.startsWith("/validcode"))
			return null;
		if (uri.startsWith("/editor/"))
			return null;
		if (uri.startsWith("/eop/"))
			return null;
		if (uri.startsWith("/test/"))
			return null;
		if (uri.endsWith("favicon.ico"))
			return null;
		if (uri.endsWith("orderGet"))
			return null;
		// add by wui
		httpRequest.setAttribute("themes", EopSetting.ADMINTHEMES_STORAGE_PATH);
		if (uri.startsWith("/appres/")) {
			return new WebResourceProcessor();
		}

		if (isExinclude(uri))
			return null;
		if (uri.startsWith("/mgWeb/") || uri.startsWith("/mgWeb")
				|| uri.startsWith("/partner/") || uri.startsWith("/partner")) {
			if (!uri.startsWith("/mgWeb/themes/")) {
				processor = new BackgroundProcessor();
			}
		} else if (uri.startsWith("/widget")) {

			if (uri.startsWith("/widgetSetting/")) {
				processor = new WidgetSettingProcessor();
			} else if (uri.startsWith("/widgetBundle/")) {
				// processor = new WidgetBundleProcessor();
			} else {
				processor = new WidgetProcessor();
			}
		} else {

			if (uri.endsWith(".action") || uri.endsWith(".do"))
				return null;
			if (EopSetting.TEMPLATEENGINE.equals("on"))
				processor = new FacadePageProcessor();
		}

		return processor;
	}

	private static boolean isExinclude(String uri) {

		String[] exts = new String[] { "jpg", "gif", "js", "png", "css", "doc",
				"xls", "swf" };
		for (String ext : exts) {
			if (uri.toUpperCase().endsWith(ext.toUpperCase())) {
				return true;
			}
		}

		return false;
	}

}