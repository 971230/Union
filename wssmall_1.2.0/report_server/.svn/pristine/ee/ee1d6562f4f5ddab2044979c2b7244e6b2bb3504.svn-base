/*     */ package com.ztesoft.crm.report.log;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class ReportLogger
/*     */ {
/*  23 */   private final Logger log = Logger.getLogger(ReportLogger.class);
/*     */ 
/*  25 */   private static final ReportLogger logger = new ReportLogger();
/*     */ 
/*     */   public static final synchronized ReportLogger getLogger() {
/*  28 */     return logger;
/*     */   }
/*     */ 
/*     */   public void warn(String message, String[] args) {
/*  32 */     String str = MessageFormat.format(message, args);
/*  33 */     this.log.warn(str);
/*     */   }
/*     */ 
/*     */   public void error(String message, String[] args) {
/*  37 */     String str = MessageFormat.format(message, args);
/*  38 */     this.log.info(str);
/*     */   }
/*     */ 
/*     */   public void error(String message, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/*  43 */     error(message, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public void error(String message, String arg0)
/*     */   {
/*  48 */     error(message, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public void error(String message, String arg0, String arg1)
/*     */   {
/*  53 */     error(message, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public void error(String message, String arg0, String arg1, String arg2)
/*     */   {
/*  58 */     error(message, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public void warn(String message, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/*  64 */     warn(message, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public void warn(String message, String arg0)
/*     */   {
/*  69 */     warn(message, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public void warn(String message, String arg0, String arg1)
/*     */   {
/*  74 */     warn(message, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public void warn(String message, String arg0, String arg1, String arg2)
/*     */   {
/*  79 */     warn(message, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public void info(String message, String arg0)
/*     */   {
/*  84 */     info(message, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public void info(String message, String arg0, String arg1)
/*     */   {
/*  89 */     info(message, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public void info(String message, String arg0, String arg1, String arg2)
/*     */   {
/*  94 */     info(message, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public void debug(String message, String arg0)
/*     */   {
/*  99 */     debug(message, new String[] { arg0 });
/*     */   }
/*     */ 
/*     */   public void debug(String message, String arg0, String arg1)
/*     */   {
/* 104 */     debug(message, new String[] { arg0, arg1 });
/*     */   }
/*     */ 
/*     */   public void debug(String message, String arg0, String arg1, String arg2)
/*     */   {
/* 109 */     debug(message, new String[] { arg0, arg1, arg2 });
/*     */   }
/*     */ 
/*     */   public void warn(String message) {
/* 113 */     this.log.warn(message);
/*     */   }
/*     */ 
/*     */   public void info(String message)
/*     */   {
/* 118 */     this.log.info(message);
/*     */   }
/*     */ 
/*     */   public void debug(String message)
/*     */   {
/* 123 */     this.log.debug(message);
/*     */   }
/*     */ 
/*     */   public void info(String message, String[] args)
/*     */   {
/* 128 */     String str = MessageFormat.format(message, args);
/* 129 */     this.log.info(str);
/*     */   }
/*     */ 
/*     */   public void info(String message, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/* 135 */     info(message, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public void debug(String message, String[] args) {
/* 139 */     String str = MessageFormat.format(message, args);
/* 140 */     this.log.debug(str);
/*     */   }
/*     */ 
/*     */   public void debug(String message, String arg0, String arg1, String arg2, String arg3)
/*     */   {
/* 146 */     debug(message, new String[] { arg0, arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */   public void error(String message) {
/* 150 */     this.log.info(message);
/*     */   }
/*     */ 
/*     */   public void error(String message, Throwable e) {
/* 154 */     this.log.info(message, e);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.log.ReportLogger
 * JD-Core Version:    0.5.3
 */