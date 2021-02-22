package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/*
 * 描述：获取任一bean的属性，并作为查询参数
 * 版本：1.0
 * 作者：文小军
 */

public class BeanParam
    extends BodyTagSupport {

  private String name;

/*
  public int doAfterBody() throws JspException {
    logger.info(bodyContent.getString());
    BodyContent body = getBodyContent();
    JspWriter out = body.getEnclosingWriter();
    System.out.print(body.getString());

    return (SKIP_BODY); //终止jsp正文处理

  }*/

@Override
public int doEndTag() throws JspException {
  if (bodyContent != null) {
    try {
      String content = bodyContent.getString();
      content = filter(content);

      // now clear the original body content and write back
      // the filtered content
      bodyContent.clearBody();
      bodyContent.print(content);

      // finally, write the contents of the bodyContent object back to the
      // original JspWriter (out) instance
      bodyContent.writeOut(getPreviousOut());
    } catch (IOException ioe) {
      throw new JspTagException(ioe.getMessage());
    }
  }

  return EVAL_PAGE;
}


  private String filter(String s) {
      StringBuffer buf = new StringBuffer(s.length());

      // loop through every character and replace if necessary
      int length = s.length();
      for (int i = 0; i < length; i++) {
        switch (s.charAt(i)) {
          case '<' :
            buf.append("&lt;");
            break;
          case '>' :
            buf.append("&gt;");
            break;
          case '&' :
            buf.append("&amp;");
            break;
          default :
            buf.append(s.charAt(i));
        }
      }

      return buf.toString();
    }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

}
