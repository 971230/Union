package zte.net.ecsord.params.ems.vo;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmsWaybill {
	@ZteSoftCommentAnnotationParam(name = "邮件数据唯一标识", type = "String", isNecessary = "N", desc = "邮件数据唯一标识")
	private String txLogisticID;
	@ZteSoftCommentAnnotationParam(name = "电商订单号", type = "String", isNecessary = "N", desc = "电商订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name = "物流运单号(一票多单主单号)", type = "String", isNecessary = "Y", desc = "物流运单号(一票多单主单号)")
	private String mailNum;
	@ZteSoftCommentAnnotationParam(name = "一票多单子单号", type = "String", isNecessary = "N", desc = "一票多单子单号，以“,”(半角逗号)分隔，非一票多单不填")
	private String subMails;
	@ZteSoftCommentAnnotationParam(name = "一票多件付费方式", type = "String", isNecessary = "N", desc = "aaa")
	private String ypdjpayment;
	@ZteSoftCommentAnnotationParam(name = "订单类型", type = "int", isNecessary = "Y", desc = "订单类型(1-普通订单)")
	private int orderType;
	@ZteSoftCommentAnnotationParam(name = "产品代码", type = "String", isNecessary = "Y", desc = "产品代码，0-快递包裹 1-标准快递,2-收件人付费（打印用户必传）")
	private String serviceType;
	@ZteSoftCommentAnnotationParam(name = "备注", type = "String", isNecessary = "N", desc = "备注")
	private String remark;
	@ZteSoftCommentAnnotationParam(name = "实际重量", type = "long", isNecessary = "N", desc = "实际重量，单位：克")
	private long weight;
	@ZteSoftCommentAnnotationParam(name = "体积重量", type = "long", isNecessary = "N", desc = "体积重量，单位：克")
	private long volumeWeight;
	@ZteSoftCommentAnnotationParam(name = "计费重量", type = "long", isNecessary = "N", desc = "计费重量，单位：克")
	private long feeWeight;
	@ZteSoftCommentAnnotationParam(name = "保险金额", type = "String", isNecessary = "N", desc = "保险金额，单位：分")
	private long insuredAmount;
	@ZteSoftCommentAnnotationParam(name = "所负责任", type = "String", isNecessary = "N", desc = "所负责任，2-保价，3-保险")
	private String insureType;
	@ZteSoftCommentAnnotationParam(name = "寄件人信息", type = "EmsSender", isNecessary = "Y", desc = "寄件人信息")
	private EmsSender sender;
	@ZteSoftCommentAnnotationParam(name = "收件人信息", type = "EmsReceiver", isNecessary = "Y", desc = "收件人信息")
	private EmsReceiver receiver;
	@ZteSoftCommentAnnotationParam(name = "商品信息", type = "EmsItem", isNecessary = "Y", desc = "商品信息")
	private List<EmsItem> items;
	@ZteSoftCommentAnnotationParam(name = "内件性质", type = "String", isNecessary = "N", desc = "内件性质（文件、物品）")
	private String cargoType;
	@ZteSoftCommentAnnotationParam(name = "EMS客户代码", type = "String", isNecessary = "N", desc = "EMS客户代码")
	private String custCode;
	@ZteSoftCommentAnnotationParam(name = "投递时间", type = "String", isNecessary = "N", desc = "投递时间(yyyy-mm-dd hh:mm:ss)")
	private String deliveryTime;
	@ZteSoftCommentAnnotationParam(name = "收件人付费", type = "long", isNecessary = "N", desc = "收件人付费")
	private long receiverPay;
	@ZteSoftCommentAnnotationParam(name = "代收货款", type = "long", isNecessary = "N", desc = "代收货款")
	private long collectionMoney;
	@ZteSoftCommentAnnotationParam(name = "是否返单", type = "String", isNecessary = "N", desc = "是否返单，0：返单，1：不返单")
	private String revertBill;
	@ZteSoftCommentAnnotationParam(name = "反向运单号", type = "String", isNecessary = "N", desc = "反向运单号")
	private String revertMailNo;
	@ZteSoftCommentAnnotationParam(name = "邮费", type = "long", isNecessary = "N", desc = "邮费")
	private long postage;
	@ZteSoftCommentAnnotationParam(name = "大写邮费金额", type = "String", isNecessary = "N", desc = "大写邮费金额")
	private String postageUppercase;
	@ZteSoftCommentAnnotationParam(name = "寄递类型", type = "String", isNecessary = "N", desc = "寄递类型，0：单程寄递，1：双程后置去程，2：双程后置返程(公安项目专用)")
	private String sendType;
	@ZteSoftCommentAnnotationParam(name = "商品金额(工本费)", type = "String", isNecessary = "N", desc = "商品金额(工本费)")
	private long commodityMoney;
	@ZteSoftCommentAnnotationParam(name = "自定义标识", type = "String", isNecessary = "N", desc = "自定义标识")
	private String state;
	@ZteSoftCommentAnnotationParam(name = "机构号码", type = "String", isNecessary = "N", desc = "机构号码")
	private String orgCode;
	@ZteSoftCommentAnnotationParam(name = "预留字段1", type = "String", isNecessary = "N", desc = "预留字段1")
	private String blank1;
	@ZteSoftCommentAnnotationParam(name = "预留字段2", type = "String", isNecessary = "N", desc = "预留字段2")
	private String blank2;
	@ZteSoftCommentAnnotationParam(name = "预留字段3", type = "String", isNecessary = "N", desc = "预留字段3")
	private String blank3;
	@ZteSoftCommentAnnotationParam(name = "预留字段4", type = "String", isNecessary = "N", desc = "预留字段4")
	private String blank4;
	@ZteSoftCommentAnnotationParam(name = "预留字段5", type = "String", isNecessary = "N", desc = "预留字段5")
	private String blank5;
	public String getTxLogisticID() {
		return txLogisticID;
	}
	public void setTxLogisticID(String txLogisticID) {
		this.txLogisticID = txLogisticID;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMailNum() {
		return mailNum;
	}
	public void setMailNum(String mailNum) {
		this.mailNum = mailNum;
	}
	public String getSubMails() {
		return subMails;
	}
	public void setSubMails(String subMails) {
		this.subMails = subMails;
	}
	public String getYpdjpayment() {
		return ypdjpayment;
	}
	public void setYpdjpayment(String ypdjpayment) {
		this.ypdjpayment = ypdjpayment;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	public long getVolumeWeight() {
		return volumeWeight;
	}
	public void setVolumeWeight(long volumeWeight) {
		this.volumeWeight = volumeWeight;
	}
	public long getFeeWeight() {
		return feeWeight;
	}
	public void setFeeWeight(long feeWeight) {
		this.feeWeight = feeWeight;
	}
	public long getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(long insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	public String getInsureType() {
		return insureType;
	}
	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}
	public EmsSender getSender() {
		return sender;
	}
	public void setSender(EmsSender sender) {
		this.sender = sender;
	}
	public EmsReceiver getReceiver() {
		return receiver;
	}
	public void setReceiver(EmsReceiver receiver) {
		this.receiver = receiver;
	}
	public List<EmsItem> getItems() {
		return items;
	}
	public void setItems(List<EmsItem> items) {
		this.items = items;
	}
	public String getCargoType() {
		return cargoType;
	}
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public long getReceiverPay() {
		return receiverPay;
	}
	public void setReceiverPay(long receiverPay) {
		this.receiverPay = receiverPay;
	}
	public long getCollectionMoney() {
		return collectionMoney;
	}
	public void setCollectionMoney(long collectionMoney) {
		this.collectionMoney = collectionMoney;
	}
	public String getRevertBill() {
		return revertBill;
	}
	public void setRevertBill(String revertBill) {
		this.revertBill = revertBill;
	}
	public String getRevertMailNo() {
		return revertMailNo;
	}
	public void setRevertMailNo(String revertMailNo) {
		this.revertMailNo = revertMailNo;
	}
	public long getPostage() {
		return postage;
	}
	public void setPostage(long postage) {
		this.postage = postage;
	}
	public String getPostageUppercase() {
		return postageUppercase;
	}
	public void setPostageUppercase(String postageUppercase) {
		this.postageUppercase = postageUppercase;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public long getCommodityMoney() {
		return commodityMoney;
	}
	public void setCommodityMoney(long commodityMoney) {
		this.commodityMoney = commodityMoney;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getBlank1() {
		return blank1;
	}
	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}
	public String getBlank2() {
		return blank2;
	}
	public void setBlank2(String blank2) {
		this.blank2 = blank2;
	}
	public String getBlank3() {
		return blank3;
	}
	public void setBlank3(String blank3) {
		this.blank3 = blank3;
	}
	public String getBlank4() {
		return blank4;
	}
	public void setBlank4(String blank4) {
		this.blank4 = blank4;
	}
	public String getBlank5() {
		return blank5;
	}
	public void setBlank5(String blank5) {
		this.blank5 = blank5;
	}
	
}
