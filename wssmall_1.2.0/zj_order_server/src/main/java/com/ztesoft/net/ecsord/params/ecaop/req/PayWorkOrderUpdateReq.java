package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.DeveloperInfo;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * 收费单同步接口
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class PayWorkOrderUpdateReq extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;
	// @ZteSoftCommentAnnotationParam(name = "更新参数", type =
	// "OrderPayWorksUpdateInfo", isNecessary = "Y", desc = "update_info：更新参数")
	// private OrderPayWorksUpdateInfo pay_info;

	@ZteSoftCommentAnnotationParam(name = "工单id", type = "String", isNecessary = "Y", desc = "工单id")
	private String work_order_id;
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String connected_system_id;
	@ZteSoftCommentAnnotationParam(name = "支付结果", type = "String", isNecessary = "Y", desc = "支付结果")
	private String pay_result;// 订单支付状态更新 0：支付成功 -1：支付失败
	@ZteSoftCommentAnnotationParam(name = "支付流水", type = "String", isNecessary = "Y", desc = "支付流水,在回调支付结果时必填")
	private String pay_sequ;
	@ZteSoftCommentAnnotationParam(name = "支付返回流水", type = "String", isNecessary = "Y", desc = "支付返回流水")
	private String pay_back_sequ;
	@ZteSoftCommentAnnotationParam(name = "支付类型", type = "String", isNecessary = "Y", desc = "支付类型")
	private String pay_type;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "支付方式")
	private String pay_method;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String remark;
	@ZteSoftCommentAnnotationParam(name = "操作员节点信息", type = "String", isNecessary = "Y", desc = "操作员节点信息")
	private DeveloperInfo developer_info;

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.updatePayWorkOrderUpdateByRequ";
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
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

	public String getWork_order_id() {
		return work_order_id;
	}

	public void setWork_order_id(String work_order_id) {
		this.work_order_id = work_order_id;
	}

	public String getConnected_system_id() {
		return connected_system_id;
	}

	public void setConnected_system_id(String connected_system_id) {
		this.connected_system_id = connected_system_id;
	}

	public String getPay_result() {
		return pay_result;
	}

	public void setPay_result(String pay_result) {
		this.pay_result = pay_result;
	}

	public String getPay_sequ() {
		return pay_sequ;
	}

	public void setPay_sequ(String pay_sequ) {
		this.pay_sequ = pay_sequ;
	}

	public String getPay_back_sequ() {
		return pay_back_sequ;
	}

	public void setPay_back_sequ(String pay_back_sequ) {
		this.pay_back_sequ = pay_back_sequ;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DeveloperInfo getDeveloper_info() {
		return developer_info;
	}

	public void setDeveloper_info(DeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}


}