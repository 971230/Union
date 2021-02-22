package com.ztesoft.net.mall.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class CrawlerUtils {
	
	private static Logger logger = Logger.getLogger(CrawlerUtils.class);
	
	/**
	 * 判断字符串是否为空，不为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(null == str || "".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断字符串是否为空，为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 返回字符串值，null返回空值""
	 * @param str
	 * @return
	 */
	public static String getValues(String str){
		if(null == str ||"".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 替换空格、回车、换行、制表位为空字符
	 * @param str
	 * @return
	 */
	public static String strReplaceBlank(String str){
		Pattern pattern = Pattern.compile("\r|\t|\n");
		Matcher m = pattern.matcher(str);
		return m.replaceAll("");
	}
	
	/**
	 * 日期数据按传入格式转换为字符型日期
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String dateToStringByFormater(Date date,String formater){
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }
	
	/**
	 * 日期数据转换为字符型日期
	 * 输入:Date
	 * 输出:yyyy-mm-dd hh24:mi:ss
	 * @return
	 */
	public static String strFormatDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	/**
	 * 字符转换为日期
	 * 输入：yyyymmddhh24miss
	 * 输出:Date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取字符型的当前时间
	 * yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
	/**
	 * 以Post方式往服务端发送请求数据
	 * @param json
	 * @param urlAddr
	 * @return
	 */
	public static String sendPostHttpRequest(String json , String urlAddr){

		StringBuffer responseStr = new StringBuffer();
		try {
//			指定服务器
			URL url = new URL(urlAddr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			指定通信方式
			conn.setRequestMethod("POST");
//			指定超时时长
			conn.setConnectTimeout(30 * 1000);
//			不使用缓存
			conn.setUseCaches(false);
//			允许输入/输出
			conn.setDoInput(true);
			conn.setDoOutput(true);
//			设置content-type
			conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			使用字节流往服务端写数据
			BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
			logger.info("URL：" + urlAddr);
			logger.info("同步json内容：" + json);
//			设置字符集
			out.write(json.getBytes("UTF-8"));
//			清空缓存、关闭连接
			out.flush();
			out.close();
//			接收服务端的返回消息
			InputStream inputStream = conn.getInputStream();
			if(null == inputStream){
				return "";
			}else{
//				设置读取输入流的字符集
				BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				String line;
				while ((line = rd.readLine()) != null) {
		        	responseStr.append(line);
		        }
				rd.close();
				inputStream.close();
			}
		} catch (IOException e) {
			responseStr.append(e.getMessage());
			logger.info(e.getMessage(), e);
		}
		logger.info("对端返回消息：" + responseStr.toString());
		return responseStr.toString();
	}
	
	/**
	 * 根据订单来源系统中文名获得编码
	 * @param orderFromName
	 * @return
	 */
	public static String getOrderFromId(String orderFromName){
		if("淘宝".equalsIgnoreCase(orderFromName)){
			return "10001";
		}else if( "联通商城".equalsIgnoreCase(orderFromName) ){
			return "10002";
		}else if( "总部商城".equalsIgnoreCase(orderFromName) ){
			return "10003";
		}else if( "网盟店铺".equalsIgnoreCase(orderFromName) ){
			return "10004";
		}else if("拍拍".equalsIgnoreCase(orderFromName) ){
			return "10005";
		}else if("农行商城".equalsIgnoreCase(orderFromName) ){
			return "10006";
		}else if("360商城".equalsIgnoreCase(orderFromName) ){
			return "10007";
		}else if("沃云购".equalsIgnoreCase(orderFromName)){
			return "10008";
		}else if("订单系统".equalsIgnoreCase(orderFromName)){
			return "10009";
		}else if("WMS".equalsIgnoreCase(orderFromName)){
			return "10010";
		}else if("商品管理系统".equalsIgnoreCase(orderFromName)){
			return "10011";
		}else if("淘宝分销".equalsIgnoreCase(orderFromName)){
			return "10012";
		}else if("沃商城".equalsIgnoreCase(orderFromName)){
			return "10036";
		}else if("CPS".equalsIgnoreCase(orderFromName)){
			return "10037";
		}else if("异业联盟".equalsIgnoreCase(orderFromName)){
			return "10038";
		}else if("电话商城".equalsIgnoreCase(orderFromName)){
			return "10015";
		}else if("微商城".equalsIgnoreCase(orderFromName)){
			return "10030";
		}else if("沃云购".equalsIgnoreCase(orderFromName)){
			return "10031";
		}else if("沃货架".equalsIgnoreCase(orderFromName)){
			return "10031";
		}else if("营业厅U惠站".equalsIgnoreCase(orderFromName)){
			return "10032";
		}else if("销售联盟".equalsIgnoreCase(orderFromName)){
			return "10033";
		}else if("vip商城".equalsIgnoreCase(orderFromName)){
			return "10034";
		}else if("电子沃店".equalsIgnoreCase(orderFromName)){
			return "10035";
		}else if("百度担保".equalsIgnoreCase(orderFromName)){
			return "10039";
		}else if("广州天猫".equalsIgnoreCase(orderFromName)){
			return "10057";
		}else{
			return orderFromName;
		}
	}
	
	/**
	 * 金额由厘转换为元
	 * @param money
	 * @return
	 */
	public static String parseMoney(Integer money){
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setGroupingUsed(false);
		String s = format.format( (double) money / 1000 );
		return s;
	}
	
	/**
	 * 金额由元转为厘
	 * @param money
	 * @return
	 */
	public static String parseMoneyToLi(double money){
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setGroupingUsed(false);
		String s = format.format(money * 1000);
		return s;
	}
	
	/**
	 * 获取所有集合2(element)在集合1(all)中不存在的元素
	 * @param all
	 * @param element
	 * @return
	 */
	public static String notContainsElement(List<String> all , List<String> element){
		StringBuffer stringBuffer = new StringBuffer();
		for (String name : element) {
			if( ! all.contains(name) ){
				stringBuffer.append(name + ".");
			}
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 获取json字符串中某一个key的value
	 * @param json 
	 * @param key 键
	 * @return
	 */
	public static String searchValue(String json, String key) {
		json = strReplaceBlank(json);
		String regex = "\"" +key + "\":(.*?),";
		Matcher matcher=Pattern.compile(regex).matcher(json);
		String value = "";
		
		while(matcher.find())
		{
			value = matcher.group(1);
		}
		// 去掉双引号("")
		value = value.trim().replaceAll("\"(\\w+)\"", "$1"); 
		return value;
	}
	// 判断一个字符串是否都为数字  
	public static boolean isDigit(String strNum) {  
	    return strNum.matches("[0-9]{1,}");  
	}  
	  
	// 判断一个字符串是否都为数字  
	public static boolean isDigit1(String strNum) {  
	    Pattern pattern = Pattern.compile("[0-9]{1,}");  
	    Matcher matcher = pattern.matcher(strNum);  
	    return matcher.matches();  
	}  
	  
    //截取数字  
    public static String getNumbers(String content) {  
	       Pattern pattern = Pattern.compile("\\d+");  
	       Matcher matcher = pattern.matcher(content);  
	       while (matcher.find()) {  
	           return matcher.group(0);  
	       }  
	       return "";  
     }  
	 //截取非数字  
	 public static String splitNotNumber(String content) {  
	    Pattern pattern = Pattern.compile("\\D+");  
	    Matcher matcher = pattern.matcher(content);  
	    while (matcher.find()) {  
	        return matcher.group(0);  
	    }  
	    return "";  
	 }  
	 
	 //判断是否是json结构 
	 public static boolean isJson(String value) { 
		 try { 
			JSONObject.fromObject(value);
		 } catch (JSONException e) { 
			 return false; 
		 } 
	 	return true; 
	 }
}
