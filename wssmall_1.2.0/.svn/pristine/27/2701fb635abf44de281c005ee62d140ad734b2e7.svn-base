package zte.net.ecsord.params.zb.req;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.orderattr.Develop;
import zte.net.ecsord.params.zb.vo.orderattr.Flow;
import zte.net.ecsord.params.zb.vo.orderattr.Goods;
import zte.net.ecsord.params.zb.vo.orderattr.Operator;
import zte.net.ecsord.params.zb.vo.orderattr.Pay;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import zte.net.ecsord.params.zb.vo.orderattr.Discount;


public class SynchronousOrderGDRequest extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "买家标识/买家ID", type = "String", isNecessary = "Y", desc = "CustomerId：买家标识/买家ID")
	private String CustomerId;

	@ZteSoftCommentAnnotationParam(name = "买家昵称", type = "String", isNecessary = "Y", desc = "RegisterName：买家昵称")
	private String RegisterName;

	@ZteSoftCommentAnnotationParam(name = "买家登录IP", type = "String", isNecessary = "N", desc = "ClientIP：买家登录IP")
	private String ClientIP;
	
	@ZteSoftCommentAnnotationParam(name = "收件人姓名", type = "String", isNecessary = "N", desc = "ConsiName：收件人姓名")
	private String ConsiName;

	@ZteSoftCommentAnnotationParam(name = "收件人证件类型", type = "String", isNecessary = "N", desc = "ConsiCertType：收件人证件类型")
	private String ConsiCertType;

	@ZteSoftCommentAnnotationParam(name = "收件人证件号码", type = "String", isNecessary = "N", desc = "ConsiCertNum：收件人证件号码")
	private String ConsiCertNum;
	
	@ZteSoftCommentAnnotationParam(name = "收件人联系电话1", type = "String", isNecessary = "Y", desc = "ConsiPhone：收件人联系电话1")
	private String ConsiPhone;
	
	@ZteSoftCommentAnnotationParam(name = "收件人联系电话2支持填写固话", type = "String", isNecessary = "N", desc = "ConsiTelPhone：收件人联系电话2支持填写固话")
	private String ConsiTelPhone;
	
	@ZteSoftCommentAnnotationParam(name = "收件人EMAIL", type = "String", isNecessary = "N", desc = "ConsiEmail：收件人EMAIL")
	private String ConsiEmail;
	
	@ZteSoftCommentAnnotationParam(name = "买家留言", type = "String", isNecessary = "N", desc = "BuyerMessage：买家留言")
	private String BuyerMessage;
	
	@ZteSoftCommentAnnotationParam(name = "收货省", type = "String", isNecessary = "Y", desc = "ConsiGoodsProv：收货省")
	private String ConsiGoodsProv;
	
	@ZteSoftCommentAnnotationParam(name = "收货市", type = "String", isNecessary = "Y", desc = "ConsiGoodsCity：收货市")
	private String ConsiGoodsCity;
	
	@ZteSoftCommentAnnotationParam(name = "收货区/县（行政区域）", type = "String", isNecessary = "N", desc = "ConsiGoodsDist：收货区/县（行政区域）")
	private String ConsiGoodsDist;
	
	@ZteSoftCommentAnnotationParam(name = "邮政编码", type = "String", isNecessary = "N", desc = "ConsiPostCode：邮政编码")
	private String ConsiPostCode;
	
	@ZteSoftCommentAnnotationParam(name = "邮寄地址", type = "String", isNecessary = "Y", desc = "ConsiPostAddress：邮寄地址")
	private String ConsiPostAddress;
	
	@ZteSoftCommentAnnotationParam(name = "邮寄备注", type = "String", isNecessary = "N", desc = "ConsiPostRemark：邮寄备注")
	private String ConsiPostRemark;
	
	@ZteSoftCommentAnnotationParam(name = "是否闪电送", type = "String", isNecessary = "Y", desc = "OrderOperType：是否闪电送")
	private String OrderOperType;
	
	@ZteSoftCommentAnnotationParam(name = "配送方式", type = "String", isNecessary = "Y", desc = "deliMode：配送方式")
	private String deliMode;
	
	@ZteSoftCommentAnnotationParam(name = "配送时间类型", type = "String", isNecessary = "N", desc = "deliTimeMode：配送时间类型")
	private String deliTimeMode;
	
	@ZteSoftCommentAnnotationParam(name = "发票类型", type = "String", isNecessary = "N", desc = "InvoiceType：发票类型")
	private String InvoiceType;
	
	@ZteSoftCommentAnnotationParam(name = "发票内容", type = "String", isNecessary = "N", desc = "InvoiceDesc：发票内容")
	private String InvoiceDesc;
	
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name = "下单时间", type = "String", isNecessary = "Y", desc = "OrderTime：下单时间")
	private String OrderTime;
	
	@ZteSoftCommentAnnotationParam(name = "订单省份", type = "String", isNecessary = "Y", desc = "OrderProvince：订单省份")
	private String OrderProvince;
	
	@ZteSoftCommentAnnotationParam(name = "订单地市", type = "String", isNecessary = "Y", desc = "OrderCity：订单地市")
	private String OrderCity;
	
	@ZteSoftCommentAnnotationParam(name = "订单来源", type = "String", isNecessary = "Y", desc = "OrderSource：订单来源")
	private String OrderSource;
	
	@ZteSoftCommentAnnotationParam(name = "订单接入类别", type = "String", isNecessary = "Y", desc = "OrderAccType：订单接入类别")
	private String OrderAccType;
	
	@ZteSoftCommentAnnotationParam(name = "订单接入说明", type = "String", isNecessary = "Y", desc = "OrderAccDesc：订单接入说明")
	private String OrderAccDesc;
	
	@ZteSoftCommentAnnotationParam(name = "外部订单编号", type = "String", isNecessary = "N", desc = "OutOrderId：外部订单编号")
	private String OutOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "订单应收总价", type = "String", isNecessary = "Y", desc = "OrderOrigFee：订单应收总价")
	private String OrderOrigFee;
	
	@ZteSoftCommentAnnotationParam(name = "订单实收总价", type = "String", isNecessary = "Y", desc = "OrderRealFee：订单实收总价")
	private String OrderRealFee;
	
	@ZteSoftCommentAnnotationParam(name = "订单减免金额", type = "String", isNecessary = "Y", desc = "OrderReliefFee：订单减免金额")
	private String OrderReliefFee;
	
	@ZteSoftCommentAnnotationParam(name = "订单减免原因", type = "String", isNecessary = "N", desc = "OrderReliefRes：订单减免原因")
	private String OrderReliefRes;
	
	@ZteSoftCommentAnnotationParam(name = "应收邮寄费用", type = "String", isNecessary = "Y", desc = "OrigPostFee：应收邮寄费用")
	private String OrigPostFee;
	
	@ZteSoftCommentAnnotationParam(name = "实收邮寄费用", type = "String", isNecessary = "Y", desc = "RealPostFee：实收邮寄费用")
	private String RealPostFee;
	
	@ZteSoftCommentAnnotationParam(name = "支付信息", type = "String", isNecessary = "Y", desc = "PayInfo：支付信息")
	private Pay PayInfo;
	
	@ZteSoftCommentAnnotationParam(name = "优惠信息", type = "String", isNecessary = "Y", desc = "Discount：优惠信息")
	private Discount Discount;
	
	@ZteSoftCommentAnnotationParam(name = "订单中商品的总数量", type = "String", isNecessary = "Y", desc = "GoodsNum：订单中商品的总数量")
	private String GoodsNum;	
	
	@ZteSoftCommentAnnotationParam(name = "商品信息", type = "String", isNecessary = "Y", desc = "GoodsInfo：商品信息")
	private List<Goods> GoodsInfo;	
	
	@ZteSoftCommentAnnotationParam(name = "操作人信息", type = "String", isNecessary = "Y", desc = "OperatorInfo：操作人信息")
	private Operator OperatorInfo;
	
	@ZteSoftCommentAnnotationParam(name = "流程信息", type = "String", isNecessary = "Y", desc = "FlowInfo：流程信息")
	private Flow FlowInfo;
	
	@ZteSoftCommentAnnotationParam(name = "发展人信息", type = "String", isNecessary = "Y", desc = "DevelopInfo：发展人信息")
	private Develop DevelopInfo;

	public String getActiveNo() {
		
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getRegisterName() {
		return RegisterName;
	}

	public void setRegisterName(String registerName) {
		RegisterName = registerName;
	}

	public String getClientIP() throws UnknownHostException {
		 
		return ClientIP;
	}

	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	
	public String getConsiName() {
		return ConsiName;
	}

	public void setConsiName(String consiName) {
		ConsiName = consiName;
	}

	public String getConsiCertType() {
		return ConsiCertType;
	}

	public void setConsiCertType(String consiCertType) {
		ConsiCertType = consiCertType;
	}

	public String getConsiCertNum() {
		return ConsiCertNum;
	}

	public void setConsiCertNum(String consiCertNum) {
		ConsiCertNum = consiCertNum;
	}

	public String getConsiPhone() {
		return ConsiPhone;
	}

	public void setConsiPhone(String consiPhone) {
		ConsiPhone = consiPhone;
	}

	public String getConsiTelPhone() {
		return ConsiTelPhone;
	}

	public void setConsiTelPhone(String consiTelPhone) {
		ConsiTelPhone = consiTelPhone;
	}

	public String getConsiEmail() {
		return ConsiEmail;
	}

	public void setConsiEmail(String consiEmail) {
		ConsiEmail = consiEmail;
	}

	public String getBuyerMessage() {
		return BuyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		BuyerMessage = buyerMessage;
	}

	public String getConsiGoodsProv() {
		return ConsiGoodsProv;
	}

	public void setConsiGoodsProv(String consiGoodsProv) {
		ConsiGoodsProv = consiGoodsProv;
	}

	public String getConsiGoodsCity() {
		return ConsiGoodsCity;
	}

	public void setConsiGoodsCity(String consiGoodsCity) {
		ConsiGoodsCity = consiGoodsCity;
	}

	public String getConsiGoodsDist() {
		return ConsiGoodsDist;
	}

	public void setConsiGoodsDist(String consiGoodsDist) {
		ConsiGoodsDist = consiGoodsDist;
	}

	public String getConsiPostCode() {
		return ConsiPostCode;
	}

	public void setConsiPostCode(String consiPostCode) {
		ConsiPostCode = consiPostCode;
	}

	public String getConsiPostAddress() {
		return ConsiPostAddress;
	}

	public void setConsiPostAddress(String consiPostAddress) {
		ConsiPostAddress = consiPostAddress;
	}

	public String getConsiPostRemark() {
		return ConsiPostRemark;
	}

	public void setConsiPostRemark(String consiPostRemark) {
		ConsiPostRemark = consiPostRemark;
	}

	public String getOrderOperType() {
		return OrderOperType;
	}

	public void setOrderOperType(String orderOperType) {
		OrderOperType = orderOperType;
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
		return InvoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		InvoiceType = invoiceType;
	}

	public String getInvoiceDesc() {
		return InvoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		InvoiceDesc = invoiceDesc;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getOrderTime() {
		
		return OrderTime;
	}

	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}

	public String getOrderProvince() {
		return OrderProvince;
	}

	public void setOrderProvince(String orderProvince) {
		OrderProvince = orderProvince;
	}

	public String getOrderCity() {
		return OrderCity;
	}

	public void setOrderCity(String orderCity) {
		OrderCity = orderCity;
	}

	public String getOrderSource() {
		return OrderSource;
	}

	public void setOrderSource(String orderSource) {
		OrderSource = orderSource;
	}

	public String getOrderAccType() {
		return OrderAccType;
	}

	public void setOrderAccType(String orderAccType) {
		OrderAccType = orderAccType;
	}

	public String getOrderAccDesc() {
		return OrderAccDesc;
	}

	public void setOrderAccDesc(String orderAccDesc) {
		OrderAccDesc = orderAccDesc;
	}

	public String getOutOrderId() {
		return OutOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		OutOrderId = outOrderId;
	}

	public String getOrderOrigFee() {
		return OrderOrigFee;
	}

	public void setOrderOrigFee(String orderOrigFee) {
		OrderOrigFee = orderOrigFee;
	}

	public String getOrderRealFee() {
		return OrderRealFee;
	}

	public void setOrderRealFee(String orderRealFee) {
		OrderRealFee = orderRealFee;
	}

	public String getOrderReliefFee() {
		return OrderReliefFee;
	}

	public void setOrderReliefFee(String orderReliefFee) {
		OrderReliefFee = orderReliefFee;
	}

	public String getOrderReliefRes() {
		return OrderReliefRes;
	}

	public void setOrderReliefRes(String orderReliefRes) {
		OrderReliefRes = orderReliefRes;
	}

	public String getOrigPostFee() {
		return OrigPostFee;
	}

	public void setOrigPostFee(String origPostFee) {
		OrigPostFee = origPostFee;
	}

	public String getRealPostFee() {
		return RealPostFee;
	}

	public void setRealPostFee(String realPostFee) {
		RealPostFee = realPostFee;
	}

	public Pay getPayInfo() {
		return PayInfo;
	}

	public void setPayInfo(Pay payInfo) {
		PayInfo = payInfo;
	}

	public Discount getDiscount() {
		return Discount;
	}

	public void setDiscount(Discount discount) {
		Discount = discount;
	}

	public String getGoodsNum() {
		return GoodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		GoodsNum = goodsNum;
	}

	public List<Goods> getGoodsInfo() {
		return GoodsInfo;
	}

	public void setGoodsInfo(List<Goods> goodsInfo) {
		GoodsInfo = goodsInfo;
	}

	public Operator getOperatorInfo() {
		return OperatorInfo;
	}

	public void setOperatorInfo(Operator operatorInfo) {
		OperatorInfo = operatorInfo;
	}

	public Flow getFlowInfo() {
		return FlowInfo;
	}

	public void setFlowInfo(Flow flowInfo) {
		FlowInfo = flowInfo;
	}

	public Develop getDevelopInfo() {
		return DevelopInfo;
	}

	public void setDevelopInfo(Develop developInfo) {
		DevelopInfo = developInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.SynchronousOrderGD";
	}
}
