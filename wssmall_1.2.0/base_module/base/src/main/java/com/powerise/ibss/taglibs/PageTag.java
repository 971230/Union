package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.io.IOException;

/*
 * 描述：分页标签
 * 版本：1.0
 * 作者：文小军
 */

public class PageTag
    extends TagSupport {

  private String name;//名称
  private int currentpage = 1; //当前页码
  private int pagesize = 10; //页大小，也就是每页显示的记录数
  private int sum = 0; //总记录数
  private int pages = 8; //显示多少页数字
  private static Logger logger = Logger.getLogger(PageTag.class);
  //遇到开始标签时执行该覆盖类
  @Override
public int doStartTag() throws JspException {
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
        HttpServletRequest) pageContext.getRequest();
    StringBuffer results = new StringBuffer();

    pagesize = (request.getParameter(name + "pagesize") == null) ? pagesize :
        java.lang.Integer.parseInt(request.getParameter(name + "pagesize"));
    currentpage = (request.getParameter(name + "currentpage") == null) ? 1 :
        java.lang.Integer.parseInt(request.getParameter(name + "currentpage"));


    //存在多个该标签时，只打印一次javascript包含
    if (null == pageContext.findAttribute("hasUtil.js")) {
      pageContext.setAttribute("hasUtil.js", "true");
      results.append(
          "<script language=javascript src='./js/util.js'></script>");
    }

    try {
      pageContext.getOut().print(results.toString());
    }
    catch (IOException ioe) {
      logger.info("Error in SelectDateTag : " + ioe);
    }
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //遇到结束标签时执行该覆盖类
  @Override
public int doEndTag() throws JspException {

    StringBuffer results = new StringBuffer();

    double d = (double) currentpage / pages;
    int iBottom = (int) java.lang.Math.ceil(d);
    int iPages = (int) java.lang.Math.ceil( (double) sum / pagesize);
    int iTop = iBottom * pages > iPages == true ?
        iPages : iBottom * pages;

        if (currentpage > iPages)
      currentpage = iPages;

    try {
      results.append("<font size='2'>共<b>" + sum + "</b>条记录     ");
      results.append("页次 <b>" + currentpage + "/" + iPages +
                     "</b></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

      results.append(
          "<font size='2'>页行数：</font><input maxLength=2 class=\"txtSolid\" name=" + name + "pagesize size=2 value=");
      results.append(pagesize);

      results.append(
          ">&nbsp;<input type=\"button\" class=\"btnSolid\" onClick=\"javascript:ok(this.form.name,\'" +
          name + "\');\" value=\"OK\">   |");

      if (currentpage <= 1)
        results.append("<font face=webdings >9</font> ");
      else {
        results.append("<a href=javascript:selData(\"" + name + "\",1," +
                       pagesize + ")");
        results.append(" title=\"首 页\" ><font face=webdings >9</font> </a>");
      }
      if (currentpage <= 1)
        results.append("<font face=webdings >7</font> ");
      else {
        results.append("<a href=javascript:selData(\"" + name + "\"," +
                       (currentpage - 1) + "," + pagesize + ")");
        results.append(" title=\"第");
        results.append(currentpage - 1);
        results.append(" 页\"><font face=webdings >7</font> </a>");

      }

      if (iBottom > 1)
        results.append("<a href=javascript:selData(\"" + name + "\"," +
                       ( (iBottom - 1) * pages) + "," + pagesize +
                       ")> ← </a>");

      for (int i = ( (iBottom - 1) * pages + 1); i <= iTop; i++) {
        if (currentpage == i)
          results.append("<b><font color=\"#FF0000\">" + i + "</font></b> ");
        else
          results.append("<a href=javascript:selData(\"" + name + "\"," + i +
                         "," +
                         pagesize + ")><b>" + i + "</b></a> ");
      }
      if (iTop < iPages)
        results.append("<a href=javascript:selData(\"" + name + "\"," +
                       (iTop + 1) + "," + pagesize + ")>→ </a>");

      if (currentpage == iPages)
        results.append(" <font face=webdings >8</font> ");
      else {
        results.append("<a href=javascript:selData(\"" + name + "\"," +
                       (currentpage + 1) + "," + pagesize + ")");
        results.append(" title=\"第" + (currentpage + 1) +
                       "页\"><font face=webdings >8</font> </a> ");

      }

      if (currentpage == iPages)
        results.append("<font face=webdings >:</font>|");
      else {
        results.append("<a href=javascript:selData(\"" + name + "\"," +
                       iPages + "," + pagesize + ")");
        results.append(" title=\"尾 页\" ><font face=webdings >:</font></a>|");
      }

      results.append(
          "   <input maxLength=2 class=\"txtSolid\" name=" + name + "currentpage size=2 value=\"" +
          currentpage +
          "\">&nbsp;<input type=\"button\" class=\"btnSolid\" onClick=\"javascript:go(this.form.name,\'" +
          name + "\'," + iPages + ");\" value=\"GO\">");

      pageContext.getOut().print(results.toString());
    }
    catch (IOException ioe) {
      logger.info("Error in SelectDateTag : " + ioe);
    }
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setSum(int sum) {
    this.sum = sum;
  }

  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }

  public int getPagesize() {
    return this.pagesize;
  }

  public int getCurrentpage() {
    return this.currentpage;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

}
