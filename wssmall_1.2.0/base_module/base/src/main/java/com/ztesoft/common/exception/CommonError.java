package com.ztesoft.common.exception;

import java.util.ResourceBundle;

/**
 * 
 * <p>Description: 系统公共的错误信息类</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class CommonError extends CrmError {

	public static final String UNDEFINED_ERROR = "common-error-0000"; //没有定义的错误
	public static final String COMMON_ERROR = "common-error-0001"; //系统运行时错误
	public static final String DATABASE_ERROR = "common-error-0002"; //数据库处理错误
	public static final String DATA_NOFOUND_ERROR = "common-error-0003"; //数据不存在
	public static final String PAGE_TOO_LARGE_ERROR = "common-error-0004"; //分页数据太大
	public static final String INVALID_REQ_ERROR = "common-error-0005"; //非法访问服务，请您先登陆。
	public static final String LOGIN_TIMEOUT_ERROR = "common-error-0006"; //请您先登陆，再使用系统。
	
	/** 信息资源 */
	private transient static final ResourceBundle messageRes = ResourceBundle
			.getBundle("com.ztesoft.common.exception.CommonResources");

	public CommonError(String errorCode) {
		super(errorCode);
	}

	@Override
	public void initialize(String errorCode) {
		setErrorMessage(messageRes.getString(errorCode));
		//setErrorResolve(messageRes.getString("resolve-" + errorCode));
	}
}
