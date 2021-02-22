/*    */ package com.ztesoft.crm.report.config.elements;
/*    */ import com.ztesoft.crm.report.lang.Utils;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class Models extends Element
/*    */ {
/*    */   private static final long serialVersionUID = -8854850013714529041L;
/*    */ 
/*    */   public final Model findModel(String uuid)
/*    */   {
/* 34 */     if (Utils.isEmpty(uuid))
/* 35 */       return null;
/* 36 */     for (Iterator objs = childs().iterator(); objs.hasNext(); ) {
/* 37 */       Model m = (Model)objs.next();
/* 38 */       if (uuid.equals(m.getId()))
/* 39 */         return m;
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     com.ztesoft.crm.report.config.elements.Models
 * JD-Core Version:    0.5.3
 */