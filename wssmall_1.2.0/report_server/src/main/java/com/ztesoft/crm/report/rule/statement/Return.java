/*    */ package com.ztesoft.crm.report.rule.statement;
/*    */ 
/*    */ import com.ztesoft.crm.report.rule.ReturnValue;
/*    */ import com.ztesoft.crm.report.rule.Statement;
/*    */ import com.ztesoft.crm.report.rule.Token;
/*    */ 
/*    */ public class Return extends Statement
/*    */ {
/*    */   private Token value;
/*    */ 
/*    */   public void setValue(Token value)
/*    */   {
/* 26 */     this.value = value;
/*    */   }
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 32 */     return new ReturnValue((this.value == null) ? null : this.value.execute());
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.statement.Return
 * JD-Core Version:    0.5.3
 */