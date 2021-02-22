/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ 
/*    */ public class GetXValue
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 29 */     Object o = func.getParameter(0);
/*    */ 
/* 31 */     if (o == null) return null;
/* 32 */     if (o instanceof Cell) {
/* 33 */       return ((Cell)o).getY().getDataValue();
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.GetXValue
 * JD-Core Version:    0.5.3
 */