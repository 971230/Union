package com.zte.cbss.autoprocess.sometestcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.MyTools;

public class MakeTradeBase {
	private static Logger logger = Logger.getLogger(MakeTradeBase.class);
	public static void main(String[] args) {
		String respXml=MyTools.openFile("d:\\写卡页面.html");
		String matStr="<input type=\"hidden\" id=\"_tradeBase\" name=\"_tradeBase\" value=\"([^&].*)\"";
//		Pattern pat = Pattern.compile("onclick=\"openmenu\\(getAttribute\\('menuaddr'\\).replace\\(new RegExp\\('&amp;amp;','gm'\\),"
//				+ "'&amp;'\\)\\);\" id=\"homemenu\" style=\"display:none\" "
//				+ "menuaddr=\"custserv\\?service=page/pub\\.chkcust\\.CustAuthMain&amp;&amp;"
//				+ "LOGIN_RANDOM_CODE=([^&]*)&amp;"			//1
//				+ "LOGIN_REMOTE_ADDR=null&amp;"
//				+ "LOGIN_CHECK_CODE=([^&]*)&amp;"			//2
//				+ "LOGIN_PROVINCE_CODE=");
		Pattern pat = Pattern.compile(matStr);
		
		Matcher mat = pat.matcher(respXml);
		if(mat.find()){
			logger.info(mat.group(1));
		}
		
		matStr="<input type=\"text\" disabled=\"disabled\" id=\"_all_infos\" name=\"_all_infos\" value=\"([^&].*)\" style=\"display:none\"/>";
		pat=Pattern.compile(matStr);
		mat=pat.matcher(respXml);
		if(mat.find()){
			logger.info(mat.group(1));
		}
	}

}
