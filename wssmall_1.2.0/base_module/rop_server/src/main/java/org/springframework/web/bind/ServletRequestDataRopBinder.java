package org.springframework.web.bind;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.util.RopWebUtils;

public class ServletRequestDataRopBinder extends WebDataBinder
{
  public ServletRequestDataRopBinder(Object target)
  {
    super(target);
  }

  public ServletRequestDataRopBinder(Object target, String objectName)
  {
    super(target, objectName);
  }

  public void bind(ServletRequest request)
  {
    MutablePropertyValues mpvs = new ServletRequestParameterPropertyValues(request);
    MultipartRequest multipartRequest = RopWebUtils.getNativeRequest(request, MultipartRequest.class);
    if (multipartRequest != null) {
      bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
    }
    addBindValues(mpvs, request);
    doBind(mpvs);
  }

  protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request)
  {
  }

  public void closeNoCatch()
    throws ServletRequestBindingException
  {
    if (getBindingResult().hasErrors())
      throw new ServletRequestBindingException("Errors binding onto object '" + getBindingResult().getObjectName() + "'", new BindException(getBindingResult()));
  }
}