package com.powerise.ibss.taglibs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class JSArrayTag
    extends TagSupport {
	private static Logger logger = Logger.getLogger(JSArrayTag.class);
  private String arrayName;
  private String action;
  private String param;

  @Override
public int doStartTag() throws JspException {

    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
         HttpServletRequest) pageContext.getRequest();

    StringBuffer results = new StringBuffer();

    DynamicDict dict = new DynamicDict(1);
    try {
      HashMap para = new HashMap();
      if (null != param) {
        StringTokenizer st = new StringTokenizer(param, ",");
        while (st.hasMoreTokens()) {
          String sName = st.nextToken();
          String sValue = st.nextToken();
          String sCond = request.getParameter(sValue);
          if (sCond!=null&&!sCond.equals(""))
            para.put(sName, sCond);
        }
      }

      dict.m_ActionId = action;
      dict.flag = 1;
      dict.setValueByName("parameter", para);
      dict = ActionDispatch.dispatch(dict);
      if (dict.flag < 0) {
        //异常信息
        logger.info(dict.flag + "    错误信息：" + dict.msg + "异常信息：" +
                           dict.exception);
      }

      Object obj = dict.getValueByName(action, false);

      results.append("<script>");

      if (obj.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) {
        ArrayList al = (ArrayList) obj;
        HashMap hm = new HashMap();
        for (int i = 0; i < al.size(); i++) {
          hm = (HashMap) al.get(i);
          results.append(arrayName + "[" + i + "] = new TreeNode(\"" +
                         (String) hm.get("KNOW_SORT_CODE") + "\", \"" +
                         (String) hm.get("KNOW_SORT_NAME") + "\", \"" +
                         (String) hm.get("UPP_SORT_CODE") +"\");");
        }
      }
      results.append("</script>");

      pageContext.getOut().print(results.toString());
      results.setLength(0);
      dict.destroy();
    }
    catch (Exception e) {
      throw new JspException("在输出JS数组时，出现异常！" + e.getMessage());
    }
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setArrayName(String arrayName) {
    this.arrayName = arrayName;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setParam(String param) {
    this.param = param;
  }

}