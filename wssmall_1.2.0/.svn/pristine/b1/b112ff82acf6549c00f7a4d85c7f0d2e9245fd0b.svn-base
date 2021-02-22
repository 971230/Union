package zte.net.ecsord.params.wyg.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


/**
 * 
 * @author sguo 
 * 订单管理系统将订单状态同步到新商城
 * 
 */
public class NotifyOrderInfoWYGRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="唯一的接口流水号",type="String",isNecessary="Y",desc="ActiveNo：订单：EM+ yyyymmddhhmiss+(5位流水号)；非订单：P51+ yyyymmddhhmiss+(5位流水号)；")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="商城前端订单编码",type="String",isNecessary="Y",desc="OutnotNeedReqStrOrderId：商城前端订单编码")
	private String OutnotNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="商城来源",type="String",isNecessary="Y",desc="OrderSource：商城来源")
	private String OrderSource;	
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="OrderSource：外部订单号")
	private String OutOrderId;
	
	@ZteSoftCommentAnnotationParam(name="订单外部状态",type="String",isNecessary="Y",desc="OrderState：订单外部状态")
	private String OrderState;		
	
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="TerminalNo：终端串号")
	private String TerminalNo;
	
	@ZteSoftCommentAnnotationParam(name="卡类ICCID",type="String",isNecessary="Y",desc="SimICCID：卡类ICCID")
	private String SimICCID;	
	
	@ZteSoftCommentAnnotationParam(name="快递公司",type="String",isNecessary="N",desc="Logistics：快递公司")
	private String Logistics;		
	
	@ZteSoftCommentAnnotationParam(name="物流单编号",type="String",isNecessary="N",desc="LogisticsNo：物流单编号")
	private String LogisticsNo;	
	
	@ZteSoftCommentAnnotationParam(name="订单日志信息",type="String",isNecessary="N",desc="OrderDesc：订单日志信息")
	private String OrderDesc;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;		


	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOutnotNeedReqStrOrderId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
	}

	public void setOutnotNeedReqStrOrderId(String outnotNeedReqStrOrderId) {
		OutnotNeedReqStrOrderId = outnotNeedReqStrOrderId;
	}

	public String getOrderSource() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
	}

	public void setOrderSource(String orderSource) {
		OrderSource = orderSource;
	}

	public String getOrderState() {
		if(null == this.OrderState){
			this.OrderState = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PLATFORM_STATUS);
		}
		return this.OrderState;
	}

	public void setOrderState(String orderState) {
		OrderState = orderState;
	}

	public String getTerminalNo() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
	}

	public void setTerminalNo(String terminalNo) {
		TerminalNo = terminalNo;
	}

	public String getSimICCID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setSimICCID(String simICCID) {
		SimICCID = simICCID;
	}

	public String getLogistics() {
		String logi_company_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		String logi_company_code = CommonDataFactory.getInstance().getLogiCompanyCode(logi_company_id);
		return logi_company_code==null?EcsOrderConsts.EMPTY_STR_VALUE:logi_company_code;
	}

	public void setLogistics(String logistics) {
		Logistics = logistics;
	}

	public String getLogisticsNo() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
	}

	public void setLogisticsNo(String logisticsNo) {
		LogisticsNo = logisticsNo;
	}

	public String getOrderDesc() {
		return "";
	}

	public void setOrderDesc(String orderDesc) {
		OrderDesc = orderDesc;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	
	public String getOutOrderId() {
		OutOrderId = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOut_tid();
		return OutOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		OutOrderId = outOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.wyg.notifyorderinfo";
	}

}
