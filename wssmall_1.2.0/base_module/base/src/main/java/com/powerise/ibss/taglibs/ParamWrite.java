package com.powerise.ibss.taglibs;

import com.powerise.ibss.system.SessionOBJ;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/*
 * 描述：获取任一bean的属性，并显示
 * 版本：1.0
 * 作者：文小军
 */

public class ParamWrite
    extends TagSupport {

  private String name;
  private String value="";
  private String valuefrom="";
  private static Logger logger = Logger.getLogger(ParamWrite.class);
  @Override
public int doEndTag() throws JspException {

    if (valuefrom.equals("request")) {
      String tmpvalue = "";
      if (null != pageContext.getRequest().getAttribute(value))
        tmpvalue = ( (String) pageContext.getRequest().getAttribute(value)).
            trim();
      if (null != pageContext.getRequest().getParameter(value))
        tmpvalue = pageContext.getRequest().getParameter(value).trim();
      if (tmpvalue.equals(""))
        value = "";
      else
        value = tmpvalue;
    }

    if (valuefrom.equals("session")) {
      SessionOBJ user = null;
      if (pageContext.getSession().getAttribute("user") != null) {
        user = (SessionOBJ) pageContext.getSession().getAttribute("user");
        if (value.equals("bureau_no"))
          value = user.getBureauNo();
        if (value.equals("site_no"))
          value = user.getSiteNo();
        if (value.equals("staff_no"))
          value = user.getStaffNo();
      }
      else
        value = "";
    }

    StringBuffer results = new StringBuffer();
    try {
      results.append(value);
      pageContext.getOut().print(results.toString());
    }
    catch
        (Exception e) {
      logger.info("设置传递参数出错！");
    }
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setValuefrom(String valuefrom) {
    this.valuefrom = valuefrom;
  }

  public void setValue(String value) {
    this.value = value;
}

}
