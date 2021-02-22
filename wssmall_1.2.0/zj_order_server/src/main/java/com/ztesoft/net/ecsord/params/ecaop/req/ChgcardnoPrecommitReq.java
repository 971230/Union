package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class ChgcardnoPrecommitReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "唯一的接口流水号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "yyyymmddhh24miss")
	private String Time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "订单中心订单号", type = "String", isNecessary = "Y", desc = "订单同步时，订单中心返回外系统的订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "原SIM卡号", type = "String", isNecessary = "N", desc = "换卡时传原SIM卡号")
	private String old_simid;
	@ZteSoftCommentAnnotationParam(name = "SIM卡号", type = "String", isNecessary = "Y", desc = "SIM卡号")
	private String simId;
	@ZteSoftCommentAnnotationParam(name = "卡类型", type = "String", isNecessary = "Y", desc = "0:白卡 1:成卡")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name = "补换卡类型", type = "String", isNecessary = "Y", desc = "1:补卡 2:换卡")
	private String chgType;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点")
	private String officeId;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "IMSI号", type = "String", isNecessary = "N", desc = "IMSI号")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name = "写卡数据脚本", type = "String", isNecessary = "N", desc = "写卡数据脚本")
	private String cardData;
	@ZteSoftCommentAnnotationParam(name = "读卡序列", type = "String", isNecessary = "N", desc = "读卡序列")
	private String procId;
	@ZteSoftCommentAnnotationParam(name = "拍照流水号", type = "String", isNecessary = "N", desc = "拍照流水号")
	private String req_swift_num;
	

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOld_simid() {
		return old_simid;
	}

	public void setOld_simid(String old_simid) {
		this.old_simid = old_simid;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getChgType() {
		return chgType;
	}

	public void setChgType(String chgType) {
		this.chgType = chgType;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getCardData() {
		return cardData;
	}

	public void setCardData(String cardData) {
		this.cardData = cardData;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getReq_swift_num() {
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.chgcardnoPrecommitReq";
	}

}
