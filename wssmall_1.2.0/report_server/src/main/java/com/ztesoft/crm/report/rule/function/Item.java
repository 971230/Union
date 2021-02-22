/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Item
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 36 */     Object o = func.getParameter(0);
/* 37 */     Object inx = func.getParameter(1);
/*    */ 
/* 39 */     if (o instanceof List) {
/* 40 */       if (inx instanceof Number)
/*    */       {
/* 42 */         return ((List)o).get(((Number)inx).intValue());
/*    */       }
/*    */ 
/* 45 */       if (inx instanceof String) {
/* 46 */         String s = (String)inx;
/* 47 */         String n = s;
/* 48 */         boolean x = true;
/*    */ 
/* 50 */         if (s.toLowerCase().startsWith("y:")) {
/* 51 */           n = s.substring(2);
/* 52 */           x = false;
/*    */         }
/* 54 */         if (s.toLowerCase().startsWith("x:")) {
/* 55 */           n = s.substring(2);
/*    */         }
/*    */ 
/* 58 */         for (Iterator objs = ((List)o).iterator(); objs.hasNext(); ) {
/* 59 */           Cell c = (Cell)objs.next();
/* 60 */           if ((x) && (c.getX().getAliasName().equals(n))) {
/* 61 */             return c;
/*    */           }
/*    */ 
/* 64 */           if ((!(x)) && (c.getY().getAliasName().equals(n))) {
/* 65 */             return c;
/*    */           }
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 73 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.Item
 * JD-Core Version:    0.5.3
 */