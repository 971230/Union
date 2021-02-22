
package com.ztesoft.net.framework.model;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public   class PagerHtmlBuilder {
	
	
	protected String url;
	private HttpServletRequest request ;
	private long pageNum;
	private long totalCount;
	private int pageSize;
	private long pageCount;
	private int showCount = 23;
	
	public PagerHtmlBuilder(long _pageNum,long _totalCount,int _pageSize){
		pageNum= _pageNum;
		totalCount= _totalCount;
		pageSize= _pageSize ;
		request =  ThreadContextHolder.getHttpRequest();
	}
	
	public String buildPageHtml() {
		this.init();
		StringBuffer pageStr = new StringBuffer("");
		pageStr.append("<div align=\"right\" class=\"turnPageBottom\"><tbody><tr>" );
		pageStr.append(this.getFirstPageString());
		pageStr.append(this.getHeadString());
		pageStr.append(this.getBodyString());
		pageStr.append(this.getFooterString());
		pageStr.append(this.getLastPageString());
		pageStr.append("</div>");
		return pageStr.toString();
	}

	
	

	
	
	/**
	 * 计算并初始化信息
	 *
	 */
	private  void init() {	
 
		pageSize = pageSize<1? 1 :pageSize;
		
		pageCount = totalCount / pageSize;
		pageCount = totalCount % pageSize > 0 ? pageCount + 1 : pageCount;
	
		pageNum = pageNum > pageCount ? pageCount : pageNum;
		pageNum = pageNum < 1 ? 1 : pageNum;
		
		 
		url =request.getServletPath();
		url = StringUtil.toUTF8(url);
		url=UrlUtils.getParamStr(url);
		url= UrlUtils.getExParamUrl(url, "page");
		
		
		
		//url = url.replaceAll("\\.html", ""); 
	 
	}
	
	/*
	 * 生成分页尾面字串
	 */
	protected String getLastPageString(){
		StringBuffer headString = new StringBuffer("");
		headString.append("<td>");
		headString.append("<a title=\"尾页\"");
		//headString.append(" onmouseout=\"this.className = 'prev'\" ");
		//headString.append("  onmouseover=\"this.className = 'onprev'\" ");
		//headString.append( " class=\"page_prev\" " );			
		headString.append(" href=\"");
		headString.append( getUrlStr (this.pageCount));
		headString.append("\" >尾页");
		headString.append("</a>\n");
		headString.append("</td>");
		return headString.toString();
	}
	
	/*
	 * 生成分页首页字串
	 */
	protected String getFirstPageString(){
		StringBuffer headString = new StringBuffer("");
		headString.append("<td>");
		headString.append("<a title=\"首页\"");
		//headString.append(" onmouseout=\"this.className = 'prev'\" ");
		//headString.append("  onmouseover=\"this.className = 'onprev'\" ");
		//headString.append( " class=\"page_prev\" " );			
		headString.append(" href=\"");
		headString.append( getUrlStr (1));
		headString.append("\" >首页");
		headString.append("</a>\n");
		headString.append("</td>");
		return headString.toString();
	}
	
	 /**
	  * 生成分页头字串
	  * @return
	  */
	protected String getHeadString() {

		StringBuffer headString = new StringBuffer("");
		headString.append("<td>");
	
		


		if (pageNum > 1) { //不是第一页，有上一页

			headString.append("<a title=\"上一页\"");
			//headString.append(" onmouseout=\"this.className = 'prev'\" ");
			//headString.append("  onmouseover=\"this.className = 'onprev'\" ");
			headString.append( " class=\"page_prev\" " );			
			headString.append(" href=\"");
			headString.append( getUrlStr (this.pageNum-1));
			headString.append("\" ><i class='page_prev_arrow'></i>上一页");
			headString.append("</a>\n");
 
		}else{//第一页
			headString.append("<span title=\"已经是第一页\" ");
			headString.append( " class=\"prev\"> 已经是第一页</span>" );
		}
		headString.append("</td>");
		return headString.toString();
	}

	
	
	
	/**
	 * 生成分页尾字串
	 * @return
	 */
	protected String getFooterString() {
		StringBuffer footerStr = new StringBuffer("");
		footerStr.append("<td style=\"padding-right: 20px;\">");
		if (pageNum < pageCount) {

		   //onmouseout=\"this.className = 'next'\" onmouseover=\"this.className = 'onnext'\"
			footerStr.append("<a title='下一页' class='page_next' ");
			footerStr.append(" href='");
			footerStr.append(  getUrlStr (this.pageNum+1) );
			footerStr.append("'>");
			footerStr.append("下一页<i class='page_next_arrow'></i></a>");

		}else{
			footerStr.append("<span title=\"已经是最后一页\" class=\"next\">已经是最后一页</span>");
		}
		footerStr.append("</td>\n");
		return footerStr.toString();
	}

	
	
	
	/**
	 * 生成分页主体字串
	 * @return
	 */
	protected String getBodyString() {

		StringBuffer pageStr = new StringBuffer();

		long start = pageNum - showCount / 2;
		start = start <= 1 ? 1 : start;

		long end = start + showCount;
		end = end > pageCount ? pageCount : end;
		//pageStr.append("<td>");
		
		for (long i = start; i <= end; i++) {

			
			if (i != pageNum) {
				pageStr.append("<a");
				pageStr.append(" href=\"");
			
				pageStr.append(  getUrlStr ( i ) );
				pageStr.append("\">");
				
				pageStr.append(i);
				pageStr.append("</a>\n");
			} else {
				pageStr.append(" <span class=\"page_cur\">");
				pageStr.append(i);
				pageStr.append("</span> ");
			}
 
		}
		//pageStr.append("</td>");
		return pageStr.toString();
	}

 


	
	
	
	/**
	 * 根据页数生成超级连接href的字串
	 * @param page
	 * @return
	 */
	protected   String getUrlStr(long page){
		String page_url;
		page_url= "wssquery-"+UrlUtils.getExParamUrl(url, "page");
		page_url+="-page-"+ page;
		String to_page = (String)request.getSession().getAttribute("to_page");
		if(!StringUtil.isEmpty(to_page)){
			return page_url+".html?to_page="+to_page ;
		}else{
			return page_url+".html" ;
		}
	}
	
	public static void main(String[] args){
		String url = "/wssquery-0";		
		String pattern = "p(\\d*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.COMMENTS);
		Matcher m = p.matcher(url);
		if (m.find()) {		
		url = url.replaceAll(pattern, "p4");
		}else{
			url = url+"p5";
		}
	 
	}
	
	private  String findUrl(String url){
		String pattern = "(.*)(p(\\d))(.*).html";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		if (m.find()) {
			String s = m.replaceAll("$1");
			return s+"-";
		}
		return null;
	}

 
	
}
