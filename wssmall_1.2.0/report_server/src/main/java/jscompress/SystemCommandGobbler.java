/*    */ package jscompress;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class SystemCommandGobbler
/*    */   implements Runnable
/*    */ {
/*    */   private OutputStream out;
/*    */   private InputStream in;
/*    */ 
/*    */   public SystemCommandGobbler(InputStream in, OutputStream out)
/*    */   {
/* 27 */     this.out = out;
/* 28 */     this.in = in;
/*    */   }
/*    */ 
/*    */   @Override
public void run() {
/* 32 */     int len = 0;
/* 33 */     byte[] buff = new byte[1024];
/*    */     try {
/* 35 */       while ((len = this.in.read(buff)) > 0)
/* 36 */         this.out.write(buff, 0, len);
/*    */     }
/*    */     catch (Exception e) {
/* 39 */       e.printStackTrace();
/*    */     }
/*    */     try {
/* 42 */       this.in.close();
/*    */     } catch (Exception localException1) {
/*    */     }
/*    */     try {
/* 46 */       this.out.close();
/*    */     } catch (Exception localException2) {
/*    */     }
/* 49 */     this.in = null;
/* 50 */     this.out = null;
/* 51 */     buff = null;
/*    */   }
/*    */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     jscompress.SystemCommandGobbler
 * JD-Core Version:    0.5.3
 */