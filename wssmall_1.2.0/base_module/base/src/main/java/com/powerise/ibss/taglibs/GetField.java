package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/*
 * 描述：字段标签，记录集标签的子标签
 * 版本：1.0
 * 作者：文小军
 */

public class GetField
    extends TagSupport {
	private static Logger logger = Logger.getLogger(GetField.class);
  private String name;
  private String replace;

  @Override
public int doStartTag() throws JspException {
	  
    try {
    ResultSet parent = (ResultSet) findAncestorWithClass(this, ResultSet.class); //获取父标签对应的对象
    if (!parent.getstart()){
      parent.start();
      parent.setstart(true);
    }
    pageContext.getOut().print(parent.getField(name,replace));
    }
    catch
        (Exception e) {/*logger.info("取"+name+"字段出错！");*/ logger.info("取"+name+"字段出错！");}
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name.toUpperCase();
  }

  public void setReplace(String replace) {
    this.replace = replace;
  }

}