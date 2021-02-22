/*    */ package com.ztesoft.crm.report.rule;
/*    */ 
/*    */ import com.ztesoft.crm.report.rule.function.Foot;
/*    */ import com.ztesoft.crm.report.rule.function.GetParameter;
/*    */ import com.ztesoft.crm.report.rule.function.GetRange;
/*    */ import com.ztesoft.crm.report.rule.function.GetRows;
/*    */ import com.ztesoft.crm.report.rule.function.GetXName;
/*    */ import com.ztesoft.crm.report.rule.function.GetXValue;
/*    */ import com.ztesoft.crm.report.rule.function.GetYName;
/*    */ import com.ztesoft.crm.report.rule.function.GetYValue;
/*    */ import com.ztesoft.crm.report.rule.function.Item;
/*    */ import com.ztesoft.crm.report.rule.function.Length;
/*    */ import com.ztesoft.crm.report.rule.function.Rank;
/*    */ import com.ztesoft.crm.report.rule.function.Remove;
/*    */ import com.ztesoft.crm.report.rule.function.SetHtml;
/*    */ import com.ztesoft.crm.report.rule.function.SetStyle;
/*    */ import com.ztesoft.crm.report.rule.function.Sort;
/*    */ import com.ztesoft.crm.report.rule.function.Top;
/*    */ import com.ztesoft.crm.report.rule.function.Value;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class FunctionFactory
/*    */ {
/* 56 */   private static final FunctionFactory factory = new FunctionFactory();
/* 57 */   private HashMap map = new HashMap();
/*    */ 
/*    */   private FunctionFactory()
/*    */   {
/* 37 */     this.map.put("rank", Rank.class.getName());
/* 38 */     this.map.put("getRange", GetRange.class.getName());
/* 39 */     this.map.put("sort", Sort.class.getName());
/* 40 */     this.map.put("top", Top.class.getName());
/* 41 */     this.map.put("foot", Foot.class.getName());
/* 42 */     this.map.put("getParameter", GetParameter.class.getName());
/* 43 */     this.map.put("setStyle", SetStyle.class.getName());
/* 44 */     this.map.put("setHtml", SetHtml.class.getName());
/* 45 */     this.map.put("value", Value.class.getName());
/* 46 */     this.map.put("item", Item.class.getName());
/* 47 */     this.map.put("length", Length.class.getName());
/* 48 */     this.map.put("getRows", GetRows.class.getName());
/* 49 */     this.map.put("remove", Remove.class.getName());
/* 50 */     this.map.put("getXName", GetXName.class.getName());
/* 51 */     this.map.put("getYName", GetYName.class.getName());
/* 52 */     this.map.put("getXValue", GetXValue.class.getName());
/* 53 */     this.map.put("getYValue", GetYValue.class.getName());
/*    */   }
/*    */ 
/*    */   public static final FunctionInvoker getFunctionInvoker(String name)
/*    */   {
/* 60 */     String cn = (String)factory.map.get(name);
/*    */ 
/* 62 */     if (cn != null)
/*    */       try {
/* 64 */         return ((FunctionInvoker)Class.forName(cn).newInstance());
/*    */       }
/*    */       catch (Exception localException)
/*    */       {
/*    */       }
/* 69 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.rule.FunctionFactory
 * JD-Core Version:    0.5.3
 */