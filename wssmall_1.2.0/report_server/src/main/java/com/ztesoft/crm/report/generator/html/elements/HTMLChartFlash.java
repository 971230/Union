/*    */ package com.ztesoft.crm.report.generator.html.elements;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class HTMLChartFlash extends HTMLNode
/*    */ {
/* 19 */   private HashMap parameters = new HashMap();
/*    */ 
/*    */   public HTMLChartFlash()
/*    */   {
/* 26 */     setTagName("object");
/* 27 */     setAttribute("classid", "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000");
/* 28 */     setWidth("100%");
/* 29 */     setHeight("100%");
/* 30 */     setAttribute("codebase", 
/* 31 */       "http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab");
/*    */ 
/* 33 */     add(createParam("wmode", "transparent"));
/* 34 */     add(createParam("quality", "high"));
/* 35 */     add(createParam("bgcolor", "#FFFFFF"));
/* 36 */     add(createParam("allowScriptAccess", "always"));
/*    */   }
/*    */ 
/*    */   private HTMLNode createParam(String name, String value)
/*    */   {
/* 41 */     HTMLNode param = new HTMLNode();
/* 42 */     param.setTagName("param");
/* 43 */     param.setAttribute("name", name);
/* 44 */     param.setAttribute("value", value);
/* 45 */     return param;
/*    */   }
/*    */ 
/*    */   private void finish() {
/* 49 */     String movie = ReportContext.getContext().getRequestURI("/report.swf", 
/* 50 */       true);
/* 51 */     StringBuffer sb = new StringBuffer(movie).append("?");
/* 52 */     for (Iterator keys = this.parameters.keySet().iterator(); keys
/* 53 */       .hasNext(); )
/*    */     {
/* 54 */       String key = (String)keys.next();
/* 55 */       sb.append(key).append("=")
/* 56 */         .append((String)this.parameters.get(key));
/* 57 */       if (keys.hasNext())
/* 58 */         sb.append("&");
/*    */     }
/* 60 */     sb.append("&autoLoad=true");
/* 61 */     add(createParam("movie", sb.toString()));
/*    */ 
/* 63 */     HTMLNode em = new HTMLNode();
/* 64 */     em.setTagName("embed");
/* 65 */     em.setAttribute("src", sb.toString());
/* 66 */     em.setAttribute("quality", "high");
/* 67 */     em.setAttribute("bgcolor", "#FFFFFF");
/* 68 */     em.setAttribute("width", "100%");
/* 69 */     em.setAttribute("height", "100%");
/* 70 */     em.setAttribute("name", getAttribute("id"));
/* 71 */     em.setAttribute("wmode", "transparent");
/* 72 */     em.setAttribute("align", "middle");
/* 73 */     em.setAttribute("play", "true");
/* 74 */     em.setAttribute("loop", "false");
/* 75 */     em.setAttribute("allowScriptAccess", "always");
/* 76 */     em.setAttribute("type", "application/x-shockwave-flash");
/* 77 */     em
/* 78 */       .setAttribute("pluginspage", 
/* 79 */       "http://www.adobe.com/go/getflashplayer");
/* 80 */     add(em);
/*    */   }
/*    */ 
/*    */   @Override
public void output(OutputStream output, String encoding)
/*    */     throws UnsupportedEncodingException, IOException
/*    */   {
/* 93 */     finish();
/* 94 */     super.output(output, encoding);
/*    */   }
/*    */ 
/*    */   public void setParameter(String name, String value) {
/* 98 */     this.parameters.put(name, value);
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.generator.html.elements.HTMLChartFlash
 * JD-Core Version:    0.5.3
 */