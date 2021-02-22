package zte.net.ecsord.params.wyg.req;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;

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

	@ZteSoftCommentAnnotationParam(name="唯一的接口流水号",type="String",isNecessary="Y",desc="ActiveNo：流水号)；")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="OutOrderId：外部订单号")
	private String OutOrderId;
	
	@ZteSoftCommentAnnotationParam(name="商城来源",type="String",isNecessary="Y",desc="OrderSource：商城来源")
	private String OrderSource;	
	
	@ZteSoftCommentAnnotationParam(name="订单外部状态",type="String",isNecessary="Y",desc="OrderState：订单外部状态 05-退单申请")
	private String OrderState;		
	
	

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由订单系统提供")
	private String reqId;	

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wyg_syn_order_status")
	private String reqType;	
	
	@ZteSoftCommentAnnotationParam(name="md5",type="String",isNecessary="Y",desc="md5")
	private String md5;	


	
	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOutOrderId() {
		return OutOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		OutOrderId = outOrderId;
	}

	public String getOrderSource() {
		return OrderSource;
	}

	public void setOrderSource(String orderSource) {
		OrderSource = orderSource;
	}

	public String getOrderState() {
		return OrderState;
	}

	public void setOrderState(String orderState) {
		OrderState = orderState;
	}



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