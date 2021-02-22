package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

public class CrawlerDeliveryInfoReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="总商订单ID（长订单号）",type="String",isNecessary="Y",desc="总商订单ID（长订单号）")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="总商订单编号(短订单号对应云订单系统外部订单号)",type="String",isNecessary="Y",desc="总商订单编号(短订单号对应云订单系统外部订单号)")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="总商模板ID",type="String",isNecessary="Y",desc="模板ID")
	private String tmplId;
	@ZteSoftCommentAnnotationParam(name="总商商户Id",type="String",isNecessary="Y",desc="商户Id")
	private String merchantId;
	@ZteSoftCommentAnnotationParam(name="总商旧省份编码",type="String",isNecessary="Y",desc="旧省份编码")
	private String oldProvinceId;
	@ZteSoftCommentAnnotationParam(name="总商旧地市编码",type="String",isNecessary="Y",desc="旧地市编码")
	private String oldCityId;
	@ZteSoftCommentAnnotationParam(name="总商省份编码",type="String",isNecessary="Y",desc="总商省份编码")
	private String provinceId;
	@ZteSoftCommentAnnotationParam(name="总商地市编码",type="String",isNecessary="Y",desc="总商地市编码")
    private String cityId;
	@ZteSoftCommentAnnotationParam(name="总商地区编码",type="String",isNecessary="Y",desc="总商地区编码")
    private String districtId;
	@ZteSoftCommentAnnotationParam(name="总商配送地址",type="String",isNecessary="Y",desc="总商配送地址")
    private String postAddr;
	@ZteSoftCommentAnnotationParam(name="总商配送方式 ",type="String",isNecessary="Y",desc="总商配送方式 (01:快递 02:自提)")
    private String dispachCode;
    //总商配送时间类型(01:不限时间送货(工作日、双休日、节假日均可送货) 02:只工作日送货(周一到周五工作时间) 03:只有双休日、节假日送货 04:延时配送(2017-01-10))
	@ZteSoftCommentAnnotationParam(name="总商配送时间类型 ",type="String",isNecessary="Y",desc="总商配送时间类型")
    private String dlvTypeCode;
	@ZteSoftCommentAnnotationParam(name="总商支付方式编码",type="String",isNecessary="Y",desc="总商支付方式编码 01:在线支付，02:货到付款")
    private String payWayCode;
	@ZteSoftCommentAnnotationParam(name="总商物流公司",type="String",isNecessary="Y",desc="总商物流公司( 不限物流 1001:邮政EMS 1002 顺丰速运)")
    private String logisticCode;
    //总商配送时间（跟配送时间类型匹配  工作日、双休日、节假日均可送货,周一到周五工作时间,只有双休日、节假日送货,2017-01-10）
	@ZteSoftCommentAnnotationParam(name="总商配送时间",type="String",isNecessary="Y",desc="总商配送时间")
    private String delayTime;
	@ZteSoftCommentAnnotationParam(name="总商自提点编码",type="String",isNecessary="N",desc="总商自提点编码")
    private String selfFetchAddrId;
	@ZteSoftCommentAnnotationParam(name="总商自提点类型",type="String",isNecessary="N",desc="总商自提点类型 0和1")
    private String selfType;
	@ZteSoftCommentAnnotationParam(name="总商自提点名称",type="String",isNecessary="N",desc="总商自提点名称")
    private String selfAddrName;
	@ZteSoftCommentAnnotationParam(name="总商联系姓名",type="String",isNecessary="Y",desc="总商联系姓名")
    private String deliverySelfName;
	@ZteSoftCommentAnnotationParam(name="总商联系电话",type="String",isNecessary="Y",desc="总商联系电话")
    private String deliverySelfPhone;
	@ZteSoftCommentAnnotationParam(name="是否手动开户",type="String",isNecessary="N",desc="是否手动开户 1：是  |（0或者不填）：否")
	private String isManual;
    
    
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.updateZbDeliveryInfo";
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOldProvinceId() {
		return oldProvinceId;
	}

	public void setOldProvinceId(String oldProvinceId) {
		this.oldProvinceId = oldProvinceId;
	}

	public String getOldCityId() {
		return oldCityId;
	}

	public void setOldCityId(String oldCityId) {
		this.oldCityId = oldCityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getPostAddr() {
		return postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getDispachCode() {
		return dispachCode;
	}

	public void setDispachCode(String dispachCode) {
		this.dispachCode = dispachCode;
	}

	public String getDlvTypeCode() {
		return dlvTypeCode;
	}

	public void setDlvTypeCode(String dlvTypeCode) {
		this.dlvTypeCode = dlvTypeCode;
	}

	public String getPayWayCode() {
		/*String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_TYPE);
		if(EcsOrderConsts.PAY_TYPE_ZXZF.equals(pay_type)){
			payWayCode = "01";
		}*/
		payWayCode = "01";
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public String getSelfFetchAddrId() {
		return selfFetchAddrId;
	}

	public void setSelfFetchAddrId(String selfFetchAddrId) {
		this.selfFetchAddrId = selfFetchAddrId;
	}

	public String getSelfType() {
		return selfType;
	}

	public void setSelfType(String selfType) {
		this.selfType = selfType;
	}

	public String getSelfAddrName() {
		return selfAddrName;
	}

	public void setSelfAddrName(String selfAddrName) {
		this.selfAddrName = selfAddrName;
	}

	public String getDeliverySelfName() {
		return deliverySelfName;
	}

	public void setDeliverySelfName(String deliverySelfName) {
		this.deliverySelfName = deliverySelfName;
	}

	public String getDeliverySelfPhone() {
		return deliverySelfPhone;
	}

	public void setDeliverySelfPhone(String deliverySelfPhone) {
		this.deliverySelfPhone = deliverySelfPhone;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
	
}
