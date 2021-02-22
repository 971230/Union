package com.ztesoft.net.model;

import java.io.Serializable;

public class InfLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//日志id
	private String log_id;
	//接口编码
	private String op_code;
	//请求时间
	private String req_time;
	//响应时间
	private String rsp_time;
	//请求地址
	private String ep_address;
	//响应状态
	private String state;
	//响应结果
	private String state_msg;
	//接口入参
	private String param_desc;
	//接口出参
	private String result_desc;
	private String col1;
	private String col2;
	//内部订单号
	private String col3;
	private String col4;
	private String col5;
	//系统来源
	private String source_from;
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getOp_code() {
		return op_code;
	}
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	public String getRsp_time() {
		return rsp_time;
	}
	public void setRsp_time(String rsp_time) {
		this.rsp_time = rsp_time;
	}
	public String getEp_address() {
		return ep_address;
	}
	public void setEp_address(String ep_address) {
		this.ep_address = ep_address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState_msg() {
		return state_msg;
	}
	public void setState_msg(String state_msg) {
		this.state_msg = state_msg;
	}
	public String getParam_desc() {
		return param_desc;
	}
	public void setParam_desc(String param_desc) {
		this.param_desc = param_desc;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
