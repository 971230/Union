/*    */ package com.ztesoft.crm.report.rule.statement;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import com.ztesoft.crm.report.rule.ReturnValue;
/*    */ import com.ztesoft.crm.report.rule.Statement;
/*    */ import com.ztesoft.crm.report.rule.Token;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ForEach extends Statement
/*    */ {
/*    */   private String declare;
/*    */   private Token range;
/*    */   private Token body;
/*    */ 
/*    */   @Override
public Object execute()
/*    */   {
/* 29 */     if (Utils.isEmpty(this.declare))
/* 30 */       return null;
/* 31 */     if (this.range == null)
/* 32 */       return null;
/* 33 */     Object o = this.range.execute();
/* 34 */     if (o == null)
/* 35 */       return null;
/* 36 */     if (o instanceof Object[]) {
/* 37 */       Object[] objs = (Object[])o;
/* 38 */       for (int i = 0; i < objs.length; ++i) {
/* 39 */         setVariateValue(this.declare, objs[i]);
/* 40 */         Object r = null;
/* 41 */         if (this.body != null)
/* 42 */           r = this.body.execute();
/* 43 */         if (r == null)
/*    */           continue;
/* 45 */         if (r instanceof Break)
/*    */           break;
/* 47 */         if (r instanceof ReturnValue) {
/* 48 */           return r;
/*    */         }
/*    */       }
/*    */     }
/* 52 */     if (o instanceof List)
/*    */     {
/* 54 */       for (Iterator objs = ((List)o).iterator(); objs.hasNext(); ) {
/* 55 */         setVariateValue(this.declare, objs.next());
/* 56 */         Object r = null;
/* 57 */         if (this.body != null)
/* 58 */           r = this.body.execute();
/* 59 */         if (r == null)
/*    */           continue;
/* 61 */         if (r instanceof Break)
/*    */           break;
/* 63 */         if (r instanceof ReturnValue) {
/* 64 */           return r;
/*    */         }
/*    */       }
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   public final void setDeclare(String declare)
/*    */   {
/* 76 */     this.declare = declare;
/*    */   }
/*    */ 
/*    */   public final Token getRange() {
/* 80 */     return this.range;
/*    */   }
/*    */ 
/*    */   public final void setRange(Token range) {
/* 84 */     this.range = range;
/*    */ 
/* 86 */     if (this.range != null)
/* 87 */       this.range.setParent(this);
/*    */   }
/*    */ 
/*    */   public final Token getBody() {
/* 91 */     return this.body;
/*    */   }
/*    */ 
/*    */   public final void setBody(Token body) {
/* 95 */     this.body = body;
/*    */ 
/* 97 */     if (this.body != null)
/* 98 */       this.body.setParent(this);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.statement.ForEach
 * JD-Core Version:    0.5.3
 */