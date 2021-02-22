package com.ztesoft.crm.report.interfaces;

import com.ztesoft.crm.report.actions.ParameterMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public abstract interface Interceptor
{
  public abstract boolean doStart(ServletContext paramServletContext, HttpServletRequest paramHttpServletRequest, String paramString1, ParameterMap paramParameterMap, String paramString2)
    throws Exception;

  public abstract boolean doEnd(ServletContext paramServletContext, HttpServletRequest paramHttpServletRequest, String paramString1, ParameterMap paramParameterMap, String paramString2)
    throws Exception;

  public abstract void release();
}

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.Interceptor
 * JD-Core Version:    0.5.3
 */