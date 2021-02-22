/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Length
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 27 */     Object o = func.getParameter(0);
/* 28 */     if (o == null) {
/* 29 */       return new Integer(0);
/*    */     }
/*    */ 
/* 33 */     if (o instanceof String) {
/* 34 */       return new Integer(((String)o).length());
/*    */     }
/*    */ 
/* 37 */     if (o instanceof Map) {
/* 38 */       return new Integer(((Map)o).size());
/*    */     }
/*    */ 
/* 41 */     if (o instanceof Collection) {
/* 42 */       return new Integer(((Collection)o).size());
/*    */     }
/*    */ 
/* 45 */     return new Integer(0);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.Length
 * JD-Core Version:    0.5.3
 */