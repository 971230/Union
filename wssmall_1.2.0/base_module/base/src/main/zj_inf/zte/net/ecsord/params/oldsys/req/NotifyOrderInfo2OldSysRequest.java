package zte.net.ecsord.params.oldsys.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.oldsys.resp.NotifyOrderInfo2OldSysResponse;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 同步订单信息到老订单系统对象
 * 
 * @author 吴家勇
 * 
 */
public class NotifyOrderInfo2OldSysRequest extends ZteRequest<NotifyOrderInfo2OldSysResponse> {

	private static final long serialVersionUID = 4771236338396087639L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "流水号", type = "String", isNecessary = "Y", desc = "访问流水号")
	private String activeNo;

	@ZteSoftCommentAnnotationParam(name = "接入ID", type = "String", isNecessary = "Y", desc = "接入ID")
	private String reqId;

	@ZteSoftCommentAnnotationParam(name = "接入类型", type = "String", isNecessary = "Y", desc = "接入类型：固定值：order_flow_submmit")
	private String reqType;

	@ZteSoftCommentAnnotationParam(name = "接入时间", type = "String", isNecessary = "Y", desc = "接入时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;

	@ZteSoftCommentAnnotationParam(name = "外部订单编号", type = "String", isNecessary = "Y", desc = "origOrderId：外部订单编号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name = "发展人编码", type = "String", isNecessary = "N", desc = "DevelopCode：发展人编码")
	private String developCode;

	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "DevelopName：发展人名称")
	private String developName;

	@ZteSoftCommentAnnotationParam(name = "发展人归属", type = "String", isNecessary = "N", desc = "developAttribution：发展人归属")
	private String developAttribution;

	@ZteSoftCommentAnnotationParam(name = "收货人姓名", type = "String", isNecessary = "N", desc = "收货人姓名")
	private String consigneeName;

	@ZteSoftCommentAnnotationParam(name = "收货人手机", type = "String", isNecessary = "N", desc = "收货人手机")
	private String telMobileNum;

	@ZteSoftCommentAnnotationParam(name = "收货人电话", type = "String", isNecessary = "N", desc = "收货人电话")
	private String telNum;

	@ZteSoftCommentAnnotationParam(name = "收货地址", type = "String", isNecessary = "N", desc = "收货地址")
	private String carryAddress;

	@ZteSoftCommentAnnotationParam(name = "取件人", type = "String", isNecessary = "N", desc = "取件人")
	private String carryPerson;

	@ZteSoftCommentAnnotationParam(name = "取件人电话", type = "String", isNecessary = "N", desc = "取件人电话")
	private String carryPersonTel;

	@ZteSoftCommentAnnotationParam(name = "配送方式", type = "String", isNecessary = "N", desc = "配送方式")
	private String carryMode;

	@ZteSoftCommentAnnotationParam(name = "物流公司", type = "String", isNecessary = "N", desc = "物流公司")
	private String postId;

	@ZteSoftCommentAnnotationParam(name = "物流单号", type = "String", isNecessary = "N", desc = "物流单号")
	private String postNo;

	@ZteSoftCommentAnnotationParam(name = "商品包类型", type = "String", isNecessary = "Y", desc = "商品包类型")
	private String packType;

	@ZteSoftCommentAnnotationParam(name = "商品名称", type = "String", isNecessary = "Y", desc = "商品名称")
	private String productName;

	@ZteSoftCommentAnnotationParam(name = "大小卡", type = "String", isNecessary = "N", desc = "大小卡")
	private String bsCard;

	@ZteSoftCommentAnnotationParam(name = "所选号码", type = "String", isNecessary = "N", desc = "所选号码")
	private String mobileTel;

	@ZteSoftCommentAnnotationParam(name = "活动类型", type = "String", isNecessary = "N", desc = "活动类型")
	private String buyMode;

	@ZteSoftCommentAnnotationParam(name = "合约期", type = "String", isNecessary = "N", desc = "合约期")
	private String agreementDate;

	@ZteSoftCommentAnnotationParam(name = "是否托收", type = "String", isNecessary = "N", desc = "是否托收，01 否，02 是")
	private String isCollection;

	@ZteSoftCommentAnnotationParam(name = "托收银行名称", type = "String", isNecessary = "N", desc = "托收银行名称")
	private String bankName;

	@ZteSoftCommentAnnotationParam(name = "托收银行账号", type = "String", isNecessary = "N", desc = "托收银行账号")
	private String collectionAccount;

	@ZteSoftCommentAnnotationParam(name = "终端串号", type = "String", isNecessary = "N", desc = "终端串号")
	private String seriesNum;

	@ZteSoftCommentAnnotationParam(name = "卡串号", type = "String", isNecessary = "N", desc = "卡串号")
	private String whiteCardNum;

	@ZteSoftCommentAnnotationParam(name = "资费方式", type = "String", isNecessary = "N", desc = "资费方式")
	private String firstFeeType;

	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "证件类型")
	private String buyerCardType;

	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "证件号码")
	private String buyerCardNo;

	@ZteSoftCommentAnnotationParam(name = "证件姓名", type = "String", isNecessary = "N", desc = "证件姓名")
	private String buyerName;

	@ZteSoftCommentAnnotationParam(name = "证件有效期", type = "String", isNecessary = "N", desc = "证件有效期")
	private String cardExp;

	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "N", desc = "证件地址")
	private String cardAdress;

	@ZteSoftCommentAnnotationParam(name = "开户时间", type = "String", isNecessary = "N", desc = "开户时间")
	private String openAccountTime;

	@ZteSoftCommentAnnotationParam(name = "是否自动化异常", type = "String", isNecessary = "N", desc = "是否自动化异常，1异常，0恢复正常")
	private String autoOrderExceptionFlag;

	@ZteSoftCommentAnnotationParam(name = "自动化异常类型", type = "String", isNecessary = "N", desc = "自动化异常类型")
	private String autoExceptionType;

	@ZteSoftCommentAnnotationParam(name = "自动化异常描述", type = "String", isNecessary = "N", desc = "自动化异常描述")
	private String autoExceptionDesc;

	@ZteSoftCommentAnnotationParam(name = "是否手工异常", type = "String", isNecessary = "N", desc = "是否手工异常, 1异常，0恢复正常")
	private String handExceptionFlag;

	@ZteSoftCommentAnnotationParam(name = "手工异常类型", type = "String", isNecessary = "N", desc = "手工异常类型")
	private String handExceptionType;

	@ZteSoftCommentAnnotationParam(name = "自动化异常描述", type = "String", isNecessary = "N", desc = "自动化异常描述")
	private String handExceptionDesc;

	@ZteSoftCommentAnnotationParam(name = "当前环节", type = "String", isNecessary = "Y", desc = "当前环节")
	private String flowNode;

	@ZteSoftCommentAnnotationParam(name = "目标环节", type = "String", isNecessary = "Y", desc = "目标环节")
	private String tarFlowNode;

	@ZteSoftCommentAnnotationParam(name = "保价费", type = "String", isNecessary = "N", desc = "保价费（分）")
	private String protectFee;

	@ZteSoftCommentAnnotationParam(name = "运费", type = "String", isNecessary = "N", desc = "运费（分）")
	private String caryyFee;

	@ZteSoftCommentAnnotationParam(name = "处理意见", type = "String", isNecessary = "N", desc = "处理意见")
	private String proceeMsg;

	@ZteSoftCommentAnnotationParam(name = "提交类型", type = "String", isNecessary = "N", desc = "提交类型：1、环节提交；2、自动化异常操作；3、手工异常操作；4、伪自动化异常；5:恢复异常")
	private String processType;

	@ZteSoftCommentAnnotationParam(name = "自动化异常发起方", type = "String", isNecessary = "N", desc = "ZB:总部系统;XSC:新商城系统;WMS:WMS系统;SENRUI:森锐系统;SPGL:商品管理;EOP:EOP系统;UNIMORDER:省分能力平台;CARD:远程写卡系统;SF:顺丰")
	private String autoExceptionSystem;
	
	@ZteSoftCommentAnnotationParam(name = "操作工号", type = "String", isNecessary = "N", desc = "操作工号")
	private String operId;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			isZbOrder = true;
		}
		this.setActiveNo(CommonDataFactory.getInstance().getActiveNo(isZbOrder));
		return this.activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		this.setReqId(InfConst.OLD_ORDER_SYS_REQ_ID);
		return this.reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		this.setReqType(InfConst.ORDER_FLOW_SUBMMIT);
		return this.reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		this.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return this.reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getOrigOrderId() {
		this.setOrigOrderId(getOrderTree().getOrderExtBusiRequest().getOut_tid());
		return this.origOrderId;
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}

	public String getDevelopCode() {
		this.setDevelopCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE));
		return this.developCode;
	}

	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}

	public String getDevelopName() {
		this.setDevelopName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME));
		return this.developName;
	}

	public void setDevelopName(String developName) {
		this.developName = developName;
	}

	public String getDevelopAttribution() {
		this.setDevelopAttribution(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_MARK));
		return this.developAttribution;
	}

	public void setDevelopAttribution(String developAttribution) {
		this.developAttribution = developAttribution;
	}

	public String getConsigneeName() {
		this.setConsigneeName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME));
		return this.consigneeName;

	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getTelMobileNum() {
		this.setTelMobileNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
		return this.telMobileNum;
	}

	public void setTelMobileNum(String telMobileNum) {
		this.telMobileNum = telMobileNum;
	}

	public String getTelNum() {
		this.setTelNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE));
		return this.telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getCarryAddress() {
		this.setCarryAddress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_ADDR));
		return this.carryAddress;
	}

	public void setCarryAddress(String carryAddress) {
		this.carryAddress = carryAddress;
	}

	public String getCarryPerson() {
		this.setCarryPerson(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CARRY_PERSON));
		return this.carryPerson;
	}

	public void setCarryPerson(String carryPerson) {
		this.carryPerson = carryPerson;
	}

	public String getCarryPersonTel() {
		this.setCarryPersonTel(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CARRY_PERSON_MOBILE));
		return this.carryPersonTel;
	}

	public void setCarryPersonTel(String carryPersonTel) {
		this.carryPersonTel = carryPersonTel;
	}

	public String getCarryMode() {
		// ??
		String carryMode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SENDING_TYPE);
		this.carryMode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.OS_SHIPPING_TYPE, carryMode);
		return this.carryMode;
	}

	public void setCarryMode(String carryMode) {
		this.carryMode = carryMode;
	}

	public String getPostId() {
		String shippingCompany = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		this.postId = CommonDataFactory.getInstance().getLogiCompanyCode(shippingCompany);
		return this.postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostNo() {
		this.setPostNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO));
		return this.postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getPackType() {
		// this.setPackType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.GOODS_TYPE));
		this.setPackType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PACK_TYPE));
		return this.packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getProductName() {
		this.setProductName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODSNAME));
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBsCard() {
		String whiteCartType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.WHITE_CART_TYPE);
		this.bsCard = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.OS_WHITE_CART_TYPE, whiteCartType);
		return this.bsCard;
	}

	public void setBsCard(String bsCard) {
		this.bsCard = bsCard;
	}

	public String getMobileTel() {
		this.setMobileTel(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM));
		return this.mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public String getBuyMode() {
		String active_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_TYPE);
		this.buyMode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.OS_ACTIVE_TYPE, active_type);
		return this.buyMode;
	}

	public void setBuyMode(String buyMode) {
		this.buyMode = buyMode;
	}

	public String getAgreementDate() {
		 this.setAgreementDate(CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		 SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT));
//		final String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
//		this.setAgreementDate(CommonDataFactory.getInstance().getNumberSpec(phoneNum, SpecConsts.NUMERO_PERIOD));
		return this.agreementDate;
	}

	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}

	public String getIsCollection() {
		isCollection = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.COLLECTION);
		if("1".equals(isCollection)){
			isCollection = "02";
		}
		else if("0".equals(isCollection)){
			isCollection = "01";
		}
		return this.isCollection;
	}

	public void setIsCollection(String isCollection) {
		this.isCollection = isCollection;
	}

	public String getBankName() {
		this.setBankName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BANK_CODE));
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCollectionAccount() {
		this.setCollectionAccount(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.COLLECTION_ACCOUNT));
		return this.collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public String getSeriesNum() {
		this.setSeriesNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
		return this.seriesNum;
	}

	public void setSeriesNum(String seriesNum) {
		this.seriesNum = seriesNum;
	}

	public String getWhiteCardNum() {
		this.setWhiteCardNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID));
		return this.whiteCardNum;
	}

	public void setWhiteCardNum(String whiteCardNum) {
		this.whiteCardNum = whiteCardNum;
	}

	public String getFirstFeeType() {
		String firstPayment = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT);
		this.firstFeeType = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.OS_OFFER_EFF_TYPE, firstPayment);
		return this.firstFeeType;
	}

	public void setFirstFeeType(String firstFeeType) {
		this.firstFeeType = firstFeeType;
	}

	public String getBuyerCardType() {
		String certiType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		this.buyerCardType = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.OS_CERTI_TYPE, certiType);
		return this.buyerCardType;
	}

	public void setBuyerCardType(String buyerCardType) {
		this.buyerCardType = buyerCardType;
	}

	public String getBuyerCardNo() {
		this.setBuyerCardNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
		return this.buyerCardNo;
	}

	public void setBuyerCardNo(String buyerCardNo) {
		this.buyerCardNo = buyerCardNo;
	}

	public String getBuyerName() {
		this.setBuyerName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME));
		return this.buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getCardExp() {
		this.setCardExp(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_FAILURE_TIME));
		return this.cardExp;
	}

	public void setCardExp(String cardExp) {
		this.cardExp = cardExp;
	}

	public String getCardAdress() {
		this.setCardAdress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ADDRESS));
		return this.cardAdress;
	}

	public void setCardAdress(String cardAdress) {
		this.cardAdress = cardAdress;
	}

	public String getOpenAccountTime() {
		this.setOpenAccountTime(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_ACCOUNT_TIME));
		return this.openAccountTime;
	}

	public void setOpenAccountTime(String openAccountTime) {
		this.openAccountTime = openAccountTime;
	}

	public String getAutoOrderExceptionFlag() {
//		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(getOrderTree().getOrderExtBusiRequest().getAbnormal_type())) {
//			this.setAutoOrderExceptionFlag(EcsOrderConsts.IS_DEFAULT_VALUE);
//		} else {
//			this.setAutoOrderExceptionFlag(EcsOrderConsts.NO_DEFAULT_VALUE);
//		}
		if(this.autoOrderExceptionFlag==null){
			this.autoOrderExceptionFlag = "";
		}
		return this.autoOrderExceptionFlag;
	}

	public void setAutoOrderExceptionFlag(String autoOrderExceptionFlag) {
		this.autoOrderExceptionFlag = autoOrderExceptionFlag;
	}

	public String getAutoExceptionType() {
//		this.setAutoExceptionType(getOrderTree().getOrderExtBusiRequest().getException_type());
		if(this.autoExceptionType==null){
			this.autoExceptionType = "";
		}
		return this.autoExceptionType;
	}

	public void setAutoExceptionType(String autoExceptionType) {
		this.autoExceptionType = autoExceptionType;
	}

	public String getAutoExceptionDesc() {
//		this.setAutoExceptionType(getOrderTree().getOrderExtBusiRequest().getException_desc());
		if(this.autoExceptionDesc == null){
			this.autoExceptionDesc = "";
		}
		return this.autoExceptionDesc;
	}

	public void setAutoExceptionDesc(String autoExceptionDesc) {
		this.autoExceptionDesc = autoExceptionDesc;
	}

	public String getHandExceptionFlag() {
//		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(getOrderTree().getOrderExtBusiRequest().getAbnormal_type())) {
//			this.setHandExceptionFlag(EcsOrderConsts.IS_DEFAULT_VALUE);
//		} else {
//			this.setHandExceptionFlag(EcsOrderConsts.NO_DEFAULT_VALUE);
//		}
		return this.handExceptionFlag;
	}

	public void setHandExceptionFlag(String handExceptionFlag) {
		this.handExceptionFlag = handExceptionFlag;
	}

	public String getHandExceptionType() {
//		this.setHandExceptionType(getOrderTree().getOrderExtBusiRequest().getException_type());
		return this.handExceptionType;
	}

	public void setHandExceptionType(String handExceptionType) {
		this.handExceptionType = handExceptionType;
	}

	public String getHandExceptionDesc() {
//		this.setHandExceptionDesc(getOrderTree().getOrderExtBusiRequest().getException_desc());
		return this.handExceptionDesc;
	}

	public void setHandExceptionDesc(String handExceptionDesc) {
		this.handExceptionDesc = handExceptionDesc;
	}

	public String getFlowNode() {
//		this.setFlowNode(getOrderTree().getOrderExtBusiRequest().getFlow_trace_id());
		return this.flowNode;
	}

	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
	}

	public String getTarFlowNode() {
		return tarFlowNode;
	}

	public void setTarFlowNode(String tarFlowNode) {
		this.tarFlowNode = tarFlowNode;
	}

	public String getProtectFee() {
		Double protectFeeD = getOrderTree().getOrderDeliveryBusiRequests().get(0).getProtect_price();
		if(protectFeeD==null){
			this.protectFee = "0";
		}
		else{
			this.protectFee = String.valueOf(protectFeeD);
		}
		return this.protectFee;
	}

	public void setProtectFee(String protectFee) {
		this.protectFee = protectFee;
	}

	public String getCaryyFee() {
		Double caryyFeeD = getOrderTree().getOrderDeliveryBusiRequests().get(0).getShipping_amount();
		if(caryyFeeD==null){
			this.caryyFee = "0";
		}
		else{
			this.caryyFee = String.valueOf(caryyFeeD);
		}
		return this.caryyFee;
	}

	public void setCaryyFee(String caryyFee) {
		this.caryyFee = caryyFee;
	}

	public String getAutoExceptionSystem() {
		return autoExceptionSystem;
	}

	public void setAutoExceptionSystem(String autoExceptionSystem) {
		this.autoExceptionSystem = autoExceptionSystem;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProceeMsg() {
		if(proceeMsg==null){
			proceeMsg = "";
		}
		return proceeMsg;
	}

	public void setProceeMsg(String proceeMsg) {
		this.proceeMsg = proceeMsg;
	}

	public String getOperId() {
		String operId = getOrderTree().getOrderExtBusiRequest().getLock_user_id();
		if (StringUtils.isEmpty(operId)) {
			if (StringUtils.isEquals(EcsOrderConsts.OPER_MODE_ZD, getOrderTree().getOrderExtBusiRequest().getOrder_model())) {
				operId = "root"; // 自动化订单未锁定的处理环节统一传root用户通知旧系统
			}
		}
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.oldsys.notifyOrderinfo";
	}

	private OrderTreeBusiRequest getOrderTree() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
	}
}
