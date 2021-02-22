/*     */ package com.ztesoft.crm.report.actions;
/*     */ import java.io.OutputStream;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public abstract class AbstractAction
/*     */   implements Action
/*     */ {
/*     */   private String contentType;
/*     */   private ServletContext context;
/*     */   private OutputStream out;
/*     */   private HttpServletRequest request;
/*     */   private HttpServletResponse response;
/*     */   private String url;
/*     */ 
/*     */   protected void close()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected abstract void execute(OutputStream paramOutputStream, ParameterMap paramParameterMap)
/*     */     throws Exception;
/*     */ 
/*     */   @Override
public final void execute(ServletContext context, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  54 */     this.context = context;
/*  55 */     this.request = request;
/*  56 */     this.response = response;
/*  57 */     ParameterMap map = new ParameterMap(request);
/*  58 */     map.setUrl(getUrl());
/*  59 */     if ("chart".equals(map.getReplyType())) {
/*  60 */       setContentType("text/xml");
/*     */     }
/*  62 */     if ("excel".equals(map.getReplyType())) {
/*  63 */       setContentType("application/octet-stream");
/*     */     }
/*  65 */     setCache();
/*  66 */     response.setContentType(
/*  67 */       this.contentType + ";charset=" + 
/*  68 */       ((map.getReplyEncoding() != null) ? map.getReplyEncoding() : 
/*  69 */       "GBK"));
/*     */     try
/*     */     {
/*  72 */       //String encode = request.getHeader("Accept-Encoding");
///*  73 */       if (!(Utils.isEmpty(encode))) { //add by wui 已经压缩过一次，不需要在压缩处理
///*  74 */         if (encode.toLowerCase().indexOf("gzip") >= 0) {
///*  75 */           response.setHeader("Content-Encoding", "gzip");
///*  76 */           this.out = new GZIPOutputStream(response.getOutputStream());
///*     */         }
///*  78 */         if (encode.toLowerCase().indexOf("compress") >= 0) {
///*  79 */           response.setHeader("Content-Encoding", "compress");
///*  80 */           this.out = new ZipOutputStream(response.getOutputStream());
///*     */         }
///*     */       } else {
/*  83 */       this.out = response.getOutputStream(); //}
/*  84 */       execute(this.out, map);
/*     */     } catch (Exception e) {
/*  86 */       e.printStackTrace(System.out);
/*  87 */       throw e;
/*     */     }finally{
				
}
/*     */   }
/*     */ 
/*     */   protected final ServletContext getContext()
/*     */   {
/*  95 */     return this.context;
/*     */   }
/*     */ 
/*     */   protected final HttpServletRequest getRequest()
/*     */   {
/* 102 */     return this.request;
/*     */   }
/*     */ 
/*     */   protected final HttpServletResponse getResponse()
/*     */   {
/* 109 */     return this.response;
/*     */   }
/*     */ 
/*     */   protected void setCache()
/*     */   {
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 119 */     return this.url;
/*     */   }
/*     */ 
/*     */   @Override
public final void release()
/*     */   {
/* 124 */     this.context = null;
/* 125 */     this.request = null;
/* 126 */     this.response = null;
/* 127 */     close();
/*     */     try {
/* 129 */       if (this.out != null)
/* 130 */         this.out.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   @Override
public final void setContentType(String str) {
/* 137 */     this.contentType = str;
/*     */   }
/*     */ 
/*     */   @Override
public void setUrl(String url)
/*     */   {
/* 145 */     this.url = url;
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.AbstractAction
 * JD-Core Version:    0.5.3
 */