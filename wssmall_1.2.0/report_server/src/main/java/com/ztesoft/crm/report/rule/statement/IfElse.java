/*    */ package com.ztesoft.crm.report.rule.statement;
/*    */ 
/*    */ import com.ztesoft.crm.report.rule.Statement;
/*    */ import com.ztesoft.crm.report.rule.Token;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class IfElse extends Statement
/*    */ {
/*    */   private Token condition;
/*    */   private Token then;
/*    */   private Token other;
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 27 */     Boolean b = (Boolean)((this.condition == null) ? new Boolean(false) : 
/* 28 */       this.condition.execute());
/*    */ 
/* 30 */     if ((b == null) || (!(b.booleanValue()))) {
/* 31 */       return ((this.other != null) ? this.other.execute() : null);
/*    */     }
/*    */ 
/* 34 */     return ((this.then != null) ? this.then.execute() : null);
/*    */   }
/*    */ 
/*    */   public Token getCondition()
/*    */   {
/* 40 */     return this.condition;
/*    */   }
/*    */ 
/*    */   public void setCondition(Token condition) {
/* 44 */     if (condition != null) condition.setParent(this);
/* 45 */     this.condition = condition;
/*    */   }
/*    */ 
/*    */   public Token getThen()
/*    */   {
/* 51 */     return this.then;
/*    */   }
/*    */ 
/*    */   public void setThen(Token then) {
/* 55 */     this.then = then;
/* 56 */     if (this.then == null) return; this.then.setParent(this);
/*    */   }
/*    */ 
/*    */   public Token getOther()
/*    */   {
/* 62 */     return this.other;
/*    */   }
/*    */ 
/*    */   public void setOther(Token other) {
/* 66 */     this.other = other;
/* 67 */     if (this.other == null) return; this.other.setParent(this);
/*    */   }
/*    */ 
/*    */   @Override
public void list(PrintStream out)
/*    */   {
/* 72 */     out.println(this);
/* 73 */     out.println("条件");
/* 74 */     if (this.condition != null) this.condition.list(out);
/* 75 */     out.println("then");
/* 76 */     if (this.then != null) this.then.list(out);
/* 77 */     out.println("else");
/* 78 */     if (this.other == null) return; this.other.list(out);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.statement.IfElse
 * JD-Core Version:    0.5.3
 */