package com.powerise.ibss.system;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


//WEB层程序
public class PrepareThing{
   public static ArrayList getPreThings(HttpServletRequest request) throws FrameException{
      //从员工登陆信息中得到角色列表与员工级别
      ArrayList things =null;
      if(request.getSession().getAttribute("user") !=null){
         SessionOBJ user =(SessionOBJ)request.getSession().getAttribute("user");
         DynamicDict aDict=new DynamicDict(1);
         if(user.getRole() !=null){
            //logger.info(".......................111.........");
            aDict.setValueByName("role", user.getRole());
            aDict.setValueByName("level", user.getAuthLevel());
            aDict.setValueByName("bureau_no", user.getBureauNo());
            aDict.setValueByName("site_no", user.getSiteNo());
            aDict.setValueByName("staff_no", user.getStaffNo());
            //aDict =ActionDispatch.dispatch(aDict, "MSM_245", false);
            aDict.m_ActionId ="MSM_245"; 
			aDict =ActionDispatch.dispatch(aDict);
            if(aDict.flag <0){
               /*
               ErrorOBJ errors =new ErrorOBJ(aDict.flag, aDict.msg, aDict.exception);
               request.setAttribute(GlobalNames.ERROR_ATTR, errors);
               return null;
               */
               throw new FrameException(aDict.flag, aDict.msg, aDict.exception);
            }
            if(!(aDict.getValueByName("things", false).equals("")))
               things =(ArrayList)aDict.getValueByName("things");
         }
      }
      return things;
   }

   //取得告警信息类型
   public static ArrayList getAlertType(HttpServletRequest request) throws FrameException{
      
      ArrayList things =null;
      if(request.getSession().getAttribute("user") !=null){
         SessionOBJ user =(SessionOBJ)request.getSession().getAttribute("user");
	  
         DynamicDict aDict=new DynamicDict(1);
			aDict.setValueByName("staff_no", user.getStaffNo());
            aDict.m_ActionId ="MSM_247"; 
			aDict =ActionDispatch.dispatch(aDict);
            if(aDict.flag <0){
               
               throw new FrameException(aDict.flag, aDict.msg, aDict.exception);
            }
            if(!(aDict.getValueByName("things", false).equals("")))
               things =(ArrayList)aDict.getValueByName("things");
      }   
      return things;
   }

   public static ArrayList getMyFun(String my_staff_code) throws FrameException{
      
      ArrayList things =null;
      DynamicDict aDict=new DynamicDict(1);
			aDict.setValueByName("staff_no", my_staff_code);
            aDict.m_ActionId ="MSM_022"; 
			aDict =ActionDispatch.dispatch(aDict);
            if(aDict.flag <0){
               
               throw new FrameException(aDict.flag, aDict.msg, aDict.exception);
            }
            if(!(aDict.getValueByName("things", false).equals("")))
               things =(ArrayList)aDict.getValueByName("things");
      return things;
   }
}
