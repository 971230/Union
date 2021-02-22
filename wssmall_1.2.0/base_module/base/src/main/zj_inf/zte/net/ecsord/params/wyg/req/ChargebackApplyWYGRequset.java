package zte.net.ecsord.params.wyg.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;
import zte.net.ecsord.params.wyg.vo.GoodInfo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 沃云购将订单状态同步到订单管理系统
 * 
 */
public class ChargebackApplyWYGRequset extends ZteRequest<ChargebackApplyWYGResponse> {

	@ZteSoftCommentAnnotationParam(name="唯一的接口流水号",type="String",isNecessary="Y",desc="serial_no：流水号)；")
	private String serial_no;
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="out_order_id：外部订单号")
	private String out_order_id;
	
	@ZteSoftCommentAnnotationParam(name="商城来源",type="String",isNecessary="Y",desc="order_source：商城来源")
	private String order_source;	
	
//	@ZteSoftCommentAnnotationParam(name="订单外部状态",type="String",isNecessary="Y",desc="OrderState：订单外部状态 05-退单申请")
//	private String OrderState;		
	
	@ZteSoftCommentAnnotationParam(name="环节编码",type="String",isNecessary="Y",desc="TraceCode：环节编码")
	private String trace_code;	
	
	

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wyg_syn_order_status")
	private String reqType;	
	
	@ZteSoftCommentAnnotationParam(name="md5",type="String",isNecessary="Y",desc="md5")
	private String md5;	
	
	/**
	 * ZX add 2016年3月30日 14:07:22 start
	 */
	@ZteSoftCommentAnnotationParam(name="处理结果描述",type="String",isNecessary="Y",desc="deal_desc：处理结果描述")
	private String deal_desc;
	
	@ZteSoftCommentAnnotationParam(name="货品信息",type="String",isNecessary="Y",desc="good_info：货品信息")
	private List<GoodInfo> good_info;
	
	@ZteSoftCommentAnnotationParam(name="退款状态",type="String",isNecessary="Y",desc="refund_state：退款状态：00成功，01-失败")
	private String refund_state;
	/**
	 * ZX add 2016年3月30日 14:07:34 end
	 */
	
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

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOrder_source() {
		return order_source;
	}

	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}

	public String getTrace_code() {
		return trace_code;
	}

	public void setTrace_code(String trace_code) {
		this.trace_code = trace_code;
	}

	public String getDeal_desc() {
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	public List<GoodInfo> getGood_info() {
		return good_info;
	}

	public void setGood_info(List<GoodInfo> good_info) {
		this.good_info = good_info;
	}

	public String getRefund_state() {
		return refund_state;
	}

	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.wyg.chargebackApply";
	}

}