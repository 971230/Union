/*     */ package jscompress;
/*     */ 
/*     */ import com.ztesoft.crm.report.lang.StreamUtils;

/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
import org.apache.log4j.Logger;
/*     */ 
/*     */ public class JsBuilder
/*     */ {
	private static Logger logger = Logger.getLogger(JsBuilder.class);
/*  31 */   private String preffix = "/com/ztesoft/crm/report/resouce/";
/*     */ 
/*     */   private String getFile(String s) {
/*  34 */     return super.getClass().getResource(
/*  35 */       this.preffix + s).getFile();
/*     */   }
/*     */ 
/*     */   public void build() throws IOException
/*     */   {
/*  40 */     BufferedReader reader = null;
/*  41 */     String uncompress = "js/report-all-uncompress.js";
/*  42 */     String compress = "js/report-all.js";
/*  43 */     FileOutputStream js = null;
/*     */ 
/*  45 */     String line = null;
/*     */     try {
/*  47 */       reader = new BufferedReader(
/*  48 */         new InputStreamReader(super.getClass().getResourceAsStream("build.properties")));
/*     */ 
/*  50 */       js = new FileOutputStream(getFile("js/report-all-uncompress.js"));
/*  51 */       while ((line = reader.readLine()) != null) {
/*  52 */         if (line.length() == 0)
/*     */           continue;
/*  54 */         append(js, line);
/*     */       }
/*     */     } catch (Exception localException1) {
/*     */     } finally {
/*  58 */       StreamUtils.close(js);
/*  59 */       StreamUtils.close(reader);
/*     */     }
/*     */     try
/*     */     {
/*  63 */       StringBuffer cmd = new StringBuffer();
/*  64 */       cmd
/*  65 */         .append("D:\\sun\\jdk5\\bin\\java -jar  F:\\projects\\report\\libs\\yuicompressor-2.4.2.jar ");
/*     */ 
/*  67 */       cmd.append(getFile(uncompress));
/*  68 */       cmd.append(" -o ").append(getFile(compress));
/*  69 */       cmd.append(" --charset gbk");
/*     */ 
/*  71 */       SystemCommandExec cmdexe = new SystemCommandExec();
/*     */ 
/*  73 */       cmdexe.execute(new String[] { "cmd", "/C", cmd.toString() }, 
/*  74 */         new SystemCommandMessageParser()
/*     */       {
/*     */         @Override
public Object parse(byte[] content)
/*     */         {
/*  78 */           return new String(content);
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e) {
/*  83 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   void deleteAll(File f)
/*     */   {
/*  89 */     File[] fs = f.listFiles(new FileFilter()
/*     */     {
/*     */       @Override
public boolean accept(File pathname)
/*     */       {
/*  93 */         if (pathname.getName().toLowerCase().equals("report-all.js"))
/*  94 */           return false;
/*  95 */         if (pathname.isDirectory())
/*  96 */           return true;
/*  97 */         return pathname.getName().toLowerCase().endsWith(".js");
/*     */       }
/*     */     });
/* 100 */     for (int i = 0; i < fs.length; ++i)
/* 101 */       if (fs[i].isDirectory())
/* 102 */         deleteAll(fs[i]);
/*     */       else
/* 104 */         fs[i].delete();
/*     */   }
/*     */ 
/*     */   private void append(OutputStream out, String f)
/*     */   {
/* 110 */     FileInputStream in = null;
/*     */     try {
/* 112 */       in = new FileInputStream(getFile(f));
/* 113 */       byte[] buff = new byte[1024];
/* 114 */       int len = 0;
/* 115 */       while ((len = in.read(buff)) > 0) {
/* 116 */         out.write(buff, 0, len);
/*     */       }
/*     */ 
/* 119 */       out.write("\n".getBytes());
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*     */     finally {
/* 124 */       StreamUtils.close(in);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] agrs) throws IOException {
/* 129 */     new JsBuilder().build();
/* 130 */     logger.info("打包完成");
/*     */   }
/*     */ }

/* Location:           F:\mblmall1.0\wssmall\WebContent\WEB-INF\lib\crm-report.jar
 * Qualified Name:     jscompress.JsBuilder
 * JD-Core Version:    0.5.3
 */