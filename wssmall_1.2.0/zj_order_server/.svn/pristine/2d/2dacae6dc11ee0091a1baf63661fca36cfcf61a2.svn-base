package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WorkPoolRel implements Serializable {

	/**
	 * 工号池员工关系表（es_worker_pool_rel）
	 */
	private static final long serialVersionUID = -2459505760820031245L;

	@ZteSoftCommentAnnotationParam(name = "工号池ID", type = "String", isNecessary = "Y", desc = "工号池ID")
	private String pool_id;
	@ZteSoftCommentAnnotationParam(name = "关联工号ID", type = "String", isNecessary = "Y", desc = "关联工号ID")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "权重，数字越大权重越高，0为屏蔽", type = "String", isNecessary = "Y", desc = "权重，数字越大权重越高，0为屏蔽")
	private String weight;
	@ZteSoftCommentAnnotationParam(name = "关联工号名称", type = "String", isNecessary = "Y", desc = "关联工号名称")
	private String operator_name;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name = "工号分配最大订单数（工号保有量）", type = "String", isNecessary = "Y", desc = "工号分配最大订单数（工号保有量）")
	private String max_counts;
	// 工号池名称
	private String pool_name;
	// 已锁定订单数量
	private String lock_counts;

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

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

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPool_name() {
		return pool_name;
	}

	public void setPool_name(String pool_name) {
		this.pool_name = pool_name;
	}

	public String getMax_counts() {
		return max_counts;
	}

	public void setMax_counts(String max_counts) {
		this.max_counts = max_counts;
	}

	public String getLock_counts() {
		return lock_counts;
	}

	public void setLock_counts(String lock_counts) {
		this.lock_counts = lock_counts;
	}

}
