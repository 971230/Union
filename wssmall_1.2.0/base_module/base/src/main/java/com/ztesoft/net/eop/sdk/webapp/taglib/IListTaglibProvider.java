package com.ztesoft.net.eop.sdk.webapp.taglib;

import java.util.List;

import com.ztesoft.net.eop.sdk.webapp.taglib.IListTaglibParam;

import javax.servlet.jsp.PageContext;

public interface IListTaglibProvider {
	public List getData(IListTaglibParam param, PageContext pageContext);
}
