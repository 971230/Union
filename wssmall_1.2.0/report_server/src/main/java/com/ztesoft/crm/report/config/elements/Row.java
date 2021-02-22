/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ 
/*    */ public class Row extends Element
/*    */ {
/*    */   private static final long serialVersionUID = 9187444536535954124L;
/*    */   private Model currentModel;
/*    */   private String model;
/*    */   private String verticalFill;
/*    */   private String buttons;
/*    */   private String align;
/*    */   private String height;
/*    */ 
/*    */   public String getHeight()
/*    */   {
/* 24 */     return this.height;
/*    */   }
/*    */ 
/*    */   public void setHeight(String height) {
/* 28 */     this.height = height;
/*    */   }
/*    */ 
/*    */   public String getButtons() {
/* 32 */     return this.buttons;
/*    */   }
/*    */ 
/*    */   public void setButtons(String buttons) {
/* 36 */     this.buttons = buttons;
/*    */   }
/*    */ 
/*    */   public String getAlign() {
/* 40 */     return this.align;
/*    */   }
/*    */ 
/*    */   public void setAlign(String align) {
/* 44 */     this.align = align;
/*    */   }
/*    */ 
/*    */   public String getVerticalFill() {
/* 48 */     return this.verticalFill;
/*    */   }
/*    */ 
/*    */   public void setVerticalFill(String verticalFill) {
/* 52 */     this.verticalFill = verticalFill;
/*    */   }
/*    */ 
/*    */   public Model getCurrentModel()
/*    */   {
/* 66 */     return this.currentModel;
/*    */   }
/*    */ 
/*    */   public String getModel()
/*    */   {
/* 73 */     return this.model;
/*    */   }
/*    */ 
/*    */   @Override
protected void init(Element top)
/*    */   {
/* 85 */     this.currentModel = ((Model)top.getElement(this.model));
/*    */   }
/*    */ 
/*    */   public void setModel(String model)
/*    */   {
/* 93 */     this.model = model;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Row
 * JD-Core Version:    0.5.3
 */