package com.ztesoft.crm.report.storage;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletContext;

public abstract interface StorageStream
{
  public abstract Storage getStorage();

  public abstract void setStorage(Storage paramStorage);

  public abstract InputStream getReport(ServletContext paramServletContext, String paramString)
    throws Exception;

  public abstract ReportFile[] getDirectory(ServletContext paramServletContext, String paramString)
    throws Exception;

  public abstract boolean createDirectory(ServletContext paramServletContext, String paramString1, String paramString2)
    throws Exception;

  public abstract boolean save(ServletContext paramServletContext, String paramString1, String paramString2)
    throws Exception;

  public abstract boolean save(ServletContext paramServletContext, String paramString, InputStream paramInputStream)
    throws Exception;

  public abstract boolean rename(ServletContext paramServletContext, String paramString1, String paramString2)
    throws Exception;

  public abstract boolean delete(ServletContext paramServletContext, String paramString)
    throws Exception;

  public abstract boolean zip(ServletContext paramServletContext, String paramString, OutputStream paramOutputStream)
    throws Exception;

  public abstract List deploy(ServletContext paramServletContext, String paramString, InputStream paramInputStream)
    throws Exception;
}

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.storage.StorageStream
 * JD-Core Version:    0.5.3
 */