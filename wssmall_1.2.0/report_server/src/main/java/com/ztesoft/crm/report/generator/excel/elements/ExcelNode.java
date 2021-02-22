/*    */ package com.ztesoft.crm.report.generator.excel.elements;
/*    */ 
/*    */ import com.ztesoft.crm.report.document.Node;
/*    */ import java.util.Iterator;
/*    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */ 
/*    */ public class ExcelNode extends Node
/*    */ {
/*    */   protected int export(HSSFWorkbook book, HSSFSheet sheet, int start)
/*    */   {
/* 29 */     for (Iterator objs = children(); objs.hasNext(); ) {
/* 30 */       ExcelNode node = (ExcelNode)objs.next();
/*    */ 
/* 32 */       start = node.export(book, sheet, start);
/* 33 */       node.release();
/*    */     }
/*    */ 
/* 37 */     return start;
/*    */   }
/*    */ 
/*    */   protected void release()
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.excel.elements.ExcelNode
 * JD-Core Version:    0.5.3
 */