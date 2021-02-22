package com.ztesoft.net.eop.processor.backend.support;

import com.ztesoft.net.eop.processor.AbstractFacadePagetParser;
import com.ztesoft.net.eop.processor.FacadePage;
import com.ztesoft.net.eop.processor.core.Request;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.sdk.utils.JspUtil;

/**
 * 后台页面包装器-新页面样式<br>
 * 用/admin/main_frame_all.jsp包装业务页面 <br>
 * @author hu.yi
 * @date 2014.05.29
 */
public class BackAllTemplateWrapper extends AbstractFacadePagetParser {
	
	public BackAllTemplateWrapper(FacadePage page, Request request) {
		super(page, request);
	}

	@Override
	protected Response parse(Response response) {
		httpRequest.setAttribute("content", response.getContent());
		String content  = JspUtil.getJspOutput("/mgWeb/main_frame_all.jsp", httpRequest, httpResponse);
		response.setContent(content);
		return response;
	}

}
