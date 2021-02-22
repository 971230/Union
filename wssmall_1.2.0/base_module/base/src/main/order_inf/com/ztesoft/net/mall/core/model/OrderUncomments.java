package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 订单异常备注表
* @作者 MoChunrun 
* @创建日期 2013-10-9 
* @版本 V 1.0
 */
public class OrderUncomments implements Serializable {

	private String order_id;
	private String comments;
	private String flag;
	private String oper_id;
	private String opet_name;
	private String oper_time;
	private String sequ;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getOpet_name() {
		return opet_name;
	}
	public void setOpet_name(String opet_name) {
		this.opet_name = opet_name;
	}
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	
}
