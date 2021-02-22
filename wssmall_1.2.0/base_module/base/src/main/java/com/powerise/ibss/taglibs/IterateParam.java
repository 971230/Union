package com.powerise.ibss.taglibs;

import com.powerise.ibss.system.SessionOBJ;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/*
 * 描述：查询参数标签，记录集标签的子标签
 * 版本：1.0
 * 作者：文小军
 */

public class IterateParam
    extends TagSupport {

  private String name;
  private String value="";
  private boolean isSessionValue = false;
  private static Logger logger = Logger.getLogger(IterateParam.class);
  @Override
public int doStartTag() throws JspException {
    try {
    IterateResultSet parent = (IterateResultSet) findAncestorWithClass(this, IterateResultSet.class); //获取父标签对应的对象
    if (!parent.getstart()){
      parent.addParam(name, value);
    }
    }
    catch
        (Exception e) {logger.info("添加参数出错！");}
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setIsSessionValue(boolean isSessionValue) {
    this.isSessionValue = isSessionValue;
  }

  public void setValue(String value) {
    if (null!=pageContext.getRequest().getAttribute(value)) this.value=((String)pageContext.getRequest().getAttribute(value)).trim();
    if (null!=pageContext.getRequest().getParameter(value)) this.value=pageContext.getRequest().getParameter(value).trim();

    if (isSessionValue) {

      SessionOBJ user = null;
      if (pageContext.getSession().getAttribute("user") != null) {
        user = (SessionOBJ) pageContext.getSession().getAttribute("user");
        if (value.equals("bureau_no"))
          this.value = user.getBureauNo();
        if (value.equals("site_no"))
          this.value = user.getSiteNo();
      }
    }
  }

}
