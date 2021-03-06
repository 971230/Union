/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ public class Variable extends Token
/*    */ {
/*    */   private String name;
/*    */   private Token expression;
/*    */ 
/*    */   public Variable()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Variable(String name)
/*    */   {
/* 22 */     this.name = name;
/*    */   }
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 27 */     if (this.expression == null) return null;
/* 28 */     Object o = this.expression.execute();
/* 29 */     this.parent.setVariateValue(this.name, o);
/*    */ 
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 41 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public Token getExpression()
/*    */   {
/* 48 */     return this.expression;
/*    */   }
/*    */ 
/*    */   public void setExpression(Token expression) {
/* 52 */     this.expression = expression;
/* 53 */     if (this.expression == null) return; this.expression.setParent(this);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.Variable
 * JD-Core Version:    0.5.3
 */