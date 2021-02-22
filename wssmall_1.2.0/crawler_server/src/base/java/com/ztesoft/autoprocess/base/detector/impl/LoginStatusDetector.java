package com.ztesoft.autoprocess.base.detector.impl;

/**
 * 登录结果检查接口
 * @author tanghaoyang
 *
 */
public interface LoginStatusDetector {

	/**
	 * 根据返回报文判断目前是否登录状态
	 * @param response 登录请求返回的报文
	 * @return boolean 是否登录状态
	 */
	public boolean isOnline(String response);
}
