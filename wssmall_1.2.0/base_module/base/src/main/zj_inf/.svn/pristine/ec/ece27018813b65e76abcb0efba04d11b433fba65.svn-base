package zte.net.ecsord.params.nd.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftParamBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.nd.resp.NotifyOrderInfoNDResponse;
import zte.net.ecsord.params.nd.vo.Carry;
import zte.net.ecsord.params.nd.vo.Gift;
import zte.net.ecsord.params.nd.vo.Good;
import zte.net.ecsord.params.nd.vo.Order;
import zte.net.ecsord.params.wms.vo.GiftInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


/**
 * 
 * @author sguo 
 * 订单系统将订单信息通知南都
 * 
 */
public class NotifyOrderInfoNDRequset extends ZteRequest<NotifyOrderInfoNDResponse> {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="activeNo：访问流水")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name="接入ID",type="String",isNecessary="Y",desc="reqId：由物流公司提供   南都：nan-fang-chuan-mei")
	private String reqId="nan-fang-chuan-mei";

	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="reqType：固定值：wl_syn_order")
	private String reqType="wl_syn_order";

	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="reqTime：格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;	
	
	@ZteSoftCommentAnnotationParam(name="订单信息",type="String",isNecessary="Y",desc="orderInfo：订单信息")
	private List<Order> orderInfo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="orderId：订单编号")
	private String notNeedReqStrorderId;	
	

	public String getNotNeedReqStrorderId() {
		return notNeedReqStrorderId;
	}

	public void setNotNeedReqStrorderId(String notNeedReqStrorderId) {
		this.notNeedReqStrorderId = notNeedReqStrorderId;
	}

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dtf.format(new Date());
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public List<Order> getOrderInfo() {
		orderInfo=new ArrayList<Order>();
		Order orderInformation=new Order();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrorderId);
		orderInformation.setOrigOrderId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.OUT_TID));
		orderInformation.setOrderId(notNeedReqStrorderId);
		orderInformation.setOrderOrigin(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.ORDER_ORIGIN));
		orderInformation.setOrderCity(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.ORDER_CITY_CODE));
		orderInformation.setOrderCreateTime(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.TID_TIME));
		orderInformation.setCreateTime(orderTree.getOrderBusiRequest().getCreate_time());
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderInformation.setLightTime(sdf.format(now));
		orderInformation.setExtOrderStatus(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.PLATFORM_STATUS));
		orderInformation.setBuyerMessage(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.BUYER_MESSAGE));
		orderInformation.setSellerMessage(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.SELLER_MESSAGE));
		orderInformation.setMemo(orderTree.getOrderBusiRequest().getRemark());
		orderInformation.setPayMode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.PAY_METHOD));
		
		String pay_status=StringUtil.toString(orderTree.getOrderBusiRequest().getPay_status());//支付状态
		String nd_pay_status = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ND_PAY_STATUS, pay_status);
		orderInformation.setPayStatus(nd_pay_status);//getPay_Status()
		orderInformation.setPayDatetime(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.PAY_FIN_TIME));
		
		
		orderInformation.setAdjustFee(Integer.toString(0));
		
		Double orderAmount=0.0;//订单总金额（分）
		Double paymoney=0.0;//用户支付的金额（分）
		Double discountFee=0.0;//优惠金额（分）
		if(orderTree.getOrderBusiRequest().getOrder_amount()!=null){
			orderAmount	=orderTree.getOrderBusiRequest().getOrder_amount()*100;
    	}
    	if(orderTree.getOrderBusiRequest().getPaymoney()!=null){
    		paymoney=orderTree.getOrderBusiRequest().getPaymoney()*100;
    	}
    	if(orderTree.getOrderBusiRequest().getDiscount()!=null){
    		discountFee=orderTree.getOrderBusiRequest().getDiscount()*100;
    	}
    	orderInformation.setOrderFee(Double.toString(orderAmount));
		orderInformation.setPayFee(Double.toString(paymoney));
		orderInformation.setDiscountFee(Double.toString(discountFee));
		
	    //收货信息
		List<Carry> Carrys=new ArrayList<Carry>();
		Carry Carry=new Carry();
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.SENDING_TYPE);
		String nd_shipping_type = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ND_SHIPPING_TYPE, sending_type);
		Carry.setCarryMode(nd_shipping_type);
		Carry.setConsigneeName(orderTree.getOrderBusiRequest().getShip_name());
		Carry.setReceiveAddress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.SHIP_ADDR));
		Carry.setReceiveMobileNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.REVEIVER_MOBILE));
		Carry.setReceiveTelNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.REFERENCE_PHONE));
		Carry.setReceiveZip(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.SHIP_ZIP));
		Carrys.add(Carry);
		orderInformation.setCarryInfo(Carrys);
		//礼品
		List<Gift> giftItem = new ArrayList<Gift>();
		List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance()
				.getOrderTree(notNeedReqStrorderId).getGiftInfoBusiRequests();
		for (AttrGiftInfoBusiRequest gift : gifts) {
			if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())){//实物赠品，非实物赠品不传给南都
				Gift giftInfo = new Gift();
				giftInfo.setGiftName(gift.getGoods_name());
				giftInfo.setGiftText(gift.getGoods_desc());
				giftInfo.setGiftNum(StringUtil.toString(gift.getSku_num()));
				giftInfo.setGiftFace(gift.getGift_value());
				giftItem.add(giftInfo);
			}
		}
		
		
		orderInformation.setGiftInfo(giftItem);
		//货品信息  多条记录拼成一条----还有部分值不对
		List<Good> Goods=new ArrayList<Good>();
		Good Good=new Good();
		Good.setPackageId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.OUT_PACKAGE_ID));
		Good.setPackageName(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrorderId, SpecConsts.TYPE_ID_10002,null, SpecConsts.PLAN_TITLE));
		Good.setAgreementDate(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrorderId, SpecConsts.TYPE_ID_10001,  null, SpecConsts.PACKAGE_LIMIT));
		Good.setBscard(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.WHITE_CART_TYPE));
		Good.setBuyerCardAddress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.CERT_ADDRESS));
		Good.setBuyerCardExp(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.CERT_FAILURE_TIME));
		Good.setBuyerCardNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.CERT_CARD_NUM));
		Good.setBuyerCardType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.CERTI_TYPE));
		Good.setBuyerName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.CERT_CARD_NAME));
		Good.setBuyMode(CommonDataFactory.getInstance().getActivitySpec(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.DISCOUNT_ID), SpecConsts.PMT_TYPE));
		Good.setFirstFeeType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.FIRST_PAYMENT));
		Good.setGradeDeposits("");
		Good.setHandsetColor(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.COLOR_CODE));
		Good.setHandsetType(CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrorderId, null, SpecConsts.GOODS_SN));
		Good.setPackType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.PACK_TYPE));
		Good.setMobileTel(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrorderId, AttrConsts.PHONE_NUM));
		Good.setProductName(CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrorderId, null, SpecConsts.GOODS_NAME));//实物
		Goods.add(Good);
		orderInformation.setGoodInfo(Goods);
		
		orderInfo.add(orderInformation);
		
		return orderInfo;
	}

	public void setOrderInfo(List<Order> orderInfo) {
		this.orderInfo = orderInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.nd.notifyorderinfo";
	}

}
