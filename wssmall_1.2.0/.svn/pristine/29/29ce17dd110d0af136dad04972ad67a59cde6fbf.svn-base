package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 订单流程处理待办事项
 * @作者 MoChunrun
 * @创建日期 2014-6-10 
 * @版本 V 1.0
 */
public class OrderToDoList implements Serializable {

	private String order_id;
	private String flow_def_id;
	private Integer flow_status;
	private String flow_user_id;
	private String flow_group_id;
	private String oper_id;
	private String oper_name;
	private Integer oper_type;
	private String prev_user_id;
	private String create_time;
	private String flow_pass_time;
	private String flow_type;
	private String hint;
	private String list_id;
	
	private String flow_name;
	//是否当前用户的
	private boolean currUserToDo = false;
	@NotDbField
	public boolean isCurrUserToDo() {
		return currUserToDo;
	}

	public void setCurrUserToDo(boolean currUserToDo) {
		this.currUserToDo = currUserToDo;
	}

	@NotDbField
	public String getStatus(){
		String status = "";
		if(flow_status==0){
			status = "待处理";
		}else if(flow_status==1){
			status = "通过";
		}else if(flow_status==2){
			status = "不通过";
		}else if(flow_status==3){
			status = "无需处理";
		}else if(flow_status==4){
			status = "解除锁定";
		}else if(flow_status==5){
			status = "锁定订单";
		}else if(flow_status==6){
			status = "分派";
		}
		return status;
	}
	
	@NotDbField
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getFlow_def_id() {
		return flow_def_id;
	}
	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}
	public Integer getFlow_status() {
		return flow_status;
	}
	public void setFlow_status(Integer flow_status) {
		this.flow_status = flow_status;
	}
	public String getFlow_user_id() {
		return flow_user_id;
	}
	public void setFlow_user_id(String flow_user_id) {
		this.flow_user_id = flow_user_id;
	}
	public String getFlow_group_id() {
		return flow_group_id;
	}
	public void setFlow_group_id(String flow_group_id) {
		this.flow_group_id = flow_group_id;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public Integer getOper_type() {
		return oper_type;
	}
	public void setOper_type(Integer oper_type) {
		this.oper_type = oper_type;
	}
	public String getPrev_user_id() {
		return prev_user_id;
	}
	public void setPrev_user_id(String prev_user_id) {
		this.prev_user_id = prev_user_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getFlow_pass_time() {
		return flow_pass_time;
	}
	public void setFlow_pass_time(String flow_pass_time) {
		this.flow_pass_time = flow_pass_time;
	}
	public String getFlow_type() {
		return flow_type;
	}
	public void setFlow_type(String flow_type) {
		this.flow_type = flow_type;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getList_id() {
		return list_id;
	}
	public void setList_id(String list_id) {
		this.list_id = list_id;
	}
	
}
