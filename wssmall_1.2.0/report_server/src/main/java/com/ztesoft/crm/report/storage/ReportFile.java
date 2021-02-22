/*    */ package com.ztesoft.crm.report.storage;
/*    */ 
/*    */ public final class ReportFile
/*    */ {
/*    */   private boolean directory;
/*    */   private String name;
/*    */   private String path;
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 19 */     return this.path;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 23 */     return this.name;
/*    */   }
/*    */ 
/*    */   public ReportFile()
/*    */   {
/* 30 */     this(false);
/*    */   }
/*    */ 
/*    */   public ReportFile(boolean directory)
/*    */   {
/* 12 */     this.directory = false;
/*    */ 
/* 35 */     this.directory = directory;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getLeaf() {
/* 43 */     return String.valueOf(this.directory);
/*    */   }
/*    */ 
/*    */   public String getPath() {
/* 47 */     return this.path;
/*    */   }
/*    */ 
/*    */   public boolean isDirectory() {
/* 51 */     return this.directory;
/*    */   }
/*    */ 
/*    */   public void setDirectory(boolean directory) {
/* 55 */     this.directory = directory;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 59 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public void setPath(String path) {
/* 63 */     this.path = path;
/*    */   }
/*    */ 
/*    */   public String getWebPath() {
/* 67 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.storage.ReportFile
 * JD-Core Version:    0.5.3
 */