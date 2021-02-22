package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 订单处理按钮
 * @作者 MoChunrun
 * @创建日期 2014-9-30 
 * @版本 V 1.0
 */
public class OrderBtn implements Serializable {

	private String trace_id;
	private String btns;
	private String cbtns;

	/**
	 * 英文名
	 */
	private String btn_ename;
	/**
	 * 中文名
	 */
	private String btn_cname;
	/**
	 * execute 直接执行
	 * dialog 打开窗口
	 * open 打开浏览器原生窗口
	 * link 当前窗口打开连接
	 * submit 提交表单
	 * ajaxSubmit 提交表单
	 */
	private String show_type;
	/**
	 * 请求地址
	 */
	private String action_url;
	
	/**
	 * 表单ID
	 */
	private String form_id;
	
	private String check_fn;
	private String callback_fn;
	private String hide_page;
	
	public String getCbtns() {
		return cbtns;
	}
	public void setCbtns(String cbtns) {
		this.cbtns = cbtns;
	}
	@NotDbField
	public String getHide_page() {
		return hide_page;
	}
	public void setHide_page(String hide_page) {
		this.hide_page = hide_page;
	}
	public String getTrace_id() {
		return trace_id;
	}
	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}
	@NotDbField
	public String getBtns() {
		return btns;
	}
	public void setBtns(String btns) {
		this.btns = btns;
	}
	@NotDbField
	public String getBtn_ename() {
		return btn_ename;
	}
	public void setBtn_ename(String btn_ename) {
		this.btn_ename = btn_ename;
	}
	@NotDbField
	public String getBtn_cname() {
		return btn_cname;
	}
	public void setBtn_cname(String btn_cname) {
		this.btn_cname = btn_cname;
	}
	@NotDbField
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	@NotDbField
	public String getAction_url() {
		return action_url;
	}
	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}
	@NotDbField
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	@NotDbField
	public String getCheck_fn() {
		return check_fn;
	}
	public void setCheck_fn(String check_fn) {
		this.check_fn = check_fn;
	}
	@NotDbField
	public String getCallback_fn() {
		return callback_fn;
	}
	public void setCallback_fn(String callback_fn) {
		this.callback_fn = callback_fn;
	}
	
}
