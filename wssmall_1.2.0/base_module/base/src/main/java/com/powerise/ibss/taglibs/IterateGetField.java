package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/*
 * 描述：字段标签，记录集标签的子标签
 * 版本：1.0
 * 作者：文小军
 */

public class IterateGetField
    extends TagSupport {

  private String name;
  private String replace;

  @Override
public int doStartTag() throws JspException {

    IterateResultSet parent = (IterateResultSet) findAncestorWithClass(this, IterateResultSet.class); //获取父标签对应的对象
    if (!parent.getstart()){
      parent.start();
      parent.setstart(true);
    }
    parent.getField(name, replace);

    //计算剩余的页面
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setReplace(String replace) {
    this.replace = replace;
  }

}
