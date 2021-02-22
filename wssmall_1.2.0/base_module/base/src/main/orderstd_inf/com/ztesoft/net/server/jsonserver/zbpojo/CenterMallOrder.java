package com.ztesoft.net.server.jsonserver.zbpojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/******************************************************
 * 总部商场的订单实体类
 * @author duanshaochu
 *
 */
public class CenterMallOrder implements Serializable{

	//访问流水
	private String activeNo = "";
	//买家标识
	private String customerId = "";
	//买家昵称
	private String registerName = "";
	//买家登录IP
	private String clientIp = "";
	//收件人姓名
	private String consiName = "";
	//收件人证件类型
	private String consiCertType = "";
	//收件人证件号码
	private String consiCertNum = "";
	//收件人联系电话1
	private String consiPhone = "";
	//收件人联系电话2支持填写固话
	private String consiTelPhone = "";
	//收件人EMAIL
	private String consiEmail = "";
	//买家留言
	private String buyerMessage = "";
	//收货省
	private String consiGoodsProv = "";
	//收货市
	private String consiGoodsCity = "";
	//收货区/县（行政区域）
	private String consiGoodsDist = "";
	//邮政编码
	private String consiPostCode = "";
	//邮寄地址
	private String consiPostAddress = "";
	//邮寄备注
	private String consiPostRemark = "";
	//是否闪电送
	private String orderOperType = "";
	//配送方式
	private String deliMode = "";
	//配送时间类型
	private String deliTimeMode = "";
	//发票类型
	private String invoiceType = "";
	//照片同步
	private String ifSendPhotos = "";
	//发票抬头
	private String invoiceTitle = "";
	//发票内容
	private String invoiceDesc = "";
	//发票号码(默认空)
	private String invoiceNo = "";
	//订单编号
	private String orderId = "";
	//订单ID
	private String orderNum = "";
	//下单时间，YYYY-MM-DD HH24:MI:SS
	private String orderTime = "";
	//订单省份
	private String orderProvince = "";
	//订单地市
	private String orderCity = "";
	//订单来源
	private String orderSource = "";
	//订单接入编码(默认自然订单)
	private String orderAccCode = "";
	//订单接入类别
	private String orderAccType = "";
	//订单接入说明
	private String orderAccDesc = "";
	//外部订单编号，即父订单编码
	private String outOrderId = "";
	//订单应收总价，单位为厘
	private String orderOrigFee = "";
	//订单实收总价，单位为厘
	private String orderRealFee = "";
	//订单减免金额，单位为厘
	private String orderReliefFee = "";
	//订单减免原因
	private String orderReliefRes = "";
	//应收邮寄费用，单位为厘
	private String origPostFee = "";
	//实收邮寄费用，单位为厘
	private String realPostFee = "";
	//退单标识
	private String returnFlag = "";
	//支付信息
	private CenterMallPayInfo payInfo;
	//优惠信息
	private List<CenterMallDiscountInfo> discountInfo;
	//订单中商品的总数量
	private String goodsNum = "";
	//赠品信息
	private List<CenterMallGiftInfo> giftInfo;
	//流程信息
	private CenterMallFlowInfo flowInfo;
	//操作人信息
	private CenterMallOperatorInfo operatorInfo;
	//商品信息
	private List<CenterMallGoodsInfo> goodsInfo;
	//联盟信息
	private CenterMallLeagueInfo leagueInfo;
	/*
	 * 预售标识(0:预售)
	 * 责任人使用人信息(集客开户传) 
	 * ZX add 2015-12-23 start
	 */ 
	private String preSale;
	private CenterMallUseCustInfo useCustInfo;
	/* 
	 * 预售标识(0:预售)
	 * 责任人使用人信息(集客开户传) 
	 * ZX add 2015-12-23 start
	 */
	private Map ext_params;
	
	public Map getExt_params() {
		return ext_params;
	}
	public void setExt_params(Map ext_params) {
		this.ext_params = ext_params;
	}
	public CenterMallLeagueInfo getLeagueInfo() {
		return leagueInfo;
	}
	public void setLeagueInfo(CenterMallLeagueInfo leagueInfo) {
		this.leagueInfo = leagueInfo;
	}
	public List<CenterMallGoodsInfo> getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(List<CenterMallGoodsInfo> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getActiveNo() {
		return activeNo;
	}
	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getConsiName() {
		return consiName;
	}
	public void setConsiName(String consiName) {
		this.consiName = consiName;
	}
	public String getConsiCertType() {
		return consiCertType;
	}
	public void setConsiCertType(String consiCertType) {
		this.consiCertType = consiCertType;
	}
	public String getConsiCertNum() {
		return consiCertNum;
	}
	public void setConsiCertNum(String consiCertNum) {
		this.consiCertNum = consiCertNum;
	}
	public String getConsiPhone() {
		return consiPhone;
	}
	public void setConsiPhone(String consiPhone) {
		this.consiPhone = consiPhone;
	}
	public String getConsiTelPhone() {
		return consiTelPhone;
	}
	public void setConsiTelPhone(String consiTelPhone) {
		this.consiTelPhone = consiTelPhone;
	}
	public String getConsiEmail() {
		return consiEmail;
	}
	public void setConsiEmail(String consiEmail) {
		this.consiEmail = consiEmail;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getConsiGoodsProv() {
		return consiGoodsProv;
	}
	public void setConsiGoodsProv(String consiGoodsProv) {
		this.consiGoodsProv = consiGoodsProv;
	}
	public String getConsiGoodsCity() {
		return consiGoodsCity;
	}
	public void setConsiGoodsCity(String consiGoodsCity) {
		this.consiGoodsCity = consiGoodsCity;
	}
	public String getConsiGoodsDist() {
		return consiGoodsDist;
	}
	public void setConsiGoodsDist(String consiGoodsDist) {
		this.consiGoodsDist = consiGoodsDist;
	}
	public String getConsiPostCode() {
		return consiPostCode;
	}
	public void setConsiPostCode(String consiPostCode) {
		this.consiPostCode = consiPostCode;
	}
	public String getConsiPostAddress() {
		return consiPostAddress;
	}
	public void setConsiPostAddress(String consiPostAddress) {
		this.consiPostAddress = consiPostAddress;
	}
	public String getConsiPostRemark() {
		return consiPostRemark;
	}
	public void setConsiPostRemark(String consiPostRemark) {
		this.consiPostRemark = consiPostRemark;
	}
	public String getOrderOperType() {
		return orderOperType;
	}
	public void setOrderOperType(String orderOperType) {
		this.orderOperType = orderOperType;
	}
	public String getDeliMode() {
		return deliMode;
	}
	public void setDeliMode(String deliMode) {
		this.deliMode = deliMode;
	}
	public String getDeliTimeMode() {
		return deliTimeMode;
	}
	public void setDeliTimeMode(String deliTimeMode) {
		this.deliTimeMode = deliTimeMode;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceDesc() {
		return invoiceDesc;
	}
	public void setInvoiceDesc(String invoiceDesc) {
		this.invoiceDesc = invoiceDesc;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderProvince() {
		return orderProvince;
	}
	public void setOrderProvince(String orderProvince) {
		this.orderProvince = orderProvince;
	}
	public String getOrderCity() {
		return orderCity;
	}
	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getOrderAccCode() {
		return orderAccCode;
	}
	public void setOrderAccCode(String orderAccCode) {
		this.orderAccCode = orderAccCode;
	}
	public String getOrderAccType() {
		return orderAccType;
	}
	public void setOrderAccType(String orderAccType) {
		this.orderAccType = orderAccType;
	}
	public String getOrderAccDesc() {
		return orderAccDesc;
	}
	public void setOrderAccDesc(String orderAccDesc) {
		this.orderAccDesc = orderAccDesc;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public CenterMallPayInfo getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(CenterMallPayInfo payInfo) {
		this.payInfo = payInfo;
	}
	public List<CenterMallDiscountInfo> getDiscountInfo() {
		return discountInfo;
	}
	public void setDiscountInfo(List<CenterMallDiscountInfo> discountInfo) {
		this.discountInfo = discountInfo;
	}
	public List<CenterMallGiftInfo> getGiftInfo() {
		return giftInfo;
	}
	public void setGiftInfo(List<CenterMallGiftInfo> giftInfo) {
		this.giftInfo = giftInfo;
	}
	public CenterMallFlowInfo getFlowInfo() {
		return flowInfo;
	}
	public void setFlowInfo(CenterMallFlowInfo flowInfo) {
		this.flowInfo = flowInfo;
	}
	public CenterMallOperatorInfo getOperatorInfo() {
		return operatorInfo;
	}
	public void setOperatorInfo(CenterMallOperatorInfo operatorInfo) {
		this.operatorInfo = operatorInfo;
	}
	public String getOrderOrigFee() {
		return orderOrigFee;
	}
	public void setOrderOrigFee(String orderOrigFee) {
		this.orderOrigFee = orderOrigFee;
	}
	public String getOrderRealFee() {
		return orderRealFee;
	}
	public void setOrderRealFee(String orderRealFee) {
		this.orderRealFee = orderRealFee;
	}
	public String getOrderReliefFee() {
		return orderReliefFee;
	}
	public void setOrderReliefFee(String orderReliefFee) {
		this.orderReliefFee = orderReliefFee;
	}
	public String getOrderReliefRes() {
		return orderReliefRes;
	}
	public void setOrderReliefRes(String orderReliefRes) {
		this.orderReliefRes = orderReliefRes;
	}
	public String getOrigPostFee() {
		return origPostFee;
	}
	public void setOrigPostFee(String origPostFee) {
		this.origPostFee = origPostFee;
	}
	public String getRealPostFee() {
		return realPostFee;
	}
	public void setRealPostFee(String realPostFee) {
		this.realPostFee = realPostFee;
	}
	public String getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getPreSale() {
		return preSale;
	}
	public void setPreSale(String preSale) {
		this.preSale = preSale;
	}
	public CenterMallUseCustInfo getUseCustInfo() {
		return useCustInfo;
	}
	public void setUseCustInfo(CenterMallUseCustInfo useCustInfo) {
		this.useCustInfo = useCustInfo;
	}
	public String getIfSendPhotos() {
		return ifSendPhotos;
	}
	public void setIfSendPhotos(String ifSendPhotos) {
		this.ifSendPhotos = ifSendPhotos;
	}
}
