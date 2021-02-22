package zte.net.ecsord.params.oldsys.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;

/**
 * 同步订单信息到老订单系统对象
 * @author 吴家勇
 *
 */
public class NotifyOrderInfo2OldSysRequest extends ZteRequest {

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
	
	@ZteSoftCommentAnnotationParam(name="外部订单编号",type="String",isNecessary="Y",desc="origOrderId：外部订单编号")
	private String origOrderId;
	
	@ZteSoftCommentAnnotationParam(name="发展人编码",type="String",isNecessary="N",desc="DevelopCode：发展人编码")
	private String developCode;

	@ZteSoftCommentAnnotationParam(name="发展人名称",type="String",isNecessary="N",desc="DevelopName：发展人名称")
	private String developName;
	
	@ZteSoftCommentAnnotationParam(name="发展人归属",type="String",isNecessary="N",desc="developAttribution：发展人归属")
	private String developAttribution;
	
	@ZteSoftCommentAnnotationParam(name="收货人姓名",type="String",isNecessary="N",desc="收货人姓名")
	private String consigneeName;
	
	@ZteSoftCommentAnnotationParam(name="收货人手机",type="String",isNecessary="N",desc="收货人手机")
	private String telMobileNum;
	
	@ZteSoftCommentAnnotationParam(name="收货人电话",type="String",isNecessary="N",desc="收货人电话")
	private String telNum;
	
	@ZteSoftCommentAnnotationParam(name="收货地址",type="String",isNecessary="N",desc="收货地址")
	private String carryArdress;
	
	@ZteSoftCommentAnnotationParam(name="取件人",type="String",isNecessary="N",desc="取件人")
	private String carryPerson;
	
	@ZteSoftCommentAnnotationParam(name="取件人电话",type="String",isNecessary="N",desc="取件人电话")
	private String carryPersonTel;
	
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="N",desc="配送方式")
	private String carryMode;
	
	@ZteSoftCommentAnnotationParam(name="物流公司",type="String",isNecessary="N",desc="物流公司")
	private String postId;
	
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="N",desc="物流单号")
	private String postNo;
	
	@ZteSoftCommentAnnotationParam(name="商品包类型",type="String",isNecessary="Y",desc="商品包类型")
	private String packType;
	
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="商品名称")
	private String productName;
	
	@ZteSoftCommentAnnotationParam(name="大小卡",type="String",isNecessary="N",desc="大小卡")
	private String bsCard;
	
	@ZteSoftCommentAnnotationParam(name="所选号码",type="String",isNecessary="N",desc="所选号码")
	private String mobileTel;
	
	@ZteSoftCommentAnnotationParam(name="活动类型",type="String",isNecessary="N",desc="活动类型")
	private String buyMode;
	
	@ZteSoftCommentAnnotationParam(name="合约期",type="String",isNecessary="N",desc="合约期")
	private String agreementDate;
	
	@ZteSoftCommentAnnotationParam(name="是否托收",type="String",isNecessary="N",desc="是否托收，01 否，02 是")
	private String isCollection;
	
	@ZteSoftCommentAnnotationParam(name="托收银行名称",type="String",isNecessary="N",desc="托收银行名称")
	private String bankName;
	
	@ZteSoftCommentAnnotationParam(name="托收银行账号",type="String",isNecessary="N",desc="托收银行账号")
	private String collectionAccount;
	
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="N",desc="终端串号")
	private String seriesNum;
	
	@ZteSoftCommentAnnotationParam(name="卡串号",type="String",isNecessary="N",desc="卡串号")
	private String whiteCardNum;
	
	@ZteSoftCommentAnnotationParam(name="资费方式",type="String",isNecessary="N",desc="资费方式")
	private String firstFeeType;
	
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="N",desc="证件类型")
	private String buyerCardType;
	
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="N",desc="证件号码")
	private String buyerCardNo;
	
	@ZteSoftCommentAnnotationParam(name="证件姓名",type="String",isNecessary="N",desc="证件姓名")
	private String buyerName;
	
	@ZteSoftCommentAnnotationParam(name="证件有效期",type="String",isNecessary="N",desc="证件有效期")
	private String cardExp;
	
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="N",desc="证件地址")
	private String cardAdress;
	
	@ZteSoftCommentAnnotationParam(name="开户时间",type="String",isNecessary="N",desc="开户时间")
	private String openAccountTime;
	
	@ZteSoftCommentAnnotationParam(name="是否自动化异常",type="String",isNecessary="N",desc="是否自动化异常，1异常，0恢复正常")
	private String autoOrderExceptionFlag;
	
	@ZteSoftCommentAnnotationParam(name="自动化异常类型",type="String",isNecessary="N",desc="自动化异常类型")
	private String autoExceptionType;
	
	@ZteSoftCommentAnnotationParam(name="自动化异常描述",type="String",isNecessary="N",desc="自动化异常描述")
	private String autoExceptionDesc;
	
	@ZteSoftCommentAnnotationParam(name="是否手工异常",type="String",isNecessary="N",desc="是否手工异常, 1异常，0恢复正常")
	private String handExceptionFlag;
	
	@ZteSoftCommentAnnotationParam(name="手工异常类型",type="String",isNecessary="N",desc="手工异常类型")
	private String handExceptionType;
	
	@ZteSoftCommentAnnotationParam(name="自动化异常描述",type="String",isNecessary="N",desc="自动化异常描述")
	private String handExceptionDesc;
	
	@ZteSoftCommentAnnotationParam(name="当前环节",type="String",isNecessary="Y",desc="当前环节")
	private String flowNode;
	
	@ZteSoftCommentAnnotationParam(name="目标环节",type="String",isNecessary="Y",desc="目标环节")
	private String tarFlowNode;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}
	
	public String getReqId(){
		return reqId;
	}
	public void setReqId(String reqId){
		this.reqId = reqId;
	}
	
	public String getReqType(){
		return InfConst.ORDER_FLOW_SUBMMIT;
	}
	public void setReqType(String reqType){
		this.reqType = reqType;
	}
	
	public String getReqTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		reqTime = dateFormat.format(now);
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	
	public String getOrigOrderId() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOut_tid();
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}
	
	public String getDevelopCode() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);
	}
	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}
	public String getDevelopName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME);
	}
	public void setDevelopName(String developName) {
		this.developName = developName;
	}
	
	public String getDevelopAttribution(){
		return developAttribution;
	}
	public void setDevelopAttribution(String developAttribution){
		this.developAttribution = developAttribution;
	}
	
	public String getConsigneeName(){
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName){
		this.consigneeName = consigneeName;
	}
	
	public String getTelMobileNum(){
		return telMobileNum;
	}
	public void setTelMobileNum(String telMobileNum){
		this.telMobileNum = telMobileNum;
	}
	
	public String getTelNum(){
		return telNum;
	}
	public void setTelNum(String telNum){
		this.telNum = telNum;
	}
	public String getCarryArdress(){
		return carryArdress;
	}
	public void setCarryArdress(String carryArdress){
		this.carryArdress = carryArdress;
	}
	
	public String getCarryPerson(){
		return carryPerson;
	}
	public void setCarryPerson(String carryPerson){
		this.carryPerson = carryPerson;
	}
	
	public String getCarryPersonTel(){
		return carryPersonTel;
	}
	public void setCarryPersonTel(String carryPersonTel){
		this.carryPersonTel = carryPersonTel;
	}
	
	public String getCarryMode(){
		return carryMode;
	}
	public void setCarryMode(String carryMode){
		this.carryMode = carryMode;
	}
	
	public String getPostId(){
		return postId;
	}
	public void setPostId(String postId){
		this.postId = postId;
	}
	
	public String getPostNo(){
		return postNo;
	}
	public void setPostNo(String postNo){
		this.postNo = postNo;
	}
	
	public String getPackType(){
		return packType;
	}
	public void setPackType(String packType){
		this.packType = packType;
	}
	
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	
	public String getBsCard(){
		return bsCard;
	}
	public void setBsCard(String bsCard){
		this.bsCard = bsCard;
	}
	
	public String getMobileTel(){
		return mobileTel;
	}
	public void setMobileTel(String mobileTel){
		this.mobileTel = mobileTel;
	}
	
	public String getBuyMode(){
		return buyMode;
	}
	public void setBuyMode(String buyMode){
		this.buyMode = buyMode;
	}
	
	public String getAgreementDate(){
		return agreementDate;
	}
	public void setAgreementDate(String agreementDate){
		this.agreementDate = agreementDate;
	}
	
	public String getIsCollection(){
		return isCollection;
	}
	public void setIsCollection(String isCollection){
		this.isCollection = isCollection;
	}
	
	public String getBankName(){
		return bankName;
	}
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	
	public String getCollectionAccount(){
		return collectionAccount;
	}
	public void setCollectionAccount(String collectionAccount){
		this.collectionAccount = collectionAccount;
	}
	
	public String getSeriesNum(){
		return seriesNum;
	}
	public void setSeriesNum(String seriesNum){
		this.seriesNum = seriesNum;
	}
	
	public String getWhiteCardNum(){
		return whiteCardNum;
	}
	public void setWhiteCardNum(String whiteCardNum){
		this.whiteCardNum = whiteCardNum;
	}
	
	public String getFirstFeeType(){
		return firstFeeType;
	}
	public void setFirstFeeType(String firstFeeType){
		this.firstFeeType = firstFeeType;
	}
	
	public String getBuyerCardType(){
		return buyerCardType;
	}
	public void setBuyerCardType(String buyerCardType){
		this.buyerCardType = buyerCardType;
	}
	
	public String getBuyerCardNo(){
		return buyerCardNo;
	}
	public void setBuyerCardNo(String buyerCardNo){
		this.buyerCardNo = buyerCardNo;
	}
	
	public String getBuyerName(){
		return buyerName;
	}
	public void setBuyerName(String buyerName){
		this.buyerName = buyerName;
	}
	
	public String getCardExp(){
		return cardExp;
	}
	public void setCardExp(String cardExp){
		this.cardExp = cardExp;
	}
	
	public String getCardAdress(){
		return cardAdress;
	}
	public void setCardAdress(String cardAdress){
		this.cardAdress = cardAdress;
	}
	
	public String getOpenAccountTime(){
		return openAccountTime;
	}
	public void setOpenAccountTime(String openAccountTime){
		this.openAccountTime = openAccountTime;
	}
	
	public String getAutoOrderExceptionFlag(){
		return autoOrderExceptionFlag;
	}
	public void setAutoOrderExceptionFlag(String autoOrderExceptionFlag){
		this.autoOrderExceptionFlag = autoOrderExceptionFlag;
	}
	
	public String getAutoExceptionType(){
		return autoExceptionType;
	}
	public void setAutoExceptionType(String autoExceptionType){
		this.autoExceptionType = autoExceptionType;
	}
	
	public String getAutoExceptionDesc(){
		return autoExceptionDesc;
	}
	public void setAutoExceptionDesc(String autoExceptionDesc){
		this.autoExceptionDesc = autoExceptionDesc;
	}
	
	public String getHandExceptionFlag(){
		return handExceptionFlag;
	}
	public void setHandExceptionFlag(String handExceptionFlag){
		this.handExceptionFlag = handExceptionFlag;
	}
	
	public String getHandExceptionType(){
		return handExceptionType;
	}
	public void setHandExceptionType(String handExceptionType){
		this.handExceptionType = handExceptionType;
	}
	
	public String getHandExceptionDesc(){
		return handExceptionDesc;
	}
	public void setHandExceptionDesc(String handExceptionDesc){
		this.handExceptionDesc = handExceptionDesc;
	}
	
	public String getFlowNode(){
		return flowNode;
	}
	public void setFlowNode(String flowNode){
		this.flowNode = flowNode;
	}
	
	public String getTarFlowNode(){
		return tarFlowNode;
	}
	public void setTarFlowNode(String tarFlowNode){
		this.tarFlowNode = tarFlowNode;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.oldsys.notifyOrderinfo";
	}
}
