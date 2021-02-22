package com.ztesoft.net.auto.rule.parser;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class JSUtils {
	private static Logger logger = Logger.getLogger(JSUtils.class);
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Context cx = Context.enter();   
        try  
        {   
             Scriptable scope = cx.initStandardObjects();   
             //String str = "var w=10;";
             String str="p=20;v2=30;";
             str+="function tint(){return 100;}";
             Object result = cx.evaluateString(scope, str, null, 1, null);   
             Double  res = Context.toNumber(result);  
             logger.info(res);
         } catch(Exception e){
        	 e.printStackTrace();
         }
         finally  
         {   
             Context.exit();   
         } 
        long end = System.currentTimeMillis();
        logger.info(end-start);
	}
	
}
