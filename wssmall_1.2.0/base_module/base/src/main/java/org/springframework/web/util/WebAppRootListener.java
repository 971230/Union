/*    */ package org.springframework.web.util;
/*    */ 
/*    */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*    */
/*    */ 
/*    */ public class WebAppRootListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   @Override
public void contextInitialized(ServletContextEvent paramServletContextEvent)
/*    */   {
/* 56 */     WebUtils.setWebAppRootSystemProperty(paramServletContextEvent.getServletContext());
/*    */   }
/*    */ 
/*    */   @Override
public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
/* 60 */     WebUtils.removeWebAppRootSystemProperty(paramServletContextEvent.getServletContext());
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.WebAppRootListener
 * JD-Core Version:    0.6.0
 */