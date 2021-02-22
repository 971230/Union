package com.ztesoft.net.eop.sdk.webapp.taglib.html.support;

import com.ztesoft.net.framework.pager.AbstractProxyPageHtmlBuilder;

/**
 * grid异步分页构建器<br>
 * 依赖/WebContent/statics/js/admin/Grid.js<br>
 * 通过$(selector).gridAjaxPager(url)在客户端完成分页加载操作<br>
 * url为去掉page参数的url<br>
 * 每个分页的连接增加pageno属性，对应其页数
 * @author kingapex
 *
 */
public class GridAjaxPageHtmlBuilder extends AbstractProxyPageHtmlBuilder {

	private String gridid;
	private int oldPage=0;
	

	public GridAjaxPageHtmlBuilder(long pageNum, long totalCount, int pageSize,String _gridid,String _asynModel) {
		super(pageNum, totalCount, pageSize,_asynModel);
		this.gridid = _gridid;
	}
	
	public GridAjaxPageHtmlBuilder(long pageNum, long totalCount, int pageSize,String _gridid) {
		this(pageNum, totalCount, pageSize, _gridid, "");
	}
	@Override
	public String getGridId() {
		return gridid;
	}
	
	/**
	 * 覆写父类的生成html方法<br>
	 * 加入分页js调用
	 */
	@Override
	public String buildPageHtml() {
		return super.buildPageHtml() + "<script>jQuery(function(){jQuery(\".gridbody[gridid='"+gridid+"']>.page\").gridAjaxPager('"+url+"');});</script>";
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
		linkHtml.append("' pageSize='");
		linkHtml.append(pageSize);
		linkHtml.append("'>");
		return linkHtml.toString();
	}

}