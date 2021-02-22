package com.ztesoft.net.framework.pager;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.RequestUtil;

/**
 * 抽像的Excelhtml代码生成器基类
 * 提供基本的逻辑支持
 * @author hu.yi
 * @date 2013.5.9
 *
 */
public abstract class AbstractExcelHtmlBuilder implements IExcelHtmlBuider {
	
	protected String url;
	protected HttpServletRequest request ;
	
	public AbstractExcelHtmlBuilder(){
		request = ThreadContextHolder.getHttpRequest();
	}
	
	@Override
	public String buildExcelHtml() {
		this.init();
		StringBuffer pageStr = new StringBuffer("");
		pageStr.append("<div class=\"excel\" >" );
		pageStr.append(this.getExcelButton());
		pageStr.append("</div>");
		return pageStr.toString();
	}

	
	
	/**
	 * 初始化url,用于地址栏方式传
	 * <br/> 
	 * 将地址栏上的参数拼装
	 *
	 */
	protected  void initUrl() {
		url =request.getContextPath()+RequestUtil.getRequestUrl(request);
		url = url.replaceAll("(&||\\?)excel=(\\d+)","");
		url =url.indexOf('?')>0?url+"&": url + "?";
	}
	
	
	/**
	 * 计算并初始化信息
	 *
	 */
	private  void init() {	
 
		if(this.url==null)
		initUrl();
	}
	
	
	/**
	 * 生成导出的按钮
	 * @return
	 */
	protected String getExcelButton() {
		
		StringBuffer button = new StringBuffer("");
		
		WebSessionContext sessonContext = ThreadContextHolder
		.getHttpSessionContext();
		
		String item = (String) sessonContext.getAttribute("excel_items");
		
		button.append("<input title=\"导出excel列表，导出符合要求的前"+ item +"条记录\"");
		button.append("  ");
		button.append("type=\"button\"");
		button.append("  ");
		button.append("value=\"导出\"");
		button.append("  ");
		button.append("class=\"comBtn\"");
		button.append("  ");
		button.append("onclick=\"");
//		button.append("location.href='");
//		button.append(this.getUrlStr());
//		button.append("'");
		button.append(this.getUrlStr());
		button.append("\"/>");
		button.append("<input style='margin-left:5px;' title=\"导出excel列表，导出本页记录\"" );
		button.append("  ");
		button.append("type=\"button\"");
		button.append("  ");
		button.append("value=\"导出本页\"");
		button.append("  ");
		button.append("class=\"comBtn\"");
		button.append("  ");
		button.append("onclick=\"");
//		button.append("location.href='");
//		button.append(this.getUrlStrForThis());
//		button.append("'");
		button.append(this.getUrlStrForThis());
		button.append("\"/>");
		
		return button.toString();
	}
	
	
	
	/**
	 * 根据页数生成前500条超级连接href的字串
	 * @param page
	 * @return
	 */
	abstract protected   String getUrlStr();
	

	/**
	 * 根据页数生成超级连接href的字串
	 * @param page
	 * @return
	 */
	abstract protected   String getUrlStrForThis();

 

}
