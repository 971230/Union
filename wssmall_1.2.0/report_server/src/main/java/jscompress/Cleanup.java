/*    */ package jscompress;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class Cleanup
/*    */ {
/*    */   private void cleansvn(File f)
/*    */   {
/* 22 */     File[] fls = f.listFiles();
/* 23 */     if (fls == null) return;
/* 24 */     for (int i = 0; i < fls.length; ++i)
/* 25 */       if (fls[i].getName().equals(".svn"))
/* 26 */         cleansvn1(fls[i]);
/*    */       else
/* 28 */         cleansvn(fls[i]);
/*    */   }
/*    */ 
/*    */   private void cleansvn1(File f)
/*    */   {
/* 34 */     if (f.isDirectory()) {
/* 35 */       File[] fls = f.listFiles();
/* 36 */       if (fls != null) {
/* 37 */         for (int i = 0; i < fls.length; ++i) {
/* 38 */           cleansvn1(fls[i]);
/*    */         }
/*    */       }
/*    */     }
/* 42 */     f.delete();
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 50 */     new Cleanup().cleansvn(
/* 51 */       new File("F:/projects/report/WebRoot/reportconfig"));
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     jscompress.Cleanup
 * JD-Core Version:    0.5.3
 */