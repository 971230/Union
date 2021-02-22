package org.springframework.web.multipart;

import javax.servlet.http.HttpServletRequest;

public abstract interface MultipartResolver
{
  public abstract boolean isMultipart(HttpServletRequest paramHttpServletRequest);

  public abstract MultipartHttpServletRequest resolveMultipart(HttpServletRequest paramHttpServletRequest)
    throws MultipartException;

  public abstract void cleanupMultipart(MultipartHttpServletRequest paramMultipartHttpServletRequest);
}

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.multipart.MultipartResolver
 * JD-Core Version:    0.6.0
 */