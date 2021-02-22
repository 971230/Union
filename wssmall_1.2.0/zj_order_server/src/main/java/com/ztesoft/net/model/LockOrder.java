package com.ztesoft.net.model;

import java.util.List;
import java.util.Map;

public class LockOrder {
	private String order_id;
	private String out_tid;
	private String lock_user_id;
	private String lock_user_name;
	private String lock_user_realname;
	private String lock_status;
	private String order_from;
	private String goods_name;
	private String tid_time;
	private String order_from_n;
	private String allot_status;
	private String order_city_code;
	private String order_county_code;
	private List<Map<String, Object>> intenthandle;
	private String ship_name;
	private String ship_tel;
    private String is_work_custom;//是否自定义流程
    private String county_code; //县编码
	
	
	public String getIs_work_custom() {
		return is_work_custom;
	}

	public void setIs_work_custom(String is_work_custom) {
		this.is_work_custom = is_work_custom;
	}
	

	public String getCounty_code() {
		return county_code;
	}

	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
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

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getLock_user_name() {
		return lock_user_name;
	}

	public void setLock_user_name(String lock_user_name) {
		this.lock_user_name = lock_user_name;
	}

	public String getTid_time() {
		return tid_time;
	}

	public void setTid_time(String tid_time) {
		this.tid_time = tid_time;
	}

	public String getLock_user_realname() {
		return lock_user_realname;
	}

	public void setLock_user_realname(String lock_user_realname) {
		this.lock_user_realname = lock_user_realname;
	}

	public String getOrder_from_n() {
		return order_from_n;
	}

	public void setOrder_from_n(String order_from_n) {
		this.order_from_n = order_from_n;
	}

	public String getAllot_status() {
		return allot_status;
	}

	public void setAllot_status(String allot_status) {
		this.allot_status = allot_status;
	}

	public List<Map<String, Object>> getIntenthandle() {
		return intenthandle;
	}

	public void setIntenthandle(List<Map<String, Object>> intenthandle) {
		this.intenthandle = intenthandle;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getOrder_county_code() {
		return order_county_code;
	}

	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
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

}
