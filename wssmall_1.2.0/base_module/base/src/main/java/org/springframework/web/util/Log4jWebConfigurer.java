/*     */ package org.springframework.web.util;
/*     */ 
/*     */

import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;

/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public abstract class Log4jWebConfigurer
/*     */ {
/*     */   public static final String CONFIG_LOCATION_PARAM = "log4jConfigLocation";
/*     */   public static final String REFRESH_INTERVAL_PARAM = "log4jRefreshInterval";
/*     */   public static final String EXPOSE_WEB_APP_ROOT_PARAM = "log4jExposeWebAppRoot";
/*     */ 
/*     */   public static void initLogging(ServletContext paramServletContext)
/*     */   {
/* 116 */     if (exposeWebAppRoot(paramServletContext)) {
/* 117 */       WebUtils.setWebAppRootSystemProperty(paramServletContext);
/*     */     }
/*     */ 
/* 121 */     String str1 = paramServletContext.getInitParameter("log4jConfigLocation");
/* 122 */     if (str1 != null)
/*     */     {
/*     */       try
/*     */       {
/* 127 */         if (!ResourceUtils.isUrl(str1))
/*     */         {
/* 129 */           str1 = SystemPropertyUtils.resolvePlaceholders(str1);
/* 130 */           str1 = WebUtils.getRealPath(paramServletContext, str1);
/*     */         }
/*     */ 
/* 134 */         paramServletContext.log("Initializing Log4J from [" + str1 + "]");
/*     */ 
/* 137 */         String str2 = paramServletContext.getInitParameter("log4jRefreshInterval");
/* 138 */         if (str2 != null)
/*     */         {
/*     */           try
/*     */           {
/* 142 */             long l = Long.parseLong(str2);
/* 143 */             Log4jConfigurer.initLogging(str1, l);
/*     */           }
/*     */           catch (NumberFormatException localNumberFormatException) {
/* 146 */             throw new IllegalArgumentException("Invalid 'log4jRefreshInterval' parameter: " + localNumberFormatException.getMessage());
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 151 */           Log4jConfigurer.initLogging(str1);
/*     */         }
/*     */       }
/*     */       catch (FileNotFoundException localFileNotFoundException) {
/* 155 */         throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter: " + localFileNotFoundException.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void shutdownLogging(ServletContext paramServletContext)
/*     */   {
/* 167 */     paramServletContext.log("Shutting down Log4J");
/*     */     try {
/* 169 */       Log4jConfigurer.shutdownLogging();
/*     */     }
/*     */     finally
/*     */     {
/* 173 */       if (exposeWebAppRoot(paramServletContext))
/* 174 */         WebUtils.removeWebAppRootSystemProperty(paramServletContext);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean exposeWebAppRoot(ServletContext paramServletContext)
/*     */   {
/* 185 */     String str = paramServletContext.getInitParameter("log4jExposeWebAppRoot");
/* 186 */     return (str == null) || (Boolean.valueOf(str).booleanValue());
/*     */   }
/*     */ }

/* Location:           C:\Users\Administrator\Desktop\spring-1.2.6.jar
 * Qualified Name:     org.springframework.web.util.Log4jWebConfigurer
 * JD-Core Version:    0.6.0
 */