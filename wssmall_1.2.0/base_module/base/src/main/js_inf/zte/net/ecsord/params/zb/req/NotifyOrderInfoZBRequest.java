package zte.net.ecsord.params.zb.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.zb.vo.Distribution;
import zte.net.ecsord.params.zb.vo.Invoice;
import zte.net.ecsord.params.zb.vo.Post;
import zte.net.ecsord.params.zb.vo.UserInfomation;
import zte.net.ecsord.params.zb.vo.orderattr.Develop;
import zte.net.ecsord.params.zb.vo.orderattr.Discount;
import zte.net.ecsord.params.zb.vo.orderattr.Fee;
import zte.net.ecsord.params.zb.vo.orderattr.Flow;
import zte.net.ecsord.params.zb.vo.orderattr.Gift;
import zte.net.ecsord.params.zb.vo.orderattr.GoodsAtt;
import zte.net.ecsord.params.zb.vo.orderattr.Operator;
import zte.net.ecsord.params.zb.vo.orderattr.Pay;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * @author li.weili 订单信息变更
 * 
 */
public class NotifyOrderInfoZBRequest extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "商城订单编号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：商城订单编号")
	private String OrderId;

	@ZteSoftCommentAnnotationParam(name = "商品类型", type = "String", isNecessary = "N", desc = "GoodsType：商品类型")
	private String GoodsType;

	@ZteSoftCommentAnnotationParam(name = "更新范围", type = "String", isNecessary = "Y", desc = "UpdateScope：更新范围")
	private String UpdateScope;

	@ZteSoftCommentAnnotationParam(name = "收货信息", type = "PostInfo", isNecessary = "N", desc = "PostInfo：收货信息")
	private Post PostInfo;

	@ZteSoftCommentAnnotationParam(name = "配送信息", type = "Distribution", isNecessary = "N", desc = "DistributionInfo：配送信息")
	private Distribution DistributionInfo;

	@ZteSoftCommentAnnotationParam(name = "发票信息", type = "Invoice", isNecessary = "N", desc = "InvoiceInfo：发票信息")
	private Invoice InvoiceInfo;

	@ZteSoftCommentAnnotationParam(name = "支付信息", type = "Pay", isNecessary = "N", desc = "PayInfo：支付信息")
	private Pay PayInfo;

	@ZteSoftCommentAnnotationParam(name = "优惠信息", type = "Discount", isNecessary = "N", desc = "DiscountInfo：优惠信息")
	private List<Discount> DiscountInfo;

	@ZteSoftCommentAnnotationParam(name = "赠品信息", type = "Gift", isNecessary = "N", desc = "GiftInfo：赠品信息")
	private List<Gift> GiftInfo;

	@ZteSoftCommentAnnotationParam(name = "商品信息", type = "Goods", isNecessary = "N", desc = "GoodsAttInfo：商品信息")
	private List<GoodsAtt> GoodsAttInfo;

	@ZteSoftCommentAnnotationParam(name = "费用明细(总部商城)", type = "Fee", isNecessary = "N", desc = "FeeInfo：费用明细(总部商城)")
	private List<Fee> FeeInfo;

	@ZteSoftCommentAnnotationParam(name = "入网信息", type = "UserInfomation", isNecessary = "N", desc = "UserInfo：入网信息")
	private UserInfomation UserInfo;

	@ZteSoftCommentAnnotationParam(name = "发展人信息", type = "Develop", isNecessary = "N", desc = "DevelopInfo：发展人信息")
	private Develop DevelopInfo;

	@ZteSoftCommentAnnotationParam(name = "操作人信息", type = "Operator", isNecessary = "N", desc = "OperatorInfo：操作人信息")
	private Operator OperatorInfo;

	@ZteSoftCommentAnnotationParam(name = "流程信息", type = "Flow", isNecessary = "N", desc = "FlowInfo：流程信息")
	private Flow FlowInfo;

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

	public String getOrderId() {
		OrderId = CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getGoodsType() {
		return CommonDataFactory.getInstance().getGoodsType(notNeedReqStrOrderId);
	}

	public void setGoodsType(String goodsType) {
		GoodsType = goodsType;
	}

	public String getUpdateScope() {
		String user_type = CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId, AttrConsts.IS_OLD);
		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		//如果是老用户订单，且证件类型为无需证件时，不变更入网信息
		if(EcsOrderConsts.IS_OLD_1.equals(user_type) && EcsOrderConsts.CERTI_TYPE_WXZ.equals(certi_type)){
			UpdateScope = EcsOrderConsts.UPDATESCOPE_111111110111;
		}
		else{
			UpdateScope = EcsOrderConsts.UPDATESCOPE_111111111111;
		}
		return UpdateScope;
	}

	public void setUpdateScope(String updateScope) {
		UpdateScope = updateScope;
	}

	public Post getPostInfo() {
		PostInfo=new Post();
		PostInfo.setBuyerMessage(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.BUYER_MESSAGE));
		PostInfo.setConsiCertNum(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
		PostInfo.setConsiCertType(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.CERTI_TYPE));
		PostInfo.setConsiEmail(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.SHIP_EMAIL));
		PostInfo.setConsiGoodsCity(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.CITY_CODE));
		PostInfo.setConsiGoodsDist(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.AREA_CODE));
		PostInfo.setConsiGoodsProv(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.PROVINCE_CODE));
		PostInfo.setConsiName(CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrOrderId).getOrderBusiRequest()
				.getShip_name());
		PostInfo.setConsiPhone(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
		PostInfo.setConsiPostAddress(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.SHIP_ADDR));
		PostInfo.setConsiPostCode(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.SHIP_ZIP));
		PostInfo.setConsiPostRemark("");
		String reference_phone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE);
		if(StringUtils.isEmpty(reference_phone)){
			reference_phone = CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE);
		}
		PostInfo.setConsiTelPhone(reference_phone);
		
		return PostInfo;
	}

	public void setPostInfo(Post postInfo) {
		PostInfo = postInfo;
	}

	public Distribution getDistributionInfo() {
		DistributionInfo=new Distribution();
		DistributionInfo.setDeliMode(CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId,
				AttrConsts.SENDING_TYPE));
		DistributionInfo.setDeliTimeMode(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.SHIPPING_TIME));
		return DistributionInfo;
	}

	public void setDistributionInfo(Distribution distributionInfo) {
		DistributionInfo = distributionInfo;
	}

	public Invoice getInvoiceInfo() {
		InvoiceInfo=new Invoice();
		String invoice_desc = CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.INVOICE_GROUP_CONTENT);
		InvoiceInfo.setInvoiceDesc((invoice_desc==null||"".equals(invoice_desc))?EcsOrderConsts.INVOICE_GROUP_CONTENT:invoice_desc);
		InvoiceInfo.setInvoiceNo(CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.INVOICE_NO));
		String invoice_title = CommonDataFactory.getInstance().getAttrFieldValue(
				notNeedReqStrOrderId, AttrConsts.RESERVE8);
		if(StringUtils.isEmpty(invoice_title)){
			invoice_title = CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME);
		}
		InvoiceInfo.setInvoiceTitle(invoice_title);
		
		String invoice_print_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.INVOICE_PRINT_TYPE);
		String invoice_type = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ZB_INVOICE_PRINT_REL_TYPE, invoice_print_type);
		InvoiceInfo.setInvoiceType(invoice_type==null?EcsOrderConsts.INVOICE_TYPE_MONTH:invoice_type);
		
		return InvoiceInfo;
	}

	public void setInvoiceInfo(Invoice invoiceInfo) {
		InvoiceInfo = invoiceInfo;
	}

	public Pay getPayInfo() {
		PayInfo=new Pay();
		PayInfo.setPayChannelId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_CHANNEL_ID));
		PayInfo.setPayChannelName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_CHANNEL_NAME));
		PayInfo.setPayFinTime(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_FIN_TIME));
		PayInfo.setPayPlatFormOrderId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PLATFORM_ORDER_ID));
		PayInfo.setPayProviderId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PLATFORM_ORDER_ID));
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
		List<Discount> discounts = new ArrayList<Discount>();
//		List<AttrDiscountInfoBusiRequest> AttrDiscountInfoBusiRequests = CommonDataFactory
//				.getInstance().getOrderTree(notNeedReqStrOrderId)
//				.getDiscountInfoBusiRequests();
//		for (AttrDiscountInfoBusiRequest AttrDiscountInfo : AttrDiscountInfoBusiRequests) {
//			Discount Discount = new Discount();
//			Discount.setDiscountID(AttrDiscountInfo.getActivity_code());
//			Discount.setDiscountName(AttrDiscountInfo.getActivity_name());
//			Discount.setDiscountType(AttrDiscountInfo.getActivity_type());
//			Discount.setDiscountValue(AttrDiscountInfo.getDiscount_num()*1000+"");
//			discounts.add(Discount);
//		}
		return discounts;
	}

	public void setDiscountInfo(List<Discount> discountInfo) {
		DiscountInfo = (List<Discount>) discountInfo;
	}

	public List<Gift> getGiftInfo() {
		
		List<Gift> giftItem = new ArrayList<Gift>();
//		List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance()
//				.getOrderTree(notNeedReqStrOrderId).getGiftInfoBusiRequests();
//		for (AttrGiftInfoBusiRequest gift : gifts) {
//			Gift giftInfo = new Gift();
//			giftInfo.setGiftType(gift.getGift_type());
//			giftInfo.setGiftID(gift.getGift_id());
//			giftInfo.setGiftName(gift.getGoods_name());
//			giftInfo.setGiftNum(gift.getSku_num());
//			giftInfo.setGiftValue(StringUtils.isEmpty(gift.getGift_value())?"0":gift.getGift_value());
//			giftItem.add(giftInfo);
//		}
		return giftItem;
	}

	public void setGiftInfo(List<Gift> giftInfo) {
		GiftInfo = giftInfo;
	}

	public List<GoodsAtt> getGoodsAttInfo() {
		List<GoodsAtt> GoodsAtts = CommonDataFactory.getInstance().getGoodsAttrInfo(notNeedReqStrOrderId);
		return GoodsAtts;
	}

	public void setGoodsAttInfo(List<GoodsAtt> goodsAttInfo) {
		GoodsAttInfo = goodsAttInfo;
	}

	public List<Fee> getFeeInfo() {
		List<Fee> fees = CommonDataFactory.getInstance().getFeeInfo(notNeedReqStrOrderId);
		return fees;
	}

	public void setFeeInfo(List<Fee> feeInfo) {
		FeeInfo = feeInfo;
	}

	public UserInfomation getUserInfo() {
		String user_type = CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId, AttrConsts.IS_OLD);
		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		//如果是老用户订单，且证件类型为无需证件时，不变更入网信息
		if(EcsOrderConsts.IS_OLD_1.equals(user_type) && EcsOrderConsts.CERTI_TYPE_WXZ.equals(certi_type)){
			UserInfo = null;
		}
		else{
			UserInfo =new UserInfomation();
			UserInfo.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.CERT_ADDRESS));
			String certLoseTime = CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.CERT_FAILURE_TIME);
			if(null != certLoseTime && !"".equals(certLoseTime)){
				certLoseTime = certLoseTime.replaceAll("-", "");
				certLoseTime = certLoseTime.replaceAll(":", "");
				certLoseTime = certLoseTime.replaceAll(" ", "");
			}
			UserInfo.setCertLoseTime(certLoseTime);
			UserInfo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
			UserInfo.setCertType(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.CERTI_TYPE));
			UserInfo.setCustomerName(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME));
			UserInfo.setCustomerType(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.CUSTOMER_TYPE));
			UserInfo.setRefereeName(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.RECOMMENDED_NAME));
			UserInfo.setRefereeNum(CommonDataFactory.getInstance().getAttrFieldValue(
					notNeedReqStrOrderId, AttrConsts.RECOMMENDED_PHONE));
			UserInfo.setUserType(CommonDataFactory.getInstance().getZbValue(notNeedReqStrOrderId, AttrConsts.IS_OLD));
		}
		return UserInfo;
	}

	public void setUserInfo(UserInfomation userInfo) {
		UserInfo = userInfo;
	}

	public Develop getDevelopInfo() {
		DevelopInfo =new Develop();
		DevelopInfo.setDevelopCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE));
		DevelopInfo.setDevelopName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME));
		return DevelopInfo;
	}

	public void setDevelopInfo(Develop developInfo) {
		DevelopInfo = developInfo;
	}

	public Operator getOperatorInfo() {
		OperatorInfo =new Operator();
		String operatorID = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getLock_user_id();
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
		FlowInfo=new Flow();
		FlowInfo.setOperMode(EcsOrderConsts.ORDER_MODE_AUTO);
		FlowInfo.setOrderFlow(CommonDataFactory.getInstance().getOrderFlow(notNeedReqStrOrderId));
		return FlowInfo;
	}

	public void setFlowInfo(Flow flowInfo) {
		FlowInfo = flowInfo;
	}
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
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
		return "com.zte.unicomService.zb.notifyOrderInfo";
	}

}
