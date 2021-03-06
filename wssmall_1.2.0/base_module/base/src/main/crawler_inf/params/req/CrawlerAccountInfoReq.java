package params.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

public class CrawlerAccountInfoReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="总商订单ID（长订单号）",type="String",isNecessary="Y",desc="总商订单ID（长订单号）")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="模板类型ID",type="String",isNecessary="Y",desc="模板类型ID")
	private String tmplId;
	@ZteSoftCommentAnnotationParam(name="商户ID",type="String",isNecessary="Y",desc="商户ID")
	private String merchantId;
	@ZteSoftCommentAnnotationParam(name="用户标识",type="String",isNecessary="Y",desc="用户标识(默认为1)")
	private String userTag;
	@ZteSoftCommentAnnotationParam(name="上网卡号码、手机号码",type="String",isNecessary="N",desc="上网卡号码、手机号码")
	private String netCardNum;
	@ZteSoftCommentAnnotationParam(name="校验开户标识",type="String",isNecessary="Y",desc="校验开户标识(默认为0)")
	private String accountFlag;
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="N",desc="终端串号")
	private String resourcesCode;
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="Y",desc="证件类型(01:15位身份证,02:18位身份证)")
    private String certType;
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="证件号码")
    private String certNum;
	@ZteSoftCommentAnnotationParam(name="证件名称",type="String",isNecessary="Y",desc="证件名称")
    private String customerName;
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="Y",desc="证件地址")
    private String certAddress;
	@ZteSoftCommentAnnotationParam(name="买家联系人",type="String",isNecessary="Y",desc="买家联系人")
    private String contactPerson;
	@ZteSoftCommentAnnotationParam(name="买家联系电话",type="String",isNecessary="Y",desc="买家联系电话")
    private String contactPhone;
	@ZteSoftCommentAnnotationParam(name="USIM卡号",type="String",isNecessary="N",desc="USIM卡号")
    private String iccId;
	@ZteSoftCommentAnnotationParam(name="是否成卡",type="String",isNecessary="Y",desc="是否成卡(1:成卡 0:不是成卡)")
    private String isOldCard;
	@ZteSoftCommentAnnotationParam(name="收货地址",type="String",isNecessary="Y",desc="收货地址")
    private String postAddr;
	@ZteSoftCommentAnnotationParam(name="商品实例ID",type="String",isNecessary="Y",desc="商品实例ID")
    private String goodInstId;
	@ZteSoftCommentAnnotationParam(name="订单金额",type="String",isNecessary="Y",desc="订单金额")
    private String incomeMoney;
	@ZteSoftCommentAnnotationParam(name="预开户号码",type="String",isNecessary="Y",desc="预开户号码")
    private String preNum;
	@ZteSoftCommentAnnotationParam(name="身份证校验是否继续开户",type="String",isNecessary="Y",desc="身份证校验是否继续开户(false：不继续，true：继续 。注为true则继续调用开户校验方法)")
    private String confirmCheckCert;
	@ZteSoftCommentAnnotationParam(name="是否是主副卡新带新且副卡带有号码的订单",type="String",isNecessary="Y",desc="是否是主副卡新带新且副卡带有号码的订单  1：是 0：不是")
    private String isZFKNewOrder;
	@ZteSoftCommentAnnotationParam(name="是否是老用户便捷换卡订单",type="String",isNecessary="Y",desc="是否是老用户便捷换卡订单 1:是  0:不是")
    private String isCardChange;
	@ZteSoftCommentAnnotationParam(name="是否需要校验发展人编码",type="String",isNecessary="Y",desc="是否需要校验发展人编码 true:校验,false:不校验")
    private boolean needCheckRecommend;
	@ZteSoftCommentAnnotationParam(name="发展人编码",type="String",isNecessary="N",desc="发展人编码")
    private String developCode;
    //终端调拨标识
	@ZteSoftCommentAnnotationParam(name="终端调拨标识",type="String",isNecessary="N",desc="终端调拨标识 1：调拨 0：不调拨")
    private String allocationFlag;
	@ZteSoftCommentAnnotationParam(name="开户流水",type="String",isNecessary="Y",desc="开户流水")
	private String manualOrderNo;
    
   /* //共享套餐手动开户页面的标识1：是 0：不是
    private String ismanual4Combine;
    //共享套餐标识
    private String isCombineOrder;
    //是否是主副卡新带新手动卡户的页面  1：是 0：不是
    private String ismanual4zfkv;
    */
    private String isNorthSixOrder;
    
    
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.checkZbAccountInfo";
	}


	public String getIsNorthSixOrder() {
		return isNorthSixOrder;
	}

	public void setIsNorthSixOrder(String isNorthSixOrder) {
		this.isNorthSixOrder = isNorthSixOrder;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getUserTag() {
		userTag = "1";
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public String getNetCardNum() {
		return netCardNum;
	}

	public void setNetCardNum(String netCardNum) {
		this.netCardNum = netCardNum;
	}

	public String getAccountFlag() {
		accountFlag = "0";
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getResourcesCode() {
		return resourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getIccId() {
		return iccId;
	}

	public void setIccId(String iccId) {
		this.iccId = iccId;
	}

	public String getIsOldCard() {
		isOldCard = "0";
		return isOldCard;
	}

	public void setIsOldCard(String isOldCard) {
		this.isOldCard = isOldCard;
	}

	public String getPostAddr() {
		return postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getGoodInstId() {
		return goodInstId;
	}

	public void setGoodInstId(String goodInstId) {
		this.goodInstId = goodInstId;
	}

	public String getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(String incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public String getPreNum() {
		return preNum;
	}

	public void setPreNum(String preNum) {
		this.preNum = preNum;
	}

	public String getConfirmCheckCert() {
		if(StringUtils.isBlank(confirmCheckCert)){
			confirmCheckCert = "false";
		}
		return confirmCheckCert;
	}

	public void setConfirmCheckCert(String confirmCheckCert) {
		this.confirmCheckCert = confirmCheckCert;
	}

	public String getIsZFKNewOrder() {
		return isZFKNewOrder;
	}

	public void setIsZFKNewOrder(String isZFKNewOrder) {
		this.isZFKNewOrder = isZFKNewOrder;
	}

	public String getCertAddress() {
		return certAddress;
	}

	public void setCertAddress(String certAddress) {
		this.certAddress = certAddress;
	}

	public String getIsCardChange() {
		return isCardChange;
	}

	public void setIsCardChange(String isCardChange) {
		this.isCardChange = isCardChange;
	}

	public boolean isNeedCheckRecommend() {
		needCheckRecommend = false;
		return needCheckRecommend;
	}

	public void setNeedCheckRecommend(boolean needCheckRecommend) {
		this.needCheckRecommend = needCheckRecommend;
	}

	public String getDevelopCode() {
		return developCode;
	}

	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/*public String getIsmanual4Combine() {
		return ismanual4Combine;
	}

	public void setIsmanual4Combine(String ismanual4Combine) {
		this.ismanual4Combine = ismanual4Combine;
	}

	public String getIsCombineOrder() {
		return isCombineOrder;
	}

	public void setIsCombineOrder(String isCombineOrder) {
		this.isCombineOrder = isCombineOrder;
	}

	public String getIsmanual4zfkv() {
		return ismanual4zfkv;
	}

	public void setIsmanual4zfkv(String ismanual4zfkv) {
		this.ismanual4zfkv = ismanual4zfkv;
	}*/
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAllocationFlag() {
		return allocationFlag;
	}

	public void setAllocationFlag(String allocationFlag) {
		this.allocationFlag = allocationFlag;
	}

	public String getManualOrderNo() {
		return manualOrderNo;
	}

	public void setManualOrderNo(String manualOrderNo) {
		this.manualOrderNo = manualOrderNo;
	}
	
}
