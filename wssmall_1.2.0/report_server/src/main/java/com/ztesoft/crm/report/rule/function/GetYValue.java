/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ 
/*    */ public class GetYValue
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 29 */     Object o = func.getParameter(0);
/*    */ 
/* 31 */     if (o == null) return null;
/* 32 */     if (o instanceof Cell)
/*    */     {
/* 34 */       return ((Cell)o).getY().getDataValue();
/*    */     }
/* 36 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.GetYValue
 * JD-Core Version:    0.5.3
 */