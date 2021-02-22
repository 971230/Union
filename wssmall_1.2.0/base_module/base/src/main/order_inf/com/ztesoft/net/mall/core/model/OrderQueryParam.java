package com.ztesoft.net.mall.core.model;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

/**
 * 订单查询参数
 * 
 * @author wui
 */
public class OrderQueryParam extends ZteRequest implements java.io.Serializable {
	@ZteSoftCommentAnnotationParam(name="订单状态",type="Integer",isNecessary="N",desc="订单状态")
	private Integer state;
	@ZteSoftCommentAnnotationParam(name="查询类型",type="String",isNecessary="N",desc="查询类型")
	private String action;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="开始时间:yyyy-MM-dd")
	private String startTime;	//开始时间
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="结束时间:yyyy-MM-dd")
	private String endTime;	//结束时间
	@ZteSoftCommentAnnotationParam(name="经销商来源",type="String",isNecessary="N",desc="经销商来源如HN")
	private String sourceFrom;	//经销商来源
	@ZteSoftCommentAnnotationParam(name="订单名称",type="String",isNecessary="N",desc="订单名称")
	private String orderName;	//订单名称
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="N",desc="订单编号")
	private String orderId;		//订单编号
	@ZteSoftCommentAnnotationParam(name="分销商id",type="String",isNecessary="N",desc="分销商id")
	private String userid;		//分销商id
	@ZteSoftCommentAnnotationParam(name="处理状态",type="String",isNecessary="N",desc="处理状态")
	private String orderstatus;	//处理状态
	@ZteSoftCommentAnnotationParam(name="查询参数名称",type="String",isNecessary="N",desc="购买人名称")
	private String userName;
	@ZteSoftCommentAnnotationParam(name="购买人ID",type="String",isNecessary="N",desc="购买人ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="联系电话",type="String",isNecessary="N",desc="联系电话")
	private String phoneNo;//联系电话
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="N",desc="物流单号")
    private String deliveryNo;//物流单号
	@ZteSoftCommentAnnotationParam(name="入网号",type="String",isNecessary="N",desc="入网号")
    private String netPhoneNo;//入网号
	@ZteSoftCommentAnnotationParam(name="终端串码",type="String",isNecessary="N",desc="终端串码")
    private String terminalNo;//终端串码
	@ZteSoftCommentAnnotationParam(name="配送方式ID",type="String",isNecessary="N",desc="配送方式ID")
	private String dlyTypeId;//配送方式ID
	@ZteSoftCommentAnnotationParam(name="支付方式ID",type="String",isNecessary="N",desc="支付方式ID")
    private String payment_id;//支付方式
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String getSourceFrom() {
		return sourceFrom;
	}

	@Override
	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getNetPhoneNo() {
		return netPhoneNo;
	}

	public void setNetPhoneNo(String netPhoneNo) {
		this.netPhoneNo = netPhoneNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getDlyTypeId() {
		return dlyTypeId;
	}

	public void setDlyTypeId(String dlyTypeId) {
		this.dlyTypeId = dlyTypeId;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	
}