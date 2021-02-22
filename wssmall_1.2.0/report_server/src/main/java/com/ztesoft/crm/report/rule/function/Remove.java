/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.lang.RegularExpressionUtils;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;

/*    */ import java.math.BigDecimal;
/*    */ import java.util.List;

import org.apache.log4j.Logger;
/*    */ 
/*    */ public class Remove
/*    */   implements FunctionInvoker
/*    */ {
	private static Logger logger = Logger.getLogger(Remove.class);
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 36 */     logger.info(func.getParameter(0) + "=" + 
/* 37 */       func.getParameter(1));
/* 38 */     List o = (List)func.getParameter(0);
/* 39 */     Object str = func.getParameter(1);
/*    */ 
/* 41 */     if ((str == null) || (o == null))
/* 42 */       return null;
/* 43 */     if ((str instanceof String) && (RegularExpressionUtils.isNumber(str))) {
/* 44 */       str = new BigDecimal(str.toString());
/*    */     }
/* 46 */     if (str instanceof Number) {
/* 47 */       int inx = ((Number)str).intValue();
/* 48 */       if ((o.size() > inx) && (inx >= 0)) {
/* 49 */         return o.remove(inx);
/*    */       }
/*    */     }
/*    */ 
/* 53 */     if ((str instanceof String) && (o instanceof List)) {
/* 54 */       String s = (String)str;
/* 55 */       boolean x = true;
/* 56 */       if (s.toLowerCase().startsWith("x:")) {
/* 57 */         s = s.substring(2);
/*    */       }
/*    */ 
/* 60 */       if (s.toLowerCase().startsWith("y:")) {
/* 61 */         s = s.substring(2);
/* 62 */         x = false;
/*    */       }
/* 64 */       List arr = o;
/* 65 */       for (int i = arr.size() - 1; i >= 0; --i) {
/* 66 */         Cell c = (Cell)arr.get(i);
/* 67 */         if (s.indexOf(".") > 0) {
/* 68 */           if ((x) && (c.getX().getAliasNameValue().equals(s))) {
/* 69 */             return arr.remove(i);
/*    */           }
/*    */ 
/* 72 */           if ((!(x)) && (c.getY().getAliasNameValue().equals(s)))
/* 73 */             return arr.remove(i);
/*    */         }
/*    */         else {
/* 76 */           if ((x) && (c.getX().getAliasName().equals(s))) {
/* 77 */             return arr.remove(i);
/*    */           }
/*    */ 
/* 80 */           if ((!(x)) && (c.getY().getAliasName().equals(s))) {
/* 81 */             return arr.remove(i);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.Remove
 * JD-Core Version:    0.5.3
 */