
package com.ztesoft.net.framework.model;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * 商品搜索结果页分页
 * @author kingapex
 *2010-4-27下午01:11:10
 */
public class SearchPagerDirectiveModel implements TemplateDirectiveModel {

	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
	 
		String total  = params.get("total").toString();
		String pagesize  = params.get("pagesize").toString();
		String pageNo=  params.get("pageno").toString();
		PagerHtmlBuilder pagerHtmlBuilder = new PagerHtmlBuilder(Integer.valueOf(pageNo), Integer.valueOf(total), Integer.valueOf(pagesize));
		String page_html = pagerHtmlBuilder.buildPageHtml();
		env.getOut().write(page_html);
	}

}
