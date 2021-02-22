package com.ztesoft.form.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author copy wui
 */
public class HtmlUtil {
	private static Logger logger = Logger.getLogger(HtmlUtil.class);
	private HtmlUtil(){};
	
	/**
	 * 
	 * @param html
	 * @param nodeName
	 * @param content
	 * @return
	 */
	public static String appendTo(String html,String nodeName,String content){
		
		return html.replaceAll("</"+nodeName+">", content+"</"+nodeName+">");
	}
	
	/**
	 * 
	 * @param html
	 * @param nodeName
	 * @param content
	 * @return
	 */
	public static String insertTo(String html,String nodeName,String content){
		return html.replaceAll("<"+nodeName+">", "<"+nodeName+">"+content);
	}
	
	public static void main(String[] args){
		String html ="<html><head>adfbb</head><body>adfadsf</body></html>";
		html = insertTo(html,"head","abc");
		logger.info(html);
	}
}
