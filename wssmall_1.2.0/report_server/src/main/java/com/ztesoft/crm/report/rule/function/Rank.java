/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ 
/*    */ public class Rank
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 25 */     Cell arr = (Cell)func.getParameter(0);
/* 26 */     return new Integer(arr.getRank());
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.Rank
 * JD-Core Version:    0.5.3
 */