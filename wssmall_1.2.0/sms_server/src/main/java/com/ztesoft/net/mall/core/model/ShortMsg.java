package com.ztesoft.net.mall.core.model;
/**
 * 短信队列表 INF_SHORT_MSG_QUEUE
 * @author chen.zewen
 *
 */
public class ShortMsg implements java.io.Serializable  {

	private Integer msg_id;
	private String lan_code;
	private String acc_nbr;
	private Integer product_id;
	private String msg;
	private String failure_desc;
	private String state;
	private String create_date;
	
	
	public Integer getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Integer msg_id) {
		this.msg_id = msg_id;
	}
	public String getLan_code() {
		return lan_code;
	}
	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFailure_desc() {
		return failure_desc;
	}
	public void setFailure_desc(String failure_desc) {
		this.failure_desc = failure_desc;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	
	
	
	
	
}
