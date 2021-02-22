package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WorkerPool implements Serializable {

	/**
	 * 工号池配置表模型（es_worker_pool）
	 */
	private static final long serialVersionUID = -4276019385270753566L;
	@ZteSoftCommentAnnotationParam(name = "工号池ID   主键", type = "String", isNecessary = "Y", desc = "工号池ID   主键")
	private String pool_id;
	@ZteSoftCommentAnnotationParam(name = "工号池名称", type = "String", isNecessary = "Y", desc = "工号池名称")
	private String pool_name;
	@ZteSoftCommentAnnotationParam(name = "创建人", type = "String", isNecessary = "Y", desc = "创建人")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "创建时间", type = "String", isNecessary = "Y", desc = "创建时间")
	private String create_time;
	@ZteSoftCommentAnnotationParam(name = "分配后订单预占时间 以分钟为单位", type = "String", isNecessary = "Y", desc = "分配后订单预占时间 以分钟为单位")
	private String lock_time;
	@ZteSoftCommentAnnotationParam(name = "工号池状态 0 有效 1 失效", type = "String", isNecessary = "Y", desc = "工号池状态 0 有效 1 失效")
	private String status;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name = "关联组织编号", type = "String", isNecessary = "Y", desc = "关联组织编号")
	private String org_id;
	// 关联组织名称
	private String org_name;
	// 操作人姓名
	private String operator_name;

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}

	public String getPool_name() {
		return pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLock_time() {
		return lock_time;
	}

	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	

}
