package com.ztesoft.net.model;

public class OttDetailsBaseBill extends BaseBill {

	private String from_ord_id;
	private String ord_fee;
	private String itv_acc;
	private String service_id;
	private String ord_state;
	public String getFrom_ord_id() {
		return from_ord_id;
	}
	public void setFrom_ord_id(String from_ord_id) {
		this.from_ord_id = from_ord_id;
	}
	public String getOrd_fee() {
		return ord_fee;
	}
	public void setOrd_fee(String ord_fee) {
		this.ord_fee = ord_fee;
	}
	public String getItv_acc() {
		return itv_acc;
	}
	public void setItv_acc(String itv_acc) {
		this.itv_acc = itv_acc;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getOrd_state() {
		return ord_state;
	}
	public void setOrd_state(String ate) {
		this.ord_state = ord_state;
	}

}
