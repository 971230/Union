package com.ztesoft.rop.client;

import com.ztesoft.rop.common.RopRequest;

public interface ClientRequest {

	

	/**
	 * 添加请求参数,默认需要签名，如果类已经标注了{@link com.rop.annotation.IgnoreSign}则始终不加入签名
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	ClientRequest addParam(String paramName, Object paramValue);

	/**
	 * 添加请求参数,如果needSign=false表示不参于签名
	 * 
	 * @param paramName
	 * @param paramValue
	 * @param needSign
	 * @return
	 */
	ClientRequest addParam(String paramName, Object paramValue, boolean needSign);

	/**
	 * 清除参数列表
	 * 
	 * @return
	 */
	ClientRequest clearParam();

	/**
	 * 使用POST发起请求
	 * 
	 * @param ropResponseClass
	 * @param methodName
	 * @param version
	 * @param <T>
	 * @return
	 */
	<T> CompositeResponse post(Class<T> ropResponseClass, String methodName,
			String version);

	/**
	 * 直接使用 ropRequest发送请求
	 * 
	 * @param ropRequest
	 * @param ropResponseClass
	 * @param methodName
	 * @param version
	 * @param <T>
	 * @return
	 */
	<T> CompositeResponse post(RopRequest ropRequest,
			Class<T> ropResponseClass, String methodName, String version);

	/**
	 * 使用GET发送服务请求
	 * 
	 * @param ropResponseClass
	 * @param methodName
	 * @param version
	 * @param <T>
	 * @return
	 */
	<T> CompositeResponse get(Class<T> ropResponseClass, String methodName,
			String version);

	/**
	 * 使用GET发送ropRequest的请求
	 * 
	 * @param ropRequest
	 * @param ropResponseClass
	 * @param methodName
	 * @param version
	 * @param <T>
	 * @return
	 */
	<T> CompositeResponse get(RopRequest ropRequest, Class<T> ropResponseClass,
			String methodName, String version);

}
