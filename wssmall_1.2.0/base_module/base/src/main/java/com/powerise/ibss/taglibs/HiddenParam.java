package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/*
 * 描述：获取任一bean的属性，并作为查询参数
 * 版本：1.0
 * 作者：文小军
 */

public class HiddenParam
    extends TagSupport {
	private static Logger logger = Logger.getLogger(HiddenParam.class);
  private String name;
  private String value="";
  private boolean isscriptvalue=false;

  @Override
public int doEndTag() throws JspException {
    StringBuffer results = new StringBuffer();
    try {
      results.append("<input type=\"hidden\" name=\""+name+"\" value=\""+value+"\">");
      if (isscriptvalue){
        results.append("<script language=\"javascript\">");
        results.append("document.all."+name+".value="+value+";");
        results.append("</script>");
      }
      pageContext.getOut().print(results.toString());
    }
    catch
        (Exception e) {
      //logger.info("设置传递参数出错！");
      logger.info("设置传递参数出错！");
    }
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setIsscriptvalue(boolean isscriptvalue) {
    this.isscriptvalue = isscriptvalue;
  }

  public void setValue(String value) {
    if (null!=pageContext.getRequest().getAttribute(value)) this.value=((String)pageContext.getRequest().getAttribute(value)).trim();
    if (null!=pageContext.getRequest().getParameter(value)) this.value=pageContext.getRequest().getParameter(value).trim();
}

}
