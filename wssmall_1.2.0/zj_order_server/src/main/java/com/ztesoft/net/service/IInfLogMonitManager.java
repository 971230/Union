/**
 * 
 */
package com.ztesoft.net.service;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.net.framework.database.Page;

/**
 * @author ZX
 * @version 2015-10-26
 * @see 接口日志监控管理
 * 
 */
public interface IInfLogMonitManager {

	/**
	 * 接口日志查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Page toInfLogPage(String startTime, String endTime, int pageNo, int pageSize)
			throws FrameException;

	/**
	 * 接口日志分析
	 * 
	 * @param startTime
	 * @param endTime
	 */
	String toInfLogAnalyze(String startTime, String endTime);

}
