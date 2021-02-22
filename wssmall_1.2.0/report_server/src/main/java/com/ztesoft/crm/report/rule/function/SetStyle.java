/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ 
/*    */ public class SetStyle
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 33 */     Cell cell = (Cell)func.getParameter(0);
/* 34 */     String style = (String)func.getParameter(1);
/* 35 */     String value = (String)func.getParameter(2);
/*    */ 
/* 37 */     if (cell == null)
/* 38 */       return null;
/* 39 */     if (func.getCount() == 2) {
/* 40 */       cell.setStyle(style);
/*    */     }
/*    */ 
/* 43 */     if (func.getCount() == 3)
/*    */     {
/* 45 */       cell.setStyle(style, value);
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.SetStyle
 * JD-Core Version:    0.5.3
 */