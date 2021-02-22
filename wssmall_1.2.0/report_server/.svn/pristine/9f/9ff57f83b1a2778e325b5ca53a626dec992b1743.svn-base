/*    */ package com.ztesoft.crm.report.document;
/*    */ 
/*    */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public abstract class Node
/*    */ {
/* 16 */   private ArrayListEx children = new ArrayListEx();
/*    */ 
/*    */   public void add(Node node) {
/* 19 */     if (node == null)
/* 20 */       return;
/* 21 */     this.children.add(node);
/*    */   }
/*    */ 
/*    */   public final void addAll(ArrayListEx arr) {
/* 25 */     if (arr != null)
/* 26 */       this.children.addAll(arr);
/*    */   }
/*    */ 
/*    */   protected final Iterator children() {
/* 30 */     return this.children.iterator();
/*    */   }
/*    */ 
/*    */   public final ArrayListEx childs() {
/* 34 */     return this.children;
/*    */   }
/*    */ 
/*    */   public void clear() {
/* 38 */     this.children.clear();
/*    */   }
/*    */ 
/*    */   public void output(OutputStream output, String encoding)
/*    */     throws Exception
/*    */   {
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.document.Node
 * JD-Core Version:    0.5.3
 */