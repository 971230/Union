package zte.net.ecsord.params.sr.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * 订单系统访问森锐仿真系统
 */
public class SimulationResultReceiveRequset extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "reqId", type = "String", isNecessary = "Y", desc = "reqId：请求id,由订单系统分配")
	private String reqId;

	@ZteSoftCommentAnnotationParam(name = "reqType", type = "String", isNecessary = "Y", desc = "reqType：请求类型,由订单系统提供")
	private String reqType;

	@ZteSoftCommentAnnotationParam(name = "serial_no", type = "String", isNecessary = "Y", desc = "serial_no：序列号")
	private String serial_no;

	@ZteSoftCommentAnnotationParam(name = "time", type = "String", isNecessary = "Y", desc = "time：时间yyyymmddhhmiss")
	private String time;

	@ZteSoftCommentAnnotationParam(name = "source_system", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;

	@ZteSoftCommentAnnotationParam(name = "receive_system", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;

	@ZteSoftCommentAnnotationParam(name = "flowInstId", type = "String", isNecessary = "Y", desc = "flowInstId：流程实例标识,在派单时由订单系统发送给仿真系统的，标识一次业务模拟办理。内部订单号?")
	private String flowInstId;

	@ZteSoftCommentAnnotationParam(name = "isSuccess", type = "String", isNecessary = "Y", desc = "isSuccess：业务办理成功标志 T-成功  F-失败")
	private String isSuccess;

	@ZteSoftCommentAnnotationParam(name = "failureDesc", type = "String", isNecessary = "Y", desc = "failureDesc：错误详情")
	private String failureDesc;

	@ZteSoftCommentAnnotationParam(name = "failureCode", type = "String", isNecessary = "Y", desc = "failureCode：错误编码")
	private String failureCode;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
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

	public String getFlowInstId() {
		return flowInstId;
	}

	public void setFlowInstId(String flowInstId) {
		this.flowInstId = flowInstId;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailureDesc() {
		return failureDesc;
	}

	public void setFailureDesc(String failureDesc) {
		this.failureDesc = failureDesc;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.infservices.simulationresultreceive";
	}

}
