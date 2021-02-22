package com.ztesoft.common.application;

import org.omg.CORBA.portable.UnknownException;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * @author AyaKoizumi 
 * @date 091104
 * */
public class Error {
	public static String getStackTraceMsg(Throwable ex){
		Throwable _ex=ex;
		StringBuffer strbMsg=new StringBuffer();
		while(_ex!=null ){
			strbMsg.append("\n");
			StackTraceElement[]lstMsg=_ex.getStackTrace(); 
			if(lstMsg!=null && lstMsg.length>0){
				strbMsg.append("errMessage[");
				strbMsg.append(ex.toString()+ex.getMessage());
				strbMsg.append("]");
				for(int i=0;i<lstMsg.length;i++){
					StackTraceElement msgVo=lstMsg[i];
					strbMsg.append("linenumber[");
					strbMsg.append(msgVo.getLineNumber());
					strbMsg.append("]classname[");
					strbMsg.append(msgVo.getClassName());
					strbMsg.append("]methodname[");
					strbMsg.append(msgVo.getMethodName());
					strbMsg.append("]");
					strbMsg.append("\n");
				}
				_ex=_ex.getCause();
				if(_ex==ex)break;
			}
		}
		return strbMsg.toString();
	}
	
	public static String getStackTraceAsString(Throwable ex) {
		if(ex==null)return "";
		if (ex instanceof UnknownException) {
			ex = ((UnknownException) ex).originalEx;
		}
		StringWriter stringWriter = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(stringWriter);
	    ex.printStackTrace(printWriter);
	    StringBuffer error = stringWriter.getBuffer();
//	    ex.printStackTrace();
	    return error.toString();
	}
}
