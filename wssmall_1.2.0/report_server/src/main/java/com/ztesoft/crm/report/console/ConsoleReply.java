/*     */ package com.ztesoft.crm.report.console;
/*     */ 
/*     */ public final class ConsoleReply
/*     */ {
/*     */   public static final String SESSION = "session";
/*     */   private String scope;
/*     */   private String forward;
/*  60 */   private boolean error = false;
/*     */   private String message;
/*     */   private Object data;
/*     */ 
/*     */   public ConsoleReply(String scope)
/*     */   {
/*  18 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public ConsoleReply()
/*     */   {
/*  23 */     this.scope = null;
/*     */   }
/*     */ 
/*     */   public final void setScope(String scope)
/*     */   {
/*  31 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public final String getForward()
/*     */   {
/*  42 */     return this.forward;
/*     */   }
/*     */ 
/*     */   public final void setForward(String forward)
/*     */   {
/*  50 */     this.forward = forward;
/*     */   }
/*     */ 
/*     */   final String getScope()
/*     */   {
/*  57 */     return this.scope;
/*     */   }
/*     */ 
/*     */   public final boolean isError()
/*     */   {
/*  68 */     return this.error;
/*     */   }
/*     */ 
/*     */   public final void setError(boolean error)
/*     */   {
/*  76 */     this.error = error;
/*     */   }
/*     */ 
/*     */   public final String getMessage()
/*     */   {
/*  83 */     return this.message;
/*     */   }
/*     */ 
/*     */   public final void setMessage(String message)
/*     */   {
/*  91 */     this.message = message;
/*     */   }
/*     */ 
/*     */   public final Object getData()
/*     */   {
/*  98 */     return this.data;
/*     */   }
/*     */ 
/*     */   public final void setData(Object data)
/*     */   {
/* 106 */     this.data = data;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.console.ConsoleReply
 * JD-Core Version:    0.5.3
 */