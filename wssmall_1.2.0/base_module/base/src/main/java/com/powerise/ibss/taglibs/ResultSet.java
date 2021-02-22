package com.powerise.ibss.taglibs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * 描述：记录集标签
 * 版本：1.0
 * 作者：文小军
 */

public class ResultSet
    extends TagSupport {

  private String methodName;
  private HashMap hm = new HashMap();
  private HashMap param = new HashMap();
  private boolean commExcute;
  private static Logger logger = Logger.getLogger(ResultSet.class);
  @Override
public int doStartTag() throws JspException {

    commExcute = false;
    hm.clear();
    param.clear();
    //计算剩余的页面
    return (EVAL_PAGE);
  }

  public void start() {
    try {

      DynamicDict dy = new DynamicDict(1);
      dy.m_ActionId = methodName;
      dy.flag = 1;

      Object arr[] = param.keySet().toArray();
        for (int i = 0; i < arr.length; i++) {
          dy.setValueByName( (String) arr[i], param.get(arr[i]));
        }

      dy.setValueByName("parameter", param);
      dy = ActionDispatch.dispatch(dy);
      if (dy.flag < 0) {
        //异常信息
        logger.info("错误信息：" + dy.msg + "异常信息：" + dy.exception);
      }

      Object obj = dy.getValueByName(methodName, false);

      if (obj.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) {
        ArrayList a = (ArrayList) obj;
        hm = (HashMap) a.get(0);
      }
    }
    catch (Exception e) {
      logger.info("没有符合条件的记录");
    }
  }

  public void addParam(String name, String value) {
    param.put(name, value);
  }

  public String getField(String name, String replace) {
    String str = (String) hm.get(name) == null ? "" : (String) hm.get(name);
    if (null != replace) {
      StringTokenizer st = new StringTokenizer(replace, ",");
      while (st.hasMoreTokens()) {

        String sName = st.nextToken();
        String sValue = st.nextToken();
        if (sName != null && str.equals(sName)) {
          str = sValue;
          break;
        }
      }
    }
    //if (str.equals(""))
    //  str = "&nbsp;";

    return str;
  }

  //以下为属性设置
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public boolean getstart() {
    return this.commExcute;
  }

  public void setstart(boolean commExcute) {
    this.commExcute = commExcute;
  }

  @Override
public void release() {

    super.release();
    hm = null;
    param = null;

  }

}
