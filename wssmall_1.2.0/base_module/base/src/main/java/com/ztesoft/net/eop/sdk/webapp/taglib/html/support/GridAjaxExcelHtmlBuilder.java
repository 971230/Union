package com.ztesoft.net.eop.sdk.webapp.taglib.html.support;

import java.util.Enumeration;

import com.ztesoft.net.framework.pager.AbstractExcelHtmlBuilder;

/**
 * grid异步打印excel构建器<br>
 * @author hu.yi
 * @date 2013.5.9
 *
 */
public class GridAjaxExcelHtmlBuilder extends AbstractExcelHtmlBuilder {

	private String gridid;
	
	private String jsonParams;

	public GridAjaxExcelHtmlBuilder(long pageNum, long totalCount, int pageSize,String _gridid) {
//		super(pageNum, totalCount, pageSize);
		this.gridid = _gridid;
	}

	/**
	 * 覆写父类的生成html方法<br>
	 * 加入分页js调用
	 */
	@Override
	public String buildExcelHtml() {
		return super.buildExcelHtml();
	}

	/**
	 * 根据页数生成前500条超级连接href的字串
	 * 
	 * @param page
	 * @return
	 */
	@Override
	protected String getUrlStr() {
		return "GridExcel.export_file("+ buildJsonParams().replace("{currentPage}", "no").replace("{totalPage}", "yes") +");";
	}
	
	
	/**
	 * 根据页数生成超级连接href的字串
	 * 
	 * @param page
	 * @return
	 */
	@Override
	protected String getUrlStrForThis() {
		return "GridExcel.export_file("+ buildJsonParams().replace("{currentPage}", "yes").replace("{totalPage}", "no") +");";
	}
	
	protected String buildJsonParams() {
		if (jsonParams != null)
			return jsonParams;
		
		String json = "{ url:'%s', currentPage:'{currentPage}', totalPage:'{totalPage}', params:{%s excel:'yes' } }";
		//防止已有param的重复添加
		if(url.indexOf("?") > -1){
			url = url.substring(0, url.indexOf("?")); //not include ?
		}
		StringBuffer paramsBuffer = new StringBuffer("");
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String paramName = (String) en.nextElement();
			String paramVal = request.getParameter(paramName);
			//去掉时间属性，可能会导致乱码
			if(!"version".equals(paramName) && !"button".equals(paramName)){
				paramsBuffer.append("'" + paramName + "':'" + paramVal + "', ");
			}
		}
		return jsonParams = String.format(json, url, paramsBuffer.toString()); 
	}
	
	
}
