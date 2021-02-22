package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.CustInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.WorkDeveloperInfo;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * 工单状态同步接口
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class WorkOrderUpdateReq extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "工单号", type = "String", isNecessary = "Y", desc = "工单号")
	private String work_order_id;
	@ZteSoftCommentAnnotationParam(name = "工单处理结果", type = "String", isNecessary = "Y", desc = "工单处理结果")
	private String field_survey_result;// 0 失败；1 成功
	@ZteSoftCommentAnnotationParam(name = "备注", type = "String", isNecessary = "Y", desc = "备注")
	private String remark;
	@ZteSoftCommentAnnotationParam(name = "操作员节点信息", type = "String", isNecessary = "Y", desc = "操作员节点信息")
	private WorkDeveloperInfo developer_info;
	@ZteSoftCommentAnnotationParam(name = "客户信息节点信息", type = "String", isNecessary = "Y", desc = "客户信息节点信息")
	private CustInfo cust_info;// 针对实名单节点

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

	public String getField_survey_result() {
		return field_survey_result;
	}

	public void setField_survey_result(String field_survey_result) {
		this.field_survey_result = field_survey_result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WorkDeveloperInfo getDeveloper_info() {
		return developer_info;
	}

	public void setDeveloper_info(WorkDeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}

	public CustInfo getCust_info() {
		return cust_info;
	}

	public void setCust_info(CustInfo cust_info) {
		this.cust_info = cust_info;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.updateWorkOrderUpdateByRequ";
	}

}
