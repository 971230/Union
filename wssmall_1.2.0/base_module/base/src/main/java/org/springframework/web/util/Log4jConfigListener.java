/*    */ package org.springframework.web.util;
/*    */ 
/*    */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*    */
/*    */ 
/*    */ public class Log4jConfigListener
/*    */   implements ServletContextListener
/*    */ {
/*    */   @Override
public void contextInitialized(ServletContextEvent paramServletContextEvent)
/*    */   {
/* 51 */     Log4jWebConfigurer.initLogging(paramServletContextEvent.getServletContext());
/*    */   }
/*    */ 
/*    */   @Override
public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
/* 55 */     Log4jWebConfigurer.shutdownLogging(paramServletContextEvent.getServletContext());
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.Log4jConfigListener
 * JD-Core Version:    0.6.0
 */