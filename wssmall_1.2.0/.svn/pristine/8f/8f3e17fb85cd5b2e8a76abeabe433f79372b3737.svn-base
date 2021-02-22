package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.ZteResponse;

public class ZbBackfillLogisticsReq extends ZteRequest<ZteResponse>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2361573940126555232L;
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="外部订单ID",type="String",isNecessary="Y",desc="外部订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="Y",desc="配送方式")
	private String logisticType;//1：自行配送，2：第三方配送， 3：客户自提
	@ZteSoftCommentAnnotationParam(name="配送类型",type="String",isNecessary="Y",desc="配送类型 paperBill 纸质运单，elecBill 电子运单")
	private String billType="paperBill";
	@ZteSoftCommentAnnotationParam(name="物流公司ID",type="String",isNecessary="Y",desc="物流公司ID1001：邮政EMS，1002:顺风")
	private String companyId;
	@ZteSoftCommentAnnotationParam(name="物流公司名称  ",type="String",isNecessary="Y",desc="物流公司名称  ，顺丰速运；邮政EMS")
	private String companyName;
	@ZteSoftCommentAnnotationParam(name="是否需要填写回单单号",type="String",isNecessary="Y",desc="是否需要填写回单单号0：不需要 1：需要")
	private String isNeedLgtsRtn;
	@ZteSoftCommentAnnotationParam(name="回单备注信息",type="String",isNecessary="Y",desc="回单备注信息")
	private String lgtsRtnOrderDesc;
	@ZteSoftCommentAnnotationParam(name="回单单号",type="String",isNecessary="Y",desc="回单单号")
	private String lgtsRtnOrder;
	@ZteSoftCommentAnnotationParam(name="物流备注",type="String",isNecessary="Y",desc="物流备注")
	private String lgtsRemark;
	@ZteSoftCommentAnnotationParam(name="是否保价",type="String",isNecessary="Y",desc="是否保价 0:不保价  1：保价")
	private String insureFlag;
	@ZteSoftCommentAnnotationParam(name="保价金额",type="String",isNecessary="Y",desc="保价金额")
	private String insureMoney;
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="物流单号")
	private String lgtsOrder;
	@ZteSoftCommentAnnotationParam(name="发货/退货省份编码",type="String",isNecessary="Y",desc="发货/退货省份编码")
	private String provinceCode;
	@ZteSoftCommentAnnotationParam(name="发货/退货城市编码",type="String",isNecessary="Y",desc="发货/退货城市编码")
	private String cityCode;
	@ZteSoftCommentAnnotationParam(name="发货/退货区县编码",type="String",isNecessary="Y",desc="发货/退货区县编码")
	private String districtCode;
	@ZteSoftCommentAnnotationParam(name="发货/退货地址",type="String",isNecessary="Y",desc="发货/退货省份地址")
	private String address;
	@ZteSoftCommentAnnotationParam(name="发货/退货联系人",type="String",isNecessary="Y",desc="发货/退货联系人")
	private String contact;
	@ZteSoftCommentAnnotationParam(name="发货/退货手机号码",type="String",isNecessary="Y",desc="发货/退货手机号码")
	private String telphone;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.zbBackfillLogistics";
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLogisticType() {
		return logisticType;
	}

	public void setLogisticType(String logisticType) {
		this.logisticType = logisticType;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIsNeedLgtsRtn() {
		return isNeedLgtsRtn;
	}

	public void setIsNeedLgtsRtn(String isNeedLgtsRtn) {
		this.isNeedLgtsRtn = isNeedLgtsRtn;
	}

	public String getLgtsRtnOrderDesc() {
		return lgtsRtnOrderDesc;
	}

	public void setLgtsRtnOrderDesc(String lgtsRtnOrderDesc) {
		this.lgtsRtnOrderDesc = lgtsRtnOrderDesc;
	}

	public String getLgtsRemark() {
		return lgtsRemark;
	}

	public void setLgtsRemark(String lgtsRemark) {
		this.lgtsRemark = lgtsRemark;
	}

	public String getInsureFlag() {
		return insureFlag;
	}

	public void setInsureFlag(String insureFlag) {
		this.insureFlag = insureFlag;
	}

	public String getInsureMoney() {
		return insureMoney;
	}

	public void setInsureMoney(String insureMoney) {
		this.insureMoney = insureMoney;
	}

	public String getLgtsRtnOrder() {
		return lgtsRtnOrder;
	}

	public void setLgtsRtnOrder(String lgtsRtnOrder) {
		this.lgtsRtnOrder = lgtsRtnOrder;
	}

	public String getLgtsOrder() {
		return lgtsOrder;
	}

	public void setLgtsOrder(String lgtsOrder) {
		this.lgtsOrder = lgtsOrder;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

}
