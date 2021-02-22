/*    */ package com.ztesoft.crm.report.rule.function;
/*    */ 
/*    */ import com.ztesoft.crm.report.db.data_new.Cell;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import com.ztesoft.crm.report.rule.Function;
/*    */ import com.ztesoft.crm.report.rule.FunctionInvoker;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class SetHtml
/*    */   implements FunctionInvoker
/*    */ {
/*    */   private final String format(String str, Function f)
/*    */   {
/* 28 */     if (Utils.isEmpty(str))
/* 29 */       return "";
/* 30 */     if (Utils.isEmpty(f))
/* 31 */       return str;
/* 32 */     Matcher m = Pattern.compile("[{][A-Za-z0-9_]+[}]").matcher(str);
/* 33 */     StringBuffer sb = new StringBuffer();
/* 34 */     while (m.find()) {
/* 35 */       String b = m.group();
/* 36 */       b = b.replaceAll("[{]|[}]", "");
/* 37 */       Object o = f.getVariateValue(b);
/* 38 */       if (o == null) o = "";
/* 39 */       m.appendReplacement(sb, o.toString());
/*    */     }
/*    */ 
/* 42 */     m.appendTail(sb);
/* 43 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   @Override
public Object invoke(Function func)
/*    */   {
/* 49 */     Cell cell = (Cell)func.getParameter(0);
/*    */ 
/* 51 */     String html = (String)func.getParameter(1);
/* 52 */     if (cell == null)
/* 53 */       return null;
/* 54 */     if (html == null) return null;
/* 55 */     html = html.replaceFirst("[{]value[}]", cell.getValue());
/*    */ 
/* 57 */     cell.setHtml(format(html, func));
/* 58 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.function.SetHtml
 * JD-Core Version:    0.5.3
 */