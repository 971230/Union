package com.powerise.ibss.taglibs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.Utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SelectTag
    extends TagSupport {

  private String name;
  private String value = "";
  private String others;
  private String action;
  private String returnResultName;
  private boolean noselected = false;
  private String param;
  private String valueField;
  private String nameField;
  private boolean classAction = false;
  private static Logger logger = Logger.getLogger(SelectTag.class);

  @Override
public int doStartTag() throws JspException {
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
        HttpServletRequest) pageContext.getRequest();
    javax.servlet.http.HttpSession session = request.getSession();

    if (null != request.getParameter(name)) {
      value = request.getParameter(name);
    }
    if (null != request.getAttribute(name)) {
      value = (String) request.getAttribute(name);
    }
    if (null != session.getAttribute(name)) {
      value = (String) session.getAttribute(name);

    }
    StringBuffer results = new StringBuffer();

    try {

      DynamicDict dict = new DynamicDict(1);
      HashMap para = new HashMap();
      if (null != param) {
        StringTokenizer st = new StringTokenizer(param, ",");

        SessionOBJ user = null; //增加的用于从user里取参数
        if (session.getAttribute("user") != null) {
          ;
        }
        user = (SessionOBJ) session.getAttribute("user");

        while (st.hasMoreTokens()) {
          String sName = st.nextToken();
          String sValue = st.nextToken();

          //增加的用于取登陆员工和操作点。可以直接在jsp中的selecttag的param里引用。
          if (sValue.trim().equalsIgnoreCase("STAFF-NO") &&
              null != user.getStaffNo()) {
            sValue = user.getStaffNo();
          }
          if (sValue.trim().equalsIgnoreCase("SITE-NO") &&
              null != user.getSiteNo()) {
            sValue = user.getSiteNo();
            //

          }
          if (null != pageContext.getAttribute(sValue)) {
            sValue = (String) pageContext.getAttribute(sValue);
          }
          if (null != request.getParameter(sValue)) {
            sValue = request.getParameter(sValue);
          }
          if (null != request.getAttribute(sValue)) {
            sValue = (String) request.getAttribute(sValue);
          }
          if (null != session.getAttribute(sValue)) {
            sValue = (String) session.getAttribute(sValue);

          }

          para.put(sName, sValue);
        }
        if (classAction) {
          Object arr[] = para.keySet().toArray();
          for (int i = 0; i < arr.length; i++) {
            dict.setValueByName( (String) arr[i], para.get(arr[i]));
          }
        }

      }

      if (action.equals("MSM_002")) {
        SessionOBJ user = null;
        if (session.getAttribute("user") != null) {
          user = (SessionOBJ) session.getAttribute("user");
          dict.setValueByName("STAFF-NO", user.getStaffNo());
        }
      }

      dict.m_ActionId = action;
      if (!classAction) {
        dict.flag = 1;
        nameField = nameField.toUpperCase();
        valueField = valueField.toUpperCase();
      }
      dict.setValueByName("parameter", para);

      dict = ActionDispatch.dispatch(dict);
      if (dict.flag < 0) {
        //异常信息
        logger.info(dict.flag + "    错误信息：" + dict.msg + "异常信息：" +
                           dict.exception);
      }

      Object obj;
      if (returnResultName != null) {
        obj = dict.getValueByName(returnResultName, false);
      }
      else {
        obj = dict.getValueByName(action, false);

      }
      results.append("<select " + others + " name=\"" + name + "\">");
      if (noselected) {
        results.append(Utility.isoToGBK("<option>请选择……</option>"));

      }
      if (obj.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) {
        ArrayList al = (ArrayList) obj;
        HashMap hm = new HashMap();
        for (int i = 0; i < al.size(); i++) {
          hm = (HashMap) al.get(i);
          if (i == 0 || value.equals(hm.get(valueField))) {
            pageContext.setAttribute(name, hm.get(valueField));
          }
          if (value.equals(hm.get(valueField))) {
            results.append("<option selected value=\"" + hm.get(valueField) +
                           "\">" +
                           hm.get(nameField) + "</option>");
          }
          else {
            results.append("<option value=\"" + hm.get(valueField) + "\">" +
                           hm.get(nameField) + "</option>");

          }
        }
      }
      results.append("</select>");

      pageContext.getOut().print(results.toString());
      results.setLength(0);
      dict.destroy();
    }
    catch (Exception e) {
      throw new JspException("在输出静态数据下拉框时，出现异常！" + e.getMessage() + " name=" +
                             name + " value=" + value);
    }
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setname(String name) {
    this.name = name;
  }

  public void setvalue(String value) {
    this.value = value;
  }

  public void setaction(String action) {
    this.action = action;
  }

  public void setothers(String others) {
    this.others = others;
  }

  public void setparam(String param) {
    this.param = param;
  }

  public void setreturnResultName(String returnResultName) {
    this.returnResultName = returnResultName;
  }

  public void setnoselected(boolean noSelected) {
    this.noselected = noSelected;
  }

  public void setclassAction(boolean classAction) {
    this.classAction = classAction;
  }

  public void setnameField(String nameField) {
    this.nameField = nameField;
  }

  public void setvalueField(String valueField) {
    this.valueField = valueField;
  }

}
