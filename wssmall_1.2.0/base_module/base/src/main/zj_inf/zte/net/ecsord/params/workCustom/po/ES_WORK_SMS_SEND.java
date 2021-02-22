package zte.net.ecsord.params.workCustom.po;

import java.io.Serializable;

public class ES_WORK_SMS_SEND implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419358342400060711L;
	private String sms_id;
	private String order_id;
	private String service_num;
	private String bill_num;
	private String sms_data;
	private String sms_flag;
	private String sms_type;
	private String send_status;
	private String send_num;
	private String exception_desc;
	private String create_time;
	private String done_time;
	
	public String getSend_status() {
		return send_status;
	}
	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}
	public String getSms_id() {
		return sms_id;
	}
	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getBill_num() {
		return bill_num;
	}
	public void setBill_num(String bill_num) {
		this.bill_num = bill_num;
	}
	public String getSms_data() {
		return sms_data;
	}
	public void setSms_data(String sms_data) {
		this.sms_data = sms_data;
	}
	public String getSms_flag() {
		return sms_flag;
	}
	public void setSms_flag(String sms_flag) {
		this.sms_flag = sms_flag;
	}
	public String getSms_type() {
		return sms_type;
	}
	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}
	public String getSend_num() {
		return send_num;
	}
	public void setSend_num(String send_num) {
		this.send_num = send_num;
	}
	public String getException_desc() {
		return exception_desc;
	}
	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDone_time() {
		return done_time;
	}
	public void setDone_time(String done_time) {
		this.done_time = done_time;
	}
	
	
}
