package com.ztesoft.ibss.common.service;

import com.ztesoft.ibss.common.util.Const;

/**
 * @author Reason
 * @version Created Sep 29, 2011
 */
public class DWRException extends Exception {

	int level ;
	String msg;
	String ex;
	
	public int getLevel() {
		return level;
	}

	public String getMsg() {
		return msg;
	}

	public String getEx() {
		return ex;
	}

	public DWRException(int level,String msg){
		this.level= level;
		this.msg = msg;
	}
	
	public DWRException(int level,String msg,String ex){
		this.level = level;
		this.msg = msg;
		this.ex = ex;
	}
	
	@Override
	public String toString(){
		String ret = "";
		if(level==Const.ALERT_LEVEL){
			ret = "提示信息：["+msg+"]";
		}else if(level==Const.ERROR_LEVEL){
			ret = "错误信息：["+msg+"],错误详情：["+ex+"]";
		}
		return ret;
	}
}
