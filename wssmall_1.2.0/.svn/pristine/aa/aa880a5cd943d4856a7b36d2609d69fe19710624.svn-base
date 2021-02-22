package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ZbOrderDeliveryReq extends ZteRequest<ZteResponse>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5950911687650082904L;

	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="外部订单ID",type="String",isNecessary="Y",desc="外部订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="Y",desc="配送方式")
	private String logisticType;//1：自行配送，2：第三方配送， 3：客户自提
	@ZteSoftCommentAnnotationParam(name="物流公司ID",type="String",isNecessary="Y",desc="物流公司ID1001：邮政EMS，1002:顺风")
	private String companyId;
	@ZteSoftCommentAnnotationParam(name="物流公司名称  ",type="String",isNecessary="Y",desc="物流公司名称  ，顺丰速运；邮政EMS")
	private String companyName;
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="物流单号")
	private String lgtsOrder;
	@ZteSoftCommentAnnotationParam(name="物流备注",type="String",isNecessary="Y",desc="物流备注")
	private String lgtsRemark;
	@ZteSoftCommentAnnotationParam(name="是否需要填写回单单号",type="String",isNecessary="Y",desc="是否需要填写回单单号0：不需要 1：需要")
	private String isNeedLgtsRtn;
	@ZteSoftCommentAnnotationParam(name="回单备注信息",type="String",isNecessary="Y",desc="回单备注信息")
	private String lgtsRtnOrderDesc;
	@ZteSoftCommentAnnotationParam(name="回单单号",type="String",isNecessary="Y",desc="回单单号")
	private String lgtsRtnOrder;
	@ZteSoftCommentAnnotationParam(name="iccid",type="String",isNecessary="Y",desc="iccid")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="Y",desc="终端串号")
	private String resourceCode;
	@ZteSoftCommentAnnotationParam(name="配送类型",type="String",isNecessary="Y",desc="配送类型 paperBill 纸质运单，elecBill 电子运单")
	private String billType="elecBill";
	@ZteSoftCommentAnnotationParam(name="自行配送员工工号",type="String",isNecessary="Y",desc="自行配送员工工号")
	private String staffId;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.zbOrderDelivery";
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

	public String getLgtsOrder() {
		return lgtsOrder;
	}

	public void setLgtsOrder(String lgtsOrder) {
		this.lgtsOrder = lgtsOrder;
	}

	public String getLgtsRemark() {
		return lgtsRemark;
	}

	public void setLgtsRemark(String lgtsRemark) {
		this.lgtsRemark = lgtsRemark;
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

	public String getLgtsRtnOrder() {
		return lgtsRtnOrder;
	}

	public void setLgtsRtnOrder(String lgtsRtnOrder) {
		this.lgtsRtnOrder = lgtsRtnOrder;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	

}
