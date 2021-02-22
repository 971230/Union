package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * liqingyi
 */

public class ActivityCo implements java.io.Serializable {

	private String id;//发布标识
	private String activity_id;//活动标识
	private String org_id;//销售组织
	private String oper_id;//发布工号
	private String created_date;//发布时间-页面点发布的时间
	private Integer status;//发布状态
	private String status_date;//状态时间
	private String source_from;//数据来源
	
	private String org_name;//销售组织名称NotDbField
	private String batch_id;//批次号
	
	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	@NotDbField
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	
}