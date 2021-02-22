package com.ztesoft.net.model;

import java.io.Serializable;

public class QueueManageVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -683096824391301917L;
	private String id;//主键
	private String queue_no;//队列编码
	private String queue_desc;//队列描述
	private String max_open_num;//最大开户阀
	private String open_coeff;//开户系数
	private String avail_card_mac_num;//可用写卡机数量
	private String open_max_failed_num;//开户最大失败阀值
	private String conti_open_failed_num;//开户连续失败数量
	private String card_max_failed_num;//写卡最大失败阀值
	private String conti_card_failed_num;//写卡连续失败数量
	private String queue_switch;//队列开关
	private String deal_reason;//操作原因
	private String source_from;
	private String phones;	//号码
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQueue_no() {
		return queue_no;
	}
	public void setQueue_no(String queue_no) {
		this.queue_no = queue_no;
	}
	public String getQueue_desc() {
		return queue_desc;
	}
	public void setQueue_desc(String queue_desc) {
		this.queue_desc = queue_desc;
	}
	public String getMax_open_num() {
		return max_open_num;
	}
	public void setMax_open_num(String max_open_num) {
		this.max_open_num = max_open_num;
	}
	public String getOpen_coeff() {
		return open_coeff;
	}
	public void setOpen_coeff(String open_coeff) {
		this.open_coeff = open_coeff;
	}
	public String getAvail_card_mac_num() {
		return avail_card_mac_num;
	}
	public void setAvail_card_mac_num(String avail_card_mac_num) {
		this.avail_card_mac_num = avail_card_mac_num;
	}
	public String getOpen_max_failed_num() {
		return open_max_failed_num;
	}
	public void setOpen_max_failed_num(String open_max_failed_num) {
		this.open_max_failed_num = open_max_failed_num;
	}
	public String getConti_open_failed_num() {
		return conti_open_failed_num;
	}
	public void setConti_open_failed_num(String conti_open_failed_num) {
		this.conti_open_failed_num = conti_open_failed_num;
	}
	public String getCard_max_failed_num() {
		return card_max_failed_num;
	}
	public void setCard_max_failed_num(String card_max_failed_num) {
		this.card_max_failed_num = card_max_failed_num;
	}
	public String getConti_card_failed_num() {
		return conti_card_failed_num;
	}
	public void setConti_card_failed_num(String conti_card_failed_num) {
		this.conti_card_failed_num = conti_card_failed_num;
	}
	public String getQueue_switch() {
		return queue_switch;
	}
	public void setQueue_switch(String queue_switch) {
		this.queue_switch = queue_switch;
	}
	public String getDeal_reason() {
		return deal_reason;
	}
	public void setDeal_reason(String deal_reason) {
		this.deal_reason = deal_reason;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getPhones() {
		return phones;
	}
	public void setPhones(String phones) {
		this.phones = phones;
	}
	
	
}
