package com.ztesoft.common.exception;

public class SystemException extends CrmException{
	
	public SystemException(String msg){
		super(new CommonError(CommonError.UNDEFINED_ERROR), msg);
	}
	
}
