package com.powerise.ibss.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);
   public static boolean Debug = true;
   
   public static boolean isDate(String vDate,String format){
      try{
         SimpleDateFormat df =new SimpleDateFormat(format);
         Date dt =df.parse(vDate);
         return true;
      }catch(ParseException pe){
         return false;
      }
   }
   /**
    通过GlobalNames.ISO_TO_GBK定义对字符串进行ISO8859-1 对GBK的转换
    @param p_in - 要转换的字符串
    @return String
    */
   public static String isoToGBK(String p_in) {

      return isoToGBK(p_in, GlobalNames.ISO_TO_GBK);
   }

   public static String isoToGBK(String p_in, boolean flag) {

      if (flag) {
         if (!(p_in == null || p_in.equals(""))) {
            try {
               p_in = new String(p_in.getBytes("ISO-8859-1"), "GBK");
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         }
      }
      return p_in;
   }

   public static String gbkToISO(String p_in) {

      return gbkToISO(p_in, GlobalNames.GBK_TO_ISO);
   }

   public static String gbkToISO(String p_in, boolean flag) {

      if (flag) {
         if (!(p_in == null || p_in.equals(""))) {
            try {
               p_in = new String(p_in.getBytes("GBK"), "ISO-8859-1");
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         }
      }
      return p_in;
   }

   public static String dateToString(Date vDate, String format) {
      SimpleDateFormat df = new SimpleDateFormat(format);
      return df.format(vDate);
   }

   /**
    * 把具有日期格式（format 格式）的字符串转换为日期类型
    * @param vDate  具有日期格式的字符串
    * @param format  日期格式
    * @return 返回日期类型
    */
   public static Date toDate(String vDate, String format) {
      try{

       SimpleDateFormat df =new SimpleDateFormat(format);
       Date dt =df.parse(vDate);
         return dt;
      }catch(ParseException pe){
         return null;
      }
   }

   public static void println(Object p_msg) {
      if (GlobalNames.PRINT_TERMINAL) {
         //logs(SysSet.getConfigDirectory(), p_msg.toString()+"\n");
         logger.info("\n>>>>>..." + p_msg.toString());
      }
   }

   public static void log(Object p_msg) {
//      if (GlobalNames.LOG_TYPE == 1) {
//         try {
//            HashMap props = SysSet.getTagProps("System");
//            if (props != null && props.get("LogPath") != null) {
//               String path = (String) props.get("LogPath");
//               logs(path, p_msg.toString() + "%%%%%");
//            } else
//               logger.info("系统设置日志输出到文件，但配置输出的文件出错。[Utility.println]");
//         } catch (Exception e) {
//            e.printStackTrace();
//         }
//      } else {
//         Utility.println(p_msg);
//      }
   }

   /**
    对HTML的特殊字符进行转码
    @param value
    @return String
    */
   public static String filter(String value) {

      if (value == null)
         return (null);

      char content[] = new char[value.length()];
      value.getChars(0, value.length(), content, 0);
      StringBuffer result = new StringBuffer(content.length + 50);
      for (int i = 0; i < content.length; i++) {
         switch (content[i]) {
            case '<':
               result.append("&lt;");
               break;
            case '>':
               result.append("&gt;");
               break;
            case '&':
               result.append("&amp;");
               break;
            case '"':
               result.append("&quot;");
               break;
            case '\'':
               result.append("&#39;");
               break;
            default:
               result.append(content[i]);
         }
      }
      return (result.toString());
   }

   public static synchronized void logs(String strFileName, String strMsg) {
      boolean bAppend;
      FileWriter out = null;
      Date dt = new Date();
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      strFileName += System.getProperty("file.separator") + df.format(dt) + ".log";
      File logFile = new File(strFileName);
      try {
         if (!logFile.exists()) {
            if (!logFile.createNewFile())
               return;
            bAppend = false;
         } else {
            if (logFile.length() > 102400) {
               //if the log file size greater than 100K,write it again.
               bAppend = false;
            } else
               bAppend = true;
         }
         out = new FileWriter(strFileName, bAppend);
         out.write(strMsg);
         out.flush();
      } catch (IOException ie) {
         ie.printStackTrace();
      }finally{
         try{
            if(out !=null){
               out.close();
               out =null;
            }
         }catch(Exception e){}
      }
   }
   
   public static void writeFile(String strFileName, String strMsg) {
      boolean bAppend;
      FileWriter out = null;
      try {
         out = new FileWriter(strFileName, false);
         out.write(strMsg);
         out.flush();
      } catch (IOException ie) {
         ie.printStackTrace();
      }finally{
         try{
            if(out !=null){
               out.close();
               out =null;
            }
         }catch(Exception e){}
      }
   }

   /**
   对字符串进行分割，分割符为p_tag,返回数组
   @param p_in
   @param p_tag
   @return
    */
   public static String[] split(String p_in, String p_tag) {
      Vector v =new Vector();
      int pos =p_in.indexOf(p_tag);
      while(pos >0){
         v.add(p_in.substring(0, pos));
         p_in =p_in.substring(pos+p_tag.length());
         pos =p_in.indexOf(p_tag);
      }
      v.add(p_in);
      String[] a=new String[v.size()];
      for(int i=0;i<v.size();i++){
         a[i] =(String)v.get(i);
      }
      v.clear();
      return a;
   }
   
   public static String replace(String soruce){
      return replace(soruce, "'", "''");
   }
   
   public static String replace(String source,String tag,String rep){
      if(source ==null)
         return null;
      if(source.indexOf(tag) ==-1)
         return source;
      int j=0;
      int i=0;
      StringBuffer strReturn=new StringBuffer(source.length() +100);
      String temp;
      int tagc =tag.length();
      while(i<source.length()){
         if(source.substring(i).startsWith(tag)){
            temp =source.substring(j,i)+rep;
            strReturn.append(temp);
            i+=tagc;
            j=i;
         }
         else{
            i+=1;
         }
         
      }
      strReturn.append(source.substring(j));
      return strReturn.toString();
   }
}
