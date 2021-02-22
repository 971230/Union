package com.ztesoft.lucence;

import java.util.List;

/**
 * 搜索配置
 * @作者 MoChunrun
 * @创建日期 2014-7-31 
 * @版本 V 1.0
 */
public interface ILucenceManager {

	/**
	 * 按状态查询
	 * @作者 MoChunrun
	 * @创建日期 2014-7-31 
	 * @param status
	 * @return
	 */
	public List<LucenceConfig> listConfigByStatus(String status);
	
	public void updateConfigStatus(String id, String status,int exe_min,String end_time,String result_msg);
	
	public void updateConfigStatus(String id,String status);
}
