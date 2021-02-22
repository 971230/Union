package com.ztesoft.net.eop.processor.facade;

import com.ztesoft.net.eop.processor.IPageParamJsonGetter;
import com.ztesoft.net.eop.processor.IPagePaser;
import com.ztesoft.net.eop.processor.IPageUpdater;
import com.ztesoft.net.eop.processor.Processor;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.eop.processor.facade.support.PageEditModeWrapper;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面处理器
 * 
 * @author kingapex
 * @version 1.0
 * @created 22-十月-2009 18:12:29
 */
public class FacadePageProcessor implements Processor {

	/**
	 * 
	 *响应页面的三种操作,通过参数_method来识别，并分别通过三个接口来完成操作： 
	 * <li>GET:解析页面： {@link com.ztesoft.net.net.eop.processor.IPagePaser}</li>
	 * <li>PUT:更新页面 ：{@link com.ztesoft.net.net.eop.processor.IPageUpdater}</li>
	 * <li>PARAMJSON:获取页面挂件参数json串com.enation.eop.api.facade.IPageParamJsonGetter</li>
	 * </br>
	 * 页面的url会被读取并做为解析实际页面文件地址的依据
	 * @param mode
	 * @param httpResponse
	 * @param httpRequest
	 */
	@Override
	public Response process(int mode, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {

		String method = RequestUtil.getRequestMethod(httpRequest);
		String uri = RequestUtil.getRequestUrl(httpRequest);
		Response response = new StringResponse();

		// 解析页面
		if (method.equals("GET")) {
			IPagePaser paser = SpringContextHolder.getBean("facadePagePaser");
			if (UserServiceFactory.getUserService().isUserLoggedIn()
					&& httpRequest.getParameter("mode") != null) {
				paser = new PageEditModeWrapper(paser);
			}

			String content = paser.pase(uri);
			response.setContent(content);

		}

		// 更新页面
		if (method.equals("PUT")) {
			String params = httpRequest.getParameter("widgetParams");
			String content = httpRequest.getParameter("bodyHtml");
			IPageUpdater pageUpdater = SpringContextHolder
					.getBean("facadePageUpdater");
			pageUpdater.update(uri, content, params);
			response.setContent("{'state':0,'message':'页面保存成功'}");
		}

		// 获取参数json
		if (method.equals("PARAMJSON")) {
			IPageParamJsonGetter pageParamJsonGetter = SpringContextHolder
					.getBean("pageParamJsonGetter");
			String json = pageParamJsonGetter.getJson(uri);
			response.setContent(json);
		}

		return response;
	}

}