package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderInfoUpdateNewReq extends ZteRequest {
	/**
	 * songqi
	 * 订单状态更新接口
	 * 
	 */
	private static final long serialVersionUID = -1041420314465765329L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no：序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "serial _time：时间")
	private String serial_time;
	@ZteSoftCommentAnnotationParam(name = "订单中心订单号", type = "String", isNecessary = "Y", desc = "order_id：订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "变更状态", type = "String", isNecessary = "Y", desc = "update_status：变更状态")
	private String update_status;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.newUpdateOrderInfoByRequ";
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getSerial_time() {
		return serial_time;
	}

	public void setSerial_time(String serial_time) {
		this.serial_time = serial_time;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUpdate_status() {
		return update_status;
	}

	public void setUpdate_status(String update_status) {
		this.update_status = update_status;
	}

}
