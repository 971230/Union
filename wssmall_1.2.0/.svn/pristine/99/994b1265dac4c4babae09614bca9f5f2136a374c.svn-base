package com.zte.cbss.autoprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.log4j.Logger;

/**
 * 工具类 
 * @author tanghaoyang
 *
 */
public class MyTools {
	private static final Logger log=Logger.getLogger(MyTools.class);
	
	/**
	 * 写入指定内容到指定文件
	 * @param filePath 指定路径，如“d:\\test\\2-登录后.html”
	 * @param content 文件内容
	 */
	public static void logToFile(String filePath, String content) {
		File file = new File(filePath);
		FileOutputStream outputStream =null;
		try{
			outputStream = new FileOutputStream(file);
			outputStream.write(content.getBytes());
			outputStream.flush();
			log.debug("生成文件["+filePath+"]成功");
		}catch(IOException e){
			log.info("生成文件["+filePath+"]出错");
		}finally{
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到指定cookie的值
	 * @param curCookieList cookie列表
	 * @param cookieName cookie名称
	 * @return
	 */
	public static String getCookieValue(List<Cookie> curCookieList,String cookieName) {
		
		if(curCookieList==null||curCookieList.size()==0){
			return null;
		}
		
		String value=null;
		for(Cookie ck:curCookieList ){
			if(ck.getName().equalsIgnoreCase(cookieName)){
				value=ck.getValue();
			}
		}
		return value;
	}
	
	/**
	 * 打开一个文件并返回该文件的内容
	 * @param szFileName
	 * @return
	 */
	public static String openFile( String szFileName ) {
		BufferedReader bis=null;
		try {
           bis= new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), "UTF-8") );
           String szContent="";
           String szTemp;
           
           while ( (szTemp = bis.readLine()) != null) {
               szContent+=szTemp+"\n";
           }
           return szContent;
       }
       catch( Exception e ) {
           return "";
       }finally{
    	   if(bis!=null)
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
	} 
}
