package com.powerise.ibss.taglibs;

import com.powerise.ibss.util.Utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;

public class Field
    extends TagSupport {
  private String item;
  private String replace;
  private String fontcolor = "";

  @Override
public int doStartTag() throws JspException {
    //获取父标记对应的对象
    Result parent = (Result) findAncestorWithClass(this, Result.class);
    String arr[] = Utility.split(item, ".");
    if (arr.length != 2) {
      throw new JspException("Row tag is error(example：rowname.fieldname(s))");
    }
    String rsName = arr[0];
    String fieldNames = arr[1];
    StringBuffer out = new StringBuffer();
    HashMap row = (HashMap) pageContext.getRequest().getAttribute(rsName);
    //logger.info("Row........................."+row);
    String rowFields[] = Utility.split(fieldNames, ",");
    if (rowFields != null) {
      String key;
      String value = "";
      for (int i = 0; i < rowFields.length; i++) {
        Object obj = row.get(rowFields[i].toUpperCase());
        if (obj != null) {
          if (replace != null) {
            if (replace != null && replace.length() > 2) {
              String[] replaces = Utility.split(
                  replace.substring(1, replace.length() - 1), "][");
              String[] reps;
              for (int j = 0; j < replaces.length; j++) {
                reps = Utility.split(replaces[j], ",");
                if (reps.length == 2) {
                  if (reps[0].equals(obj.toString().trim())) {
                    obj = reps[1];
                    break;
                  }
                }
              }
            }
          }
          try {
            //System.out.print(rsName+"."+rowFields[i]+"="+obj);
            if (!fontcolor.equals(""))
              pageContext.getOut().print("<font color='" + fontcolor + "'>" + obj + "</font>");
            else
              pageContext.getOut().print(obj);
          }
          catch (IOException io) {
            throw new JspException(io.getMessage());
          }
        }
      }
    }
    try {
      pageContext.getOut().print(out.toString());
      out.setLength(0);
    }
    catch (IOException io) {
      throw new JspException(io.getMessage());
    }
    return (EVAL_PAGE);
  }

  public void setFontColor(String p0) {
    fontcolor = p0;
  }

  public void setItem(String p0) {
    item = p0;
  }

  public void setReplace(String p0) {
    replace = p0;
  }

  public String getFontColor() {
    return fontcolor;
  }

  public String getItem() {
    return item;
  }

  public String getReplace() {
    return replace;
  }

  @Override
public void release() {
    replace = "";
    fontcolor = "";
    item = null;
  }
}
