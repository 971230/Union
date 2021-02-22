/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GetRange
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 34 */     DataSet ds = (DataSet)func.getVariateValue(DataSet.class.getName());
/*    */ 
/* 36 */     List arr = ds.getRange((String)func.getParameter(0), (String)
/* 37 */       func.getParameter(1));
/*    */ 
/* 39 */     return arr;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.GetRange
 * JD-Core Version:    0.5.3
 */