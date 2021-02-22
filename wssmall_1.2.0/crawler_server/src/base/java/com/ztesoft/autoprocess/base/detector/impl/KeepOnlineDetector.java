package com.ztesoft.autoprocess.base.detector.impl;


/**
 * 维持在线状态接口
 * @author tanghaoyang
 *
 */
public interface KeepOnlineDetector {
	
	/**
	 * 维持在线状态
	 * @return boolean 是否在线
	 * @throws Exception 
	 */
	public boolean doKeepOnline() throws Exception;
	
	/**
	 * 关闭连接
	 */
	public void shutdown();
}
