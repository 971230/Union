package com.ztesoft.common.util.object;

import java.io.Serializable;

/**
 * @author xiaof
 * @date 2011-12-21
 */
public class ServiceResult<T> implements Serializable {
	/**
	 * 服务调用返回码: 0 成功; 其它 错误
	 */
	private String resultCode;
	/**
	 * 服务调用返回信息: 一般为调用失败时返回错误信息
	 */
	private String resultMessage;
	/**
	 * 服务调用返回其他数据: 对后台生成ID的实体INSERT操作时返回实体ID. 查询时返回实体对象.
	 * 查询实体集时返回实体集的XML片断或ArrayList. 查询树时返回XML字符串. 发生异常时返回异常堆栈信息.
	 */
	private T resultObject;

	public ServiceResult() {
	}

	public ServiceResult(String resultCode, String resultMessage, T resultObject) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.resultObject = resultObject;
	}

	/**
	 * 获取服务调用返回码. 0 成功; 其它 错误.
	 * 
	 * @return 服务调用返回码
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * 设置服务调用返回码
	 * 
	 * @param resultCode
	 *            服务调用返回码
	 * @see #getResultCode()
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 获取服务调用返回信息. 一般为调用失败时返回错误信息.
	 * 
	 * @return 服务调用返回信息
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	/**
	 * 设置服务调用返回信息
	 * 
	 * @param resultMessage
	 *            服务调用返回信息
	 * @see #getResultMessage()
	 */
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * 获取服务调用返回数据. 对后台生成ID的实体INSERT操作时返回实体ID. 查询时返回实体对象.
	 * 查询实体集时返回实体集的XML片断或ArrayList. 查询树时返回XML字符串. 发生异常时返回异常堆栈信息.
	 * 
	 * @return 服务调用返回数据
	 */
	public T getResultObject() {
		return resultObject;
	}

	/**
	 * 设置服务调用返回数据.
	 * 
	 * @param resultObject
	 *            服务调用返回数据
	 * @see #getResultObject()
	 */
	public void setResultObject(T resultObject) {
		this.resultObject = resultObject;
	}
}
