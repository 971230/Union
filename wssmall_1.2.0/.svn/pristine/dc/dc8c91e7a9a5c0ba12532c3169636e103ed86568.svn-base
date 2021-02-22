package zte.net.ecsord.params.zb.req;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrLeagueInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockRequest;
import zte.net.ecsord.params.zb.vo.orderattr.Develop;
import zte.net.ecsord.params.zb.vo.orderattr.Discount;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.params.zb.vo.orderattr.Flow;
import zte.net.ecsord.params.zb.vo.orderattr.Gift;
import zte.net.ecsord.params.zb.vo.orderattr.Goods;
import zte.net.ecsord.params.zb.vo.orderattr.Operator;
import zte.net.ecsord.params.zb.vo.orderattr.Pay;
import zte.net.ecsord.params.zb.vo.orderattr.LeagueInfo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class SynchronousOrderZBRequest extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

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
	
	@ZteSoftCommentAnnotationParam(name = "发票号", type = "String", isNecessary = "N", desc = "InvoiceNo：发票号")
	private String InvoiceNo;
	
	@ZteSoftCommentAnnotationParam(name = "发票抬头", type = "String", isNecessary = "N", desc = "InvoiceTitle：发票抬头")
	private String InvoiceTitle;
	
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

	@ZteSoftCommentAnnotationParam(name = "订单应收总价", type = "int", isNecessary = "Y", desc = "OrderOrigFee：订单应收总价")
	private int OrderOrigFee;

	@ZteSoftCommentAnnotationParam(name = "订单实收总价", type = "int", isNecessary = "Y", desc = "OrderRealFee：订单实收总价")
	private int OrderRealFee;

	@ZteSoftCommentAnnotationParam(name = "订单减免金额", type = "int", isNecessary = "Y", desc = "OrderReliefFee：订单减免金额")
	private int OrderReliefFee;

	@ZteSoftCommentAnnotationParam(name = "订单减免原因", type = "String", isNecessary = "N", desc = "OrderReliefRes：订单减免原因")
	private String OrderReliefRes;

	@ZteSoftCommentAnnotationParam(name = "应收邮寄费用", type = "int", isNecessary = "Y", desc = "OrigPostFee：应收邮寄费用")
	private int OrigPostFee;

	@ZteSoftCommentAnnotationParam(name = "实收邮寄费用", type = "int", isNecessary = "Y", desc = "RealPostFee：实收邮寄费用")
	private int RealPostFee;

	@ZteSoftCommentAnnotationParam(name = "支付信息", type = "String", isNecessary = "Y", desc = "PayInfo：支付信息")
	private Pay PayInfo;

	
	@ZteSoftCommentAnnotationParam(name = "优惠信息", type = "String", isNecessary = "Y", desc = "Discount：优惠信息")
	private List<Discount> DiscountInfo;

	@ZteSoftCommentAnnotationParam(name = "订单中商品的总数量", type = "int", isNecessary = "Y", desc = "GoodsNum：订单中商品的总数量")
	private int GoodsNum;

	@ZteSoftCommentAnnotationParam(name = "商品信息", type = "List<Goods>", isNecessary = "Y", desc = "GoodsInfo：商品信息")
	private List<Goods> GoodsInfo;
	
	@ZteSoftCommentAnnotationParam(name = "赠品信息", type = "String", isNecessary = "Y", desc = "Gift：赠品信息")
	private List<Gift> GiftInfo;

	@ZteSoftCommentAnnotationParam(name = "操作人信息", type = "String", isNecessary = "Y", desc = "OperatorInfo：操作人信息")
	private Operator OperatorInfo;

	@ZteSoftCommentAnnotationParam(name = "流程信息", type = "String", isNecessary = "Y", desc = "FlowInfo：流程信息")
	private Flow FlowInfo;

	@ZteSoftCommentAnnotationParam(name = "发展人信息", type = "String", isNecessary = "Y", desc = "DevelopInfo：发展人信息")
	private Develop DevelopInfo;
	
	@ZteSoftCommentAnnotationParam(name = "联盟信息", type = "String", isNecessary = "Y", desc = "LeagueInfo：联盟信息")
	private LeagueInfo LeagueInfo;

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getCustomerId() {
		CustomerId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.UID);
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getRegisterName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_OWNER_NAME);
	}

	public void setRegisterName(String registerName) {
		RegisterName = registerName;
	}

	public String getClientIP() throws UnknownHostException {

		return "0.0.0.0";
	}

	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	public String getConsiName() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getOrderBusiRequest().getShip_name();
	}

	public void setConsiName(String consiName) {
		ConsiName = consiName;
	}

	public String getConsiCertType() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
	}

	public void setConsiCertType(String consiCertType) {
		ConsiCertType = consiCertType;
	}

	public String getConsiCertNum() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);
	}

	public void setConsiCertNum(String consiCertNum) {
		ConsiCertNum = consiCertNum;
	}

	public String getConsiPhone() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.REVEIVER_MOBILE);
	}

	public void setConsiPhone(String consiPhone) {
		ConsiPhone = consiPhone;
	}

	public String getConsiTelPhone() {
		String reference_phone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE);
		return reference_phone==null ? CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.REVEIVER_MOBILE):reference_phone;
	}

	public void setConsiTelPhone(String consiTelPhone) {
		ConsiTelPhone = consiTelPhone;
	}

	public String getConsiEmail() {
		ConsiEmail = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SHIP_EMAIL);
		return ConsiEmail;
	}

	public void setConsiEmail(String consiEmail) {
		ConsiEmail = consiEmail;
	}

	public String getBuyerMessage() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.BUYER_MESSAGE);
	}

	public void setBuyerMessage(String buyerMessage) {
		BuyerMessage = buyerMessage;
	}

	public String getConsiGoodsProv() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PROVINCE_CODE);
	}

	public void setConsiGoodsProv(String consiGoodsProv) {
		ConsiGoodsProv = consiGoodsProv;
	}

	public String getConsiGoodsCity() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CITY_CODE);
	}

	public void setConsiGoodsCity(String consiGoodsCity) {
		ConsiGoodsCity = consiGoodsCity;
	}

	public String getConsiGoodsDist() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.AREA_CODE);
	}

	public void setConsiGoodsDist(String consiGoodsDist) {
		ConsiGoodsDist = consiGoodsDist;
	}

	public String getConsiPostCode() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SHIP_ZIP);
	}

	public void setConsiPostCode(String consiPostCode) {
		ConsiPostCode = consiPostCode;
	}

	public String getConsiPostAddress() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SHIP_ADDR);
	}

	public void setConsiPostAddress(String consiPostAddress) {
		ConsiPostAddress = consiPostAddress;
	}

	public String getConsiPostRemark() {
		return "";
	}

	public void setConsiPostRemark(String consiPostRemark) {
		ConsiPostRemark = consiPostRemark;
	}

	public String getOrderOperType() {
		String shipping_quick = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SHIPPING_QUICK);
		if(EcsOrderConsts.SHIPPING_QUICK_01.equals(shipping_quick)){
			shipping_quick = EcsOrderConsts.SHIPPING_QUICK_02;
		}
		else if(EcsOrderConsts.SHIPPING_QUICK_02.equals(shipping_quick)){
			shipping_quick = EcsOrderConsts.SHIPPING_QUICK_01;
		}
		return shipping_quick;
	}

	public void setOrderOperType(String orderOperType) {
		OrderOperType = orderOperType;
	}

	public String getDeliMode() {
		return CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId,
				AttrConsts.SENDING_TYPE);
	}

	public void setDeliMode(String deliMode) {
		this.deliMode = deliMode;
	}

	public String getDeliTimeMode() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SHIPPING_TIME);
	}

	public void setDeliTimeMode(String deliTimeMode) {
		this.deliTimeMode = deliTimeMode;
	}

	public String getInvoiceType() {
		String invoice_print_type = CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId, AttrConsts.INVOICE_PRINT_TYPE);
		return (invoice_print_type == null || "".equals(invoice_print_type)) ? EcsOrderConsts.INVOICE_TYPE_MONTH : invoice_print_type;
	}

	public void setInvoiceType(String invoiceType) {
		InvoiceType = invoiceType;
	}
	
	public String getInvoiceNo() {
		
		InvoiceNo = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
			.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getInvoice_no();
		return InvoiceNo == null ? "" : InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}

	public String getInvoiceDesc() {
		//当发票类型为空默认MONTH,发票内容为空默认为MX。否则总部接口报错：校验数据错误：发票内容转码失败[InvoiceDesc:]
		InvoiceDesc = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.INVOICE_GROUP_CONTENT);
		return StringUtil.isEmpty(InvoiceDesc) ? EcsOrderConsts.INVOICE_GROUP_CONTENT : InvoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		InvoiceDesc = invoiceDesc;
	}

	public String getOrderTime() {

		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.TID_TIME);
	}

	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}

	public String getOrderProvince() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_PROVINCE_CODE);
	}

	public void setOrderProvince(String orderProvince) {
		OrderProvince = orderProvince;
	}

	public String getOrderCity() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_CITY_CODE);
	}

	public void setOrderCity(String orderCity) {
		OrderCity = orderCity;
	}

	public String getOrderSource() {
		//新协议
		OrderSource = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SOURCE_TYPE);
		//旧协议，总部新版本没有上线，按旧协议送
//		OrderSource = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_JOIN_CATEGORY);
		return OrderSource;
	}

	public void setOrderSource(String orderSource) {
		OrderSource = orderSource;
	}

	public String getOrderAccType() {
//		新协议
		OrderAccType = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REGIST_TYPE);
		//旧协议，总部新版本没有上线，按旧协议送
//		String order_source = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_JOIN_CATEGORY);
//		String zb_order_source = order_source == null ? "" : "C".equals(order_source)?"D": order_source;
//		String order_origin = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_ORIGIN);
//		String zb_order_origin = zb_order_source.concat(order_origin);
//		List<Map> relations = CommonDataFactory.getInstance().getDictRelationListData(StypeConsts.ZB_ORDER_ACC_TYPE);
//		for(Map relation : relations){
//			if(zb_order_origin.equals(relation.get("field_value").toString())){
//				OrderAccType = (String) relation.get("other_field_value");
//				break ;
//			}
//		}
		return OrderAccType == null ? "" : OrderAccType;
	}

	public void setOrderAccType(String orderAccType) {
		OrderAccType = orderAccType;
	}

	public String getOrderAccDesc() {
		return "";
	}

	public void setOrderAccDesc(String orderAccDesc) {
		OrderAccDesc = orderAccDesc;
	}

	public String getOutOrderId() {
		OutOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
		return OutOrderId == null ? "" : OutOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		OutOrderId = outOrderId;
	}

	public int getOrderOrigFee() {
		String oofStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SELL_PRICE);
		if(null == oofStr || oofStr.equals("")){
			oofStr = "0";
		}
		OrderOrigFee = Double.valueOf(oofStr).intValue() * 1000;
		return OrderOrigFee;
	}

	public void setOrderOrigFee(int orderOrigFee) {
		OrderOrigFee = orderOrigFee;
	}

	public int getOrderRealFee() {
		String orfStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_REAL_FEE);
		if(null == orfStr || orfStr.equals("")){
			orfStr = "0";
		}
		OrderRealFee = Double.valueOf(orfStr).intValue() * 1000;
		return OrderRealFee;
	}

	public void setOrderRealFee(int orderRealFee) {
		OrderRealFee = orderRealFee;
	}

	public int getOrderReliefFee() {
		String orfStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DISCOUNT_VALUE);
		if(null == orfStr || orfStr.equals("")){
			orfStr = "0";
		}
		OrderReliefFee = Double.valueOf(orfStr).intValue() * 1000;
		return OrderReliefFee;
	}

	public void setOrderReliefFee(int orderReliefFee) {
		OrderReliefFee = orderReliefFee;
	}

	public String getOrderReliefRes() {
		return "";
	}

	public void setOrderReliefRes(String orderReliefRes) {
		OrderReliefRes = orderReliefRes;
	}

	public int getOrigPostFee() {
		String opfStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.POST_FEE);
		if(null == opfStr || opfStr.equals("")){
			opfStr = "0";
		}
		OrigPostFee = Double.valueOf(opfStr).intValue() * 1000;
		return OrigPostFee;
	}

	public void setOrigPostFee(int origPostFee) {
		OrigPostFee = origPostFee;
	}

	public int getRealPostFee() {
		String rpfStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.POST_FEE);
		if(null == rpfStr || rpfStr.equals("")){
			rpfStr = "0";
		}
		RealPostFee = Double.valueOf(rpfStr).intValue() * 1000;
		return RealPostFee;
	}

	public void setRealPostFee(int realPostFee) {
		RealPostFee = realPostFee;
	}

	public Pay getPayInfo() {
		PayInfo=new Pay();
		PayInfo.setPayChannelId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_CHANNEL_ID));
		PayInfo.setPayChannelName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_CHANNEL_NAME));
		PayInfo.setPayFinTime(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_FIN_TIME));
		PayInfo.setPayPlatFormOrderId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PLATFORM_ORDER_ID));
		PayInfo.setPayProviderId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_ID));
		PayInfo.setPayProviderName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_NAME));
		String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_TYPE);
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, StypeConsts.SHIPPING_TYPE);
		
		String zb_pay_type = CommonDataFactory.getInstance().getOtherDictVodeValue(sending_type+"_order_pay_type", pay_type);
		if(StringUtils.isEmpty(zb_pay_type)){
			zb_pay_type = pay_type;
		}
		PayInfo.setPayType(zb_pay_type);
		
		
		String pay_way = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_METHOD);
		String zb_pay_way = CommonDataFactory.getInstance().getOtherDictVodeValue(sending_type+"_order_pay_way", pay_way);
		if(StringUtils.isEmpty(zb_pay_way)){
			zb_pay_way = pay_way;
		}
		PayInfo.setPayWay(zb_pay_way);
		
		return PayInfo;
	}

	public void setPayInfo(Pay payInfo) {
		PayInfo = payInfo;
	}

	public List<Discount> getDiscountInfo() {
		DiscountInfo = new ArrayList<Discount>();
//		List<AttrDiscountInfoBusiRequest> AttrDiscountInfoBusiRequests = CommonDataFactory
//				.getInstance().getOrderTree(notNeedReqStrOrderId)
//				.getDiscountInfoBusiRequests();
//		for (AttrDiscountInfoBusiRequest AttrDiscountInfo : AttrDiscountInfoBusiRequests) {
//			Discount Discount = new Discount();
//			Discount.setDiscountID(AttrDiscountInfo.getActivity_code());
//			Discount.setDiscountName(AttrDiscountInfo.getActivity_name());
//			Discount.setDiscountType(AttrDiscountInfo.getActivity_type());
//			Discount.setDiscountValue(AttrDiscountInfo.getDiscount_num()*1000+"");
//			DiscountInfo.add(Discount);
//		}
		return DiscountInfo;
	}

	public void setDiscountInfo(List<Discount> discountInfo) {
		DiscountInfo = discountInfo;
	}

	public int getGoodsNum() {
		String numStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.GOODS_NUM);
		if(StringUtils.isEmpty(numStr))
			numStr="1";
		GoodsNum = Integer.valueOf(numStr);
		return GoodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		GoodsNum = goodsNum;
	}

	public List<Goods> getGoodsInfo() {
		Goods goods = new Goods();
//		List<Fee> feeList = new ArrayList<Fee>();
//		List<AttrFeeInfoBusiRequest> feeS = CommonDataFactory.getInstance()
//				.getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
//		for (AttrFeeInfoBusiRequest fee : feeS) {
//			Fee feeInfo = new Fee();
//			feeInfo.setFeeID(fee.getFee_item_code());
//			feeInfo.setOrigFee(fee.getO_fee_num()*1000);
//			feeInfo.setRealFee(fee.getN_fee_num()*1000);
//			feeInfo.setReliefFee(fee.getDiscount_fee()*1000);
//			feeInfo.setReliefResult(fee.getDiscount_reason());
//			feeInfo.setFeeDes(fee.getFee_item_name());
//
//			feeList.add(feeInfo);
//		}
//		String goodsType =  CommonDataFactory.getInstance().getGoodsType(notNeedReqStrOrderId);
//		if(EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equals(goodsType)){
//			String hasHy = CommonDataFactory.getInstance()
//					.hasTypeOfProduct(notNeedReqStrOrderId, EcsOrderConsts.PRODUCT_TYPE_CONTRACT);
//			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(hasHy)){
//				goodsType = EcsOrderConsts.ZB_GOODS_TYPE_ZHYL;
//			}
//		}
//		goods.setGoodsType(goodsType);
//		String gofStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SELL_PRICE);
//		if(null == gofStr || gofStr.equals("")){
//			gofStr = "0";
//		}
//		goods.setGoodsOrigFee(Double.valueOf(gofStr).intValue() * 1000);
//		
//		String grfStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SELL_PRICE);
//		if(grfStr == null || grfStr.equals("")){
//			grfStr = "0";
//		}
//		GoodsInfo = new ArrayList<Goods>();
//		goods.setGoodsRealFee(Double.valueOf(grfStr).intValue() * 1000);
//		goods.setGoodsReliefRes("");				//商品减免原因（为定义字段）
//		goods.setFeeInfo(feeList);
//		String crm_offer_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, SpecConsts.CRM_OFFER_ID);
//		if(StringUtils.isEmpty(crm_offer_id)){
//			crm_offer_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
//			if(crm_offer_id.length()> 12){
//				crm_offer_id = crm_offer_id.substring(crm_offer_id.length()-12, crm_offer_id.length());
//			}
//		}
//		goods.setGoodsCode(crm_offer_id);
//		goods.setGoodsName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODSNAME));
//		goods.setGoodsAttInfo(CommonDataFactory.getInstance().getGoodsAttrInfo(notNeedReqStrOrderId));
//		GoodsInfo.add(goods);
		return GoodsInfo;
	}

	public void setGoodsInfo(List<Goods> goodsInfo) {
		GoodsInfo = goodsInfo;
	}
	
	public List<Gift> getGiftInfo() {
		GiftInfo = new ArrayList<Gift>();
//		List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance()
//				.getOrderTree(notNeedReqStrOrderId).getGiftInfoBusiRequests();
//		for (AttrGiftInfoBusiRequest gift : gifts) {
//			Gift giftInfo = new Gift();
//
//			giftInfo.setGiftType(gift.getGift_type());
//			giftInfo.setGiftID(gift.getGift_id());
//			giftInfo.setGiftName(gift.getGoods_name());
//			giftInfo.setGiftNum(gift.getSku_num());
//			giftInfo.setGiftValue(StringUtils.isEmpty(gift.getGift_value())?"0":gift.getGift_value());
//			GiftInfo.add(giftInfo);
//
//		}
		return GiftInfo;
	}

	public void setGiftInfo(List<Gift> giftInfo) {
		GiftInfo = giftInfo;
	}

	public Operator getOperatorInfo() {
		OperatorInfo = new Operator();
		
		List<OrderLockRequest> orderLockRequestList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderLockRequest();
		String trace_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		String lock_user_id = "";
		for (OrderLockRequest orderLockRequest : orderLockRequestList) {
			if(trace_id.equals(orderLockRequest.getTache_code())) {
				lock_user_id = orderLockRequest.getLock_user_id();
				break;
			}
		}
		
		String operatorID = lock_user_id;//CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_id();
		if(StringUtils.isEmpty(operatorID)){
			String oper_code = CommonDataFactory.getInstance().getOperatorCode(notNeedReqStrOrderId);
			AdminUser adminUser = CommonDataFactory.getInstance().getOperatorInfo(oper_code);
			OperatorInfo.setOperatorCode(oper_code);
			OperatorInfo.setOperatorName(adminUser==null?"":adminUser.getRealname());
		}else {
			OperatorInfo.setOperatorCode(operatorID);
			String operatorName = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_name();
			OperatorInfo.setOperatorName(operatorName == null ? "" : operatorName);
		}
		return OperatorInfo;
	}

	public void setOperatorInfo(Operator operatorInfo) {
		OperatorInfo = operatorInfo;
	}

	public Flow getFlowInfo() {
		FlowInfo = new Flow();
		FlowInfo.setOperMode(EcsOrderConsts.ORDER_MODE_AUTO);
		FlowInfo.setOrderFlow(CommonDataFactory.getInstance().getOrderFlow(notNeedReqStrOrderId));
		return FlowInfo;
	}

	public void setFlowInfo(Flow flowInfo) {
		FlowInfo = flowInfo;
	}
	
	public String getInvoiceTitle() {
		InvoiceTitle = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.RESERVE8);
		if(StringUtils.isEmpty(InvoiceTitle)){
			InvoiceTitle = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME);
		}
		return InvoiceTitle == null ? "" : InvoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		InvoiceTitle = invoiceTitle;
	}

	public Develop getDevelopInfo() {
		DevelopInfo = new Develop();
		DevelopInfo.setDevelopCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE));
		DevelopInfo.setDevelopName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME));
		return DevelopInfo;
	}

	public void setDevelopInfo(Develop developInfo) {
		DevelopInfo = developInfo;
	}
	
	public LeagueInfo getLeagueInfo() {
//		List<AttrLeagueInfoBusiRequest> leagues = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getLeagueInfoBusiRequests();
//		AttrLeagueInfoBusiRequest league = leagues.size()>0?leagues.get(0):null;
//		LeagueInfo = new LeagueInfo();
//		if(league!=null){
//			LeagueInfo.setLeagueID(league.getLeague_id());
//			LeagueInfo.setLeagueName(league.getLeague_name());
//			LeagueInfo.setHigherLeagueID(league.getHigher_league_id());
//			LeagueInfo.setHigherLeagueName(league.getHigher_league_name());
//		}
//		else{
//			LeagueInfo.setLeagueID(EcsOrderConsts.EMPTY_STR_VALUE);
//			LeagueInfo.setLeagueName(EcsOrderConsts.EMPTY_STR_VALUE);
//			LeagueInfo.setHigherLeagueID(EcsOrderConsts.EMPTY_STR_VALUE);
//			LeagueInfo.setHigherLeagueName(EcsOrderConsts.EMPTY_STR_VALUE);
//		}
		return LeagueInfo;
	}

	public void setLeagueInfo(LeagueInfo leagueInfo) {
		LeagueInfo = leagueInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.SynchronousOrderZB";
	}
}
