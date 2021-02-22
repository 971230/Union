/*    */ package com.ztesoft.crm.report.generator.html.elements;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ 
/*    */ public class HTMLFormFieldEvent extends HTMLNode
/*    */ {
/*    */   private String name;
/*    */   private String content;
/*    */   private String id;
/*    */   private String functionName;
/*    */ 
/*    */   public HTMLFormFieldEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public HTMLFormFieldEvent(String tagName)
/*    */   {
/* 27 */     super(tagName);
/*    */   }
/*    */ 
/*    */   public HTMLFormFieldEvent(String tagName, String innerHTML)
/*    */   {
/* 36 */     super(tagName, innerHTML);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 45 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 49 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getContent() {
/* 53 */     return this.content;
/*    */   }
/*    */ 
/*    */   public void setContent(String content) {
/* 57 */     this.content = content;
/*    */   }
/*    */ 
/*    */   public String getId() {
/* 61 */     return this.id;
/*    */   }
/*    */ 
/*    */   @Override
public void setId(String id) {
/* 65 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public void setFunctionName(String functionName)
/*    */   {
/* 71 */     this.functionName = functionName;
/*    */   }
/*    */ 
/*    */   @Override
public void output(OutputStream output, String encoding)
/*    */     throws UnsupportedEncodingException, IOException
/*    */   {
/* 77 */     StringBuffer sb = new StringBuffer();
/* 78 */     sb.append("function ").append(this.functionName).append("(");
/* 79 */     sb.append("form,field,oldValue,oldText){");
/* 80 */     sb.append(this.content);
/* 81 */     sb.append("}");
/* 82 */     output.write(sb.toString().getBytes(encoding));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLFormFieldEvent
 * JD-Core Version:    0.5.3
 */