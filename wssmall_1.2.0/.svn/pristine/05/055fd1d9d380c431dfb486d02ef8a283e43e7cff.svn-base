package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.JobRunLog;
import com.ztesoft.net.mall.core.model.JobTask;
import com.ztesoft.net.mall.core.service.IJobTaskManager;

public class JobTaskManager extends BaseSupport implements IJobTaskManager {

	@Override
	public List<JobTask> queryJobTask(String className, String method) {
		String sql = "select t.* from ES_JOB_TASK t where t.class_name=? and t.run_method=? and t.status='00A' and "+DBTUtil.current()+" between t.start_time and t.end_time";
		List<JobTask> jobList = this.baseDaoSupport.queryForList(sql, JobTask.class, className,method);
		//按method_name查询不到，则按class搜索
		if(ListUtil.isEmpty(jobList)){
			sql = "select t.* from ES_JOB_TASK t where t.class_name=? and t.status='00A' and "+DBTUtil.current()+" between t.start_time and t.end_time";
			jobList = this.baseDaoSupport.queryForList(sql, JobTask.class, className);
		}
		return jobList;
	}

	@Override
	public void updateJobTask(String status, String job_id) {
		String sql = "update ES_JOB_TASK t set t.status=? where job_id=?";
		this.baseDaoSupport.execute(sql, status,job_id);
	}

	@Override
	public void insertJobRunLog(JobRunLog log) {
		this.baseDaoSupport.insert("ES_JOB_RUN_LOG", log);
	}

	@Override
	public List<JobTask> listJobTask() {
		String sql = "select t.* from ES_JOB_TASK t where t.status='00A' and "+DBTUtil.current()+" between t.start_time and t.end_time and source_from is not null";
		List<JobTask> jobList = this.baseDaoSupport.queryForList(sql, JobTask.class);
		return jobList;
	}

}
