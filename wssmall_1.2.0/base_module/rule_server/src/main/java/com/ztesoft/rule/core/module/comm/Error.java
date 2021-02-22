package com.ztesoft.rule.core.module.comm;

/**
 * 
 * 错误类
 * @author easonwu 
 * @creation Dec 17, 2013
 *
 */
public class Error {
	//定义一堆静态errorCode & errorMsg...
	
	public static final String IFactFilter_code = "3000" ;
	
	//错误编码
	private String errorCode ;
	
	//错误信息
	private String errorMsg ;
	
	//错误堆栈信息
	private String stackTrace ;
	
	//规则编码
	private String ruleCode ;
	
	//扩展信息
	private String extMsg ;
	
	//默认构造
	public Error(){
		
	}

	/**
	 * 构造函数
	 * @param errorCode 错误编码
	 * @param errorMessage 错误提示信息
	 */
	public Error(String errorCode , String errorMsg){
		this.errorCode = errorCode ;
		this.errorMsg = errorMsg ;
	}
	
	/**
	 * 构造函数
	 * @param ruleCode 规则编码
	 * @param errorCode 错误编码
	 * @param errorMsg 错误提示信息
	 */
	public Error(String ruleCode , String errorCode , String errorMsg){
		this(errorCode , errorMsg) ;
		this.ruleCode = ruleCode ;
	}
	
	/**
	 * 构造函数
	 * @param ruleCode 规则编码
	 * @param errorCode 错误编码
	 * @param errorMsg 错误提示信息
	 * @param stackTrace 错误堆栈信息
	 */
	public Error(String ruleCode , String errorCode , String errorMsg , String stackTrace){
		this(ruleCode,errorCode , errorMsg ) ;
		this.stackTrace = stackTrace ;
	}
	
	
//	/**
//	 * 构造函数
//	 * @param errorCode 错误编码
//	 * @param errorMessage 错误提示信息
//	 */
//	public Error(String ruleCode ,String errorCode , String errorMsg){
//		this(errorCode , errorMsg) ;
//		this.ruleCode = ruleCode ;
//	}
	
	/**
	 * 
	 * 获取当前错误类标识：默认为ruleCode,子类可根据需要做扩展
	 * 
	 */
	public String getErrorId(){
		return ruleCode ;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getExtMsg() {
		return extMsg;
	}

	public void setExtMsg(String extMsg) {
		this.extMsg = extMsg;
	}
	
	
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
}
