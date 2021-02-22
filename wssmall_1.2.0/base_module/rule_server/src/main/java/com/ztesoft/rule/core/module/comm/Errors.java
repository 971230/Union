package com.ztesoft.rule.core.module.comm;

import java.util.ArrayList;
import java.util.List;


/**
 * 错误列表
 * @author easonwu 
 * @creation Dec 17, 2013
 * 
 */
public class Errors {
	
	//错误信息存储容器
	private List<Error> errors = new ArrayList<Error>() ;



	/**
	 * 添加错误对象
	 * @param error
	 */
	public void addError(Error error){
		errors.add(error) ;
	}
	
	/**
	 * 添加错误对象
	 * @param ruleCode
	 * @param errorCode
	 * @param errorMsg
	 * @param stackTrace
	 */
	public void addError(String ruleCode , String errorCode , String errorMsg , String stackTrace){
		addError(new Error( ruleCode ,  errorCode ,  errorMsg ,  stackTrace)) ;
	}
	

	/**
	 * 添加错误对象
	 * @param ruleCode
	 * @param errorCode
	 * @param errorMsg
	 */
	public void addError(String ruleCode , String errorCode , String errorMsg){
		addError(new Error( ruleCode ,  errorCode ,  errorMsg )) ;
	}
	

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	
	
	
	
	
}
