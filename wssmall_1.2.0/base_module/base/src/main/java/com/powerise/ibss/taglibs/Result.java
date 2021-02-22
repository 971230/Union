package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Result
    extends BodyTagSupport {
  private ArrayList result;
  private int fieldNum = 1;
  private String name;
  private String var;
  private String message = "";
  private String scope = "";
  private int pagesize;
  private int iCount = 0;

  private int iPageNo = 0;
  private HashMap row;
  private Iterator iterator;
  public static int strToInt(String str, int iDef) {
    if (str == null || str.trim().equals("")) {
      return iDef;
    }
    try {
      return Integer.parseInt(str);
    }
    catch (Exception e) {
    }
    return iDef;
  }

  public ArrayList getReault() {
    String sValue = null;
    Object obj = null;
    int iValue = 0;
//logger.info("getResult() pagesize=" + pagesize);
    if (pagesize < 1) {
      obj = pageContext.getRequest().getAttribute("PAGESIZE");
      if (obj != null && (obj instanceof java.lang.String)) {
        sValue = (String) obj;
      }
      pagesize = strToInt(sValue, 0);
    }
    iPageNo = 0;
    sValue = pageContext.getRequest().getParameter("paraPage");
    iPageNo = strToInt(sValue, 0);
    if (iPageNo < 1) {
      sValue = pageContext.getRequest().getParameter("para_Page");
      iPageNo = strToInt(sValue, 0);
    }
    if (iPageNo < 1) {
      sValue = pageContext.getRequest().getParameter("para_currentpage");
      iPageNo = strToInt(sValue, 0);
    }
    if (iPageNo < 1) {
      iPageNo = 1;
    }
    ArrayList aList = null;
    ArrayList bList = null;
    if (name == null) {
      name = "";

    }
//logger.info("iPageNo=" + iPageNo + " pagesize=" + pagesize);
    obj = pageContext.getRequest().getAttribute(name.trim().toUpperCase());
//logger.info("1--obj=" + obj + ",name=" + name.trim().toUpperCase());
    if ( (obj != null) && (obj instanceof java.util.ArrayList)) {
      bList = (ArrayList) obj;
      if (pagesize < 1) {
        return bList;
      }
      int iLoop = 0;
      int iStart = (iPageNo - 1) * pagesize;
      int iEnd = iPageNo * pagesize;
      int iSize = bList.size();

      if (bList.size() < iEnd) {
        iEnd = bList.size();
      }
      if (bList.size() < iStart) {
        return null;
      }


//logger.info("iSize=" + iSize + " iStart=" + iStart + " iEnd=" + iEnd);
      for (iLoop = iStart; iLoop < iEnd; iLoop++) {
        if (aList == null) {
          aList = new ArrayList();
        }
        aList.add(bList.get(iLoop));
      }
      iSize = bList.size();
//      logger.info("iSize=" + iSize);
    }
    return aList;
  }

  @Override
public int doStartTag() throws JspException {
    ArrayList aList = null;
    if (pagesize != 0) { //当数据集标签包含pagesize 时，以数据集标签pagesieze为准
      pageContext.getRequest().setAttribute("PAGESIZE", String.valueOf(pagesize));
    }
    if (pageContext.getRequest().getAttribute(name.toUpperCase())instanceof
        java.util.ArrayList) {
      result = (ArrayList) pageContext.getRequest().getAttribute(name.toUpperCase());
    }
    if (result != null && result.size() != 0) {
      pageContext.getRequest().setAttribute("SUM", String.valueOf(result.size()));
      if (scope.equalsIgnoreCase("session")) {
        pageContext.getSession().setAttribute(name.toUpperCase(), result);
      }
      else {
        pageContext.getRequest().setAttribute(name.toUpperCase(), result);
      }
    }
    if (pageContext.getRequest().getAttribute("PAGESIZE") != null) {
      pagesize = Integer.parseInt( (String) pageContext.getRequest().
                                  getAttribute("PAGESIZE"));
    }
    result = getReault();
//logger.info("getReault.size=" + result.size());
    if (result != null && result.size() > 0) {
      if (pagesize != 0 && result.size() < pagesize) {
        //组合空行
        int emptyRows = pagesize - result.size();
        HashMap emptyRow = null;
        HashMap row0 = (HashMap) result.get(0);
        Object[] fields = row0.keySet().toArray();
        emptyRow = new HashMap();
        for (int i = 0; i < fields.length; i++) {
          emptyRow.put(fields[i], "");
        }
        if (emptyRow != null) {
          for (int i = 0; i < emptyRows; i++) {
            result.add(emptyRow);
          }
        }
      }

      iterator = result.iterator();
      Object obj = iterator.next();
      pageContext.getRequest().setAttribute(var, obj);
      return (EVAL_PAGE);
    }
    else {
      try {
        /*if(message !=null){
            StringBuffer html=new StringBuffer();
            html.append("<br>");
            html.append(message);
            for(int i=0; i<pagesize +5; i++){
            html.append("<br>");
            }
             pageContext.getOut().print ("<tr><td align=center colspan='" + fieldNum +
            "'>" +
            html.toString() + "</td></tr>") ;
            }*/
        StringBuffer html = new StringBuffer();
        for (int i = 0; i < pagesize; i++) {
          html.append("<tr><td colspan='" + fieldNum + "'>&nbsp;</td></tr>");
        }
        pageContext.getOut().print(html.toString());

      }
      catch (IOException io) {
        throw new JspTagException(io.getMessage());
      }
      return (SKIP_BODY);
    }
  }

  @Override
public int doAfterBody() {
    //logger.info(bodyContent==null?"":bodyContent.getString());

    if (iterator != null && iterator.hasNext()) {
      pageContext.setAttribute(var, iterator.next(), PageContext.REQUEST_SCOPE);
      return (EVAL_BODY_AGAIN);
    }
    else {
      return (SKIP_BODY);
    }
  }

  @Override
public int doEndTag() throws JspException {
    try {
      if (bodyContent != null && bodyContent.getString() != null) {
        pageContext.getOut().print(bodyContent.getString());
      }
    }
    catch (IOException io) {
      throw new JspTagException(io.getMessage());
    }
    return EVAL_PAGE;
  }

  public void setResult(ArrayList p0) {
    result = p0;
  }

  public void setFieldNum(int p0) {
    fieldNum = p0;
  }

  public void setName(String p0) {
    name = p0;
  }

  public void setVar(String p0) {
    var = p0;
  }

  public void setMessage(String p0) {
    message = p0;
  }

  public void setScope(String p0) {
    scope = p0;
  }

  public void setPagesize(int p0) {
    pagesize = p0;
  }

  public ArrayList getResult() {
    return result;
  }

  public int getFieldNum() {
    return fieldNum;
  }

  public String getName() {
    return name;
  }

  public String getVar() {
    return var;
  }

  public String getMessage() {
    return message;
  }

  public String getScope() {
    return scope;
  }

  public int getPagesize() {
    return pagesize;
  }

  @Override
public void release() {
    iterator = null;
    result = null;
    name = null;
    var = null;
    message = null;
    scope = "";
    row = null;
  }
}
