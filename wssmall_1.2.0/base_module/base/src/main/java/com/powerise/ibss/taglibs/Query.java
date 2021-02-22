package com.powerise.ibss.taglibs;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.ErrorOBJ;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.GlobalNames;

public class Query
    extends TagSupport {
  private HashMap condition;
  private String service;
  private boolean dynamic;
  private boolean execute = true;
  private String scope;
  private int pagesize;
  private int cover; // 当自带的参数与request中的参数同名时，0：用自带的参数，1：用request中的参数
  private static Logger logger = Logger.getLogger(Query.class);

  @Override
public int doStartTag() throws JspException {
    //保存PAGESIZE，打印ResultSet时，计算空白行用到
    if (pagesize != 0) {
      pageContext.getRequest().setAttribute("PAGESIZE", String.valueOf(pagesize));
    }
    if (!execute) {
      return Tag.EVAL_PAGE;
    }
    HashMap dyCondition = new HashMap();
    if (condition != null) {
      Object keys[] = condition.keySet().toArray();
      for (int i = 0; i < keys.length; i++) {
        if (dynamic) { //动态SQL字段区分大小写
          dyCondition.put(keys[i].toString(), condition.get(keys[i]));
        }
        else {
          dyCondition.put(keys[i].toString().toUpperCase(),
                          condition.get(keys[i]));
        }
      }
      //condition.clear();
      //condition =null;
    }
    //得到网页提交的参数
    HashMap reqParas = getRequestParas(pageContext.getRequest());
    if (reqParas != null) {
      Object keys[] = reqParas.keySet().toArray();
      for (int i = 0; i < keys.length; i++) {
        if ( (!dyCondition.containsKey(keys[i])) ||
            (dyCondition.containsKey(keys[i]) && cover == 1)) {
          if (dynamic) { //动态SQL字段区分大小写
            dyCondition.put(keys[i].toString(), reqParas.get(keys[i]));
          }
          else {
            dyCondition.put(keys[i].toString().toUpperCase(),
                            reqParas.get(keys[i]));

          }
        }
      }
      reqParas.clear();
      reqParas = null;
    }
    //得到登陆员工信息
    HttpSession session = pageContext.getSession();
    HashMap userInfo = null;
//    HashMap userInfo = getUserInfo(session);
    if (userInfo != null) {
      Object keys[] = userInfo.keySet().toArray();
      for (int i = 0; i < keys.length; i++) {
        if (dynamic) { //动态SQL字段区分大小写
          dyCondition.put(keys[i].toString(), userInfo.get(keys[i]));
        }
        else {
          dyCondition.put(keys[i].toString().toUpperCase(), userInfo.get(keys[i]));
        }
      }
      userInfo.clear();
      userInfo = null;
    }
    try {
      DynamicDict aDict = new DynamicDict(1);
      if (dynamic) {
        aDict.setValueByName("parameter", dyCondition);
      }
      else {
        aDict.m_Values = dyCondition;
        //logger.info("the data is :"+dyCondition);
      }
//logger.info("dyCondition=" + dyCondition);
//logger.info("Query step 0");

      if (pagesize != 0 && aDict.getValueByName("PAGESIZE", false).equals("")) {
//logger.info("Query step 01");
        aDict.setValueByName("PAGESIZE", String.valueOf(pagesize));
        if (dyCondition.get("currentpage") != null) { //动态SQL的特殊处理
          aDict.setValueByName("CURRENTPAGE", dyCondition.get("currentpage"));
        }
        else {
          aDict.setValueByName("CURRENTPAGE", "1");
        }
      }
//logger.info("Query step 02");
      if (dynamic) {
        aDict.flag = 1;
      }
      aDict.m_ActionId = service;
//logger.info("Query step 03");
      aDict = ActionDispatch.dispatch(aDict);
//logger.info("Query step 04, aDict.flag=" + aDict.flag + "--" + aDict.exception + "--" + aDict.msg);
      if (aDict.flag < 0) {
        ErrorOBJ error = new ErrorOBJ(aDict.flag, aDict.msg, aDict.exception);
        //pageContext.getRequest().setAttribute(GlobalNames.ERROR_ATTR, error);
        pageContext.getRequest().setAttribute("Error", error);
        return (EVAL_PAGE);
      }
//logger.info("Query step 1    m_Values=" + aDict.m_Values);
      HashMap retHash = aDict.m_Values;
      Object keys[] = retHash.keySet().toArray();
      ArrayList dummyList = new ArrayList(); //虚拟数据集
      HashMap dummyMap = new HashMap();
      for (int i = 0; i < keys.length; i++) {
        if (retHash.get(keys[i])instanceof java.util.ArrayList) {
          pageContext.getRequest().setAttribute(keys[i].toString().toUpperCase(),
                                                retHash.get(keys[i]));
        }
        else {
          dummyMap.put(keys[i].toString().toUpperCase(), retHash.get(keys[i]));
        }
      }
//logger.info("Query step 2  dummyMap.size()=" + dummyMap.size());
      if (!aDict.getValueByName("SUM", false).equals("")) {
        pageContext.getRequest().setAttribute("SUM", aDict.getValueByName("SUM"));
      }
      dummyList.add(dummyMap);
      //添加虚拟数据集
      pageContext.getRequest().setAttribute("DATA", dummyList);
//logger.info("Query service=" + service + ",dummyList.size=" + dummyList.size());
      //logger.info("return :"+retHash);
    }
    catch (FrameException e) {
      ErrorOBJ err = new ErrorOBJ(e.getErrorCode(), e.getErrorMsg());
      pageContext.getRequest().setAttribute("Error", err);
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return (EVAL_PAGE);
  }

  private HashMap getRequestParas(ServletRequest request) {
    Enumeration paranames = request.getParameterNames();
    HashMap reqParas = null;
    while (paranames.hasMoreElements()) {
      if (reqParas == null) {
        reqParas = new HashMap();
      }
      String paraName = paranames.nextElement().toString();
      if (paraName.toUpperCase().startsWith(GlobalNames.BEGING_TAG.toUpperCase())) {
        reqParas.put(paraName.substring(GlobalNames.BEGING_TAG.length()),
                     request.getParameter(paraName));
      }
    }
    return reqParas;
  }

  private HashMap getUserInfo(HttpSession session) {
    HashMap userInfo = null;
logger.info(session.getAttribute("user"));
    if (session.getAttribute("user") != null) {
      userInfo = new HashMap();
logger.info(session.getAttribute("user"));
      SessionOBJ user = (SessionOBJ) session.getAttribute("user");
      userInfo.put("SITE-NO", user.getSiteNo());
      userInfo.put("SITE-NAME", user.getSiteName());
      userInfo.put("BUREAU-NO", user.getBureauNo());
      userInfo.put("BUREAU-NAME", user.getBureauName());
      userInfo.put("STAFF-NO", user.getStaffNo());
      userInfo.put("STAFF-NAME", user.getStaffName());
      userInfo.put("AUTH-LEVEL", user.getAuthLevel());
    }
    return userInfo;
  }

  @Override
public int doEndTag() throws JspException {
    return (EVAL_PAGE);
  }

  public void setCondition(HashMap p0) {
    condition = p0;
  }

  public void setService(String p0) {
    service = p0;
  }

  public void setScope(String p0) {
    scope = p0;
  }

  public void setCover(int p0) {
    cover = p0;
  }

  public void setPagesize(int p0) {
    pagesize = p0;
  }

  public void setDynamic(boolean p0) {
    dynamic = p0;
  }

  public void setExecute(boolean p0) {
    execute = p0;
  }

  public HashMap getCondition() {
    return condition;
  }

  public String getService() {
    return service;
  }

  public String getScope() {
    return scope;
  }

  public int getCover() {
    return cover;
  }

  public int getPagesize() {
    return pagesize;
  }

  public boolean getDynamic() {
    return dynamic;
  }

  public boolean getExecute() {
    return execute;
  }

  @Override
public void release() {
    service = null;
    scope = null;
    //if(condition !=null){ condition.clear(); condition =null;}
  }
}
