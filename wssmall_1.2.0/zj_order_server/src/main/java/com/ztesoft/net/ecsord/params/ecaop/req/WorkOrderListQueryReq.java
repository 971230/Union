package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * 工单列表查询
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class WorkOrderListQueryReq extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;

	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String work_order_id;
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String order_id;
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String type;// 01 -- 收费单 02 -- 外勘单 03 -- 挽留单 04 -- 实名单 05 -- 写卡单
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String status;// 工单处理状态 0 — 未处理 1 — 已处理
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String operator_id;// 操作员工号
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String operator_office_id;// 操作点id
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String operator_num;// 操作员联系电话
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String operator_name;// 操作员姓名
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String order_contact_name;// 联系人姓名
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String order_contact_phone;// 联系人电话
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String order_contact_addr;// 联系人地址/宽带标准地址
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String order_product_name;// 订单商品名称
	// @ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary =
	// "Y", desc = "")
	// private String update_time;// 更新时间
	//
	// @ZteSoftCommentAnnotationParam(name = "查询参数", type = "", isNecessary =
	// "Y", desc = "")
	// private OrderWorksListQueryInfo query_info;//

	@ZteSoftCommentAnnotationParam(name = "开始时间yyyymmdd", type = "String", isNecessary = "Y", desc = "开始时间")
	private String start_time;
	@ZteSoftCommentAnnotationParam(name = "结束时间yyyymmdd", type = "String", isNecessary = "Y", desc = "结束时间")
	private String end_time;
	@ZteSoftCommentAnnotationParam(name = "处理状态", type = "String", isNecessary = "Y", desc = "处理状态0 – 全部；1 – 未处理；2 – 已处理")
	private String status;
	@ZteSoftCommentAnnotationParam(name = "操作员手机号码", type = "String", isNecessary = "Y", desc = "操作员手机号码")
	private String operator_num;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String order_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.queryWorkOrderListByRequ";
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getReceive_system() {
		return receive_system;
	}

	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator_num() {
		return operator_num;
	}

	public void setOperator_num(String operator_num) {
		this.operator_num = operator_num;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}