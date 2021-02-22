package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;

/**
 * 资料返档
 * 
 * @author wui 2010-4-6上午11:11:27
 */
public class CustReturned implements java.io.Serializable {
	// 客户名称,证件号码，证件类型，用户号码、用户套餐、订购时间、终端串码、

	private String offer_name;
	private String terminal_code;
	private String acc_nbr;
	private String cust_name;
	private String certi_type;
	private String certi_number;
	private String create_date;
	private String terminal_name;
	private String status_date;
	private String order_id;
	private String accept_id;
	private String state;
	private String fee;
	private String crm_order_id;
	private String comments;
	private String batch_id;
	private String cust_pwd;
	private String lan_id;
	private String cust_addr;
	private String ship_name;
	private String ship_tel;
	private String ship_addr;
	private String state_name;
	private String startTime;
	private String endTime;
	private String s_comments;	//描述的缩略文字
	private String realname;	//代理商名称，即导入人名称
	
	
	
	private String certi_type_name;
	
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getCerti_number() {
		return certi_number;
	}
	public void setCerti_number(String certi_number) {
		this.certi_number = certi_number;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getAccept_id() {
		return accept_id;
	}
	public void setAccept_id(String accept_id) {
		this.accept_id = accept_id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(String crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	@NotDbField
	public String getCerti_type_name() {
		String cretiName = "";
		if(Consts.CERTI_TYPE_A.equals(certi_type)){
			cretiName = "本地身份证";
		}else if(Consts.CERTI_TYPE_B.equals(certi_type)){
			cretiName = "军人证";
		}else if(Consts.CERTI_TYPE_C.equals(certi_type)){
			cretiName = "户口簿";
		}else if(Consts.CERTI_TYPE_D.equals(certi_type)){
			cretiName = "单位公章证明";
		}else if(Consts.CERTI_TYPE_E.equals(certi_type)){
			cretiName = "工商执照";
		}else if(Consts.CERTI_TYPE_F.equals(certi_type)){
			cretiName = "教师证";
		}else if(Consts.CERTI_TYPE_G.equals(certi_type)){
			cretiName = "外地身份证";
		}else if(Consts.CERTI_TYPE_H.equals(certi_type)){
			cretiName = "香港身份证";
		}else if(Consts.CERTI_TYPE_I.equals(certi_type)){
			cretiName = "澳门身份证";
		}else if(Consts.CERTI_TYPE_P.equals(certi_type)){
			cretiName = "其他";
		}
		return cretiName;
	}
	@NotDbField
	public void setCerti_type_name(String certi_type_name) {
		this.certi_type_name = certi_type_name;
	}
	public String getCust_pwd() {
		return cust_pwd;
	}
	public void setCust_pwd(String cust_pwd) {
		this.cust_pwd = cust_pwd;
	}
	
	
	public String getCust_addr() {
		return cust_addr;
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_tel() {
		return ship_tel;
	}
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	
	
	@NotDbField
	public String getState_name() {
		String state_name = "";
		if(Consts.ACCEPT_STATE_FAIL.equals(state)){
			state_name = "失败";
		}else if(Consts.ACCEPT_STATE_SUCC.equals(state)){
			state_name = "成功";
		}
		return state_name;
	}
	
	@NotDbField
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@NotDbField
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@NotDbField
	public String getS_comments() {
		return s_comments;
	}
	
	@NotDbField
	public void setS_comments(String s_comments) {
		this.s_comments = s_comments;
	}
	
	@NotDbField
	public String getRealname() {
		return realname;
	}
	
	@NotDbField
	public void setRealname(String realname) {
		this.realname = realname;
	}
}