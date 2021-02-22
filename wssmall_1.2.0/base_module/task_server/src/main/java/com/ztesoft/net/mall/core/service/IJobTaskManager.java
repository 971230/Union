package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.JobRunLog;
import com.ztesoft.net.mall.core.model.JobTask;

/**
 * 定时任务运行控制管理
 * @作者 MoChunrun
 * @创建日期 2014-4-21 
 * @版本 V 1.0
 */
public interface IJobTaskManager {

	/**
	 * 查询定时任务，只查询有效的
	 * @作者 MoChunrun
	 * @创建日期 2014-4-21 
	 * @param className
	 * @param method
	 * @param ip
	 * @param port
	 * @return
	 */
	public List<JobTask> queryJobTask(String className,String method);
	
	/**
	 * 修改定时任务状态
	 * @作者 MoChunrun
	 * @创建日期 2014-4-21 
	 * @param status
	 * @param job_id
	 */
	public void updateJobTask(String status,String job_id);
	
	public void insertJobRunLog(JobRunLog log);
	
	public List<JobTask> listJobTask();
	
}
