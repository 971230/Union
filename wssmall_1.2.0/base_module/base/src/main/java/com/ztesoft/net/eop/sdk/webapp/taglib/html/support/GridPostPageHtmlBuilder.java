package com.ztesoft.net.eop.sdk.webapp.taglib.html.support;

import com.ztesoft.net.framework.pager.AbstractProxyPageHtmlBuilder;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * grid异步分页构建器<br>
 * 依赖/WebContent/statics/js/admin/Grid.js<br>
 * 通过$(selector).gridAjaxPager(url)在客户端完成分页加载操作<br>
 * url为去掉page参数的url<br>
 * 每个分页的连接增加pageno属性，对应其页数
 * @author kingapex
 *
 */
public class GridPostPageHtmlBuilder extends AbstractProxyPageHtmlBuilder {

	private String gridid;
	private String formId;
	private String ajax;
	private String action;
	private int oldPage=0;
	
	@Override
	public String getGridId() {
		return gridid;
	}

	public GridPostPageHtmlBuilder(long pageNum, long totalCount, int pageSize,String _gridid, String _formId, String _ajax, String _action,String _asynModel) {
		super(pageNum, totalCount, pageSize,_asynModel);
		if (StringUtil.isEmpty(_formId))
			_formId = "";
		if (StringUtil.isEmpty(_action))
			_action = "";
		if (!"yes".equals(_ajax)) 
			_ajax = "no";
		this.gridid = _gridid;
		this.ajax = _ajax;
		this.formId = _formId;
		this.action = _action;
	}
	public GridPostPageHtmlBuilder(long pageNum, long totalCount, int pageSize,String _gridid, String _formId, String _ajax, String _action) {
		this(pageNum, totalCount, pageSize, _gridid, _formId, _ajax, _action, "");
	}

	/**
	 * 覆写父类的生成html方法<br>
	 * 加入分页js调用
	 */
	@Override
	public String buildPageHtml() {
		if(this.url==null)
			initUrl();
		String options = String.format("{formId:'%s', action:'%s', url:'%s', ajax:'%s'}", 
				formId, action, url,  ajax);
		return super.buildPageHtml() + "<script>jQuery(function(){jQuery(\".gridbody[gridid='"+gridid+"']>.page\").gridPostPager("+options+");});</script>";
	}

	/**
	 * 根据页数生成超级连接href的字串
	 * 
	 * @param page
	 * @return
	 */
	@Override
	protected String getUrlStr(long page) {
		StringBuffer linkHtml = new StringBuffer();
		linkHtml.append("href='javascript:;'");
		linkHtml.append(" pageNo='");
		linkHtml.append(page);
		//linkHtml.append("&pageSize=");
		//linkHtml.append(pageSize);
		linkHtml.append("'>");
		return linkHtml.toString();
	}

}
