package zte.net.ecsord.params.bss.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class AccountOpenFormalSubmissionReq extends ZteRequest {
	/**
	 * 沃受理开户正式提交接请求参数
	 */
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="订单号，非空")
	private String serial_num;

	@ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="订单类型,非空，0提交 1取消")
	private String order_type;
	
	@ZteSoftCommentAnnotationParam(name="办理操作员",type="String",isNecessary="Y",desc="办理操作员，非空")
	private String operator_id;
	
	@ZteSoftCommentAnnotationParam(name="办理操作点",type="String",isNecessary="Y",desc="办理操作点，非空")
	private String office_id;
	

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
		
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.AccountOpenFormalSubmission";
	}

}
