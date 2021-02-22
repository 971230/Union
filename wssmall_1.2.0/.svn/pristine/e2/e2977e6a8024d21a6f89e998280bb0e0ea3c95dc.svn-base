package com.powerise.ibss.util;

/**
 错误号段100002***
 */

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.ejb.DispatchEJB;
import com.powerise.ibss.framework.ejb.DispatchEJBHome;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

public class EJBUtil {
   private static Context ctx =null;

   public static DispatchEJB getDispatchEJB() throws FrameException {
      DispatchEJB dispatchEJB = null;
      try {
         Object objref = lookup(JNDINames.DispatchEJB);
         DispatchEJBHome dispatchEJBHome =
                 (DispatchEJBHome) PortableRemoteObject.narrow(objref, DispatchEJBHome.class);
         dispatchEJB = dispatchEJBHome.create();
      } catch (RemoteException re) {
         throw new FrameException(-100002003, "EJBUtil.DispatchEJB()出现异常！", re.getMessage());
      } catch (CreateException re1) {
         throw new FrameException(-100002002, "EJBUtil.DispatchEJB()出现异常！", re1.getMessage());
      }
      return dispatchEJB;
   }


   private static Object lookup(String p_jndi_name)
           throws FrameException {
      Object obj = null;
      try {
         if(ctx ==null)
            ctx = SysSet.getInitialContext();
         obj = ctx.lookup(p_jndi_name);
      } catch (Exception ce) {
         ce.printStackTrace();
         throw new FrameException(-100002001, "定位JNDI(" + p_jndi_name + ")出错！",ce.getMessage());
      }
      return obj;
   }

}
