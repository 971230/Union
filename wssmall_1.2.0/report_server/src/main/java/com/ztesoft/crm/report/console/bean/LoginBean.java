/*    */ package com.ztesoft.crm.report.console.bean;
/*    */ 
/*    */ import com.ztesoft.crm.report.actions.ParameterMap;
/*    */ import com.ztesoft.crm.report.console.Bean;
/*    */ import com.ztesoft.crm.report.console.ConsoleReply;
/*    */ import com.ztesoft.crm.report.console.vo.User;
/*    */ import com.ztesoft.crm.report.lang.ObjectPropertyUtils;
/*    */ 
/*    */ public class LoginBean extends Bean
/*    */ {
/*    */   public ConsoleReply login(ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 33 */     User user = new User();
/* 34 */     ObjectPropertyUtils.copyProperties(user, map, null);
/* 35 */     ConsoleReply rpy = new ConsoleReply("session");
/*    */ 
/* 37 */     if ((user.getName().equals("reportadmin")) && 
/* 38 */       (user.getPassword().equals("520817"))) {
/* 39 */       rpy.setData(user);
/*    */ 
/* 41 */       rpy.setForward("console/editreport.jsp");
/* 42 */       return rpy;
/*    */     }
/*    */ 
/* 45 */     if ((user.getName().equals("sysadmin")) && 
/* 46 */       (user.getPassword().equals("520817"))) {
/* 47 */       rpy.setData(user);
/*    */ 
/* 49 */       rpy.setForward("console/monitor.jsp");
/* 50 */       return rpy;
/*    */     }
/*    */ 
/* 53 */     rpy.setError(true);
/* 54 */     rpy.setForward("console/console_nologin.jsp");
/*    */ 
/* 56 */     return rpy;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.bean.LoginBean
 * JD-Core Version:    0.5.3
 */