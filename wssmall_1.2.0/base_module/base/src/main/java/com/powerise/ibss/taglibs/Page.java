package com.powerise.ibss.taglibs;

import com.powerise.ibss.util.GlobalNames;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 分页显示标签。可以指定页码、页大小进行显示。可以动态跳到指定页面。
 */
public class Page
    extends TagSupport {
  private String name;
  private int currentpage = 1;
  private int pagesize = 10;
  private int sum = 0;
  private int pages = 8;

  private HashMap hashmap;
  private StringBuffer html;
  private String url;
  /**
      @return int
      @throws javax.servlet.jsp.JspException
   */
  @Override
public int doStartTag() throws JspException {
    if (name == null) {
      name = GlobalNames.BEGING_TAG;
    }
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
        HttpServletRequest) pageContext.getRequest();
    if (sum == 0 && request.getAttribute("SUM") != null) {
      sum = Integer.parseInt(request.getAttribute("SUM").toString());

    }

//logger.info("Page SUM ----------:" + sum);
    url = request.getRequestURI();
    //if (request.getQueryString() != null) {
    hashmap = new HashMap();
    //得到上页面传过来的参数列表，并用于后续页面使用
    Enumeration en = request.getParameterNames();
    while (en.hasMoreElements()) {
      Object key = en.nextElement();
      String value = request.getParameter(key.toString());
      if (value != null) {
        hashmap.put(key, value);
      }
    }
    //logger.info("如参："+hashmap);
    //}
    pagesize = (request.getParameter(name + "pagesize") == null) ? pagesize :
        java.lang.Integer.parseInt(request.getParameter(name + "pagesize"));
    currentpage = (request.getParameter(name + "currentpage") == null) ? 1 :
        java.lang.Integer.parseInt(request.getParameter(name + "currentpage"));
    double dLabour = (double) currentpage / pages;
    int iBottom = (int) java.lang.Math.ceil(dLabour);
    int iLabourPages = (int) java.lang.Math.ceil( (double) sum / pagesize);
    int iTop = iBottom * pages > iLabourPages == true ?
        iLabourPages : iBottom * pages;

    if (currentpage > iLabourPages) {
      currentpage = iLabourPages;

    }
    html = new StringBuffer();
    html.append(
        "<table align=right><tr><td align=right><input type=hidden name='" +
        GlobalNames.BEGING_TAG + "pagesize' value='" + pagesize + "'>");
    html.append("共 " + sum + " 行记录     ");
    html.append("页次 " + currentpage + "/" + iLabourPages +
                "(每页 " + pagesize + " 行)");
    return (EVAL_PAGE);
  }

  /**
      @return int
      @throws javax.servlet.jsp.JspException
   */
  @Override
public int doEndTag() throws JspException {
    double dLabour = (double) currentpage / pages;
    int iBottom = (int) java.lang.Math.ceil(dLabour);
    int iLabourPages = (int) java.lang.Math.ceil( (double) sum / pagesize);
    int iTop = iBottom * pages > iLabourPages == true ?
        iLabourPages : iBottom * pages;

    try {
      /*
                html.append(
           "页行数：<input maxLength=2 name=" + name + "pagesize size=2 value=");
                html.append(pagesize);
                html.append(
           "><input type=\"button\" onClick=\"javascript:setPage(this.form.name,\'" +
              name + "\');\" value=\"OK\">   |");
       */
      if (currentpage <= 1) {
        html.append("<font face=webdings >9</font> ");
      }
      else {
        html.append("<a href=javascript:selData(\"" + name + "\",1," +
                    pagesize + ")");
        html.append(" title=\"首 页\" ><font face=webdings >9</font> </a>");
      }
      if (currentpage <= 1) {
        html.append("<font face=webdings >7</font> ");
      }
      else {
        html.append("<a href=javascript:selData(\"" + name + "\"," +
                    (currentpage - 1) + "," + pagesize + ")");
        html.append(" title=\"第");
        html.append(currentpage - 1);
        html.append(" 页\"><font face=webdings >7</font> </a>");

      }
      if (iBottom > 1) {
        html.append("<a href=javascript:selData(\"" + name + "\"," +
                    ( (iBottom - 1) * pages) + "," + pagesize +
                    ")> ← </a>");
      }
      if (iBottom > 0) {
        for (int i = ( (iBottom - 1) * pages + 1); i <= iTop; i++) {
          if (currentpage == i) {
            html.append("<b><font color=\"#FF0000\">" + i + "</font></b> ");
          }
          else {
            html.append("<a href=javascript:selData(\"" + name + "\"," + i +
                        "," +
                        pagesize + ")><b>" + i + "</b></a> ");
          }
        }
      }
      if (iTop < iLabourPages) {
        html.append("<a href=javascript:selData(\"" + name + "\"," +
                    (iTop + 1) + "," + pagesize + ")>→ </a>");

      }
      if (currentpage == iLabourPages) {
        html.append(" <font face=webdings >8</font> ");
      }
      else {
        html.append("<a href=javascript:selData(\"" + name + "\"," +
                    (currentpage + 1) + "," + pagesize + ")");
        html.append(" title=\"第" + (currentpage + 1) +
                    "页\"><font face=webdings >8</font> </a> ");

      }

      if (currentpage == iLabourPages) {
        html.append("<font face=webdings >:</font>|");
      }
      else {
        html.append("<a href=javascript:selData(\"" + name + "\"," +
                    iLabourPages + "," + pagesize + ")");
        html.append(" title=\"尾 页\" ><font face=webdings >:</font></a>|");
      }

      html.append(
          "   <input maxLength=2 name=" + name +
          "currentpage size=2 class=\"txtSolid\" value=\"" +
          currentpage +
          "\"><input type=\"button\" class=\"btnSolid\" onClick=\"javascript:go(this.form.name,\'" +
          name + "\'," + iLabourPages + ");\" value=\"跳转\">");
      html.append("\n</td></tr></table>");
      appendJavaScript();
      pageContext.getOut().print(html.toString());
      url = null;
      html.setLength(0);
      if (hashmap != null) {
        hashmap.clear();
        hashmap = null;
      }
    }
    catch (IOException ioe) {
      throw new JspException(ioe.toString());
    }
    return (EVAL_PAGE);
  }

  /**
      @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
      @param sum
   */
  public void setSum(int sum) {
    this.sum = sum;
  }

  /**
      @param pagesize
   */
  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }

  /**
      @param pages
   */
  public void setPages(int pages) {
    this.pages = pages;
  }

  private void appendJavaScript() {
    html.append("\n<script language=\"JavaScript\">\n");
    html.append(" function selData(name,page,pagesize) {\n");

    html.append(" var e = document.activeElement;\n");
    html.append(" if (e.tagName != 'A') return;\n");
    html.append(" var el = e;\n");
    html.append(" while (el.tagName != 'FORM') el = el.parentElement;\n");
    html.append(" var form=el.name;\n");

    html.append(
        " var ctlcurrentpage=window.document.getElementById(name+\"currentpage\");\n");
    html.append(
        " var ctlpagesize=window.document.getElementById(name+\"pagesize\");\n");

    html.append(" if (ctlcurrentpage!=null) ctlcurrentpage.value = page;\n");
    html.append(" if (ctlpagesize!=null) ctlpagesize.value = pagesize;\n");

    html.append(" document.forms[form].action=\"" + url + "\";\n");
    html.append(" document.forms[form].submit();\n");
    html.append(" }\n");

    html.append(" function setPage(form,name)\n");
    html.append(" {\n");
    html.append(
        " var ctlpagesize=window.document.getElementById(name+\"pagesize\"); \n");
    html.append(
        " if(ctlpagesize.value==\"\") {ctlpagesize.focus();return false;}\n");
    html.append(" if(isNaN(ctlpagesize.value)){\n  ");
    html.append("     alert(\"页行数必须为数字类型!\");\n");
    html.append("     ctlpagesize.value=1;ctlpagesize.focus();return false;}\n");
    html.append(" if(ctlpagesize.value<=0){ctlpagesize.focus();ctlpagesize.value=1;return false;}\n");

    html.append(" document.forms[form].submit();\n");
    html.append(" }\n");

    html.append(" function go(form,name,maxsize)\n");
    html.append(" {\n");
    html.append(
        " var ctlcurrentpage=window.document.getElementById(name+\"currentpage\");\n");
    html.append(
        " if(ctlcurrentpage.value==\"\") {ctlcurrentpage.focus();return false;}\n");
    html.append(" if(isNaN(ctlcurrentpage.value)){alert(\"页行数必须为数字类型!\");ctlcurrentpage.value=1;ctlcurrentpage.focus();return false;}\n");
    html.append(" if(ctlcurrentpage.value<=0) ctlcurrentpage.value=1;\n");
    html.append(
        " if (ctlcurrentpage.value>maxsize) ctlcurrentpage.value=maxsize; \n");
    html.append(" document.forms[form].action=\"" + url + "\";\n");
    html.append(" document.forms[form].submit();\n");
    html.append(" }\n");
    html.append("</script>\n");
    if (hashmap != null) {
      Object[] arr = hashmap.keySet().toArray();
      for (int i = 0; i < arr.length; i++) {
        String pName = (String) arr[i];
        if (!pName.startsWith(GlobalNames.BEGING_TAG)) {
          pName = GlobalNames.BEGING_TAG + pName;
        }
/*
        html.append("<input type=hidden name='" + pName + "' value='" +
                    (String) hashmap.get(arr[i]) + "'>\n");
*/
      }
    }
  }
}
