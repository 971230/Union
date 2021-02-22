package com.ztesoft.api;

import com.ztesoft.api.consts.ApiConsts;

/**
 * API前置检查异常。
 * 
 * @author wui
 * @since 1.0, May 23, 2012
 */
public class ApiBusiException extends Exception {

	private static final long serialVersionUID = -7787145910600194272L;

	public ApiBusiException(String errMsg) {
		super(ApiConsts.EXP_BUSS + ":" + "ApiBusiException"+":"+errMsg);
	}

}
