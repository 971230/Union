package com.ztesoft.net.eop.sdk.webapp.taglib.html.support;

import com.ztesoft.net.eop.sdk.webapp.bean.Grid;
import com.ztesoft.net.eop.sdk.webapp.taglib.IListTaglibParam;
import com.ztesoft.net.eop.sdk.webapp.taglib.IListTaglibProvider;
import com.ztesoft.net.framework.database.Page;

import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.List;

public class GridBodyProvider implements IListTaglibProvider {
	@Override
	public List getData(IListTaglibParam _param, PageContext pageContext) {

		GridBodyParam param = (GridBodyParam) _param;
		String from = param.getFrom();

		Object obj = pageContext.getAttribute(from);
		if (obj == null){
			obj = pageContext.getRequest().getAttribute(from);
			if (obj == null){
				return new ArrayList();
			}
		}
		//	from	即可以是Page对象，也可以是Grid对象。
		Page page = null;
		List list = null;
		if(obj instanceof Page){
			page = (Page)obj;
			list  = page.getResult();
		}
		else if(obj instanceof Grid ){
			page = ((Grid)obj).getWebpage();
			list  = page.getResult();
		}else if(obj instanceof List){
			list = (List)obj;
		}
		
		return list;
	}
}
