package com.ztesoft.net.mall.core.model;


/**
 * 告警任务模板实体
 * @author hu.yi
 * @date 2013.12.06
 */
public class AlarmTmplDate {

	private String templet_id;
	private String templet_name;
	private String templet_type;
	private String applic_scene_id;
	private String applic_scene_name;
	private String create_time;
	private String able_time;
	private String enable_time;
	private String temp_date;
	private String status;
	private String operation_time;
	private String operator_name;
	
	
	public String getTemplet_id() {
		return templet_id;
	}
	public void setTemplet_id(String templet_id) {
		this.templet_id = templet_id;
	}
	public String getTemplet_name() {
		return templet_name;
	}
	public void setTemplet_name(String templet_name) {
		this.templet_name = templet_name;
	}
	public String getTemplet_type() {
		return templet_type;
	}
	public void setTemplet_type(String templet_type) {
		this.templet_type = templet_type;
	}
	public String getApplic_scene_id() {
		return applic_scene_id;
	}
	public void setApplic_scene_id(String applic_scene_id) {
		this.applic_scene_id = applic_scene_id;
	}
	public String getApplic_scene_name() {
		return applic_scene_name;
	}
	public void setApplic_scene_name(String applic_scene_name) {
		this.applic_scene_name = applic_scene_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperation_time() {
		return operation_time;
	}
	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public String getAble_time() {
		return able_time;
	}
	public void setAble_time(String able_time) {
		this.able_time = able_time;
	}
	public String getEnable_time() {
		return enable_time;
	}
	public void setEnable_time(String enable_time) {
		this.enable_time = enable_time;
	}
	public String getTemp_date() {
		return temp_date;
	}
	public void setTemp_date(String temp_date) {
		this.temp_date = temp_date;
	}
}
