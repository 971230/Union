/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Top
/*    */   implements FunctionInvoker
/*    */ {
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 35 */     List arr = (List)func.getParameter(0);
/* 36 */     Cell[] cells = (Cell[])arr.toArray(new Cell[arr.size()]);
/* 37 */     new Sort().quickSort(cells);
/* 38 */     Object l = func.getParameter(1);
/* 39 */     int len = (l instanceof Number) ? ((Number)l).intValue() : 
/* 40 */       Utils.parseInt(l.toString(), 0);
/* 41 */     if (len < 0) {
/* 42 */       return new Cell[0];
/*    */     }
/* 44 */     if (len > cells.length) {
/* 45 */       return cells;
/*    */     }
/* 47 */     Cell[] temp = new Cell[len];
/*    */ 
/* 49 */     for (int i = 0; i < len; ++i) {
/* 50 */       temp[i] = cells[i];
/*    */     }
/* 52 */     cells = null;
/*    */ 
/* 54 */     return temp;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.Top
 * JD-Core Version:    0.5.3
 */