package com.ztesoft.net.auto.rule.parser.exp;

import com.ztesoft.net.framework.util.StringUtil;

/**
 * 自定议函数
 * @作者 MoChunrun
 * @创建日期 2014-12-6 
 * @版本 V 1.0
 */
public class MyExpFunction {

	/**
	 * in
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param src
	 * @param target
	 * @return
	 */
	public boolean in(String src,String target){
		boolean srcFlag = StringUtil.isEmpty(src);
		boolean targetFlag = StringUtil.isEmpty(target);
		if(srcFlag && targetFlag)return true;
		if(targetFlag)return false;
		if(srcFlag)return false;
		String [] ss = target.split(",");
		for(String s:ss){
			if(src.endsWith(s))return true;
		}
		return false;
	}
	
	/**
	 * 比较两个字符串
	 * @作者 MoChunrun
	 * @创建日期 2014-12-6 
	 * @param src
	 * @param target
	 * @return
	 */
	public boolean matches(String src,String target){
		boolean srcFlag = StringUtil.isEmpty(src);
		boolean targetFlag = StringUtil.isEmpty(target);
		if(srcFlag && targetFlag)return true;
		if(srcFlag)return false;
		if(targetFlag)return false;
		return src.equals(target);
	}
	
}
