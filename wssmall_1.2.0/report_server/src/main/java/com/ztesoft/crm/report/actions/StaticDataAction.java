/*    */ package com.ztesoft.crm.report.actions;
/*    */ 
/*    */ import com.ztesoft.crm.report.ReportContext;
/*    */ import com.ztesoft.crm.report.interfaces.StaticDataGetter;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StaticDataAction extends AbstractAction
/*    */ {
/*    */   @Override
protected void execute(OutputStream out, ParameterMap map)
/*    */     throws Exception
/*    */   {
/* 27 */     StaticDataGetter getter = null;
/*    */     try {
/* 29 */       getter = (StaticDataGetter)Class.forName(
/* 30 */         ReportContext.getContext().getStaticDataGetter())
/* 31 */         .newInstance();
/*    */ 
/* 33 */       List arr = getter.execute(getContext(), getRequest(), 
/* 34 */         map.getString("attrCode"), map);
/* 35 */       StringBuffer sb = new StringBuffer();
/*    */ 
/* 37 */       sb.append("[");
/* 38 */       for (Iterator objs = arr.iterator(); objs.hasNext(); ) {
/* 39 */         sb.append(objs.next().toString());
/* 40 */         if (objs.hasNext()) {
/* 41 */           sb.append(",");
/*    */         }
/*    */       }
/* 44 */       sb.append("]");
/* 45 */       out.write(sb.toString().getBytes(map.getReplyEncoding()));
/*    */     }
/*    */     catch (Exception e) {
/* 48 */       e.printStackTrace(System.out);
/* 49 */       e.printStackTrace(System.err);
/*    */     }
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.actions.StaticDataAction
 * JD-Core Version:    0.5.3
 */