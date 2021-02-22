/*     */ package com.ztesoft.crm.report.rule;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.ArrayListEx;
/*     */ import com.ztesoft.crm.report.rule.statement.Break;
/*     */ import com.ztesoft.crm.report.rule.statement.Continue;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Token
/*     */ {
/*     */   protected Token parent;
/*  52 */   private List childs = new ArrayListEx();
/*     */ 
/*  61 */   private HashMap map = new HashMap();
/*     */ 
/*     */   public Object execute()
/*     */   {
/*  23 */     Object o = null;
/*  24 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); ) {
/*  25 */       Token t = (Token)objs.next();
/*     */ 
/*  27 */       o = t.execute();
/*  28 */       if (o == null) {
/*     */         continue;
/*     */       }
/*  31 */       if (o instanceof Continue)
/*  32 */         return o;
/*  33 */       if (o instanceof Break)
/*  34 */         return o;
/*  35 */       if (o instanceof ReturnValue)
/*  36 */         return o;
/*     */     }
/*  38 */     return o;
/*     */   }
/*     */ 
/*     */   public final void clear() {
/*  42 */     this.map.clear();
/*  43 */     this.map = null;
/*     */   }
/*     */ 
/*     */   public final void setParent(Token p)
/*     */   {
/*  49 */     this.parent = p;
/*     */   }
/*     */ 
/*     */   public final void add(Token token)
/*     */   {
/*  55 */     if (token != null) {
/*  56 */       token.parent = this;
/*  57 */       this.childs.add(token);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final Object getVariateValue(String name)
/*     */   {
/*  64 */     Object v = this.map.get(name);
/*  65 */     if (v != null) {
/*  66 */       return v;
/*     */     }
/*  68 */     if (this.parent != null) {
/*  69 */       return this.parent.getVariateValue(name);
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   public final void setVariateValue(String name, Object value) {
/*  75 */     Token t = this;
/*     */ 
/*  78 */     while (t.parent != null) {
/*  79 */       if (t instanceof Statement)
/*     */         break;
/*  81 */       t = t.parent;
/*     */     }
/*     */ 
/*  85 */     if (t != null)
/*  86 */       t.map.put(name, value);
/*     */   }
/*     */ 
/*     */   public void list(PrintStream out) {
/*  90 */     out.println(this);
/*  91 */     for (Iterator objs = this.childs.iterator(); objs.hasNext(); )
/*  92 */       ((Token)objs.next()).list(out);
/*     */   }
/*     */ 
/*     */   public final void putAll(Map m)
/*     */   {
/*  97 */     if (m != null)
/*  98 */       this.map.putAll(m);
/*     */   }
/*     */ 
/*     */   public final Token getToken(int inx)
/*     */   {
/* 103 */     if (inx < 0)
/* 104 */       return null;
/* 105 */     if (inx >= this.childs.size())
/* 106 */       return null;
/* 107 */     return ((Token)this.childs.get(inx));
/*     */   }
/*     */ 
/*     */   public int getCount() {
/* 111 */     return this.childs.size();
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.Token
 * JD-Core Version:    0.5.3
 */