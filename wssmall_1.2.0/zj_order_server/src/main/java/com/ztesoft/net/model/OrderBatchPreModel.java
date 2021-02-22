package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 批量预处理查询结果对象
 * @author duan.shaochu
 * 
 */
public class OrderBatchPreModel  implements Serializable{

	private String out_tid;	//外部订单号
	private String order_id;	//内部订单号
	private String lock_user_id;	//锁单人ID
	private String lock_status;	//锁单状态
	private String lock_user_name;	//锁单人名称
	private String contact_addr;	//收货地址
	private String ship_tel;	//收货电话
	private String ship_name;	//收件人
	
	private String goods_name;	//商品名称
	private String handle_flag; //处理成功失败标志 0 成功  非0 不成功
	private String cert_card_num; //证件号码
	private String cert_address;//证件地址

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getLock_user_id() {
		return lock_user_id;
	}

	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public String getLock_status() {
		return lock_status;
	}

	public void setLock_status(String lock_status) {
		this.lock_status = lock_status;
	}

	public String getLock_user_name() {
		return lock_user_name;
	}

	public void setLock_user_name(String lock_user_name) {
		this.lock_user_name = lock_user_name;
	}

	public String getContact_addr() {
		return contact_addr;
	}

	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getHandle_flag() {
		return handle_flag;
	}

	public void setHandle_flag(String handle_flag) {
		this.handle_flag = handle_flag;
	}

	public String getCert_card_num() {
		return cert_card_num;
	}

	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}

	public String getCert_address() {
		return cert_address;
	}

	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
}
