/*     */ package com.ztesoft.crm.report.generator.html.elements;
/*     */ 
/*     */ import com.ztesoft.crm.report.ReportContext;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class HTMLChartSimpleFlash extends HTMLNode
/*     */ {
/*  19 */   private HashMap parameters = new HashMap();
/*     */ 
/*     */   public HTMLChartSimpleFlash()
/*     */   {
/*  26 */     setTagName("object");
/*  27 */     setAttribute("classid", "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000");
/*  28 */     setWidth("100%");
/*  29 */     setHeight("100%");
/*  30 */     setAttribute("codebase", 
/*  31 */       "http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab");
/*     */ 
/*  33 */     add(createParam("wmode", "transparent"));
/*  34 */     add(createParam("quality", "high"));
/*  35 */     add(createParam("bgcolor", "#FFFFFF"));
/*  36 */     add(createParam("allowScriptAccess", "always"));
/*     */   }
/*     */ 
/*     */   private HTMLNode createParam(String name, String value)
/*     */   {
/*  45 */     HTMLNode param = new HTMLNode();
/*  46 */     param.setTagName("param");
/*  47 */     param.setAttribute("name", name);
/*  48 */     param.setAttribute("value", value);
/*  49 */     return param;
/*     */   }
/*     */ 
/*     */   private void finish() {
/*  53 */     String movie = ReportContext.getContext().getRequestURI("/reportSimple.swf", 
/*  54 */       true);
/*  55 */     StringBuffer sb = new StringBuffer(movie).append("?");
/*  56 */     for (Iterator keys = this.parameters.keySet().iterator(); keys
/*  57 */       .hasNext(); )
/*     */     {
/*  58 */       String key = (String)keys.next();
/*  59 */       sb.append(key).append("=")
/*  60 */         .append((String)this.parameters.get(key));
/*  61 */       if (keys.hasNext())
/*  62 */         sb.append("&");
/*     */     }
/*  64 */     sb.append("&autoLoad=true");
/*  65 */     add(createParam("movie", sb.toString()));
/*     */ 
/*  67 */     HTMLNode em = new HTMLNode();
/*  68 */     em.setTagName("embed");
/*  69 */     em.setAttribute("src", sb.toString());
/*  70 */     em.setAttribute("quality", "high");
/*  71 */     em.setAttribute("bgcolor", "#FFFFFF");
/*  72 */     em.setAttribute("width", "100%");
/*  73 */     em.setAttribute("height", "100%");
/*  74 */     em.setAttribute("name", getAttribute("id"));
/*  75 */     em.setAttribute("wmode", "transparent");
/*  76 */     em.setAttribute("align", "middle");
/*  77 */     em.setAttribute("play", "true");
/*  78 */     em.setAttribute("loop", "false");
/*  79 */     em.setAttribute("allowScriptAccess", "always");
/*  80 */     em.setAttribute("type", "application/x-shockwave-flash");
/*  81 */     em
/*  82 */       .setAttribute("pluginspage", 
/*  83 */       "http://www.adobe.com/go/getflashplayer");
/*  84 */     add(em);
/*     */   }
/*     */ 
/*     */   @Override
public void output(OutputStream output, String encoding)
/*     */     throws UnsupportedEncodingException, IOException
/*     */   {
/*  97 */     finish();
/*  98 */     super.output(output, encoding);
/*     */   }
/*     */ 
/*     */   public void setParameter(String name, String value) {
/* 102 */     this.parameters.put(name, value);
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLChartSimpleFlash
 * JD-Core Version:    0.5.3
 */