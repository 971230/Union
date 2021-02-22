package com.ztesoft.net.server.jsonserver.zjpojo;

import java.util.List;
import java.util.Map;

public class ZJLocalMallOrder {
	private Map ext_params;
	private String outOrderId; //订单号
	private String 	orderSrc;//订单来源
	private String 	dataSrc;//数据来源
	private String 	areaId;//地市
	private String 	dealTime;//下单时间
	private String 	payTime;//支付时间
	private String 	payType;//支付类型
	private String 	payStatus;//支付状态
	private String 	deliveryType;//配送方式
	private String 	buyer;//买家姓名
	private String 	buyerPhone;//买家电话
	private String 	orderAmount;//订单总金额，订单厘
	private String 	channel;//渠道分销
	private String 	tmallAuthorId;//外部担保授权号
	private String 	tmallTradeId;//外部担保交易流水号
	private String 	tmallFrostId;//外部担保冻结流水号
	private String 	instalment;//分期付款期数
	private String 	freezeDate;//银行授权日期
	private String 	receiver;//收货人
	private String 	receiveAddr;//收货人地址
	private String 	receiveTel;//收货人电话
	private String 	receivePostcode;//收货人邮编
	private String 	receiveMail;//收货人邮箱
	private String 	developName;//发展点名称
	private String 	developCode;//发展点编码
	private String 	developAdmin;//驻点管理员
	private String 	developAdminTel;//管理员电话
	private String 	developCustid;//客户归集人编码
	private String 	groupTotalNum;//订单项数量
	private List<Item> orderItem;//订单项
	
	public Map getExt_params() {
		return ext_params;
	}
	public void setExt_params(Map ext_params) {
		this.ext_params = ext_params;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getOrderSrc() {
		return orderSrc;
	}
	public void setOrderSrc(String orderSrc) {
		this.orderSrc = orderSrc;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTmallAuthorId() {
		return tmallAuthorId;
	}
	public void setTmallAuthorId(String tmallAuthorId) {
		this.tmallAuthorId = tmallAuthorId;
	}
	public String getTmallTradeId() {
		return tmallTradeId;
	}
	public void setTmallTradeId(String tmallTradeId) {
		this.tmallTradeId = tmallTradeId;
	}
	public String getTmallFrostId() {
		return tmallFrostId;
	}
	public void setTmallFrostId(String tmallFrostId) {
		this.tmallFrostId = tmallFrostId;
	}
	public String getInstalment() {
		return instalment;
	}
	public void setInstalment(String instalment) {
		this.instalment = instalment;
	}
	public String getFreezeDate() {
		return freezeDate;
	}
	public void setFreezeDate(String freezeDate) {
		this.freezeDate = freezeDate;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiveAddr() {
		return receiveAddr;
	}
	public void setReceiveAddr(String receiveAddr) {
		this.receiveAddr = receiveAddr;
	}
	public String getReceiveTel() {
		return receiveTel;
	}
	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}
	public String getReceivePostcode() {
		return receivePostcode;
	}
	public void setReceivePostcode(String receivePostcode) {
		this.receivePostcode = receivePostcode;
	}
	public String getReceiveMail() {
		return receiveMail;
	}
	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}
	public String getDevelopName() {
		return developName;
	}
	public void setDevelopName(String developName) {
		this.developName = developName;
	}
	public String getDevelopCode() {
		return developCode;
	}
	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}
	public String getDevelopAdmin() {
		return developAdmin;
	}
	public void setDevelopAdmin(String developAdmin) {
		this.developAdmin = developAdmin;
	}
	public String getDevelopAdminTel() {
		return developAdminTel;
	}
	public void setDevelopAdminTel(String developAdminTel) {
		this.developAdminTel = developAdminTel;
	}
	public String getDevelopCustid() {
		return developCustid;
	}
	public void setDevelopCustid(String developCustid) {
		this.developCustid = developCustid;
	}
	public String getGroupTotalNum() {
		return groupTotalNum;
	}
	public void setGroupTotalNum(String groupTotalNum) {
		this.groupTotalNum = groupTotalNum;
	}
	public List<Item> getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(List<Item> orderItem) {
		this.orderItem = orderItem;
	}
}
