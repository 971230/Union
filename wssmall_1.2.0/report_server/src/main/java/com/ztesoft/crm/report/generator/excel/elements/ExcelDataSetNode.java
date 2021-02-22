/*    */ package com.ztesoft.crm.report.generator.excel.elements;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.DataSet;
/*    */ import org.apache.poi.hssf.usermodel.HSSFSheet;
/*    */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*    */ 
/*    */ public class ExcelDataSetNode extends ExcelNode
/*    */ {
/*    */   private String columnAlign;
/*    */   private DataSet dataSet;
/*    */   private String title;
/*    */ 
/*    */   public void setColumnAlign(String columnAlign)
/*    */   {
/* 28 */     this.columnAlign = columnAlign;
/*    */   }
/*    */ 
/*    */   public DataSet getDataSet()
/*    */   {
/* 34 */     return this.dataSet;
/*    */   }
/*    */ 
/*    */   public void setTitle(String title)
/*    */   {
/* 40 */     this.title = title;
/*    */   }
/*    */ 
/*    */   public String getTitle() {
/* 44 */     return this.title;
/*    */   }
/*    */ 
/*    */   public void setDataSet(DataSet dataSet) {
/* 48 */     this.dataSet = dataSet;
/*    */   }
/*    */ 
/*    */   @Override
protected int export(HSSFWorkbook book, HSSFSheet sheet, int start)
/*    */   {
/* 53 */     if (this.dataSet == null)
/* 54 */       return start;
/* 55 */     return new DataSetToExcel(book, sheet, this.columnAlign).convert(this.dataSet, 
/* 56 */       start);
/*    */   }
/*    */ 
/*    */   @Override
protected void release()
/*    */   {
/* 61 */     if (this.dataSet != null) this.dataSet.clear();
/* 62 */     this.dataSet = null;
/*    */   }
/*    */ 
/*    */   @Override
public void clear()
/*    */   {
/* 67 */     super.clear();
/* 68 */     release();
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.excel.elements.ExcelDataSetNode
 * JD-Core Version:    0.5.3
 */