package com.powerise.ibss.cs;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class SessionListener
  implements HttpSessionBindingListener
{
  private String mName = null;
  private Object mValue = null;
  private static Logger logger = Logger.getLogger(SessionListener.class);
  public SessionListener(String paramString, Object paramObject)
  {
    this.mName = paramString;
    this.mValue = paramObject;
  }

  @Override
public void valueBound(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
  }

  @Override
public void valueUnbound(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    DynamicDict localDynamicDict = new DynamicDict(0);
    try {
    	if(!(this.mValue instanceof HashMap)){
    		return;
    	}
      HashMap localHashMap =  (HashMap)this.mValue;
      String loginTime = getStrFromMap(localHashMap,"LOGON_TIME");//(String)localHashMap.get("LOGON_TIME");
      String loginIp = getStrFromMap(localHashMap,"LOGON_IP");//(String)localHashMap.get("LOGON_IP");
      String userNo = getStrFromMap(localHashMap,"USER_NO");//(String)localHashMap.get("USER_NO");
      String loginType = getStrFromMap(localHashMap,"LOGIN_TYPE");//(String)localHashMap.get("LOGIN_TYPE");
      if(userNo!=null && !userNo.equals("")  && this.mName.equals("user")){
	      localDynamicDict.flag = 0;
	      localDynamicDict.m_ActionId = "WB_USER_LOGOUT";
	      localDynamicDict.setValueByName("NOTES", "");
	      localDynamicDict.setValueByName("LOGON_TIME", loginTime);
	      localDynamicDict.setValueByName("LOGON_IP", loginIp);
	      localDynamicDict.setValueByName("USER_NO", userNo);
	      localDynamicDict.setValueByName("LOGIN_TYPE", loginType);
	      localDynamicDict = ActionDispatch.dispatch(localDynamicDict);
	      /*logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	      logger.info("valueUnbound");
	      logger.info("SessionName = " + this.mName);
	      logger.info("SessionValue = " + this.mValue.toString());
	      logger.info("Sessiontype = " + loginTime + "USER_NO" + userNo + "LOGIN_TYPE" + loginType);
	      logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
      */
	      logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	      logger.info("valueUnbound");
	      logger.info("SessionName = " + this.mName);
	      logger.info("SessionValue = " + this.mValue.toString());
	      logger.info("Sessiontype = " + loginTime + "USER_NO" + userNo + "LOGIN_TYPE" + loginType);
	      logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	      
	      }
    }
    catch (Exception localException) {
      localException.printStackTrace();
    } finally {
      localDynamicDict.destroy();
    }
  }
  
  private String getStrFromMap(HashMap map,String key){
	  String ret = null;
	  if(map.containsKey(key)){
		  Object obj = map.get(key);
		  if(obj instanceof String){
			  ret = (String)obj;
		  }
	  }
	  return ret;
  }
}
