package com.ztesoft.crm.report.interfaces;

import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public abstract interface StaticDataGetter
{
  public abstract List execute(ServletContext paramServletContext, HttpServletRequest paramHttpServletRequest, String paramString1, String paramString2)
    throws Exception;

  public abstract List execute(ServletContext paramServletContext, HttpServletRequest paramHttpServletRequest, String paramString, HashMap paramHashMap)
    throws Exception;
}

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.interfaces.StaticDataGetter
 * JD-Core Version:    0.5.3
 */