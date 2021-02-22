package com.ztesoft.net.eop.processor.facade.support;

import com.ztesoft.net.eop.processor.AbstractFacadePageWrapper;
import com.ztesoft.net.eop.processor.FacadePage;
import com.ztesoft.net.eop.processor.core.Request;
import com.ztesoft.net.eop.processor.core.Response;

/**
 * @author kingapex
 * @version 1.0
 * @created 22-十月-2009 18:12:30
 */
public class WidgetToolWrapper extends AbstractFacadePageWrapper {

	private static final String toolsElement = "<div id=\"widget_setting\"></div><form id=\"pageForm\" method=\"POST\"><input type=\"hidden\" id=\"bodyHtml\" name=\"bodyHtml\"> </form></body>";

	/**
	 * 
	 * @param page
	 */
	public WidgetToolWrapper(FacadePage page, Request request) {
		super(page, request);
	}

	@Override
	protected Response wrap(Response response) {
		String content = response.getContent();
		content = content.replaceAll("</body>", toolsElement);
		content = content.replaceAll("</BODY>", toolsElement);

		response.setContent(content);
		return response;
	}

}