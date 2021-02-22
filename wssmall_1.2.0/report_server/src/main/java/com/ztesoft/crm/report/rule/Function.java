/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class Function extends Token
/*    */ {
/*    */   private String name;
/*    */ 
/*    */   public Object getParameter(int inx)
/*    */   {
/* 22 */     Token p = getToken(inx);
/* 23 */     return ((p == null) ? null : p.execute());
/*    */   }
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 33 */     FunctionInvoker fi = FunctionFactory.getFunctionInvoker(this.name);
/*    */ 
/* 35 */     return fi.invoke(this);
/*    */   }
/*    */ 
/*    */   @Override
public void list(PrintStream out)
/*    */   {
/* 40 */     out.println(this.name);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 46 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 50 */     this.name = name;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.Function
 * JD-Core Version:    0.5.3
 */