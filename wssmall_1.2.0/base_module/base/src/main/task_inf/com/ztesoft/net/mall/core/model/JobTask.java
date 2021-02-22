package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 定时任务执行控制
 * @作者 MoChunrun
 * @创建日期 2014-4-21 
 * @版本 V 1.0
 */
public class JobTask implements Serializable {

	@ZteSoftCommentAnnotationParam(name="定时任务ID",type="Array",isNecessary="Y",desc="定时任务ID")
	private String job_id;
	@ZteSoftCommentAnnotationParam(name="定时任务可执行IP",type="Array",isNecessary="Y",desc="定时任务可执行IP")
	private String run_ip;
	@ZteSoftCommentAnnotationParam(name="定时任务可执行端口",type="Array",isNecessary="Y",desc="定时任务可执行端口")
	private String run_port;
	@ZteSoftCommentAnnotationParam(name="定时任务类名",type="Array",isNecessary="Y",desc="定时任务类名")
	private String class_name;
	@ZteSoftCommentAnnotationParam(name="定时任务执行方法名",type="Array",isNecessary="Y",desc="定时任务执行方法名")
	private String run_method;
	private String status;
	@ZteSoftCommentAnnotationParam(name="定时任务有效开始时间",type="Array",isNecessary="Y",desc="定时任务有效开始时间")
	private String start_time;
	@ZteSoftCommentAnnotationParam(name="定时任务有效结束时间",type="Array",isNecessary="Y",desc="定时任务有效结束时间")
	private String end_time;
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getRun_ip() {
		return run_ip;
	}
	public void setRun_ip(String run_ip) {
		this.run_ip = run_ip;
	}
	public String getRun_port() {
		return run_port;
	}
	public void setRun_port(String run_port) {
		this.run_port = run_port;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getRun_method() {
		return run_method;
	}
	public void setRun_method(String run_method) {
		this.run_method = run_method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
