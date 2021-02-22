package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;

public class OrderResultNotifyVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680892215352113936L;

	private String order_id;
	
	private String out_id;
	
	private String result_code;
	
	private String result_msg;
	
	private String notify_method;
	
	private int notify_status;
	
	private int notify_count;
	
	private String create_date;
	
	private String notify_date;
	
	private String error_msg;
	
	private String source_from;
	
	private String ext_1;
	
	private String ext_2;
	
	private String ext_3;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public String getNotify_method() {
		return notify_method;
	}

	public void setNotify_method(String notify_method) {
		this.notify_method = notify_method;
	}

	public int getNotify_status() {
		return notify_status;
	}

	public void setNotify_status(int notify_status) {
		this.notify_status = notify_status;
	}

	public int getNotify_count() {
		return notify_count;
	}

	public void setNotify_count(int notify_count) {
		this.notify_count = notify_count;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getNotify_date() {
		return notify_date;
	}

	public void setNotify_date(String notify_date) {
		this.notify_date = notify_date;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getExt_1() {
		return ext_1;
	}

	public void setExt_1(String ext_1) {
		this.ext_1 = ext_1;
	}

	public String getExt_2() {
		return ext_2;
	}

	public void setExt_2(String ext_2) {
		this.ext_2 = ext_2;
	}

	public String getExt_3() {
		return ext_3;
	}

	public void setExt_3(String ext_3) {
		this.ext_3 = ext_3;
	}

	
}
