package com.ztesoft.component.common.staticdata.exception;

import com.ztesoft.common.exception.CrmException;
/**
 * 
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class StaticDataException extends CrmException {

	/**
	 * 
	 * StaticDataException构建器
	 */
	public StaticDataException(String errorCode) {
		super(new StaticDataError(errorCode));
	}

	/**
	 * 
	 * StaticDataException构建器
	 */
	public StaticDataException(String errorCode, Throwable cause) {
		super(new StaticDataError(errorCode), cause);
	}
	
	/**
	 * 
	 * StaticDataException构建器
	 */
	public StaticDataException(StaticDataError error, Throwable cause) {
		super(error, cause);
	}

	/**
	 * 
	 * CommonException.java构建器
	 */
	public StaticDataException(StaticDataError error) {
		super(error);
	}

	/**
	 * 
	 * StaticDataException构建器
	 */
	public StaticDataException(StaticDataError error, Exception e) {
		super(error, e);
	}
	
	/**
	 * 
	 * StaticDataException构建器
	 */
	public StaticDataException(StaticDataError error, String msg) {
		super(error, msg);
	}

}
