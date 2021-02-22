/*    */ package com.ztesoft.crm.report.actions;
/*    */ 
/*    */ import com.ztesoft.crm.report.log.ReportLogger;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class ResourceAction extends AbstractAction
/*    */ {
/*    */   @Override
protected void setCache()
/*    */   {
/*    */   }
/*    */ 
/*    */   @Override
protected void execute(OutputStream out, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 30 */     InputStream in = super.getClass().getResourceAsStream(getUrl());
/*    */     try
/*    */     {
/* 33 */       byte[] buff = new byte[1024];
/* 34 */       int len = 0;
/* 35 */       while ((len = in.read(buff)) > 0) {
/* 36 */         out.write(buff, 0, len);
/*    */       }
/*    */ 	
/* 39 */       buff = null;
/*    */     } catch (Exception e) {
/* 41 */       ReportLogger.getLogger().info("请求资源失败，URL：{0}", getUrl());
/* 42 */       throw e;
/*    */     } finally {
/*    */       try {
/* 45 */         if (in != null)
/* 46 */           in.close();
/*    */       }
/*    */       catch (Exception localException1)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.ResourceAction
 * JD-Core Version:    0.5.3
 */