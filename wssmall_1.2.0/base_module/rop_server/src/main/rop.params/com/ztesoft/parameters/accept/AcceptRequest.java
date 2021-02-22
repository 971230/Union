package com.ztesoft.parameters.accept;

import com.ztesoft.parameters.AbstractRopRequest;
import com.ztesoft.parameters.accept.bean.MainOrdBean;

import javax.validation.constraints.NotNull;

/**
 * @author Reason.Yea 
 * @version 创建时间：May 20, 2013
 * 订单请求对象
 */
public class AcceptRequest extends AbstractRopRequest {
	@NotNull
	private MainOrdBean mainOrd;
	
	private String sms_code;
	
	private String sms_random_code;
	
	private String acc_nbr;
	private String area_code;
	private String ord_id;
	private String ord_desc;
	private String plan_id;
	private String plan_num;
	private String item_type;
	private String item_fee;
	private String rel_ord_code;
	
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getOrd_id() {
		return ord_id;
	}

	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}

	public String getOrd_desc() {
		return ord_desc;
	}

	public void setOrd_desc(String ord_desc) {
		this.ord_desc = ord_desc;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getPlan_num() {
		return plan_num;
	}

	public void setPlan_num(String plan_num) {
		this.plan_num = plan_num;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getItem_fee() {
		return item_fee;
	}

	public void setItem_fee(String item_fee) {
		this.item_fee = item_fee;
	}

	public String getRel_ord_code() {
		return rel_ord_code;
	}

	public void setRel_ord_code(String rel_ord_code) {
		this.rel_ord_code = rel_ord_code;
	}

	public String getSms_code() {
		return sms_code;
	}

	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}

	public String getSms_random_code() {
		return sms_random_code;
	}

	public void setSms_random_code(String sms_random_code) {
		this.sms_random_code = sms_random_code;
	}

	public MainOrdBean getMainOrd() {
		return mainOrd;
	}

	public void setMainOrd(MainOrdBean mainOrd) {
		this.mainOrd = mainOrd;
	}
}
