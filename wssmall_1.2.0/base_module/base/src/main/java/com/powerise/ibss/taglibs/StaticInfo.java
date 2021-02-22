package com.powerise.ibss.taglibs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.Utility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

public class StaticInfo
    extends TagSupport {

  private String name;
  private String value = "";
  private String tablename = "";
  private String fieldname = "";
  private String others = "";
  private String action = "";
  private boolean noselected;
  private String param;
  private String valueField;
  private String nameField;
  private String selectID = "";
  private static Logger logger = Logger.getLogger(StaticInfo.class);
  @Override
public int doStartTag() throws JspException {
    javax.servlet.http.HttpServletRequest request = (javax.servlet.http.
        HttpServletRequest) pageContext.getRequest();
    javax.servlet.http.HttpSession session = request.getSession();

    if (null != request.getAttribute(name)) {
      value = (String) request.getAttribute(name);
    }
    if (null != session.getAttribute(name)) {
      value = (String) session.getAttribute(name);

    }
    StringBuffer results = new StringBuffer();

    ArrayList static_info = null;
    DynamicDict dict = new DynamicDict(1);
    String rs_name = "";
    try {
//logger.info("param=" + param);
      if ( (param != null || valueField != null || nameField != null) &&
          !action.equalsIgnoreCase("MSM_002")) {

        results.append("<select " + others + " id=\"" + selectID +
                       "\" name=\"" + name + "\">");
        if (noselected) {
          results.append(Utility.isoToGBK(
              "<option value=\"\">请选择……</option>"));
        }
        HashMap para = new HashMap();
        if (null != param) {
          StringTokenizer st = new StringTokenizer(param, ",");
          while (st.hasMoreTokens()) {
            String sName = st.nextToken();
            String sValue = st.nextToken();

//logger.info("action=" + action + ",name=[" + sName + "],value=[" + sValue + "]");
            para.put(sName, sValue);
          }
        }

        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
          String s1 = (String) en.nextElement();
          String s2 = request.getParameter(s1);
          para.put(s1, s2);
        }

        dict.m_ActionId = action;
        dict.flag = 1;
        dict.setValueByName("parameter", para);
        dict = ActionDispatch.dispatch(dict);
        if (dict.flag < 0) {
          //异常信息
          logger.info(dict.flag + "    ACTION_ID:" + action + "，错误信息：" + dict.msg + "异常信息：" +
                             dict.exception);
        }
        Object obj = dict.getValueByName(action, false);
        if (obj.getClass().getName().equalsIgnoreCase(
            "java.util.ArrayList")) {
          ArrayList al = (ArrayList) obj;
          HashMap hm = new HashMap();
          for (int i = 0; i < al.size(); i++) {

            hm = (HashMap) al.get(i);
            if ((value != null) && (value.equals(hm.get(valueField)))) {
              results.append("<option selected value=\"" +
                             hm.get(valueField) +
                             "\">" +
                             hm.get(nameField) + "</option>");
            }
            else {
              results.append("<option value=\"" + hm.get(valueField) +
                             "\">" +
                             hm.get(nameField) + "</option>");

            }
          }
        }
        results.append("</select>");
      }
      else {
        if (action != null && action.equalsIgnoreCase("MSM_002")) {
          dict.m_ActionId = action;
          SessionOBJ user = (SessionOBJ) session.getAttribute("user");
          dict.setValueByName("STAFF-NO", user.getStaffNo());
          rs_name = "BUREAU";
          dict = ActionDispatch.dispatch(dict);
          //logger.info(dict.m_Values);
          if (dict.getValueByName(rs_name, false).equals("")) {
            dict.destroy();
            results.append("<select " + others + " id=\"" + selectID +
                           "\" name=\"" + name + "\">");
            results.append("</select>");
            pageContext.getOut().print(results.toString());
            results.setLength(0);
            return (EVAL_PAGE);
          }
          static_info = (ArrayList) dict.getValueByName(rs_name);
          results.append("<select " + others + " id=\"" + selectID +
                         "\" name=\"" + name + "\">");
          if (noselected) {
            results.append("<option value=\"\">任意区域...</option>");

          }
          HashMap tmp;
          for (int i = 0; i < static_info.size(); i++) {
            tmp = (HashMap) static_info.get(i);
            if (tmp.get("bureau_no") != null && tmp.get("bureau_name") != null) {
              String option_value = tmp.get("bureau_no").toString();
              String option_text = tmp.get("bureau_name").toString().trim();
              if (option_value.equals(value)) {
                results.append("<option selected value=\""
                               + option_value + "\">" +
                               option_text + "</option>");
              }
              else {
                results.append("<option value=\""
                               + option_value + "\">" +
                               option_text + "</option>");
              }
            }
          }
          results.append("</select>");
        }
        else {
          dict.m_ActionId = "MSM_151";
          if (action != null && action.length() > 0) {
            dict.setValueByName("action_id", action);
            rs_name = action.toUpperCase();
          }
          else {
            dict.setValueByName("table_name", tablename);
            dict.setValueByName("field_name", fieldname);
            rs_name = tablename.toUpperCase();
          }

          dict = ActionDispatch.dispatch(dict);
          //logger.info(dict.m_Values);
          if (dict.getValueByName(rs_name, false).equals("")) {
            dict.destroy();
            results.append("<select " + others + " id=\"" + selectID +
                           "\" name=\"" + name + "\">");
            results.append("</select>");
            pageContext.getOut().print(results.toString());
            results.setLength(0);
            return (EVAL_PAGE);
          }
          static_info = (ArrayList) dict.getValueByName(rs_name);
          results.append("<select " + others + " id=\"" + selectID +
                         "\" name=\"" + name + "\">");
          if (noselected) {
            results.append("<option value=\"\">请选择……</option>");

          }
          HashMap tmp;
          for (int i = 0; i < static_info.size(); i++) {
            tmp = (HashMap) static_info.get(i);
            if (tmp.get("value") != null && tmp.get("text") != null) {
              String option_value = tmp.get("value").toString();
              String option_text = tmp.get("text").toString().trim();
              if (option_value.equals(value)) {
                results.append("<option selected value=\""
                               + option_value + "\">" +
                               option_text + "</option>");
              }
              else {
                results.append("<option value=\""
                               + option_value + "\">" +
                               option_text + "</option>");
              }
            }
          }
          results.append("</select>");
        }
      }
      pageContext.getOut().print(results.toString());
      results.setLength(0);
      dict.destroy();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new JspException("在输出静态数据下拉框时，出现异常！" + e.getMessage());
    }
    return (EVAL_PAGE);
  }

  //以下为属性设置
  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setTablename(String tablename) {
    this.tablename = tablename;
  }

  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setOthers(String others) {
    this.others = others;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public void setNoselected(boolean noSelected) {
    this.noselected = noSelected;
  }

  public void setNameField(String nameField) {
    this.nameField = nameField.toUpperCase();
  }

  public void setValueField(String valueField) {
    this.valueField = valueField.toUpperCase();
  }

  @Override
public void setId(String id) {
    this.selectID = id;
  }

  public String getAction() {
    return action;
  }

}