package com.ztesoft.net.mall.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ztesoft.form.util.StringUtil;

/**
 * 网店工具类
 * 
 * @author apexking
 * 
 */
public abstract class UrlUtils {
	private static Logger logger = Logger.getLogger(UrlUtils.class);

	//用于搜索的字段名字
	private static String[] searchFields = new String[]{"cat","brand","price","sort","prop","circlar","capacity","color",
			"diameter","jpgn","mate","moisture","nattr","refractive","tyjgn","page","tag","order","time_order","keyword","distype","agn"}; //添加agn作为商户id
	
	
	public static String getParamStr(String servletPath){
		String pattern = "/wssquery-(.*).html";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(servletPath);
		String str= servletPath;
		if (m.find()) {
			str = m.replaceAll("$1");
		}
		return str;
		
	}
	
	/**
	 * 组合排列搜索条件，并组合成链接地址返回
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String addUrl(String url, String name, String value){
		String pattern = "/wssquery-(.*)";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		String str= "";
		if (m.find()) {
			str = m.replaceAll("$1");
		}		
		if(str == null || str.equals("")){
			return url;
		}
		//组合新的URL
		String newUrl = "";
		String temp = null;
		for(String field : searchFields){
			temp = getParamStringValue(str,field);
			if(temp != null){
				newUrl += "-" + field + "-" + temp;
			}
			if(name != null && name.equals(field)){ //添加新的url 
				newUrl += "-" + field + "-" + value;
			}
		}
		return "/wssquery" + newUrl + ".html";
	}
	
	
	/**
	 * 解析一个搜索地址字串（在网店搜索页面中），根据传递的参数字串解析出参数值如：<br/>
	 * 有参数字串：cat{5}keyword{测试}<br/>
	 * 则cat参数值为:5,keyword参数值为:测试<br/>
	 * 
	 * @param url搜索地址字串
	 * @param name 参数名称
	 * @return
	 */
	public static String getParamStringValue(String param, String name) {//wssquery-cat-1.html
//		String pattern = "(.*)" + name + "\\{(.[^\\{]*)\\}(.*)";
		param = getParamStr(param);
		String pattern = "(.*)" + name + "\\-(.[^\\-]*)(.*)";
		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(param);
		if (m.find()) {
			value = m.replaceAll("$2");
		}
		String prefix =".html";
		if(!StringUtil.isEmpty(value) && value.lastIndexOf(prefix)>-1)
			value = value.replace(prefix, "");
		return value;
	} 
	
	
	
	public static int getParamInitValue(String param, String name) {
		String temp_str = getParamStringValue(param,name);
		
		int value = Integer.valueOf(temp_str);
		return value;
		
	}
	
	/**
	 * 解析一个搜索地址字串（在网店搜索页面中），得到排除某个参数后的字串
	 * @param url 搜索字串
	 * @param name 要排除的字串
	 * @return
	 */
	public static String getExParamUrl(String url,String name){//wssquery-cat-1-prop-1-0,2-1.html
//		String pattern = "(.*)" + name + "\\{(.[^\\{]*)\\}(.*)";
		String pattern = "(.*)" + name + "\\-(.[^\\-]*)(\\-|.*)(.*)";
//		logger.info(pattern);
		String value = "";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		if (m.find()) {
			value = m.replaceAll("$1$4");
		}else{
			value=url;
		}
		if(value != null){
			if(value.startsWith("-")){
				value = value.substring(1,value.length());
			}
			if(value.endsWith("-")){
				value = value.substring(0,value.length()-1);
			}
		}
		return value;
 
	}
	
	
	
	
	
	/**
	 * 操作一个搜索字串：向字串中添加参数值。<br/>
	 * 在原有参数值的基础上加入新的参数值，用“,”号隔开。
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String appendParamValue(String url,String name,String value){
		
		String old_value= getParamStringValue(url, name);//wssquery-cat-1-prop-1-0,2-1.html
		String new_url =  getExParamUrl(url,name);
		if(old_value!=null){
			if("prop".equals(name)){ //对于属性字串的特殊处理
				old_value= old_value.replaceAll( value.split("_")[0] +"_(\\d+)", "");//0-0,1-0
				old_value=old_value.replace(",,", ",");
				if( old_value.startsWith(",") ){
					old_value  = old_value.substring(1,old_value.length());
				}
				
				if(old_value.endsWith(",")){
					old_value = old_value.substring(0,old_value.length()-1);
				}
				
			}
			
			if(!old_value.equals(""))
				value=","+value;
			
		}else{
			old_value="";
		}
		
//		new_url =new_url+name+"-"+old_value+value;
		if("/wssquery".equals(new_url))
		{ //add bywui
			new_url +="-"+name+"-"+old_value+value+".html";
			return new_url;
		}
		new_url = addUrl(new_url,name,old_value+value);
		
		return new_url;
	}
	
	
	/**
	 * 解析一个伪静态url字串<br/>
	 * 得到这个伪静脉url字串的前缀<br/>
	 * 如：/goods_list_tag-tag_id{3}.html 得到goods_list_tag<br/>
	 * /wssquery-cat_id{3}page{1}.html 得到search
	 * @param url
	 * @return
	 */
	public static String getUrlPrefix(String url){
		String pattern = "/(.*)-(.*)";

		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		if (m.find()) {
			value = m.replaceAll("$1"); 
		}else{
			value=null;
		}
		return value;
	}
	
	
	/**
	 * 获取排除某个属性后的属性字串
	 * @param index
	 * @param propStr
	 * @return
	 */
	public static String getPropExSelf(int index,String url){//wssquery-cat-1-prop-1-0,2-1.html
		
		if(StringUtil.isEmpty(url)){
			return url;
		}
		String propstr = getParamStringValue(url, "prop");
		if(StringUtil.isEmpty(propstr)) return url;
		
		String newprostr ="";
		String[] propar = propstr.split(",");
		for(String prop:propar){
			String[] ar =prop.split("_");
			if( !ar[0].equals(""+index )){ //剔除掉这个属性
				if(!newprostr.equals(""))
					newprostr+=",";
				newprostr+=prop;
			}
		}
		if(!StringUtil.isEmpty(newprostr)){
			url =url.replaceAll(propstr, newprostr);
		}else{
			url = url.replaceAll("-prop-" + propstr, "");
		}
		return url;
		
	}
	
	public static void main(String args[]){
//		String str = UrlUtils.getParamStr("/wssquery-cat-1.html");
//		logger.info(str);
//		logger.info( "9_1,8_2,2_1".replaceAll("(,|2_(\\d+)", "")  );
//		String url  ="cat{102}prop{2_1,1_2,0_3}name{2010}page{9}";
//		String newUrl = "cat-102-page-9-prop-2_1,1_2,0_3";
////		logger.info( getPropExSelf(2,url) );
////		logger.info(getExParamUrl(newUrl,"cat"));
////		logger.info(getExParamUrl(newUrl,"prop"));
////		logger.info(getExParamUrl(newUrl,"name"));
////		logger.info(getExParamUrl(newUrl,"page"));
////		url="search-cat{4}brand{13}prop{}.html";
////		logger.info( getExParamUrl(url, "prop") );
////		String pattern = "(.*)\\-(.[^\\-]*)";
////		String value = "";
////		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
////		Matcher m = p.matcher(newUrl);
////
////		if (m.find()) {
////			value = m.replaceAll("$1$2");
////			logger.info(value);
////		}
//		String temp = "/wssquery-cat-1";
//		logger.info(addUrl(temp,"circlar","3"));
		
		String type ="brand";
		String value ="7";
		String url ="/wssquery-cat-42";
		url= UrlUtils.appendParamValue(url, type, value);
		logger.info(url);
		
		
		type ="brand";
		value ="7";
		url ="/wssquery-cat-42.html";
		url= UrlUtils.appendParamValue(url, type, value);
		logger.info(url);
		
	
		type ="brand";
		value ="7";
		url ="cat-42";
		url= UrlUtils.appendParamValue(url, type, value);
		logger.info(url);
	}
}
