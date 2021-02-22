package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AdvanceLogs;

/**
 * 预存款日志
 * 
 * @author lzf<br/>
 *         2010-3-25 下午01:35:21<br/>
 *         version 1.0<br/>
 */
public interface IAdvanceLogsManager {

	/**
	 * 列表当前会员的预存款日志
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pageAdvanceLogs(int pageNo, int pageSize);

	/**
	 * 新增日志
	 * 
	 * @param advanceLogs
	 */
	public void add(AdvanceLogs advanceLogs);

	/**
	 * 列表指定会员的预存款日志
	 * 
	 * @param member_id
	 * @return
	 */
	public List listAdvanceLogsByMemberId(String member_id);

}
