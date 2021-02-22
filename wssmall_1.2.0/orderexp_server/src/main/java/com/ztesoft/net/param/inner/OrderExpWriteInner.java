package com.ztesoft.net.param.inner;

import java.io.Serializable;

/**
 * 异常能力写入入参
 * @author shen.qiyu
 *
 */
public class OrderExpWriteInner  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//esearch日志id
	private String log_id;
	//订单ID
	private String obj_id;
	//订单类型(order,order_queue)
	private String obj_type;
	//搜索id
	private String search_id;
	//搜索编码
	private String search_code;
	//业务异常描述
	private String error_stack_msg;
	//异常类型
	private String exp_type;
	//错误堆栈
	private String error_msg;
	//外部订单id
	private String out_tid;
	//写入异常开关：0：开 1：关
	private String write_flag;
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getObj_type() {
		return obj_type;
	}
	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	
	public String getError_stack_msg() {
		return error_stack_msg;
	}
	public void setError_stack_msg(String error_stack_msg) {
		this.error_stack_msg = error_stack_msg;
	}
	public String getExp_type() {
		return exp_type;
	}
	public void setExp_type(String exp_type) {
		this.exp_type = exp_type;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getWrite_flag() {
		return write_flag;
	}
	public void setWrite_flag(String write_flag) {
		this.write_flag = write_flag;
	}
	
}
