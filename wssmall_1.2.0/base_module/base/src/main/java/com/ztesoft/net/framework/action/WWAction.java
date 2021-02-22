package com.ztesoft.net.framework.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;

public class WWAction extends ActionSupport implements SessionAware {
	protected final Logger logger = Logger.getLogger(getClass());
	protected Page webpage;

	protected Map session = null;

	protected List msgs = new ArrayList();

	protected Map urls = new HashMap();

	protected static final String MESSAGE = "message";

	protected static final String JSON_MESSAGE = "json_message";
	
	protected static final String RESULT_MESSAGE = "result_message";

	protected String excel;
	
	protected String totalPage;	//标志导出为全部记录
	
	protected String currentPage;	//标志导出为本页记录

	protected String script = "";

	protected String json;
 

	protected int page;

	protected int pageSize;

	// 页面id,加载页面资源所用
	protected String pageId;

	public String list_ajax() {
	
		return "list";
	}

	public String add_input() {

		return "add";
	}

	public String edit_input() {

		return "edit";
	}

	public int getPageSize() {
		
		WebSessionContext sessonContext = ThreadContextHolder
		.getSessionContext();
		
		String item = (String) sessonContext.getAttribute("excel_items");
		
		HttpServletRequest request=ServletActionContext.getRequest();
		String path=request.getRequestURI();
		if(this.session.containsKey(path) == true){
			pageSize = (Integer)this.session.get(path);
		}
		
		if("yes".equals(currentPage) && !"yes".equals(totalPage)){
			return Integer.valueOf(EopSetting.BACKEND_PAGESIZE);
		}else if("yes".equals(totalPage) && !"yes".equals(currentPage)){
			return Integer.valueOf(item);
		}else if(this.pageSize>0){
			return this.pageSize;
		}else{
			return Integer.valueOf(EopSetting.BACKEND_PAGESIZE);
		}
	}

	public void setPageSize(int pageSize) {
		HttpServletRequest request=ServletActionContext.getRequest();
		String path=request.getRequestURI();
		this.session.put(path, pageSize);
		
		this.pageSize = pageSize;
	}

	public int getPage() {
		page = page < 1 ? 1 : page;
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

 

	public Map getSession() {
		return session;
	}

	@Override
	public void setSession(Map session) {
		this.session = session;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	protected void render(String text, String contentType) {
		try {
			HttpServletResponse response = getResponse();
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * 直接输出纯字符串.
	 */
	protected void renderText(String text) {
		render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	protected void renderHtml(String text) {
		render(text, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXML(String text) {
		render(text, "text/xml;charset=UTF-8");
	}
	
	
	protected void showSuccessJson(String message){
		if(StringUtil.isEmpty(message))
			this.json="{result:1}";
		else
			this.json="{result:1,message:'"+message+"'}";
	}
	
	
	protected void showErrorJson(String message){
		if(StringUtil.isEmpty(message))
			this.json="{result:0}";
		else
			this.json="{result:0,message:'"+message+"'}";
	}
	


	public List getMsgs() {
		return msgs;
	}

	public void setMsgs(List msgs) {
		this.msgs = msgs;
	}

	public Map getUrls() {
		return urls;
	}

	public void setUrls(Map urls) {
		this.urls = urls;
	}

	public Page getWebpage() {
		return webpage;
	}

	public void setWebpage(Page webpage) {
		this.webpage = webpage;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
