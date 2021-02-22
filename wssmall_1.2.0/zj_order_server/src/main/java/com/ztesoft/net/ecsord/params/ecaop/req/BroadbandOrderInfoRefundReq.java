package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandRefundBussiInfoVO;
import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class BroadbandOrderInfoRefundReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "支付发起方支付流水", type = "String", isNecessary = "Y", desc = "支付发起方支付流水")
	private String outTradeNo;
	@ZteSoftCommentAnnotationParam(name = "退款发起方退款流水", type = "String", isNecessary = "Y", desc = "退款发起方退款流水")
	private String outRefundNo;
	@ZteSoftCommentAnnotationParam(name = "交易类型", type = "String", isNecessary = "Y", desc = "交易类型 01：WEB线下02：WEB线上03：WAP04: APP")
	private String tradeType="04";
	@ZteSoftCommentAnnotationParam(name = "退款金额（分）", type = "Number", isNecessary = "Y", desc = "退款金额（分）")
	private String refundPrice;
	@ZteSoftCommentAnnotationParam(name = "退款描述", type = "String", isNecessary = "Y", desc = "退款描述")
	private String outTradeDesc;
	@ZteSoftCommentAnnotationParam(name = "业务系统编码", type = "String", isNecessary = "Y", desc = "业务系统编码（业务系统编码序列8位）例如：沃受理、订单中心")
	private String serviceId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "N", desc = "渠道类型")
	private String channelType="1";
	@ZteSoftCommentAnnotationParam(name = "商户编码", type = "String", isNecessary = "N", desc = "商户编码例如：营业厅或合作厅编码（最大32位）")
	private String merchantId;
	@ZteSoftCommentAnnotationParam(name = "支付中心异步通知支付结果回调地址", type = "String", isNecessary = "Y", desc = "支付中心异步通知支付结果回调地址")
	private String notifyUrl="http://132.151.42.121:8006/api/rest";
	@ZteSoftCommentAnnotationParam(name = "业务信息", type = "Object", isNecessary = "Y", desc = "业务信息")
	private BroadbandRefundBussiInfoVO busiInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOutTradeNo() {
		String payplatformorderid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PLATFORM_ORDER_ID);
		String[] new_pay_ids = payplatformorderid.split("\\|");
		outTradeNo = new_pay_ids[0];
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getRefundPrice() {
		String paymoney = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderBusiRequest().getPaymoney()+"";
		Integer cny = (int)(Double.parseDouble(paymoney)*100);
		refundPrice = cny+"";
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getOutTradeDesc() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFUND_DESC);
	}

	public void setOutTradeDesc(String outTradeDesc) {
		this.outTradeDesc = outTradeDesc;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public BroadbandRefundBussiInfoVO getBusiInfo() {
		
		return busiInfo;
	}

	public void setBusiInfo(BroadbandRefundBussiInfoVO busiInfo) {
		this.busiInfo = busiInfo;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.broadbandOrderInfoRefundReq";
	}
	
}