package zte.net.ecsord.params.wyg.req;

import params.ZteRequest;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author Rapon 
 * 沃云购受理单信息查询
 * 
 */
public class WYGAcceptanceQueryReq extends ZteRequest<ChargebackApplyWYGResponse> {

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	
	
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wyg_acceptance_query")
	private String reqType;	
	
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no：唯一的接口流水号")
	private String serial_no;
	
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间yyyymmddhhmiss")
	private String time;
	
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="out_order_id：外部订单号")
	private String out_order_id;

	@ZteSoftCommentAnnotationParam(name = "api执行方法设置", type = "String", isNecessary = "Y", desc = "api执行方法设置")
	private String api_method;

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

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return (null == this.api_method || this.api_method.equals("")) ? "com.zte.unicomService.wyg.acceptanceQuery" : this.api_method;
	}

	public void setApi_method(String api_method) {
		this.api_method = api_method;
	}

}